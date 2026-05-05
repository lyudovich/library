package com.library.core.services;

public interface AuditService {
    void log(String action, String data);
}
