package com.nimantha.utilitybilltracker.services;

import com.nimantha.utilitybilltracker.dto.CreateBillDTO;
import com.nimantha.utilitybilltracker.models.Bill;
import com.nimantha.utilitybilltracker.models.User;
import com.nimantha.utilitybilltracker.models.Utility;
import com.nimantha.utilitybilltracker.models.UtilityRepository;
import com.nimantha.utilitybilltracker.repositories.BillRepository;
import com.nimantha.utilitybilltracker.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private final UtilityRepository utilityRepository;
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
}
