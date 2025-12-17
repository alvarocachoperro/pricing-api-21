package es.ecommerce.demo.infra.rest.spring.api;

import es.ecommerce.demo.app.service.PriceService;
import es.ecommerce.demo.app.service.exception.PriceNotFoundException;
import es.ecommerce.demo.generated.api.ProductPriceApi;
import es.ecommerce.demo.generated.model.PriceDto;
import es.ecommerce.demo.generated.model.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RestController
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class PricesController implements ProductPriceApi {
    private final PriceService priceService;

    @Override
    public ResponseEntity<PriceDto> productPriceGet(OffsetDateTime date, Long productId, Long brandId) {
        var price = priceService.getPrice(date.toLocalDateTime(), productId, brandId);
        PriceDto dto = mapToGeneratedDto(price);
        return ResponseEntity.ok(dto);
    }

    private PriceDto mapToGeneratedDto(es.ecommerce.demo.domain.Price p) {
        Long priceList = (long) p.getPriceList();
        Double value = toDouble(p.getValue());
        OffsetDateTime start = p.getStartDate().atOffset(ZoneOffset.UTC);
        OffsetDateTime end = p.getEndDate().atOffset(ZoneOffset.UTC);
        return new PriceDto(
                p.getProductId(),
                p.getBrandId(),
                priceList,
                start,
                end,
                value,
                p.getCurrency()
        );
    }

    private Double toDouble(BigDecimal v) {
        return v != null ? v.doubleValue() : null;
    }

    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handlePriceNotFound(PriceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidArguments(IllegalArgumentException ex) {
        String error = "Error:" + ex.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), error);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex) {
        String error;
        if (ex.getRequiredType() != null && ex.getRequiredType().getName().equals("java.time.OffsetDateTime") && ex.getName().equals("date")) {
            Object val = ex.getValue();
            error = "Error:Parse attempt failed for value [" + String.valueOf(val) + "]";
        } else {
            String detail = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
            error = "Error:" + detail;
        }
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), error);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        String error = String.format("Parameter '%s' is missing", name);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), error);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
