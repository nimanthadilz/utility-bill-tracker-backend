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
@RequestMapping("/api/v1/utilities")
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
    public UtilityDTO getUtility(@PathVariable Long id) {
        return utilityService.getUtilityById(id);
    }

    @GetMapping
    public CustomPageDTO<UtilityDTO> getAllUtilities(@RequestParam(defaultValue = "0") @Min(value = 0, message =
            "must be a non-negative integer") int page,
                                                     @RequestParam(defaultValue = "10") @Min(value = 1, message =
                                                             "must be a positive integer") int size) {
        return new CustomPageDTO<>(utilityService.getUtilities(page, size));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateUtility(@PathVariable Long id,
                                                     @RequestBody UpdateUtilityRequest updateUtilityRequest) {
        utilityService.updateUtility(id, updateUtilityRequest);
        return new ResponseEntity<>(new ResponseDTO("Updated utility successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteUtility(@PathVariable Long id) {
        utilityService.deleteUtility(id);
        return new ResponseEntity<>(new ResponseDTO("Deleted utility successfully"), HttpStatus.OK);
    }
}
