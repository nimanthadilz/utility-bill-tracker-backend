package com.nimantha.utilitybilltracker.validation;

import com.nimantha.utilitybilltracker.dto.UpdateBillRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UpdateBillDateRangeValidator implements ConstraintValidator<ValidDateRange, UpdateBillRequest> {
    @Override
    public boolean isValid(UpdateBillRequest value, ConstraintValidatorContext context) {
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
