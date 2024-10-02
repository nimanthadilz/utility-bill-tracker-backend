package com.nimantha.utilitybilltracker.controllers;

import com.nimantha.utilitybilltracker.dto.*;
import com.nimantha.utilitybilltracker.services.UtilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/utility")
@RequiredArgsConstructor
public class UtilityController {
    private final UtilityService utilityService;

    @PostMapping
    public CreateUtilityResponse createUtility(@Valid @RequestBody CreateUtilityRequest createUtilityRequest) {
        UserDTO userDTO = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CreateUtilityDTO createUtilityDTO = new CreateUtilityDTO(
                createUtilityRequest.getName(),
                createUtilityRequest.getAccountNo(),
                userDTO.username()
        );
        utilityService.createUtility(createUtilityDTO);
        return new CreateUtilityResponse("Created utility successfully.");
    }

    @GetMapping("/{id}")
    public UtilityDTO getUtility(@PathVariable String id) {
        return utilityService.getUtilityById(Long.valueOf(id));
    }

    @GetMapping
    public CustomPageDTO<UtilityDTO> getAllUtilities(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return new CustomPageDTO<>(utilityService.getUtilities(page, size));
    }
}
