package com.nimantha.utilitybilltracker.repositories;

import com.nimantha.utilitybilltracker.models.Bill;
import org.springframework.data.repository.CrudRepository;

public interface BillRepository extends CrudRepository<Bill, Long> {
}
