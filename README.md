# BankDemo-Core

### A core processing demo service to handle multiple transactions in a short time.

For bank system demo project.</br>
Related repositories

1. Need kafka security to be configured
2. Need gateway repo to be implemented
3. Need service discovery repo to be implemented
4. Need cloud config server repo to be implemented
5. Requires Frontend service to be implemented


---
### Main dependencies/features
- PostgresSQL
- KafkaListener (consumer)
- Various types of thread handling processor
- API controllers for testing

---
### How to set up and run locally
```
 Todo: Implement integration testing service to activate local kafka brokers and zookeeper with proper topic.
 Todo: Implement local PSQL service to make this service to connect fully functional database automatically (with test datasets too).
 Run BankDemoApplication under src.main.java.org.demo.bankdemo.util
 
 Todo:provide API services to integration testing
 Todo:provide secured kafka connection (Consumer side SSL or SASL SSO)
 
```

Last updated date: April 24, 2024.

---