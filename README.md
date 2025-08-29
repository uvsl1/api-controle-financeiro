# Controle Financeiro - API (Em Desenvolvimento)

API REST desenvolvida em Java com Spring Boot para gerenciar e monitorar despesas e receitas.
Permite registrar gastos, categorizá-los e disponibilizar relatórios para análise de onde e como o dinheiro está sendo utilizado.

**⚠️ Projeto em desenvolvimento:**  ajustes ainda sendo implementados.

---

## Sumário

- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Funcionalidades da API](#funcionalidades-da-api)
  - [Autenticação](#autenticacao)
  - [Gestão de Despesas](#gestao-de-despesas)
  - [Gestão de Receitas](#gestao-de-receitas)
  - [Balanço Mensal](#balanco-mensal)
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

A API oferece recursos para registrar, consultar, atualizar e excluir despesas e receitas, além de gerar balanço geral, relatórios mensais e por categoria.

## Autenticação

| Método | Endpoint | Descrição |
|--------|---------|-----------|
| POST   | `/api/auth/register` | Registrar um novo usuário |
| POST   | `/api/auth/login` | Autenticar usuário e retornar token JWT |

**Exemplos de requests com JSON:**  

**Exemplo para registrar:**
```json
{
  "name": "{nome}",
  "email": "{email}",
  "password": "{senha}"
}
```

**Exemplo para login:**
```json
{
  "email": "{email}",
  "password": "{senha}"
}
```

> Após o login, o token deve ser enviado em todas as requisições autenticadas no header Authorization:  
> `Authorization: Bearer <seu_token_aqui>`

## Gestão de Despesas

| Método | Endpoint | Descrição |
|--------|---------|-----------|
| POST   | `/api/expenses/create` | Criar nova despesa do usuário autenticado |
| GET    | `/api/expenses/me` | Listar todas as despesas do usuário autenticado |
| GET    | `/api/expenses/{id}` | Retorna os detalhes de uma despesa baseada no id da despesa do usuário autenticado. |
| PATCH  | `/api/expenses/{id}` | Permite modificar apenas campos específicos de uma despesa baseada no id da despesa do usuário autenticado. |
| DELETE | `/api/expenses/{id}` | Remove uma despesa baseada no id da despesa do usuário autenticado. |
| GET    | `/api/expenses/month?year={ano}&month={mês}` | Resumo mensal das despesas do usuário autenticado |
| GET    | `/api/expenses/category-summary?categoryName={categoria}&year={ano}&month={mês}` | Resumo de despesas por categoria no mês do usuário autenticado |
| GET    | `/api/expenses/all-category-summary?year={ano}&month={mês}` | Resumo completo de todas as categorias no mês do usuário autenticado |

**Exemplos de requests com JSON:**

**Exemplo para criar despesa:**
```json
{
  "description": "Shampoo",
  "amount": 25.0,
  "numberOfInstallments": 1,
  "categoryName": "Beleza",
  "startDate": "2025-08-01",
  "paymentMethod": "CREDITO",
  "fixedExpense": false
}
```

**Exemplo para alterar algum campo da despesa:**  
>Informe os campos que deseja alterar e seus novos valores. É possível atualizar múltiplos campos em uma única requisição. 
```json
{
  "amount": 25.0
}
```

## Gestão de Receitas

| Método | Endpoint | Descrição |
|--------|---------|-----------|
| POST   | `/api/incomes/create` | Criar nova receita do usuário autenticado |
| GET    | `/api/incomes/me` | Listar todas as receitas do usuário autenticado |
| PATCH  | `/api/incomes/{id}` | Permite modificar apenas campos específicos de uma receita baseada no id da receita do usuário autenticado. |
| DELETE | `/api/incomes/{id}` | Remove uma receita baseada no id da receita do usuário autenticado. |
| GET    | `/api/incomes/month?year={ano}&month={mês}` | Resumo mensal das receitas do usuário autenticado |

**Exemplos de requests com JSON:**

**Exemplo para criar receita:**
```json
{
  "description": "Venda de aparelho doméstico",
  "amount": 320.0,
  "isFixed": false,
  "startDate": "2025-08-01"
}
```

**Exemplo para alterar algum campo da receita:**
>Informe os campos que deseja alterar e seus novos valores. É possível atualizar múltiplos campos em uma única requisição.
```json
{
  "amount": 300.0
}
```

## Balanço Mensal

| Método | Endpoint | Descrição |
|--------|---------|-----------|
| GET    | `/api/balances/month?year={ano}&month={mês}` | Saldo do mês (receitas - despesas) do usuário autenticado |

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
