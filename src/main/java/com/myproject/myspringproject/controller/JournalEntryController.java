package com.myproject.myspringproject.controller;

import com.myproject.myspringproject.entity.JournalEntry;
import com.myproject.myspringproject.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

  private Map<Long, JournalEntry> journalEntries = new HashMap<>();

  @Autowired
  private JournalEntryService journalEntryService;


  // to enter the data in the DB
  @PostMapping
  public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
    try{
//      myEntry.setDate(LocalDateTime.now());
      journalEntryService.saveEntry(myEntry);
      return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }
  }

  // To get the data form the DB -- this is calling the getALl() method defied in the JournalEntryService class
  @GetMapping
    public ResponseEntity<?> getAll() {
    System.out.println("Get all call");
    List<JournalEntry> all = journalEntryService.getAll();

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

  @DeleteMapping("id/{myId}")
  public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId ){
    journalEntryService.deleteById(myId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("id/{id}")
  public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
    // Extracting the old entry by id

    JournalEntry old = journalEntryService.findById(id).orElse(null);
    if(old != null){
      old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
      old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
      journalEntryService.saveEntry(old);
      return new ResponseEntity<>(old, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}
