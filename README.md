# 🛒 Microserviço de Produtos - E-commerce

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Clean Architecture](https://img.shields.io/badge/Architecture-Clean%20Architecture-blue.svg)](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Microserviço desenvolvido com **Java 21** e **Spring Boot 3.2** para gerenciamento de produtos em um sistema de e-commerce, implementando **Clean Architecture** (Uncle Bob) com princípios SOLID e padrão **Ports & Adapters** (Hexagonal Architecture).

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Arquitetura](#-arquitetura)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Pré-requisitos](#-pré-requisitos)
- [Como Executar](#-como-executar)
- [Endpoints da API](#-endpoints-da-api)
- [Testes](#-testes)
- [Docker](#-docker)
- [Documentação da API](#-documentação-da-api)
- [Próximos Passos](#-próximos-passos)

## 🎯 Sobre o Projeto

Este microserviço foi desenvolvido como exemplo de **boas práticas de arquitetura de software**, implementando:

- ✅ **Clean Architecture** - Separação clara de responsabilidades
- ✅ **Princípios SOLID** - Código limpo e manutenível
- ✅ **Ports & Adapters** - Desacoplamento de frameworks
- ✅ **Domain-Driven Design (DDD)** - Modelagem rica do domínio
- ✅ **Test-Driven Development (TDD)** - Alta cobertura de testes
- ✅ **RESTful API** - Endpoints bem definidos
- ✅ **API Documentation** - Swagger/OpenAPI

## 🏗️ Arquitetura

Este projeto segue a **Clean Architecture** proposta por Robert C. Martin (Uncle Bob), organizando o código em camadas concêntricas onde **as dependências sempre apontam para dentro**.

### Diagrama da Arquitetura

```
┌─────────────────────────────────────────────────────────┐
│          PRESENTATION (Framework & Drivers)             │
│  ┌───────────────────────────────────────────────────┐  │
│  │   Controllers │ DTOs │ Exception Handlers        │  │
│  └───────────────────────────────────────────────────┘  │
│                          ↓                              │
│  ┌───────────────────────────────────────────────────┐  │
│  │         INFRASTRUCTURE (Interface Adapters)       │  │
│  │  ┌────────────────────────────────────────────┐   │  │
│  │  │  Repositories │ JPA Entities │ Mappers    │   │  │
│  │  └────────────────────────────────────────────┘   │  │
│  └───────────────────────────────────────────────────┘  │
│                          ↓                              │
│           ┌──────────────────────────────┐              │
│           │    APPLICATION (Use Cases)   │              │
│           │  ┌────────────────────────┐  │              │
│           │  │  Business Logic        │  │              │
│           │  │  Orchestration         │  │              │
│           │  └────────────────────────┘  │              │
│           └──────────────────────────────┘              │
│                          ↓                              │
│                ┌─────────────────┐                      │
│                │  DOMAIN (Core)  │                      │
│                │  ┌───────────┐  │                      │
│                │  │ Entities  │  │                      │
│                │  │ Rules     │  │                      │
│                │  │ Ports     │  │                      │
│                │  └───────────┘  │                      │
│                └─────────────────┘                      │
└─────────────────────────────────────────────────────────┘

        Dependências apontam sempre para DENTRO →
```

### Camadas da Aplicação

#### 🔵 1. Domain (Núcleo)
**Localização:** `com.ecommerce.produtos.domain`

A camada mais interna, contém as **regras de negócio puras**:
- **Entidades**: `Produto` (sem anotações de framework!)
- **Ports (Interfaces)**: `ProdutoRepositoryPort`
- **Exceptions**: `ProdutoNotFoundException`
- **Não depende de nada** - Código 100% Java puro

#### 🟢 2. Application (Casos de Uso)
**Localização:** `com.ecommerce.produtos.application`

Contém a **lógica de aplicação** e orquestra o fluxo de dados:
- `CriarProdutoUseCase`
- `BuscarProdutoPorIdUseCase`
- `ListarTodosProdutosUseCase`
- `AtualizarProdutoUseCase`
- `DeletarProdutoUseCase`
- `BuscarPorCategoriaUseCase`

**Depende apenas:** Domain

#### 🟡 3. Infrastructure (Adaptadores)
**Localização:** `com.ecommerce.produtos.infrastructure`

Implementa as **interfaces definidas no Domain**:
- **Persistência**: `ProdutoJpaEntity`, `ProdutoJpaRepository`
- **Adapters**: `ProdutoRepositoryImpl` (implementa `ProdutoRepositoryPort`)
- **Mappers**: Conversão Domain ↔ JPA
- **Config**: Configuração do Spring e injeção de dependências

**Depende de:** Domain + Application

#### 🔴 4. Presentation (Interface Externa)
**Localização:** `com.ecommerce.produtos.presentation`

Camada de **entrada da aplicação**:
- **Controllers**: Endpoints REST
- **DTOs**: `ProdutoRequestDTO`, `ProdutoResponseDTO`
- **Mappers**: Conversão Domain ↔ DTO
- **Exception Handlers**: Tratamento de erros HTTP

**Depende de:** Application

## 🚀 Tecnologias Utilizadas

### Core
- **Java 21** - LTS (Long Term Support)
- **Spring Boot 3.2.0** - Framework principal
- **Maven 3.9+** - Gerenciamento de dependências

### Persistência
- **Spring Data JPA** - Abstração de persistência
- **Hibernate** - ORM (Object-Relational Mapping)
- **H2 Database** - Banco de dados em memória

### Documentação
- **SpringDoc OpenAPI 3** - Documentação automática da API
- **Swagger UI** - Interface gráfica para testar endpoints

### Validação
- **Jakarta Validation (Bean Validation)** - Validação de dados

### Testes
- **JUnit 5** - Framework de testes
- **Mockito** - Mock de dependências
- **AssertJ** - Assertions fluentes
- **Spring Boot Test** - Testes de integração
- **JaCoCo** - Cobertura de código

### DevOps
- **Docker** - Containerização
- **Docker Compose** - Orquestração de containers

## 📁 Estrutura do Projeto

```
microservice-produtos/
├── src/main/java/com/ecommerce/produtos/
│   ├── MicroserviceProdutosApplication.java
│   │
│   ├── domain/                          # ⭐ CAMADA DE DOMÍNIO
│   │   ├── entity/
│   │   │   └── Produto.java            # Entidade pura (sem @Entity)
│   │   ├── repository/
│   │   │   └── ProdutoRepositoryPort.java  # Interface (Port)
│   │   └── exception/
│   │       └── ProdutoNotFoundException.java
│   │
│   ├── application/                     # ⭐ CAMADA DE APLICAÇÃO
│   │   └── usecase/
│   │       ├── CriarProdutoUseCase.java
│   │       ├── BuscarProdutoPorIdUseCase.java
│   │       ├── ListarTodosProdutosUseCase.java
│   │       ├── AtualizarProdutoUseCase.java
│   │       ├── DeletarProdutoUseCase.java
│   │       └── BuscarPorCategoriaUseCase.java
│   │
│   ├── infrastructure/                  # ⭐ CAMADA DE INFRAESTRUTURA
│   │   ├── persistence/
│   │   │   ├── entity/
│   │   │   │   └── ProdutoJpaEntity.java    # Entidade JPA
│   │   │   ├── repository/
│   │   │   │   ├── ProdutoJpaRepository.java
│   │   │   │   └── ProdutoRepositoryImpl.java  # Adapter
│   │   │   └── mapper/
│   │   │       └── ProdutoMapper.java
│   │   └── config/
│   │       ├── BeanConfiguration.java
│   │       └── OpenApiConfig.java
│   │
│   └── presentation/                    # ⭐ CAMADA DE APRESENTAÇÃO
│       ├── controller/
│       │   └── ProdutoController.java
│       ├── dto/
│       │   ├── ProdutoRequestDTO.java
│       │   └── ProdutoResponseDTO.java
│       ├── mapper/
│       │   └── ProdutoDTOMapper.java
│       └── exception/
│           └── GlobalExceptionHandler.java
│
├── src/main/resources/
│   └── application.properties
│
└── src/test/java/com/ecommerce/produtos/
    ├── application/usecase/             # Testes unitários dos Use Cases
    ├── domain/entity/                   # Testes unitários da Entidade
    ├── infrastructure/persistence/      # Testes de integração (JPA)
    └── presentation/controller/         # Testes de integração (REST)
```

## 📦 Pré-requisitos

- **JDK 21** ou superior ([Download OpenJDK](https://openjdk.java.net/))
- **Maven 3.9+** ([Download Maven](https://maven.apache.org/download.cgi))
- **Docker** (opcional, para containerização)

### Verificar instalação:

```bash
java -version   # Deve mostrar versão 21 ou superior
mvn -version    # Deve mostrar versão 3.9 ou superior
```

## 🚀 Como Executar

### 1️⃣ Clonar o repositório

```bash
cd /home/flavio/Projetos/
git clone <url-do-repositorio>
cd microservice-produtos
```

### 2️⃣ Compilar o projeto

```bash
mvn clean install
```

Este comando irá:
- Baixar todas as dependências
- Compilar o código
- Executar todos os testes
- Gerar o arquivo JAR

### 3️⃣ Executar a aplicação

**Opção A: Usando Maven**
```bash
mvn spring-boot:run
```

**Opção B: Executando o JAR diretamente**
```bash
java -jar target/microservice-produtos-1.0.0.jar
```

A aplicação estará disponível em: **http://localhost:8080**

### 4️⃣ Acessar a documentação da API

**Swagger UI:** http://localhost:8080/swagger-ui.html

**OpenAPI JSON:** http://localhost:8080/v3/api-docs

### 5️⃣ Acessar o Console H2

**URL:** http://localhost:8080/h2-console

**Configurações de conexão:**
- **JDBC URL:** `jdbc:h2:mem:produtosdb`
- **Username:** `sa`
- **Password:** *(deixe em branco)*

## Endpoints da API

### POST /api/produtos
Cria um novo produto.

**Request Body:**
```json
{
  "nome": "Notebook",
  "descricao": "Notebook Dell Inspiron",
  "preco": 2999.99,
  "estoque": 10,
  "categoria": "Eletrônicos"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "nome": "Notebook",
  "descricao": "Notebook Dell Inspiron",
  "preco": 2999.99,
  "estoque": 10,
  "categoria": "Eletrônicos",
  "dataCriacao": "2024-01-15T10:30:00"
}
```

### GET /api/produtos
Lista todos os produtos.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nome": "Notebook",
    "descricao": "Notebook Dell Inspiron",
    "preco": 2999.99,
    "estoque": 10,
    "categoria": "Eletrônicos",
    "dataCriacao": "2024-01-15T10:30:00"
  }
]
```

### GET /api/produtos/{id}
Busca um produto por ID.

**Response:** `200 OK`
```json
{
  "id": 1,
  "nome": "Notebook",
  "descricao": "Notebook Dell Inspiron",
  "preco": 2999.99,
  "estoque": 10,
  "categoria": "Eletrônicos",
  "dataCriacao": "2024-01-15T10:30:00"
}
```

**Error:** `404 Not Found` - Se o produto não existir

### PUT /api/produtos/{id}
Atualiza um produto existente.

**Request Body:**
```json
{
  "nome": "Notebook Atualizado",
  "descricao": "Descrição atualizada",
  "preco": 3499.99,
  "estoque": 15,
  "categoria": "Eletrônicos"
}
```

**Response:** `200 OK` (com o produto atualizado)

**Error:** `404 Not Found` - Se o produto não existir

### DELETE /api/produtos/{id}
Deleta um produto.

**Response:** `204 No Content`

**Error:** `404 Not Found` - Se o produto não existir

### GET /api/produtos/categoria/{categoria}
Busca produtos por categoria.

**Response:** `200 OK` (lista de produtos da categoria)

## Tratamento de Erros

A API retorna mensagens de erro estruturadas:

### Erro de Validação (400 Bad Request)
```json
{
  "status": 400,
  "message": "Erro de validação",
  "errors": {
    "nome": "O nome do produto é obrigatório",
    "preco": "O preço deve ser maior que zero"
  },
  "timestamp": "2024-01-15T10:30:00"
}
```

### Recurso Não Encontrado (404 Not Found)
```json
{
  "status": 404,
  "message": "Produto não encontrado com id: 1",
  "timestamp": "2024-01-15T10:30:00"
}
```

### Erro Interno (500 Internal Server Error)
```json
{
  "status": 500,
  "message": "Erro interno do servidor: ...",
  "timestamp": "2024-01-15T10:30:00"
}
```

## ✅ Validações Implementadas

A validação ocorre em **duas camadas**:

### 1. Validação de Entrada (DTOs)
- **Nome**: Obrigatório, entre 3 e 100 caracteres
- **Descrição**: Opcional, máximo 500 caracteres
- **Preço**: Obrigatório, maior que zero (`@DecimalMin`)
- **Estoque**: Obrigatório, não pode ser negativo (`@Min(0)`)
- **Categoria**: Obrigatória, máximo 50 caracteres

### 2. Validação de Negócio (Entidade Domain)
- Preço deve ser positivo
- Estoque não pode ser negativo
- Nome com tamanho adequado
- Lógica de redução/aumento de estoque
- Verificação de disponibilidade

## 🧪 Testes

Este projeto segue **TDD** (Test-Driven Development) com alta cobertura de código.

### Estrutura de Testes

```
src/test/java/
├── application/usecase/          # 🔵 Testes Unitários (Use Cases)
│   ├── CriarProdutoUseCaseTest
│   ├── BuscarProdutoPorIdUseCaseTest
│   ├── ListarTodosProdutosUseCaseTest
│   ├── AtualizarProdutoUseCaseTest
│   ├── DeletarProdutoUseCaseTest
│   └── BuscarPorCategoriaUseCaseTest
│
├── domain/entity/                 # 🔵 Testes Unitários (Entidade)
│   └── ProdutoTest
│
├── infrastructure/persistence/    # 🟢 Testes de Integração (JPA)
│   └── repository/
│       └── ProdutoRepositoryImplTest
│
└── presentation/controller/       # 🟢 Testes de Integração (REST)
    └── ProdutoControllerTest
```

### Tipos de Testes

#### 🔵 Testes Unitários
**Características:**
- ⚡ Rápidos (sem Spring Context)
- 🎯 Isolados (usa Mocks)
- 📦 Testam lógica de negócio pura
- 🔧 Framework: JUnit 5 + Mockito

**Exemplo:**
```java
@ExtendWith(MockitoExtension.class)
class CriarProdutoUseCaseTest {
    @Mock
    private ProdutoRepositoryPort repositoryPort;
    
    @Test
    void deveCriarProdutoComDadosValidos() {
        // Testa o Use Case sem banco de dados!
    }
}
```

#### 🟢 Testes de Integração
**Características:**
- 🐢 Mais lentos (Spring Context)
- 🔗 Testam interação entre camadas
- 💾 Usam H2 in-memory
- 🔧 Framework: Spring Boot Test

### Comandos de Teste

#### Executar todos os testes
```bash
mvn test
```

#### Executar apenas testes unitários
```bash
mvn test -Dtest="*UseCase*Test,*ProdutoTest"
```

#### Executar apenas testes de integração
```bash
mvn test -Dtest="*RepositoryImpl*Test,*Controller*Test"
```

#### Executar testes com relatório de cobertura
```bash
mvn clean test
```

O relatório JaCoCo será gerado em: `target/site/jacoco/index.html`

#### Ver relatório de cobertura no navegador
```bash
xdg-open target/site/jacoco/index.html
```

### Cobertura de Testes

O projeto está configurado com **JaCoCo** para garantir **mínimo de 70% de cobertura**.

**Métricas:**
- ✅ **Use Cases**: 100% de cobertura
- ✅ **Entidade Domain**: 100% de cobertura
- ✅ **Repositories**: Testes de integração completos
- ✅ **Controllers**: Testes de integração completos

### Exemplo de Teste

```java
// Teste Unitário - Use Case
@Test
@DisplayName("Deve criar produto com dados válidos")
void deveCriarProdutoComDadosValidos() {
    // Arrange
    when(repositoryPort.salvar(any(Produto.class)))
        .thenReturn(produtoEsperado);

    // Act
    Produto resultado = useCase.executar(
        "Notebook", "Dell", new BigDecimal("2999.99"), 10, "Eletrônicos"
    );

    // Assert
    assertThat(resultado).isNotNull();
    assertThat(resultado.getNome()).isEqualTo("Notebook");
    verify(repositoryPort, times(1)).salvar(any(Produto.class));
}
```

## 🐳 Docker

### Usando Docker Compose (Recomendado)

#### Construir e executar
```bash
docker-compose up -d --build
```

#### Ver logs
```bash
docker-compose logs -f microservice-produtos
```

#### Parar containers
```bash
docker-compose down
```

### Usando Docker diretamente

#### Construir imagem
```bash
docker build -t microservice-produtos:1.0.0 .
```

#### Executar container
```bash
docker run -d -p 8080:8080 --name microservice-produtos microservice-produtos:1.0.0
```

#### Ver logs
```bash
docker logs -f microservice-produtos
```

### Acessar aplicação containerizada

- **API:** http://localhost:8080/api/produtos
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **H2 Console:** http://localhost:8080/h2-console

### Dockerfile - Multi-stage Build

O projeto usa **multi-stage build** para otimizar a imagem:

1. **Estágio Build:** Compila com Maven
2. **Estágio Runtime:** Imagem final otimizada (~150MB)

**Benefícios:**
- ✅ Imagem menor e mais rápida
- ✅ Mais segura (usuário não-root)
- ✅ Otimizada para produção

## 📚 Documentação da API

### Swagger UI

Acesse a documentação interativa em: **http://localhost:8080/swagger-ui.html**

O Swagger UI permite:
- ✅ Visualizar todos os endpoints
- ✅ Ver os modelos de request/response
- ✅ Testar a API diretamente no navegador
- ✅ Ver códigos de resposta HTTP
- ✅ Exemplos de requisições

### OpenAPI Specification

A especificação OpenAPI (JSON) está disponível em:
**http://localhost:8080/v3/api-docs**

## 🎯 Benefícios da Clean Architecture

### Antes (Layered Architecture)
```
Controller → Service → Repository → Model (JPA)
```

**Problemas:**
- ❌ Acoplamento ao framework (JPA)
- ❌ Difícil testar regras de negócio
- ❌ Difícil trocar tecnologias
- ❌ Lógica espalhada

### Depois (Clean Architecture)
```
Presentation → Application → Domain ← Infrastructure
```

**Benefícios:**
- ✅ **Desacoplamento:** Domain não depende de frameworks
- ✅ **Testabilidade:** Testes unitários rápidos e isolados
- ✅ **Flexibilidade:** Fácil trocar JPA por MongoDB, por exemplo
- ✅ **Manutenibilidade:** Responsabilidades bem definidas
- ✅ **SOLID:** Seguimento dos princípios de design
- ✅ **Escalabilidade:** Fácil adicionar novos casos de uso

### Fluxo de Dados

```
HTTP Request → Controller (Presentation)
                    ↓
         Converte DTO → Domain
                    ↓
              Use Case (Application)
                    ↓
         Valida e executa regras
                    ↓
         Repository Port (Domain Interface)
                    ↓
    Repository Impl (Infrastructure)
                    ↓
         Converte Domain → JPA
                    ↓
              Banco de Dados
                    ↓
         Converte JPA → Domain
                    ↓
              Use Case
                    ↓
         Converte Domain → DTO
                    ↓
         Controller → HTTP Response
```

## 🚀 Próximos Passos

Para evoluir este microserviço, considere adicionar:

### Nível Intermediário
- [ ] **PostgreSQL** - Trocar H2 por banco de produção
- [ ] **Migrations** - Flyway ou Liquibase
- [ ] **Segurança** - Spring Security com JWT
- [ ] **Paginação** - Spring Data Pageable
- [ ] **Cache** - Redis com Spring Cache
- [ ] **Validação Avançada** - Custom validators

### Nível Avançado
- [ ] **Service Discovery** - Eureka Server/Client
- [ ] **API Gateway** - Spring Cloud Gateway
- [ ] **Circuit Breaker** - Resilience4j
- [ ] **Message Queue** - RabbitMQ ou Kafka
- [ ] **Distributed Tracing** - Spring Cloud Sleuth/Zipkin
- [ ] **Configuração Centralizada** - Spring Cloud Config
- [ ] **Observabilidade** - Prometheus + Grafana
- [ ] **Event Sourcing** - CQRS pattern
- [ ] **Logging Estruturado** - ELK Stack

## 📖 Referências

- [Clean Architecture - Robert C. Martin](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Hexagonal Architecture - Alistair Cockburn](https://alistair.cockburn.us/hexagonal-architecture/)
- [Domain-Driven Design - Eric Evans](https://www.domainlanguage.com/ddd/)
- [SOLID Principles](https://en.wikipedia.org/wiki/SOLID)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

## 👨‍💻 Autor

**Flávio Henrique**

Projeto desenvolvido para demonstrar boas práticas de arquitetura de software com:
- Clean Architecture
- SOLID Principles
- Test-Driven Development
- Domain-Driven Design

## 📄 Licença

Este projeto está sob a licença MIT. Sinta-se livre para usar, modificar e distribuir.

---

⭐ Se este projeto foi útil, considere dar uma estrela!

🐛 Encontrou algum problema? Abra uma [issue](../../issues).

💡 Tem sugestões? Pull requests são bem-vindos!


