package in.sp.main.exception;

public class NoAppointmentFoundException extends RuntimeException {
    public NoAppointmentFoundException(String message) {
        super(message);
    }
}
