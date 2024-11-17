package com.myproject.myspringproject.repository;

import com.myproject.myspringproject.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository <JournalEntry, ObjectId> {
}
