package com.nimantha.utilitybilltracker.repositories;

import com.nimantha.utilitybilltracker.models.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query("select b from Bill b where utility.id = :id")
    Page<Bill> findByUtilityId(Long id, Pageable pageable);

    @Modifying
    @Query("delete from Bill where utility.id = :id")
    void deleteByUtilityId(Long id);
}
