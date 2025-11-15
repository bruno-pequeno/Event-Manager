package bruno.pequeno.event_manager.domain.exceptions;

public class ErrorResponse {
    public String message;
    public Object details;

    public ErrorResponse(String message, Object details) {
        this.message = message;
        this.details = details;
    }
}
