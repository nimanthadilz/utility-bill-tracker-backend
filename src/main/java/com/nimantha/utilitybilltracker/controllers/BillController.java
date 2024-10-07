package com.nimantha.utilitybilltracker.controllers;

import com.nimantha.utilitybilltracker.dto.*;
import com.nimantha.utilitybilltracker.services.BillService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bills")
@RequiredArgsConstructor
@Validated
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

    @GetMapping("/{id}")
    public BillDTO getBill(@PathVariable Long id) {
        return billService.getBillById(id);
    }

    @GetMapping
    public CustomPageDTO<BillDTO> getAllBills(@RequestParam(defaultValue = "0") @Min(value = 0, message =
            "must be a non-negative integer") int page,
                                              @RequestParam(defaultValue = "10") @Min(value = 1, message =
                                                      "must be a positive integer") int size) {
        return new CustomPageDTO<>(billService.getBills(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
        return new ResponseEntity<>(new ResponseDTO("Deleted bill successfully"), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateBill(@PathVariable Long id,
                                                  @RequestBody @Valid UpdateBillRequest updateBillRequest) {
        billService.updateBill(id, updateBillRequest);
        return new ResponseEntity<>(new ResponseDTO("Updated bill successfully"), HttpStatus.OK);
    }
}
