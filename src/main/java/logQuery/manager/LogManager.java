package logQuery.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import logQuery.model.Log;
import logQuery.model.SearchQuery;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Author : Paritosh
 * Date : 21/02/21
 */
public class LogManager {

    public static Map<String, List<Log>> pfmToObj = new HashMap<>();
    public static Map<String, List<Log>> levelToObj = new HashMap<>();
    public static List<Log> logs = new ArrayList<>();

    public static void init(List<String> jsonStrings) {
        Gson g = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.SSSz")
                .create();
        for (String jsonString : jsonStrings) {
            Log log = g.fromJson(jsonString, Log.class);
            logs.add(log);

            // add in pfmToObj
            List<Log> logListRef;
            if (pfmToObj.containsKey(log.getPfm()))
                logListRef = pfmToObj.get(log.getPfm());
            else
                logListRef = new ArrayList<>();
            logListRef.add(log);
            pfmToObj.put(log.getPfm(), logListRef);

            //add in levelToObj
            if (levelToObj.containsKey(log.getLevel()))
                logListRef = levelToObj.get(log.getLevel());
            else
                logListRef = new ArrayList<>();
            logListRef.add(log);
            levelToObj.put(log.getLevel(), logListRef);
        }
    }

    public static int searchLogs(SearchQuery query) {
        Set<Log> searchedLogs = new HashSet<>(pfmToObj.get(query.getPlatform()));
        if (query.getCondition() == SearchQuery.COND.NONE && query.getStartTime() == null)
            return searchedLogs.size();

        // for no condition and only start and end time
        if (query.getCondition()!= null && query.getCondition() != SearchQuery.COND.NONE) {
            switch (query.getCondition()) {
                case AND:
                    searchedLogs = searchedLogs.stream()
                            .filter(log -> log.getLevel().equals(query.getLevel()))
                            .collect(Collectors.toSet());
                    break;
                case OR:
                    searchedLogs.addAll(levelToObj.get(query.getLevel()));
            }
        }
        if (query.getStartTime() != null) {
            searchedLogs = searchedLogs.stream()
                    .filter(log -> (log.getTimestamp().after(query.getStartTime())
                            && log.getTimestamp().before(query.getEndTime())))
                    .collect(Collectors.toSet());
        }
        return searchedLogs.size();
    }
}
