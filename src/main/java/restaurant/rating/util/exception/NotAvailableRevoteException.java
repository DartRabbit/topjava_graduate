package restaurant.rating.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Revote is not available")
public class NotAvailableRevoteException extends RuntimeException{
    public NotAvailableRevoteException(String message) {
        super(message);
    }
}
