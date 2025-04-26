package com.nimantha.utilitybilltracker.mapper;

import com.nimantha.utilitybilltracker.dto.PaymentDTO;
import com.nimantha.utilitybilltracker.dto.UtilityDTO;
import com.nimantha.utilitybilltracker.models.Payment;
import com.nimantha.utilitybilltracker.models.Utility;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UtilityMapper {
    @Mapping(target = "accountNumber", source = "accountNo")
    UtilityDTO utilityToUtilityDTO(Utility entity);
}
