package logQuery;

import logQuery.exception.LogQueryException;
import logQuery.manager.LogManager;
import logQuery.model.Log;
import logQuery.model.SearchQuery;
import logQuery.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Author : Paritosh
 * Date : 21/02/21
 */
public class LogQuerySearch {

    private static SearchQuery searchQuery;
    private static final String LOG_FILE_PATH = "/home/paritosh/IdeaProjects/LogQuery/src/main/resources/sample-data.txt";
    public static final boolean _DEBUG_MODE = false;

    public static void main(String[] args) throws LogQueryException {
        try {
            // Read logs from file.
            Util.printLog("Reading log File [" + LOG_FILE_PATH + "]");
            List<String> data = Util.readFile(LOG_FILE_PATH);
            Util.printLog("Log File read successfully. [" + data.size() + "] lines found");
            LogManager.init(data);

            // Read argument.
            Util.printLog("Reading search query");
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the search Query: ");
            String query = sc.nextLine();
            Util.printLog("Search query read: [" + query + "]");

            // Process argument.
            searchQuery = new SearchQuery(query);
            Util.printLog("Search query formed into model");

            // Find the count.
            int count = LogManager.searchLogs(searchQuery);
            System.out.println(count);
        } catch (LogQueryException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }


}
