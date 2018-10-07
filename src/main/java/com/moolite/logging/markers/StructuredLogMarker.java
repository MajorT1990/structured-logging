package com.moolite.logging.markers;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.Marker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

public abstract class StructuredLogMarker implements Marker {

    private Marker marker;

    public StructuredLogMarker() {
        marker = getConcreteMarker();
    }

    protected abstract Marker getConcreteMarker();

    public abstract Map<String, ?> toMap(ILoggingEvent event);

    public abstract String getEventType();

    @Override
    public String getName() {
        return marker.getName();
    }

    @Override
    public void add(Marker reference) {
        marker.add(reference);
    }

    @Override
    public boolean remove(Marker reference) {
       return marker.remove(reference);
    }

    @Override
    public boolean hasChildren() {
        return marker.hasReferences();
    }

    @Override
    public boolean hasReferences() {
        return marker.hasReferences();
    }

    @Override
    public Iterator<Marker> iterator() {
        return marker.iterator();
    }

    @Override
    public boolean contains(Marker other) {
        return marker.contains(other);
    }

    @Override
    public boolean contains(String name) {
        return marker.contains(name);
    }

    protected void addMap(String key, Map<String, ?> mapValue, Map<String, Object> map) {
        if (mapValue != null && !mapValue.isEmpty()) {
            map.put(key, mapValue);
        }
    }

    protected void addTimestamp(String key, long timeStamp, Map<String, Object> map) {

            String formatted = formatTimestamp(timeStamp);
            if (formatted != null) {
                map.put(key, formatted);
            }

    }

    protected void add(String fieldName, String value, Map<String, Object> map) {
        if (value != null) {
            map.put(fieldName, value);
        }
    }


    private String formatTimestamp(long timestamp) {
        String timestampFormat = "yyyy-MM-dd HH:mm:ss.SSS";
        String timestampFormatTimezoneId = ZoneId.systemDefault().getId();

        if (timestamp < 0) {
            return String.valueOf(timestamp);
        }
        Date date = new Date(timestamp);
        DateFormat format = createDateFormat(timestampFormat);

        if (timestampFormatTimezoneId != null) {
            TimeZone tz = TimeZone.getTimeZone(timestampFormatTimezoneId);
            format.setTimeZone(tz);
        }

        return format(date, format);
    }

    private DateFormat createDateFormat(String timestampFormat) {
        return new SimpleDateFormat(timestampFormat);
    }

    private String format(Date date, DateFormat format) {
        return format.format(date);
    }


}
