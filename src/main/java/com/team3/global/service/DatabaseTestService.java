package com.team3.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class DatabaseTestService {

    @Autowired
    private EntityManager entityManager;

    public void testConnection() {
        Query query = entityManager.createNativeQuery("SELECT 1");
        Long result = ((Number) query.getSingleResult()).longValue();
        System.out.println("Database connected: " + (result == 1L));
    }
}
