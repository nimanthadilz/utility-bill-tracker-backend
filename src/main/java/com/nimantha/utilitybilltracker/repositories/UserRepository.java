package com.nimantha.utilitybilltracker.repositories;

import com.nimantha.utilitybilltracker.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
