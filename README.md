# Payment Service Provider (PSP)
Desafio backend Pagar.me

## Bibliotecas e ferramentas
- Java 17
- Spring Boot 3.0.10
- Maven
- JPA
- Hibernate
- PostgreSQL
- JUnit
- Git e Github

## Metodologias e Designs
- MVC
- TDD
- Conventional Commits

## Requisitos

Você deve criar um serviço com os seguintes requisitos:

- [] O serviço deve processar transações, recebendo as seguintes informações:
    * Valor da transação
    * Descrição da transação. Ex: `'Smartband XYZ 3.0'`
    * Método de pagamento (`debit_card` ou `credit_card`)
    * Número do cartão
    * Nome do portador do cartão
    * Data de validade do cartão
    * Código de verificação do cartão (CVV)
- [] O serviço deve retornar uma lista das transações já criadas 
- [] Como o número do cartão é uma informação sensível, o serviço só pode armazenar e retornar os 4 últimos dígitos do cartão. 
- [] O serviço deve criar os recebíveis do cliente (`payables`), com as seguintes regras:
    * Se a transação for feita com um cartão de débito:
        * O payable deve ser criado com status = `paid` (indicando que o cliente já recebeu esse valor)
        * O payable deve ser criado com a data de pagamento (payment_date) = data da criação da transação (D+0).
    * Se a transação for feita com um cartão de crédito:
        * O payable deve ser criado com status = `waiting_funds` (indicando que o cliente vai receber esse dinheiro no futuro)
        * O payable deve ser criado com a data de pagamento (payment_date) = data da criação da transação + 30 dias (D+30).
- [] No momento de criação dos payables também deve ser descontado a taxa de processamento (que chamamos de `fee`) do cliente. Ex: se a taxa for 5% e o cliente processar uma transação de R$100,00, ele só receberá R$95,00. Considere as seguintes taxas:
    * 3% para transações feitas com um cartão de débito
    * 5% para transações feitas com um cartão de crédito
- [] O serviço deve prover um meio de consulta para que o cliente visualize seu saldo com as seguintes informações:
    * Saldo `available` (disponível): tudo que o cliente já recebeu (payables `paid`)
    * Saldo `waiting_funds` (a receber): tudo que o cliente tem a receber (payables `waiting_funds`)

## Milhas extra
- [] Docker
- [] Swagger
- [] Flyway or Liquibase
- [] Cache