package com.moolite.logging;

import com.moolite.logging.markers.LogMarker;
import com.moolite.logging.markers.StructuredLogMarker;
import com.moolite.logging.service.ErrorService;
import org.slf4j.*;

public class Main {
    private static StructuredLogMarker logMarker = new LogMarker();

    static Logger logger = LoggerFactory.getLogger( Main.class );
    public static void main(String[] args) {
        MDC.put("traceId", "mytraceid");
        MDC.put("spanId", "span1");
        logger.info("some info");

        try {
            logger.info(logMarker, "some trace");
            ErrorService errorService = new ErrorService();
            errorService.doSomething();
        } catch (Exception e) {
            logger.error("something went wrong", e);
        }
    }
}
