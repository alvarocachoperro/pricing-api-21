The application uses an in-memory H2 database that is automatically populated with sample data on startup. You can access the H2 console at:
- **`URL`**: `http://localhost:8080/h2-console`
- **`JDBC URL`**: `jdbc:h2:mem:testdb`
- **`Username`**: `sa`
- **`Password`**: `password`

### Example Queries
Here are a few example queries you can try:

- Test 1: Query price for product 35455 on 2020-06-14 at 10:00:00 for brand 1:
    ```bash
    GET /product-price?date=2020-06-14T10:00:00&productId=35455&brandId=1
    ```
- Test 2: Query price for product 35455 on 2020-06-14 at 16:00:00 for brand 1:
    ```bash
    GET /product-price?date=2020-06-14T16:00:00&productId=35455&brandId=1
    ```
- Test 3: Query price for product 35455 on 2020-06-15 at 10:00:00 for brand 1:
    ```bash
    GET /product-price?date=2020-06-15T10:00:00&productId=35455&brandId=1
    ```
### Troubleshooting
- If you encounter any issues with the application, ensure that your environment meets the prerequisites and that the H2 database is correctly configured.


### License
- This project is licensed under the MIT License.

### OpenAPI / API-first
- Este proyecto adopta un enfoque API-first. El contrato se define en `src/main/resources/openapi/pricing-api.yaml` y la UI de Swagger se sirve en `/swagger-ui.html` mediante `springdoc-openapi`.
- Parte de las interfaces/modelos bajo el paquete `es.ecommerce.demo.generated.*` provienen del contrato y se usan para alinear los DTOs expuestos por la API.
