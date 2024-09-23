package com.tollplaza.TollPlaza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tollplaza.TollPlaza.entity.CollectionData;
import com.tollplaza.TollPlaza.service.CollectionService;


@RestController
@RequestMapping("/api")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;
    
    @PostMapping("/saveDailyCollection")
    public ResponseEntity<String> saveDailyCollection(@RequestBody CollectionData collectionData) {
        try {
            collectionService.saveCollectionData(collectionData);
            return ResponseEntity.ok("Data saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving data");
        }
    }
    
    @GetMapping("/getamount")
    public List<CollectionData> getMethodName() {
        return collectionService.getamount();
    }
    
    
    @GetMapping("/latestCollection")
    public CollectionData getLatestCollection() {
        return collectionService.getLatestCollection();
    }
       
}