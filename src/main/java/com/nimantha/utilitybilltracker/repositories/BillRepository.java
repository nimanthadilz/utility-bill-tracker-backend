package com.nimantha.utilitybilltracker.repositories;

import com.nimantha.utilitybilltracker.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BillRepository extends JpaRepository<Bill, Long> {
    @Modifying
    @Query("delete from Bill where utility.id = :id")
    void deleteByUtilityId(Long id);
}
