package com.tollplaza.TollPlaza.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tollplaza.TollPlaza.entity.CollectionData;
import com.tollplaza.TollPlaza.repository.CollectionRepository;

@Service
public class CollectionService {
	

    @Autowired
    private CollectionRepository collectionRepository;
    
    public void saveCollectionData(CollectionData collectionData) {
        // Calculate totalAmount
        double totalAmount = collectionData.getTotalManualAmount() + 
                             collectionData.getShortAmount() - 
                             collectionData.getAccessAmount();

        // Set all fields including totalAmount
        CollectionData entity = new CollectionData();
        entity.setTotalManualAmount(collectionData.getTotalManualAmount());
        entity.setShortAmount(collectionData.getShortAmount());
        entity.setAccessAmount(collectionData.getAccessAmount());
        entity.setOperatorId(collectionData.getOperatorId());
        entity.setTotalAmount(totalAmount); // Set the totalAmount

        collectionRepository.save(entity);
    }
    

//    public void saveCollectionData(CollectionData collectionData) {
//        CollectionData entity = new CollectionData();
//        entity.setTotalManualAmount(collectionData.getTotalManualAmount());
//        entity.setShortAmount(collectionData.getShortAmount());
//        entity.setAccessAmount(collectionData.getAccessAmount());
//        entity.setOperatorId(collectionData.getOperatorId());
//
//        collectionRepository.save(entity);
//    }
    
    public List<CollectionData> getamount() {
    return	collectionRepository.getamount();
    
    }
    
    public CollectionData getLatestCollection() {
        return collectionRepository.findLatestCollection();
    }

}
