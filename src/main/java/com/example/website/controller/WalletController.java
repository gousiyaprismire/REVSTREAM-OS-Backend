package com.example.website.controller;

import com.example.website.dto.NewOrder;
import com.example.website.dto.VerifyPayment;
import com.example.website.dto.WalletTransactionDTO;
import com.example.website.entity.Wallet;
import com.example.website.entity.WalletTransaction;
import com.example.website.repository.WalletTransactionRepository;
import com.example.website.service.WalletService;
import com.razorpay.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/wallet")
public class WalletController {

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    private final WalletService walletService;
    private final WalletTransactionRepository transactionRepository;

    public WalletController(WalletService walletService,WalletTransactionRepository transactionRepository){
        this.walletService=walletService;
        this.transactionRepository=transactionRepository;
    }


    @PostMapping("/order-add-money")
    public ResponseEntity<?> orderRequest(
            Authentication authentication,
            @RequestBody NewOrder order
    ) {
        Long userId = (Long) authentication.getCredentials();

        Order rpOrder = walletService.createAddMoneyOrder(userId, order.getAmount());

        return ResponseEntity.ok(
                Map.of("orderId",rpOrder.get("id"),
                        "amount",order.getAmount(),
                        "currency",rpOrder.get("currency"),
                        "key",razorpayKeyId
                )
        );
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<?> verifyOrder(
            Authentication authentication,
            @RequestBody VerifyPayment payment
    ){
        Long userId = (Long) authentication.getCredentials();
        walletService.verifyPaymentAndAddMoney(userId,payment.razorpayOrderId,payment.razorpayPaymentId,payment.razorpaySignature,payment.amount);
        return ResponseEntity.ok(Map.of("message","ok"));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withDrawMoney(
            Authentication authentication,
            @RequestBody Map<?,?> body
    ){
        Long userId = (Long) authentication.getCredentials();
        Wallet wallet =walletService.getWallet(userId);
        if (wallet == null){
            return ResponseEntity.notFound().build();
        }
        Double amount= (Double) body.get("amount");

        if(!body.containsKey("amount")){
            return ResponseEntity.badRequest().body(Map.of("message","invalid amount"));
        }

        if(amount > wallet.getBalance()){
            return ResponseEntity.badRequest().body(Map.of("message","insufficient balance"));
        }

        walletService.withdrawMoneyRequest(wallet, amount);
        return ResponseEntity.ok(body);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllTransactions(Authentication authentication) {

        Long userId = (Long) authentication.getCredentials();

        Wallet wallet = walletService.getWallet(userId);
        List<WalletTransaction> transactions =
                transactionRepository.findByWallet(wallet);

        List<WalletTransactionDTO> txns =
                transactions.stream()
                        .map(t -> mapTransaction(t, userId))
                        .toList();

        return ResponseEntity.ok(
                Map.of(
                        "wallet", wallet,
                        "transactions", txns
                )
        );
    }


    private WalletTransactionDTO mapTransaction(
            WalletTransaction t,
            Long currentUserId
    ) {
        String direction;
        String description;
        Long counterpartyId = null;
        Long taskId = t.getTask() != null ? t.getTask().getId() : null;
        String taskName = t.getTask() != null ? t.getTask().getTitle() : null;

        switch (t.getType()) {

            case ADD -> {
                direction = "CREDIT";
                description = "Money added to wallet";
            }

            case TASK_LOCK -> {
                direction = "DEBIT";
                description = "Money locked for task #" + taskId +"  "+taskName ;
            }

            case TASK_RELEASE -> {
                boolean isSender =
                        t.getTransferFrom() != null &&
                                t.getTransferFrom().getId().equals(currentUserId);

                direction = isSender ? "DEBIT" : "CREDIT";

                counterpartyId = isSender
                        ? t.getTransferTo().getId()
                        : t.getTransferFrom().getId();

                description = isSender
                        ? "Payment sent for task #" + taskId +"  "+taskName
                        : "Payment received for task #" + taskId+"  "+taskName;
            }

            case WITHDRAW -> {
                direction = "DEBIT";
                description = "Money withdrawn from wallet";
            }

            default -> {
                direction = "UNKNOWN";
                description = "Unknown transaction";
            }
        }

        return new WalletTransactionDTO(
                t.getId(),
                t.getType(),
                t.getAmount(),
                t.getStatus(),
                t.getCreatedAt(),
                direction,
                description,
                taskId,
                counterpartyId
        );
    }

}
