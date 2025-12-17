package es.ecommerce.demo.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Generated;
import io.swagger.v3.oas.annotations.media.Schema;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Schema(name = "ErrorResponse", description = "Estructura estándar de error de la API")
public class ErrorResponse {

  @Schema(description = "Código HTTP del error", example = "400")
  private Integer status;

  @Schema(description = "Mensaje descriptivo del error", example = "Parameter 'productId' is missing")
  private String message;

  public ErrorResponse() { }

  public ErrorResponse(Integer status, String message) {
    this.status = status;
    this.message = message;
  }

  @JsonProperty("status")
  public Integer getStatus() { return status; }
  public void setStatus(Integer status) { this.status = status; }

  @JsonProperty("message")
  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ErrorResponse that = (ErrorResponse) o;
    return Objects.equals(status, that.status) && Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() { return Objects.hash(status, message); }
}
