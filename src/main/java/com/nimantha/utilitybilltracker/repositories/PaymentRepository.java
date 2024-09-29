package com.nimantha.utilitybilltracker.repositories;

import com.nimantha.utilitybilltracker.models.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
}
