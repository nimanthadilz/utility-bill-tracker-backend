package com.nimantha.utilitybilltracker.services;

import com.nimantha.utilitybilltracker.dto.CreateUtilityDTO;
import com.nimantha.utilitybilltracker.dto.UpdateUtilityRequest;
import com.nimantha.utilitybilltracker.dto.UtilityDTO;
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

@Service
@RequiredArgsConstructor
public class UtilityService {
    private final UtilityRepository utilityRepository;
    private final BillRepository billRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UtilityService.class);

    public void createUtility(CreateUtilityDTO createUtilityDTO) {
        User user = userRepository.findById(createUtilityDTO.username())
                                  .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        logger.info("Create utility request: {}", createUtilityDTO);
        Utility utility = Utility.builder()
                                 .name(createUtilityDTO.name())
                                 .accountNo(createUtilityDTO.accountNumber())
                                 .user(user)
                                 .build();
        utilityRepository.save(utility);
        logger.info("Created utility successfully: {}", createUtilityDTO);
    }

    public UtilityDTO getUtilityById(Long id) {
        Utility utility = utilityRepository.findById(id)
                                           .orElseThrow(() -> new EntityNotFoundException("Utility id not found: " + id));
        return new UtilityDTO(
                utility.getId(),
                utility.getName(),
                utility.getAccountNo()
        );
    }

    public Page<UtilityDTO> getUtilities(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UtilityDTO> utilityList = utilityRepository.findAll(pageable)
                                                        .map((utility) -> new UtilityDTO(utility.getId(),
                                                                                         utility.getName(),
                                                                                         utility.getAccountNo()));
        return utilityList;
    }

    @Transactional
    public void deleteUtility(Long id) {
        if (!utilityRepository.existsById(id)) {
            throw new EntityNotFoundException("Utility id not found: " + id);
        }
        paymentRepository.deleteByUtilityId(id);
        billRepository.deleteByUtilityId(id);
        utilityRepository.deleteById(id);
    }

    public void updateUtility(Long id, UpdateUtilityRequest updateUtilityRequest) {
        Utility utility = utilityRepository.findById(id)
                                           .orElseThrow(() -> new EntityNotFoundException("Utility id not found: " + id));

        String accountNo = updateUtilityRequest.getAccountNo();
        String name = updateUtilityRequest.getName();

        if (accountNo != null) {
            utility.setAccountNo(accountNo);
        }

        if (name != null) {
            utility.setName(name);
        }

        utilityRepository.save(utility);
    }
}
