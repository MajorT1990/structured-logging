package com.moolite.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;
import com.moolite.logging.markers.LogMarker;
import com.moolite.logging.markers.StructuredLogMarker;

import java.util.LinkedHashMap;
import java.util.Map;

public class StructuredJsonLayout extends JsonLayout {

    @Override
    protected Map toJsonMap(ILoggingEvent event) {
        StructuredLogMarker marker = getStructuredLogMarker(event);

        Map<String, Object> dexterLoggingEntry = new LinkedHashMap<String, Object>();


        addMap("payload", true, marker.toMap(event), dexterLoggingEntry);
        add("event", true, marker.getEventType() , dexterLoggingEntry);

        return dexterLoggingEntry;
    }

    private StructuredLogMarker getStructuredLogMarker(ILoggingEvent event) {
        if(event.getMarker() != null && event.getMarker() instanceof StructuredLogMarker) {
            return (StructuredLogMarker) event.getMarker();
        } else {
            return new LogMarker();
        }
    }

}
