package com.myproject.myspringproject.controller;

import com.myproject.myspringproject.entity.JournalEntry;
import com.myproject.myspringproject.entity.User;
import com.myproject.myspringproject.service.JournalEntryService;
import com.myproject.myspringproject.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

  private Map<Long, JournalEntry> journalEntries = new HashMap<>();

  @Autowired
  private JournalEntryService journalEntryService;

  @Autowired
  private UserService userService;

  // to enter the data in the DB
  @PostMapping("{username}")
  public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String username){
    try{
      User user = userService.findByUserName(username);
//      myEntry.setDate(LocalDateTime.now());
      journalEntryService.saveEntry(myEntry, username);
      return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }
  }

  // To get the data form the DB -- this is calling the getALl() method defied in the JournalEntryService class
  @GetMapping("{username}")
    public ResponseEntity<?> getAllJournalEntryOfUser(@PathVariable String username) {
    User user = userService.findByUserName(username);
    List<JournalEntry> all  = user.getJournalEntries();
   // List<JournalEntry> all = journalEntryService.getAll();
    if( all != null && !all.isEmpty()){
      return new ResponseEntity<>(all,HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("id/{myId}")
  public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
    Optional<JournalEntry>  journalEntry = journalEntryService.findById(myId);
    if(journalEntry.isPresent()){
      return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
    }else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("id/{username}/{myId}")
  public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String username ){
    journalEntryService.deleteById(myId, username);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("id/{username}/{myId}")
  public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry, @PathVariable String username){

    // Extracting the old entry by id
    JournalEntry old = journalEntryService.findById(myId).orElse(null);
    if(old != null){
      old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
      old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
      journalEntryService.saveEntry(old);
      return new ResponseEntity<>(old, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}
