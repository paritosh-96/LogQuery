package logQuery.util;

import logQuery.LogQuerySearch;
import logQuery.exception.LogQueryException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Author : Paritosh
 * Date : 21/02/21
 */
public class Util {

    public static List<String> readFile(String filePath) throws LogQueryException {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())
                lines.add(myReader.nextLine());
            myReader.close();
        } catch (FileNotFoundException e) {
            throw new LogQueryException.FileNotFound(filePath);
        }
        return lines;
    }

    public static void printLog(String message) {
        if(LogQuerySearch._DEBUG_MODE)
            System.out.println(message);
    }
}
