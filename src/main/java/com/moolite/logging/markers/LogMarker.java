package com.moolite.logging.markers;

import ch.qos.logback.classic.pattern.RootCauseFirstThrowableProxyConverter;
import ch.qos.logback.classic.pattern.ThrowableProxyConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

import static ch.qos.logback.contrib.json.classic.JsonLayout.*;

public class LogMarker extends StructuredLogMarker {

    private ThrowableProxyConverter converter = new RootCauseFirstThrowableProxyConverter();
    @Override
    protected Marker getConcreteMarker() {
        return MarkerFactory.getMarker("trace");
    }

    @Override
    public Map toMap(ILoggingEvent event) {
        Map<String, Object> loggingData = new LinkedHashMap<>();
        addTimestamp(TIMESTAMP_ATTR_NAME, event.getTimeStamp(), loggingData);
        add(LEVEL_ATTR_NAME, String.valueOf(event.getLevel()), loggingData);
        add(THREAD_ATTR_NAME, event.getThreadName(), loggingData);
        addMap("context", event.getMDCPropertyMap(), loggingData);
        add(LOGGER_ATTR_NAME, event.getLoggerName(), loggingData);
        add(FORMATTED_MESSAGE_ATTR_NAME, event.getFormattedMessage(), loggingData);
        addThrowableInfo(event, loggingData);

        return loggingData;
    }


    private void addThrowableInfo(ILoggingEvent value, Map<String, Object> map) {
        this.converter.start();
        if (value != null) {
            IThrowableProxy throwableProxy = value.getThrowableProxy();
            if (throwableProxy != null) {
                String ex = converter.convert(value);
                if (ex != null && !ex.equals("")) {
                    map.put(EXCEPTION_ATTR_NAME, ex);
                }
            }
        }
        this.converter.stop();
    }


    @Override
    public String getEventType() {
        return "log";
    }
}
