package logQuery.exception;

/**
 * Author : Paritosh
 * Date : 21/02/21
 */
public class LogQueryException extends Exception {

    String errorMessage;

    public LogQueryException(String message) {
        this.errorMessage = message;
    }

    public static class FileNotFound extends LogQueryException {
        public FileNotFound(String fileName) {
            super("File not found: " + fileName);
        }
    }

    public static class WrongSearchQuery extends LogQueryException {
        public WrongSearchQuery(String error) {
            super("Search query found wrong. Error: [" + error + "]");
        }
    }

    @Override
    public String getMessage() {
        return this.errorMessage;
    }
}
