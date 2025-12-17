package es.ecommerce.demo.app.service.exception;


import lombok.Getter;

@Getter
public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(String message) {
        super(message);
    }
}
