package com.example.dddinaction.domain.organization.emp;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmpHandler {
    public String generateNum() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
