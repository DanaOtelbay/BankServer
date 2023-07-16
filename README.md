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
<img src="img/createNewAccount.png" alt="Image Description" width="400" height="300">

--------------------------------

- [x] View account balance;
  <img src="img/getBalanceByAccountId.png" alt="Image Description" width="400" height="300">

--------------------------------

- [x] Transfer of funds between accounts.
  <img src="img/transferMoney.png" alt="Image Description" width="400" height="300">

---

#### 2.2. Error handling is provided (for example, with insufficient account balance).
- [x] An error when one of the two or both accounts do not exist.;
<img src="img/ErrorAccountDoesntExist.png" alt="Image Description" width="400" height="300">

--------------------------------

- [x] Error when insufficient balance in the source account.
  <img src="img/catchErrorWhenTransfer.png" alt="Image Description" width="400" height="300">

---

### 3. Integration requirements
- [x] Implemented REST API for interacting with the microservice.

---

### 4. Testing

- [x] 4.1. Unit tests for the core business logic.
- [x] 4.2. Integration tests to test the interaction between
system components.

---

### 5. Security with API token

---

- [x] 5.1 Register
  <img src="img/register.png" alt="Image Description" width="400" height="300">

- [x] 5.1 Authentication
  <img src="img/authenticate.png" alt="Image Description" width="400" height="300">

--- 

- [x] 5.2 Logging of operations in a Spring Boot application

---