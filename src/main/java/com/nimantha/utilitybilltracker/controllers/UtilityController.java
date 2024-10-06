package com.nimantha.utilitybilltracker.controllers;

import com.nimantha.utilitybilltracker.dto.*;
import com.nimantha.utilitybilltracker.services.UtilityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/utility")
@RequiredArgsConstructor
@Validated
public class UtilityController {
    private final UtilityService utilityService;

    @PostMapping
    public ResponseEntity<ResponseDTO> createUtility(@RequestBody @Valid CreateUtilityRequest createUtilityRequest) {
        UserDTO userDTO = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CreateUtilityDTO createUtilityDTO = new CreateUtilityDTO(
                createUtilityRequest.getName(),
                createUtilityRequest.getAccountNo(),
                userDTO.username()
        );
        utilityService.createUtility(createUtilityDTO);
        return new ResponseEntity<>(new ResponseDTO("Created utility successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public UtilityDTO getUtility(@PathVariable String id) {
        return utilityService.getUtilityById(Long.valueOf(id));
    }

    @GetMapping
    public CustomPageDTO<UtilityDTO> getAllUtilities(@RequestParam(defaultValue = "0") @Min(value = 0, message =
            "must be a non-negative integer") int page,
                                                     @RequestParam(defaultValue = "10") @Min(value = 1, message =
                                                             "must be a positive integer") int size) {
        return new CustomPageDTO<>(utilityService.getUtilities(page, size));
    }
}
