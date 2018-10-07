package com.moolite.logging.service;

import org.slf4j.MDC;

public class ErrorService {
    public void doSomething() {
        MDC.put("spanId", "span2");
        int x = 15 /0;
    }
}
