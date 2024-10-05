package com.nimantha.utilitybilltracker.validation;

import com.nimantha.utilitybilltracker.dto.CreateBillRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, CreateBillRequest> {
    @Override
    public boolean isValid(CreateBillRequest value, ConstraintValidatorContext context) {
        if (value.getStartDate() != null && value.getEndDate() != null) {
            if (!value.getEndDate().isAfter(value.getStartDate())) {
                context.disableDefaultConstraintViolation();

                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                       .addPropertyNode("endDate")
                       .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
