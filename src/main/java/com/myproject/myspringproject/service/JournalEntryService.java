package com.myproject.myspringproject.service;

import com.myproject.myspringproject.entity.JournalEntry;
import com.myproject.myspringproject.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry( JournalEntry journalEntry){
        try{
            journalEntry.setDate(LocalDateTime.now());
            journalEntryRepository.save(journalEntry);
        }catch (Exception e){

        }

    }

    // this method calling the interface JournalEntryRepository which extends mongoDB services which contains all mongo methods
    public List<JournalEntry> getAll(){
        System.out.println(journalEntryRepository.toString());
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        journalEntryRepository.deleteById(id);
    }



}
