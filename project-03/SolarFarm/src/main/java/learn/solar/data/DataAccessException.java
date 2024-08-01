package learn.solar.data;

public class DataAccessException extends Exception {
    public DataAccessException( String message ){
        super( message );
    }

    public DataAccessException( String message, Throwable innerException ){
        super( message, innerException);
    }

}
