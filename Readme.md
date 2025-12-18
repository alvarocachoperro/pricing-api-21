# Pricing API

This project is a Spring Boot application that provides a REST API for querying product prices based on brand, product, and date. The application uses an in-memory H2 database to store pricing information and returns the applicable price based on the highest priority within a specific date range.

## Features

- REST API to query prices for a product based on brand, product ID, and date.
- In-memory H2 database with sample data initialization.
- Unit tests to validate the correctness of the API responses.
- Composite primary key implementation for the `PRICES` table.

## Technologies Used

- Java 21
- Spring Boot 3.3.2
- H2 Database
- JPA/Hibernate
- Maven (3.9.x+ recommended 3.9.6+)
- JUnit 5
- MockMvc
- OpenAPI (contract-first) + Swagger UI

## Project Structure

- **src/main/java**: Contains the source code for the application.
  - **controller**: Contains the REST controller (`PriceController.java`).
  - **service**: Contains the service layer (`PriceService.java`).
  - **repository**: Contains the repository interface (`PriceRepository.java`).
  - **model**: Contains the JPA entity (`PriceJPA.java`) and composite key class (`PriceIdJPA.java`).
- **src/main/resources**: Contains configuration files and SQL scripts.
  - **application.properties**: Configures the H2 database.
  - **data.sql**: Initializes the H2 database with sample data.
- **src/test/java**: Contains unit tests for the application.

## Getting Started

### Prerequisites

- Java 21 (JDK 21)
- Maven 3.9.x or higher (recommended 3.9.6+)
- Make sure `JAVA_HOME` points to JDK 21.
  - macOS/Linux (bash/zsh):
    ```bash
    export JAVA_HOME=$(/usr/libexec/java_home -v 21)
    export PATH="$JAVA_HOME/bin:$PATH"
    ```
  - Windows (PowerShell):
    ```powershell
    setx JAVA_HOME "C:\\Program Files\\Java\\jdk-21"
    setx PATH "%JAVA_HOME%\\bin;%PATH%"
    ```

### Running the Application

1. **Clone the repository**:
   ```bash
   git clone https://github.com/alvarocachoperro/pricing-api.git
   cd pricing-api
   ```

2. **Build the project**:
    ```bash
    mvn clean install
    ```

3. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

4. Access the API:
  - The application will start at http://localhost:8080.
  - You can query the price for a product using the endpoint:
    ```bash
        GET /product-price?date=2020-06-14T10:00:00&productId=35455&brandId=1
    ```
### API Endpoints
- **GET /product-price**
  - **Parameters**:
    - **`date`** (required): The date and time of the price request (format: yyyy-MM-dd'T'HH:mm:ss).
    - **`productId`** (required): The product ID.
    - **`brandId`** (required): The brand ID.
  - **Response**:
    - **`productId`**: The product ID.
    - **`brandId`**: The brand ID.
    - **`priceList`**: The applicable price list ID.
    - **`startDate`**: The start date of the price validity period.
    - **`endDate`**: The end date of the price validity period.
    - **`value`**: The final price.
    - **`currency`**: The currency of the price.

#### Example Responses

- 200 OK
  ```json
  {
    "productId": 35455,
    "brandId": 1,
    "priceList": 2,
    "startDate": "2020-06-14T15:00:00Z",
    "endDate": "2020-06-14T18:30:00Z",
    "value": 25.45,
    "currency": "EUR"
  }
  ```

- 400 Bad Request (invalid parameter types)
  ```json
  {
    "status": 400,
    "message": "Error:Parse attempt failed for value [35455]"
  }
  ```

- 400 Bad Request (missing parameter)
  ```json
  {
    "status": 400,
    "message": "Parameter 'productId' is missing"
  }
  ```

- 404 Not Found
  ```json
  {
    "status": 404,
    "message": "Price not found"
  }
  ```

### Testing
This project keeps a clear separation of responsibilities in its test suite:

- Controller/integration tests (`PriceControllerTest`):
  - Verify the HTTP endpoint behavior end-to-end (`GET /product-price`) using MockMvc.
  - Cover success scenarios (200) and error handling (400 for type mismatch/missing parameters, 404 when price is not found).

- Service unit tests (`PriceServiceImplTest`):
  - Focus on business logic using a mocked repository.
  - Validate the selection rule based on repository results: 0 elements → exception, 1 element → returned as-is, 2+ elements → highest `priority`.

Redundant tests that duplicated scenarios were removed to keep the suite lean and faster, avoiding overlap between integration and unit layers.

Run all tests with:
```bash
mvn test
```

### H2 Database

The application uses an in-memory H2 database that is automatically populated with sample data on startup from `src/main/resources/data.sql`. You can access the H2 console at:
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
- This project follows an API-first approach. The contract is defined in `src/main/resources/openapi/pricing-api.yaml` and Swagger UI is available at `/swagger-ui.html` via `springdoc-openapi`.
- Part of the interfaces/models under the package `es.ecommerce.demo.generated.*` are generated from the contract and used to align the DTOs exposed by the API.

### Swagger UI
- Open Swagger UI at: `http://localhost:8080/swagger-ui.html`
- The generated API interface is implemented by the controller, ensuring the runtime API matches the OpenAPI contract.
