package com.myproject.myspringproject.service;

import com.myproject.myspringproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {
    @Autowired
    private UserRepository userRepository;


}
