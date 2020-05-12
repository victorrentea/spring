package com.example.demo.service;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BusinessLogicService {
    private final AltaClasa alta;

    public BusinessLogicService(AltaClasa alta) {
        this.alta = alta;
    }

    public String m() {
        return alta.m().toUpperCase();
    }
}

@Service
class AltaClasa {

    public String m() {
        return UUID.randomUUID().toString();
    }
}