package com.nimantha.utilitybilltracker.utils;

import com.nimantha.utilitybilltracker.dto.UserDTO;
import com.nimantha.utilitybilltracker.models.User;
import com.nimantha.utilitybilltracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Utils {
    @Autowired
    private UserRepository userRepository;
}
