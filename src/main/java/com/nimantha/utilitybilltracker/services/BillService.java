package com.nimantha.utilitybilltracker.services;

import com.nimantha.utilitybilltracker.dto.BillDTO;
import com.nimantha.utilitybilltracker.dto.CreateBillDTO;
import com.nimantha.utilitybilltracker.dto.UpdateBillRequest;
import com.nimantha.utilitybilltracker.models.Bill;
import com.nimantha.utilitybilltracker.models.User;
import com.nimantha.utilitybilltracker.models.Utility;
import com.nimantha.utilitybilltracker.models.UtilityRepository;
import com.nimantha.utilitybilltracker.repositories.BillRepository;
import com.nimantha.utilitybilltracker.repositories.PaymentRepository;
import com.nimantha.utilitybilltracker.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private final UtilityRepository utilityRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(BillService.class);

    public void createBill(CreateBillDTO createBillDTO) {
        User user = userRepository.findById(createBillDTO.username())
                                  .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Utility utility = utilityRepository.findById(createBillDTO.utilityId())
                                           .orElseThrow(() -> new EntityNotFoundException("Utility not found"));
        logger.info("Create utility request: {}", createBillDTO);
        Bill bill = Bill.builder()
                        .utility(utility)
                        .amount(createBillDTO.amount())
                        .startDate(createBillDTO.startDate())
                        .endDate(createBillDTO.endDate())
                        .build();
        billRepository.save(bill);
        logger.info("Created bill successfully: {}", createBillDTO);
    }

    public BillDTO getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                                  .orElseThrow(() -> new EntityNotFoundException("Bill id not found: " + id));
        return new BillDTO(
                bill.getId(),
                bill.getStartDate(),
                bill.getEndDate(),
                bill.getAmount()
        );
    }

    public Page<BillDTO> getBills(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<BillDTO> billList = billRepository.findAll(pageable)
                                               .map((bill) -> new BillDTO(bill.getId(),
                                                                          bill.getStartDate(),
                                                                          bill.getEndDate(),
                                                                          bill.getAmount()));
        return billList;
    }

    @Transactional
    public void deleteBill(Long id) {
        if (!billRepository.existsById(id)) {
            throw new EntityNotFoundException("Bill id not found: " + id);
        }
        paymentRepository.deleteByBillId(id);
        billRepository.deleteById(id);
    }

    public void updateBill(Long id, UpdateBillRequest createBillRequest) {
        Bill bill = billRepository.findById(id)
                                  .orElseThrow(() -> new EntityNotFoundException("Bill id not found: " + id));

        LocalDate startDate = createBillRequest.getStartDate();
        LocalDate endDate = createBillRequest.getEndDate();
        Double amount = createBillRequest.getAmount();

        if (startDate != null) {
            bill.setStartDate(startDate);
        }

        if (endDate != null) {
            bill.setEndDate(endDate);
        }
        billRepository.save(bill);
    }

    public Page<BillDTO> getBillsByUtilityId(Long id, int page, int size) {
        if (!utilityRepository.existsById(id)) {
            throw new EntityNotFoundException("Utility id not found: " + id);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<BillDTO> billList = billRepository.findByUtilityId(id, pageable)
                                               .map(bill -> new BillDTO(bill.getUtility().getId(),
                                                                        bill.getStartDate(),
                                                                        bill.getEndDate(),
                                                                        bill.getAmount()));
        return billList;
    }
}
