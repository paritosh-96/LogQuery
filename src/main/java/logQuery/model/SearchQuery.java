package logQuery.model;

import logQuery.exception.LogQueryException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Author : Paritosh
 * Date : 21/02/21
 */
public class SearchQuery {

    public enum COND {AND, OR, NONE}

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    String platform;
    String level;
    COND condition;
    Timestamp startTime;
    Timestamp endTime;

    public String getPlatform() {
        return platform;
    }

    public String getLevel() {
        return level;
    }

    public COND getCondition() {
        return condition;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    // DESKTOP startTime="2020-11-18 13:00:00" endTime="2020-11-18 14:00:00"
    public SearchQuery(String searchQuery) throws LogQueryException {
        if (searchQuery == null || searchQuery.isEmpty())
            throw new LogQueryException.WrongSearchQuery("Query found empty or null");
        String[] parts = searchQuery.trim().split(" ");
        platform = parts[0];
        if (parts.length > 1) {
            int i = 1;
            if (parts[i].equalsIgnoreCase(COND.AND.toString()) ||
                    parts[i].equalsIgnoreCase(COND.OR.toString())) {
                switch (parts[i].trim().toUpperCase()) {
                    case "AND":
                        this.condition = COND.AND;
                        break;
                    case "OR":
                        this.condition = COND.OR;
                        break;
                    default:
                        this.condition = COND.NONE;
                }
                if (parts.length < (i + 2))
                    throw new LogQueryException.WrongSearchQuery("No level value found for condition: [" + this.condition + "]");

                this.level = parts[++i];
                i++;
            } else {
                this.condition = COND.NONE;
                this.level = null;
            }
            if (parts.length > i && parts[i].startsWith("startTime")) {
                try {
                    String[] startTimeParts = parts[i].trim().split("=");
                    if (startTimeParts.length < 2)
                        throw new LogQueryException.WrongSearchQuery("Invalid Start time");


                    startTime = Timestamp.valueOf(sdf.format(sdf.parse(startTimeParts[1].trim())));

                    if (parts.length < (i + 2))
                        throw new LogQueryException.WrongSearchQuery("End time not found with the start Time");

                    String[] endTimeParts = parts[++i].trim().split("=");
                    if (endTimeParts.length < (i + 2))
                        throw new LogQueryException.WrongSearchQuery("Invalid End time");

                    endTime = Timestamp.valueOf(sdf.format(sdf.parse(endTimeParts[1])));
                } catch (ParseException e) {
                    throw new LogQueryException.WrongSearchQuery("Invalid start or end time format + [" + e.getMessage() + "]");
                }
            } else {
                startTime = null;
                endTime = null;
            }
        }
    }
}
