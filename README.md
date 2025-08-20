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
- Postgresql (Ainda não implementado, usando H2 Database até o momento)
- Spring Security (Ainda não implementado)
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
---

## Exemplos de Requisições

### Criar Usuário
`POST /api/users`

### Corpo da requisição (JSON)
```json
{
  "name": "Ugo V",
  "email": "ugo@mail.com",
  "password": "123"
}
```

>**Observação:** no futuro será implementada a lógica de criação de contas com autenticação e autorização, permitindo login seguro e controle de acesso.


### Criar Despesa
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

### Clonar o repositório
```bash
git clone https://github.com/uvsl1/api-controle-financeiro.git
cd api-controle-financeiro
```

### Executar a aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: ```http://localhost:8080```.

---
