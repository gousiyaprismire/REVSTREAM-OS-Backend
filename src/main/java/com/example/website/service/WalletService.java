package com.example.website.service;

import com.example.website.entity.Task;
import com.example.website.entity.User;
import com.example.website.entity.Wallet;
import com.example.website.entity.WalletTransaction;
import com.example.website.repository.UserRepository;
import com.example.website.repository.WalletRepository;
import com.example.website.repository.WalletTransactionRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class WalletService {

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    private RazorpayClient razorpayClient;

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final WalletTransactionRepository transactionRepository;

    public WalletService(WalletRepository walletRepository,
                         WalletTransactionRepository transactionRepository,
                         UserRepository userRepository
    ) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }


    @PostConstruct
    public void init() throws Exception {
        this.razorpayClient = new RazorpayClient(
                razorpayKeyId,
                razorpayKeySecret
        );
    }

    public Map<String,Object> getWalletStatus(Long registrationId) {
        Wallet wallet = getWallet(registrationId);
        Map<String,Object> map = new HashMap<>();
        map.put("balance",wallet.getBalance());
        map.put("locked_balance",wallet.getLockedBalance());
        map.put("pendingWithdrawn",wallet.getPendingWithdrawn());
        return map;
    }




    public Wallet createWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setRegistration(user);
        wallet.setBalance(0.0);
        wallet.setLockedBalance(0.0);
        return walletRepository.save(wallet);
    }


    public Order createAddMoneyOrder(Long registrationId, Double amount) {

        try {
            Optional<User> optionalRegistration= userRepository.findById(registrationId);
            if (optionalRegistration.isEmpty()){
                throw new RuntimeException("user ID not found");
            }
            User user =optionalRegistration.get();
            Wallet wallet;
            Optional<Wallet> optionalWallet=walletRepository.findByUserId(registrationId);
            wallet = optionalWallet.orElseGet(() -> createWallet(user));

            WalletTransaction newtx=new WalletTransaction();
            newtx.setWallet(wallet);
            newtx.setType("ADD");
            newtx.setStatus("PENDING");
            newtx.setAmount(amount);
            WalletTransaction savedTransaction = transactionRepository.save(newtx);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "wallet_" + savedTransaction.getId());
            orderRequest.put("payment_capture", 1);

            Order order=razorpayClient.orders.create(orderRequest);

            newtx.setReceiptID("wallet_" + savedTransaction.getId());
            newtx.setRazorpayPaymentId(order.get("id"));
            transactionRepository.save(newtx);

            return order;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create Razorpay order", e);
        }
    }


    public void verifyPaymentAndAddMoney(
            Long registrationId,
            String razorpayOrderId,
            String razorpayPaymentId,
            String razorpaySignature,
            Double amount
    ) {

        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", razorpayOrderId);
            options.put("razorpay_payment_id", razorpayPaymentId);
            options.put("razorpay_signature", razorpaySignature);

            boolean isValid = Utils.verifyPaymentSignature(
                    options,
                    razorpayKeySecret
            );

            if (!isValid) {
                throw new RuntimeException("Invalid Razorpay signature");
            }

            WalletTransaction transaction=transactionRepository.findByRazorpayPaymentId(razorpayOrderId);
            transaction.setStatus("SUCCESS");
            transactionRepository.save(transaction);
            Wallet wallet = transaction.getWallet();
            wallet.setBalance(wallet.getBalance() + amount);
            walletRepository.save(wallet);
        } catch (Exception e) {
            throw new RuntimeException("Payment verification failed", e);
        }
    }

    public void lockMoneyForTask(
            Task task,
            long registrationId
    ){
        Wallet wallet=getWallet(registrationId);
        WalletTransaction transaction=new WalletTransaction();
        transaction.setWallet(wallet);
        transaction.setStatus("success");
        transaction.setAmount(task.getPrice());
        transaction.setType("task_lock");
        transactionRepository.save(transaction);
        wallet.setBalance(wallet.getBalance() - task.getPrice());
        wallet.setLockedBalance(wallet.getLockedBalance() + task.getPrice());
        walletRepository.save(wallet);
    }

    public double getBalance(long registrationId){
        return getWallet(registrationId).getBalance();
    }

    public void withdrawMoneyRequest(Wallet wallet, Double amount){
        wallet.setPendingWithdrawn(wallet.getPendingWithdrawn()+amount);
        wallet.setBalance(wallet.getBalance()-amount);
        walletRepository.save(wallet);

        WalletTransaction transaction=new WalletTransaction();
        transaction.setWallet(wallet);
        transaction.setStatus("PENDING");
        transaction.setType("WITHDRAW");
        transaction.setAmount(amount);
        transactionRepository.save(transaction);
    }

    public void withdrawStatusUpdate(Long transactionId,String status){
        Optional<WalletTransaction> opTransaction = transactionRepository.findById(transactionId);
        if (opTransaction.isEmpty()){
            return;
        }
        WalletTransaction transaction=opTransaction.get();
        transaction.setStatus(status);
        transactionRepository.save(transaction);
    }


    public Wallet getWallet(Long registrationId) {

        return walletRepository.findByUserId(registrationId)
                .orElseGet(() -> {
                    User user = userRepository.findById(registrationId)
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    return createWallet(user);
                });
    }
}
