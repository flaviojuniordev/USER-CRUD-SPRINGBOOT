# CRUD de Usuários em Java Spring Boot

Este projeto é um CRUD (Create, Read, Update, Delete) de usuários, desenvolvido em Java utilizando o framework Spring Boot e integrado a um banco de dados MySQL. O sistema permite gerenciar usuários com diferentes tipos, como 'professor' e 'aluno'.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação.
- **Spring Boot**: Framework para criação de aplicações Java.
- **MySQL**: Sistema de gerenciamento de banco de dados relacional.
- **Docker**: Para containerização da aplicação.

## Funcionalidades

- Cadastro de usuários com nome, sobrenome, senha, email e tipo de usuário (professor ou aluno).
- Listagem de usuários cadastrados.
- Atualização de informações do usuário.
- Exclusão de usuários.

## Estrutura do Projeto

```
src/
 ├── main/
 │    ├── java/
 │    │    └── com/
 │    │         └── seu_pacote/
 │    │              ├── controller/
 │    │              ├── model/
 │    │              ├── repository/
 │    │              └── service/
 │    └── resources/
 │         ├── application.properties
 └── test/
```

## Configuração do Banco de Dados

Para configurar o banco de dados MySQL, siga os passos abaixo:

1. **Crie um banco de dados**:
   - Execute o seguinte comando no MySQL:
     ```sql
     CREATE DATABASE seu_banco_de_dados;
     ```

2. **Configurações no `application.properties`**:
   - Atualize as configurações de conexão com o banco de dados no arquivo `src/main/resources/application.properties`:
     ```properties
     spring.datasource.url=jdbc:mysql://127.0.0.1:3306/seu_banco_de_dados
     spring.datasource.username=seu_usuario
     spring.datasource.password=sua_senha
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     ```

## Como Executar

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/seu_usuario/seu_repositorio.git
   cd seu_repositorio
   ```

2. **Execute a aplicação**:
   - Com o Maven, execute:
     ```bash
     mvn spring-boot:run
     ```

3. **Acesse a API**:
   - A aplicação estará disponível em `http://localhost:8080`.

## Como Contribuir

Contribuições são bem-vindas! Sinta-se à vontade para abrir uma issue ou enviar um pull request.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
