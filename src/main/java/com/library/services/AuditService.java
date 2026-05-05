package com.library.services;

public interface AuditService {
    void log(String action, String data);
}
