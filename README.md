# Controle Financeiro - API (Em Desenvolvimento)

API REST desenvolvida em Java com Spring Boot para gerenciar e monitorar despesas.  
Permite registrar gastos, categorizá-los e disponibilizar relatórios para análise de onde e como o dinheiro está sendo utilizado. 

**⚠️ Projeto em desenvolvimento:** funcionalidades, segurança, integrações, ajustes e outros ainda estão sendo implementadas.

---

## Sumário

- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Funcionalidades da API](#funcionalidades-da-api)
    - [Gestão de Despesas](#gestao-de-despesas)
    - [Gestão de Receitas](#gestao-de-receitas)
    - [Relatórios e Resumos](#relatorios-e-resumos)
- [Exemplos de Requisições](#exemplos-de-requisicoes)
- [Como executar o projeto](#como-executar-o-projeto)

---

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Maven
- JPA (Java Persistence API)
- Postgres
- Docker
- Spring Security
- JWT (JSON Web Token)
- Swagger (Ainda não implementado)

---

## Funcionalidades da API

A **Controle Financeiro API** oferece recursos para registrar, consultar, atualizar e excluir despesas, além de gerar relatórios mensais e por categoria.

### **Gestão de Despesas**
- **Criar despesa**  
  `POST /api/expenses/create`  
  Registra uma nova despesa com descrição, valor, data e categoria.

- **Listar despesas por usuário**  
  `GET /api/expenses/user/{userId}`  
  Retorna todas as despesas cadastradas de um usuário específico.

- **Buscar despesa por ID**  
  `GET /api/expenses/{id}`  
  Retorna os detalhes de uma despesa específica.

- **Atualização parcial de despesa**  
  `PATCH /api/expenses/{id}`  
  Permite modificar apenas campos específicos de uma despesa.

- **Excluir despesa**  
  `DELETE /api/expenses/{id}`  
  Remove uma despesa do sistema.

---

### **Gestão de Receitas**
- **Criar receita**  
  `POST /api/incomes/create`  
  Registra uma nova receita com descrição, valor, data e usuário.

- **Listar receitas por usuário**  
  `GET /api/incomes/user/{userId}`  
  Retorna todas as receitas cadastradas de um usuário específico.

- **Atualização parcial de receita**  
  `PATCH /api/incomes/{id}`  
  Permite modificar apenas campos específicos de uma receita.

- **Excluir receita**  
  `DELETE /api/incomes/{id}`  
  Remove uma receita do sistema.

---

### **Relatórios e Resumos**
- **Resumo mensal de despesas**  
  `GET /api/expenses/month?year={ano}&month={mes}&userId={id}`  
  Retorna o total gasto no mês informado, junto com estatísticas adicionais.

- **Resumo por categoria**  
  `GET /api/expenses/category-summary?userId={id}&categoryName={nome}&year={ano}&month={mes}`  
  Retorna o total gasto e o percentual de gastos de uma categoria específica dentro do mês. 

- **Resumo completo por todas as categorias do mês**  
  `GET /api/expenses/all-category-summary?userId={id}&year={ano}&month={mes}`  
  Retorna o total gasto no mês e uma lista com todas as categorias, incluindo o valor gasto e o percentual de cada categoria em relação ao total.

- **Resumo mensal de receitas**  
  `GET /api/incomes/month?year={ano}&month={mes}&userId={id}`  
  Retorna o total de receitas no mês informado.

- **Saldo mensal (receitas - despesas)**  
  `GET /api/balances/month?year={ano}&month={mes}&userId={id}`  
  Retorna o saldo do mês informado, calculado a partir da diferença entre receitas e despesas.  

---

## Exemplos de Requisições

### Criar Conta
`POST /api/auth/register`

### Corpo da requisição (JSON)
```json
{
  "name": "{nome}",
  "email": "{email}",
  "password": "{senha}"
}
```

### Logar na Conta
`POST /api/auth/login`

```json
{
    "email": "{email}",
    "password": "{senha}"
}
```

> **Importante:** após o login, a resposta conterá um **token JWT**.  
> Esse token deve ser enviado em todas as requisições, no **header Authorization**.

####  No Postman ou Insomnia:
- Vá até a aba **Authorization** da sua requisição.
- Escolha o tipo **Bearer Token**.
- Cole o token no campo **Token**.
 


### Criar Despesa

>Datas sempre serão `YYYY-MM-DD`

`POST /api/expenses/create`

```json
{
  "description": "Shampoo",
  "amount": 25.0,
  "numberOfInstallments": 1,
  "categoryName": "Beleza",
  "startDate": "2025-08-01",
  "paymentMethod": "CREDIT",
  "fixedExpense": false,
  "userId": 1
}
```

### Criar Receita
`POST /api/incomes/create`

```json
{
  "description": "Venda de aparelho doméstico",
  "amount": 320.00,
  "isFixed": false,
  "startDate": "2025-08-01",
  "userId": 1
}
```

---

## Como executar o projeto

### Requisitos

- Docker instalado: [https://docs.docker.com/get-docker/](https://docs.docker.com/get-docker/)
- Docker Compose instalado (normalmente já vem com o Docker Desktop)

### Clonar o repositório
```bash
git clone https://github.com/uvsl1/api-controle-financeiro.git
cd api-controle-financeiro
```

### Executar via Docker Compose
Assim, sobe automaticamente o Postgres e a API em containers separados, sem precisar instalar o banco localmente.
```bash
docker-compose up --build
```

> A aplicação ficará disponível em: `http://localhost:8080`

---
