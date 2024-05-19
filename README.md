# BankDemo-Core

### A core processing demo service to handle multiple transactions in a short time.

For bank system demo project.</br>

---
### Main dependencies/features
- PostgresSQL
- KafkaListener (consumer)
- Various types of thread handling processor
- API controllers for testing (Todo)

### Prerequisites

- Integration tool to set up local PSQL, Kafka, Flyway, Kafka-ui(for producer test)
  <br/>[Integration-testing repo](`https://github.com/MarcoBackman/integration-testing-tool.git`)
- `Todo: Front end service to perform UI testing.`

### Active property, environment variables setup

- For **local application run**, enable active properties as following.
`default,local`
- For environment variables, you must provide db information like below. (replace from `{...}` with your configurations)
`DB_NAME={db_name};DB_PASSWORD={db_pass};DB_USER={db_user}`

- For **maven test run**, setup environment variables same as above and run `local,test` as active profile

- For **maven install**, setup environment variables same as above and run `default,local,test` as active profile

---
### How to set up and run locally

> [!CAUTION] 
> Make sure that environment variables are set correctly and your target db is running. </br>
> Make sure your kafka system is running (zookeeper and brokers)

1. Run flyway migration via integration testing tool or directly by executing maven flyway
   - Method 1 : [Integration-testing repo](`https://github.com/MarcoBackman/integration-testing-tool.git`) &#8592; Simply run this application (please follow instructions)
   - Method 2 : Run following command via command prompt `mvn clean flyway:migrate`
2. Run maven compile/install mvn compile
3. Run BankDemoApplication under src.main.java.org.demo.bankdemo.util

### Future implementation
- Todo: provide API services for integration testing
- Todo: provide secured kafka connection (Consumer side SSL or SASL SSO)


Last updated date: May 19, 2024.
---
