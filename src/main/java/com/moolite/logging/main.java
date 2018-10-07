package com.moolite.logging;

import com.moolite.logging.service.ErrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class main {

    static Logger logger = LoggerFactory.getLogger( main.class );
    public static void main(String[] args) {

        MDC.put("traceId", "mytraceid");
        MDC.put("spanId", "span1");
        logger.info("some info");
        try {
            ErrorService errorService = new ErrorService();
            errorService.doSomething();
        } catch (Exception e) {
            logger.error("something went wrong", e);
        }
    }
}
