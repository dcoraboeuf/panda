package net.panda.dao;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public final class SQLUtils {

    public static final String PARAMETER_SEPARATOR = "&&&&";
    public static final String PARAMETER_ASSIGNEMENT = "=";

    private SQLUtils() {
    }

    public static String dateToDB(LocalDate date) {
        return date.toString();
    }

    public static LocalDate dateFromDB(String str) {
        return LocalDate.parse(str);
    }

    public static String timeToDB(LocalTime time) {
        return time.toString("HH:mm");
    }

    public static LocalTime timeFromDB(String str) {
        return LocalTime.parse(str);
    }

    public static DateTime now() {
        return DateTime.now(DateTimeZone.UTC);
    }

    public static Timestamp toTimestamp(DateTime dateTime) {
        return new Timestamp(dateTime.getMillis());
    }

    public static DateTime getDateTime(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        return getDateTime(timestamp);
    }

    public static DateTime getDateTime(Timestamp timestamp) {
        return timestamp != null ? new DateTime(timestamp.getTime(), DateTimeZone.UTC) : null;
    }

    public static <E extends Enum<E>> E getEnum(Class<E> enumClass, ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        if (value == null) {
            return null;
        } else {
            return Enum.valueOf(enumClass, value);
        }
    }

    public static Locale toLocale(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        if (StringUtils.isBlank(value)) {
            return Locale.ENGLISH;
        } else {
            return Locale.forLanguageTag(value);
        }
    }

    public static String parametersToDB(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "";
        } else {
            StringBuilder s = new StringBuilder();
            int index = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (index > 0) {
                    s.append(PARAMETER_SEPARATOR);
                }
                String name = entry.getKey();
                String value = entry.getValue();
                s.append(name).append(PARAMETER_ASSIGNEMENT).append(value);
                index++;
            }
            return s.toString();
        }
    }

    public static Map<String, String> parametersFromDB(ResultSet rs, String column) throws SQLException {
        String dbValue = rs.getString(column);
        if (StringUtils.isBlank(dbValue)) {
            return Collections.emptyMap();
        } else {
            String[] parameters = StringUtils.split(dbValue, PARAMETER_SEPARATOR);
            Map<String, String> result = new LinkedHashMap<>();
            for (String parameter : parameters) {
                String name = StringUtils.substringBefore(parameter, PARAMETER_ASSIGNEMENT);
                String value = StringUtils.substringAfter(parameter, PARAMETER_ASSIGNEMENT);
                result.put(name, value);
            }
            return result;
        }
    }
}
