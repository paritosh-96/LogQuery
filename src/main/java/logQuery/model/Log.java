package logQuery.model;

import java.sql.Timestamp;

/**
 * Author : Paritosh
 * Date : 21/02/21
 */
public class Log {

    String pfm;
    String level;
    String step;
    Timestamp timestamp;

    public String getPfm() {
        return pfm;
    }

    public String getLevel() {
        return level;
    }

    public String getStep() {
        return step;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
