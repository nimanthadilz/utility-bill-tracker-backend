package com.nimantha.utilitybilltracker.controllers;

import com.nimantha.utilitybilltracker.dto.CreateBillDTO;
import com.nimantha.utilitybilltracker.dto.CreateBillRequest;
import com.nimantha.utilitybilltracker.dto.ResponseDTO;
import com.nimantha.utilitybilltracker.dto.UserDTO;
import com.nimantha.utilitybilltracker.services.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bill")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    @PostMapping
    public ResponseEntity<ResponseDTO> createBill(@RequestBody @Valid CreateBillRequest createBillRequest) {
        UserDTO userDTO = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CreateBillDTO createBillDTO = new CreateBillDTO(
                userDTO.username(),
                createBillRequest.getUtilityId(),
                createBillRequest.getStartDate(),
                createBillRequest.getEndDate(),
                createBillRequest.getAmount()
        );
        billService.createBill(createBillDTO);
        return new ResponseEntity<>(new ResponseDTO("Created bill successfully"), HttpStatus.CREATED);
    }
}
