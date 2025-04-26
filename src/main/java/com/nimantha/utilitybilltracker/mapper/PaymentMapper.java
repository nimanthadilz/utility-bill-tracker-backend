package com.nimantha.utilitybilltracker.mapper;

import com.nimantha.utilitybilltracker.dto.PaymentDTO;
import com.nimantha.utilitybilltracker.models.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "billId", source = "bill.id")
    PaymentDTO paymentToPaymentDTO(Payment entity);
}
