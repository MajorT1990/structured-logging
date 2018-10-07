package com.moolite.logging;

import com.moolite.logging.service.ErrorService;
import org.slf4j.*;

public class main {

    static Marker trace = MarkerFactory.getMarker("trace");

    static Logger logger = LoggerFactory.getLogger( main.class );
    public static void main(String[] args) {

        MDC.put("traceId", "mytraceid");
        MDC.put("spanId", "span1");
        logger.info("some info");

        try {
            logger.info(trace, "some trace");
            ErrorService errorService = new ErrorService();
            errorService.doSomething();
        } catch (Exception e) {
            logger.error("something went wrong", e);
        }
    }
}
