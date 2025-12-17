package es.ecommerce.demo.generated.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.annotation.Generated;
import io.swagger.v3.oas.annotations.media.Schema;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Schema(name = "PriceDto", description = "Precio aplicable para un producto y marca en un rango de fechas")
public class PriceDto {

  @Schema(description = "Identificador del producto", example = "35455")
  private Long productId;

  @Schema(description = "Identificador de la marca", example = "1")
  private Long brandId;

  @Schema(description = "Identificador de la tarifa aplicada", example = "2")
  private Long priceList;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @Schema(description = "Inicio de vigencia del precio (ISO‑8601)", example = "2020-06-14T15:00:00Z")
  private OffsetDateTime startDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @Schema(description = "Fin de vigencia del precio (ISO‑8601)", example = "2020-12-31T23:59:59Z")
  private OffsetDateTime endDate;

  @Schema(description = "Precio final", example = "25.45")
  private Double value;

  @Schema(description = "Moneda ISO 4217", example = "EUR")
  private String currency;

  public PriceDto() { }

  public PriceDto(Long productId, Long brandId, Long priceList, OffsetDateTime startDate, OffsetDateTime endDate, Double value, String currency) {
    this.productId = productId;
    this.brandId = brandId;
    this.priceList = priceList;
    this.startDate = startDate;
    this.endDate = endDate;
    this.value = value;
    this.currency = currency;
  }

  @JsonProperty("productId")
  public Long getProductId() { return productId; }
  public void setProductId(Long productId) { this.productId = productId; }

  @JsonProperty("brandId")
  public Long getBrandId() { return brandId; }
  public void setBrandId(Long brandId) { this.brandId = brandId; }

  @JsonProperty("priceList")
  public Long getPriceList() { return priceList; }
  public void setPriceList(Long priceList) { this.priceList = priceList; }

  @JsonProperty("startDate")
  public OffsetDateTime getStartDate() { return startDate; }
  public void setStartDate(OffsetDateTime startDate) { this.startDate = startDate; }

  @JsonProperty("endDate")
  public OffsetDateTime getEndDate() { return endDate; }
  public void setEndDate(OffsetDateTime endDate) { this.endDate = endDate; }

  @JsonProperty("value")
  public Double getValue() { return value; }
  public void setValue(Double value) { this.value = value; }

  @JsonProperty("currency")
  public String getCurrency() { return currency; }
  public void setCurrency(String currency) { this.currency = currency; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PriceDto that = (PriceDto) o;
    return Objects.equals(productId, that.productId) && Objects.equals(brandId, that.brandId) && Objects.equals(priceList, that.priceList) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(value, that.value) && Objects.equals(currency, that.currency);
  }

  @Override
  public int hashCode() { return Objects.hash(productId, brandId, priceList, startDate, endDate, value, currency); }
}
