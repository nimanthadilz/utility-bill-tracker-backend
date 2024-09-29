package com.nimantha.utilitybilltracker.services;

import com.nimantha.utilitybilltracker.dto.CreateUtilityDTO;
import com.nimantha.utilitybilltracker.dto.UtilityDTO;
import com.nimantha.utilitybilltracker.models.User;
import com.nimantha.utilitybilltracker.models.Utility;
import com.nimantha.utilitybilltracker.models.UtilityRepository;
import com.nimantha.utilitybilltracker.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilityService {
    private final UtilityRepository utilityRepository;
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
}
