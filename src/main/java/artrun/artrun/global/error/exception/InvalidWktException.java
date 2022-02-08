package artrun.artrun.global.error.exception;

/**
 * org.locationtech.jts.io.ParseException 예외 전환
 */
public class InvalidWktException extends InvalidValueException {

    public InvalidWktException(String msg) {
        super(msg);
    }
}
