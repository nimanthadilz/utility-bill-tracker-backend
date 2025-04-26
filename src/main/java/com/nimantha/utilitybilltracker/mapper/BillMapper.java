package com.nimantha.utilitybilltracker.mapper;

import com.nimantha.utilitybilltracker.dto.BillDTO;
import com.nimantha.utilitybilltracker.models.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillMapper {
    @Mapping(target = "utilityId", source = "utility.id")
    BillDTO billToBillDTO(Bill entity);
}
