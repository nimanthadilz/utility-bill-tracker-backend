package com.nimantha.utilitybilltracker.services;

import com.nimantha.utilitybilltracker.dto.CreatePaymentRequest;
import com.nimantha.utilitybilltracker.dto.PaymentDTO;
import com.nimantha.utilitybilltracker.dto.UpdatePaymentRequest;
import com.nimantha.utilitybilltracker.mapper.PaymentMapper;
import com.nimantha.utilitybilltracker.models.Bill;
import com.nimantha.utilitybilltracker.models.Payment;
import com.nimantha.utilitybilltracker.repositories.BillRepository;
import com.nimantha.utilitybilltracker.repositories.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final BillRepository billRepository;
    private final PaymentRepository paymentRepository;
    private final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentMapper paymentMapper;

    public PaymentDTO createPayment(CreatePaymentRequest createPaymentRequest) {
        Bill bill = billRepository.findById(createPaymentRequest.getBillId())
                                  .orElseThrow(() -> new EntityNotFoundException("Bill not found"));
        logger.info("Create payment request: {}", createPaymentRequest);
        Payment payment = Payment.builder()
                                 .date(createPaymentRequest.getDate())
                                 .amount(createPaymentRequest.getAmount())
                                 .bill(bill)
                                 .build();
        Payment savedEntity = paymentRepository.save(payment);
        logger.info("Created payment successfully: {}", createPaymentRequest);
        return paymentMapper.paymentToPaymentDTO(savedEntity);
    }

    public PaymentDTO getBillById(Long id) {
        Payment payment = paymentRepository.findById(id)
                                           .orElseThrow(() -> new EntityNotFoundException("Payment id not found: " + id));
        return new PaymentDTO(
                payment.getId(),
                payment.getDate(),
                payment.getAmount(),
                payment.getBill().getId()
        );
    }

    public Page<PaymentDTO> getPayments(int pageNo, int pageSize, Long billId) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        if (billId != null) {
            return paymentRepository.findByBillId(pageable, billId).map((payment) -> new PaymentDTO(payment.getId(),
                                                                                                    payment.getDate(),
                                                                                                    payment.getAmount(),
                                                                                                    payment.getBill()
                                                                                                           .getId()));
        }
        return paymentRepository.findAll(pageable)
                                .map((payment) -> new PaymentDTO(payment.getId(),
                                                                 payment.getDate(),
                                                                 payment.getAmount(),
                                                                 payment.getBill().getId()));
    }

    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new EntityNotFoundException("Payment id not found: " + id);
        }
        paymentRepository.deleteById(id);
    }

    public void updatePayment(Long id, UpdatePaymentRequest updatePaymentRequest) {
        Payment payment = paymentRepository.findById(id)
                                           .orElseThrow(() -> new EntityNotFoundException("Payment id not found: " + id));

        LocalDate date = updatePaymentRequest.getDate();
        Double amount = updatePaymentRequest.getAmount();

        if (date != null) {
            payment.setDate(date);
        }

        if (amount != null) {
            payment.setAmount(amount);
        }
        paymentRepository.save(payment);
    }
}
