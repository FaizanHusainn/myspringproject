package com.myproject.myspringproject.controller;

import com.myproject.myspringproject.entity.JournalEntry;
import com.myproject.myspringproject.entity.User;
import com.myproject.myspringproject.repository.UserRepository;
import com.myproject.myspringproject.service.JournalEntryService;
import com.myproject.myspringproject.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveEntry(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable  String username){
    User userInDb = userService.findByUserName(username);
    if(userInDb != null){
        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(user.getPassword());
        userService.saveEntry(userInDb);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
