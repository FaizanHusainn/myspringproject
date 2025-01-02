package com.myproject.myspringproject.service;

import com.myproject.myspringproject.entity.JournalEntry;
import com.myproject.myspringproject.entity.User;
import com.myproject.myspringproject.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
        try{
            // Finding the user from the user table
            User user = userService.findByUserName(username);
            // set the date to save the entry in the journalEntry in the JournalEntries table
            journalEntry.setDate(LocalDateTime.now());
            // This will save the updated list in the Journal Entry table and give the save journal entry list
            JournalEntry save = journalEntryRepository.save(journalEntry);
            // this will add the list of journal entries in the journal entry section of user table
            user.getJournalEntries().add(save);
            // this will save the journalEntries in the user table
            userService.saveEntry(user);

        }catch (Exception e){

        }

    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    // this method calling the interface JournalEntryRepository which extends mongoDB services which contains all mongo methods
    public List<JournalEntry> getAll(){
        System.out.println(journalEntryRepository.toString());
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String username){
        User user = userService.findByUserName(username);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);

        journalEntryRepository.deleteById(id);
    }



}
