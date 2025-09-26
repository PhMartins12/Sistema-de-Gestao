# Sistema de Gestão de Projetos e Equipes

Olá! Este é um sistema de console para gestão de projetos, construído em Java. A ideia foi criar uma ferramenta simples e funcional para organizar o fluxo de trabalho em ambientes com múltiplas equipes e projetos, resolvendo o problema da falta de controle.

### O que ele faz?

- Gerencia Usuários: Permite cadastrar usuários com perfis (`ADMINISTRADOR`, `GERENTE`, `COLABORADOR`) e controlar o acesso com um sistema de login.
- Gerencia Projetos: Você pode criar projetos, definir datas, status e, o mais importante, atribuir um gerente responsável.
- Gerencia Equipes: Permite criar equipes, adicionar membros (que são os usuários já cadastrados) e visualizar quem faz parte de cada time.
- Alocação Integrada: Conecta tudo, permitindo que você aloque equipes inteiras a um ou mais projetos, mostrando quem está trabalhando em quê.

### Tecnologias Utilizadas

- Linguagem: Java
- Banco de Dados: PostgreSQL
- Conexão: JDBC
- Arquitetura: MVC (Model-View-Controller) e DAO (Data Access Object)
- Gerenciador de Dependências: Maven

### Como Começar

Para rodar o projeto, você vai precisar do Java (JDK), Maven e PostgreSQL instalados.

- 1. Banco de Dados:
    - Crie um banco de dados no seu PostgreSQL.
    - Para criar as tabelas, utilize o script SQL que está no arquivo `TabelasDoBanco.txt` neste repositório.

- 2. Configuração do Projeto:
    - Clone o projeto para a sua máquina.
    - **Muito importante:** Abra o arquivo `src/main/java/br/com/oracleproject/util/ConnectionFactory.java` e altere os dados de `URL`, `USER` e `PASSWORD` para os da sua conexão com o banco de dados.

- 3. Execução:
    - Abra o projeto na sua IDE (IntelliJ, Eclipse, etc.).
    - Deixe o Maven baixar as dependências.
    - Execute a classe `Main.java` para iniciar o programa no console.
