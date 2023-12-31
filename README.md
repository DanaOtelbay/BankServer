# Bank Server

## Information

###### The application is written in Java using Spring Boot. A simple microservice for conducting monetary transactions and integration with imitation of an external payment system.

---

# Getting Started
###### These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See running for notes on how to run the project on a system.

### Prerequisites
- 1.Clone the project to your local environment:
```aidl
git clone https://github.com/DanaOtelbay/BankServer.git
```
- 2.Running
  - Run 'BankServerApplication'.

---

### 2. Functionality:

---

#### 2.1. The service supports the following operations:


--------------------------------

- [x] Create a new account (with the ability to specify the initial balance);
![](img/createNewAccount.png)

--------------------------------

- [x] View account balance using account id as pathVariable;
![](img/getBalanceByAccountId.png)

--------------------------------

- [x] Transfer of funds between accounts.
![](img/transferMoney.png)

---

#### 2.2. Error handling is provided (for example, with insufficient account balance).
- [x] An error when one of the two or both accounts do not exist.;
  ![](img/ErrorAccountDoesntExist.png)

--------------------------------

- [x] Error when insufficient balance in the source account.
  ![](img/catchErrorWhenTransfer.png)

---

### 3. Integration requirements
- [x] Implemented REST API for interacting with the microservice.

---

### 4. Testing

- [x] 4.1. Unit tests for the core business logic.
  - Run 'AccountServiceTest' in test/unitTest package.
  - Run 'TransactionServiceTest' in test/unitTest package.
- [x] 4.2. Integration tests to test the interaction between system components.
  - Run 'TransactionServiceIntegrationTest' in test/integrationTest package.
  - Run 'AccountServiceIntegrationTest' in test/integrationTest package.
  
---

### 5. Security with API token

---

- [x] 5.1 Register
![](img/register.png)

- [x] 5.1 Authentication
![](img/authenticate.png)

--- 

- [x] 5.2 Logging of operations in a Spring Boot application

---