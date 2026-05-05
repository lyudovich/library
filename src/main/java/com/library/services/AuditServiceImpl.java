package com.library.services;

import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService {

    @Override
    public void log(String action, String data) {
        System.out.println("AUDIT: " + action + " | " + data);
    }
}
