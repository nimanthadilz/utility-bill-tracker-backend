package com.nimantha.utilitybilltracker.controllers;

import com.nimantha.utilitybilltracker.dto.*;
import com.nimantha.utilitybilltracker.services.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Validated
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ResponseDTO> createPayment(@RequestBody @Valid CreatePaymentRequest createPaymentRequest) {
        paymentService.createPayment(createPaymentRequest);
        return new ResponseEntity<>(new ResponseDTO("Created payment successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public PaymentDTO getBill(@PathVariable Long id) {
        return paymentService.getBillById(id);
    }

    @GetMapping
    public CustomPageDTO<PaymentDTO> getAllPayments(@RequestParam(defaultValue = "0") @Min(value = 0, message =
            "must be a non-negative integer") int page,
                                                    @RequestParam(defaultValue = "10") @Min(value = 1, message =
                                                            "must be a positive integer") int size) {
        return new CustomPageDTO<>(paymentService.getPayments(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return new ResponseEntity<>(new ResponseDTO("Deleted payment successfully"), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO> updatePayment(@PathVariable Long id,
                                                     @RequestBody @Valid UpdatePaymentRequest updatePaymentRequest) {
        paymentService.updatePayment(id, updatePaymentRequest);
        return new ResponseEntity<>(new ResponseDTO("Updated payment successfully"), HttpStatus.OK);
    }
}
