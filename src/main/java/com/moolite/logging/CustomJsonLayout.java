package com.moolite.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;
import org.slf4j.Marker;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomJsonLayout extends JsonLayout {

    @Override
    protected Map toJsonMap(ILoggingEvent event) {
        Marker marker = event.getMarker();
        String logType = "log";
        if(marker != null) {
            logType = marker.getName();
        }

        Map<String, Object> loggingData = new LinkedHashMap<String, Object>();
        Map<String, Object> dexterLoggingEntry = new LinkedHashMap<String, Object>();

        Map<String, Object> testMap = new LinkedHashMap<String, Object>();
        testMap.put("joske", "flippers");
        testMap.put("aapje", "schaap");

        addTimestamp(TIMESTAMP_ATTR_NAME, this.includeTimestamp, event.getTimeStamp(), loggingData);
        add(LEVEL_ATTR_NAME, this.includeLevel, String.valueOf(event.getLevel()), loggingData);
        add(THREAD_ATTR_NAME, this.includeThreadName, event.getThreadName(), loggingData);
        addMap(MDC_ATTR_NAME, true, event.getMDCPropertyMap(), loggingData);
        //wtf?
        addMap("derp", true, event.getMDCPropertyMap(), loggingData);
        add(LOGGER_ATTR_NAME, this.includeLoggerName, event.getLoggerName(), loggingData);
        add(FORMATTED_MESSAGE_ATTR_NAME, this.includeFormattedMessage, event.getFormattedMessage(), loggingData);
        add(MESSAGE_ATTR_NAME, this.includeMessage, event.getMessage(), loggingData);
        add(CONTEXT_ATTR_NAME, this.includeContextName, event.getLoggerContextVO().getName(), loggingData);
        addThrowableInfo(EXCEPTION_ATTR_NAME, this.includeException, event, loggingData);
        addCustomDataToJsonMap(loggingData, event);

        addMap("payload", true, loggingData, dexterLoggingEntry);
        add("event", true, logType, dexterLoggingEntry);

        return dexterLoggingEntry;
    }

}
