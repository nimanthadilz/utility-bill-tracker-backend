package com.nimantha.utilitybilltracker.repositories;

import com.nimantha.utilitybilltracker.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("delete from Payment where bill.utility.id = :id")
    @Modifying
    void deleteByUtilityId(Long id);

    @Query("delete from Payment where bill.id = :id")
    @Modifying
    void deleteByBillId(Long id);
}
