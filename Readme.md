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
- Maven
- JUnit 5
- MockMvc

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
- Maven 3.9.x o superior (recomendado 3.9.6+)
- Nota: asegúrate de que `JAVA_HOME` apunte al JDK 21.
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
 - **GET /api/prices/product-price** 
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

### Testing
The project includes unit tests that verify the correctness of the API. To run the tests, use:
```bash
mvn test
```

### H2 Database
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
