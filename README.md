# Microserviço de Produtos - E-commerce

Este é um microserviço básico desenvolvido com Java e Spring Boot para gerenciamento de produtos em um sistema de e-commerce. Este projeto serve como base educacional para compreender os fundamentos de microserviços.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA** - Para persistência de dados
- **H2 Database** - Banco de dados em memória
- **Lombok** - Para reduzir boilerplate
- **Maven** - Gerenciamento de dependências

## Estrutura do Projeto

```
microservice-produtos/
├── src/main/java/com/ecommerce/produtos/
│   ├── MicroserviceProdutosApplication.java
│   ├── controller/          # Camada de controle REST
│   ├── service/             # Camada de serviço (lógica de negócio)
│   ├── repository/          # Camada de persistência
│   ├── model/               # Entidades JPA
│   ├── dto/                 # Data Transfer Objects
│   └── exception/           # Tratamento de exceções
├── src/main/resources/
│   └── application.properties
└── src/test/                # Testes unitários e de integração
```

## Pré-requisitos

- JDK 17 ou superior
- Maven 3.6 ou superior

## Como Executar

### 1. Compilar o projeto

```bash
cd /home/flavio/Projetos/microservice-produtos
mvn clean install
```

### 2. Executar a aplicação

```bash
mvn spring-boot:run
```

Ou executar diretamente o arquivo JAR:

```bash
java -jar target/microservice-produtos-1.0.0.jar
```

A aplicação estará disponível em: `http://localhost:8080`

### 3. Acessar o Console H2

O console H2 está disponível em: `http://localhost:8080/h2-console`

**Configurações de conexão:**
- JDBC URL: `jdbc:h2:mem:produtosdb`
- Username: `sa`
- Password: (deixe em branco)

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

## Validações Implementadas

- **Nome**: Obrigatório, entre 3 e 100 caracteres
- **Descrição**: Opcional, máximo 500 caracteres
- **Preço**: Obrigatório, maior que zero
- **Estoque**: Obrigatório, não pode ser negativo
- **Categoria**: Obrigatória, máximo 50 caracteres

## Executando os Testes

Para executar todos os testes:

```bash
mvn test
```

## Containerização com Docker

Este projeto inclui suporte completo para Docker, permitindo executar a aplicação de forma isolada e consistente em qualquer ambiente.

### Pré-requisitos

- Docker instalado (versão 20.10 ou superior)
- Docker Compose instalado (versão 1.29 ou superior) - opcional, mas recomendado

### Opção 1: Usando Docker diretamente

#### Passo 1: Construir a imagem Docker

```bash
cd /home/flavio/Projetos/microservice-produtos
docker build -t microservice-produtos:1.0.0 .
```

**Explicação:**
- `docker build`: Comando para construir a imagem
- `-t microservice-produtos:1.0.0`: Define o nome e versão da imagem (tag)
- `.`: Indica que o Dockerfile está no diretório atual

#### Passo 2: Executar o container

```bash
docker run -d -p 8080:8080 --name microservice-produtos microservice-produtos:1.0.0
```

**Explicação:**
- `docker run`: Comando para executar um container
- `-d`: Executa em modo detached (background)
- `-p 8080:8080`: Mapeia a porta 8080 do container para a porta 8080 do host
- `--name microservice-produtos`: Define um nome para o container
- `microservice-produtos:1.0.0`: Nome da imagem a ser executada

#### Passo 3: Verificar se está rodando

```bash
docker ps
```

Você deve ver o container `microservice-produtos` na lista.

#### Passo 4: Ver os logs

```bash
docker logs microservice-produtos
```

#### Passo 5: Parar o container

```bash
docker stop microservice-produtos
```

#### Passo 6: Remover o container

```bash
docker rm microservice-produtos
```

### Opção 2: Usando Docker Compose (Recomendado)

Docker Compose facilita o gerenciamento de containers e é ideal para desenvolvimento.

#### Passo 1: Construir e executar

```bash
cd /home/flavio/Projetos/microservice-produtos
docker-compose up -d --build
```

**Explicação:**
- `docker-compose up`: Cria e inicia os containers
- `-d`: Executa em modo detached (background)
- `--build`: Reconstrói as imagens antes de iniciar

#### Passo 2: Ver os logs

```bash
docker-compose logs -f microservice-produtos
```

O `-f` permite acompanhar os logs em tempo real (similar ao `tail -f`).

#### Passo 3: Verificar status

```bash
docker-compose ps
```

#### Passo 4: Parar os containers

```bash
docker-compose down
```

#### Passo 5: Parar e remover volumes (limpeza completa)

```bash
docker-compose down -v
```

### Entendendo os Arquivos de Containerização

#### Dockerfile

O `Dockerfile` utiliza uma estratégia de **multi-stage build** para otimizar a imagem final:

1. **Estágio de Build**: Compila a aplicação usando Maven
   - Usa a imagem `maven:3.9.6-eclipse-temurin-21`
   - Baixa dependências e compila o projeto
   - Gera o arquivo JAR

2. **Estágio de Runtime**: Cria a imagem final otimizada
   - Usa apenas o JRE (não precisa do JDK/Maven)
   - Copia o JAR compilado do estágio anterior
   - Executa como usuário não-root para segurança
   - Imagem final muito menor (~150MB vs ~700MB)

**Vantagens:**
- Imagem final menor (mais rápida para baixar e executar)
- Mais segura (usuário não-root)
- Otimizada para produção

#### .dockerignore

Arquivo que especifica quais arquivos/diretórios devem ser **ignorados** durante o build:

- Evita copiar arquivos desnecessários (como `target/`, `.idea/`, etc.)
- Reduz o tamanho do contexto de build
- Acelera o processo de build

#### docker-compose.yml

Arquivo de configuração que define:

- Como construir a imagem
- Portas a expor
- Variáveis de ambiente
- Healthcheck (verificação de saúde)
- Política de restart
- Redes Docker

**Vantagens:**
- Comandos mais simples
- Configuração versionada no código
- Fácil de compartilhar com a equipe

### Acessando a Aplicação Containerizada

Após iniciar o container, a aplicação estará disponível em:

- **API**: `http://localhost:8080/api/produtos`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **H2 Console**: `http://localhost:8080/h2-console`

### Comandos Úteis

```bash
# Ver logs em tempo real
docker logs -f microservice-produtos

# Executar comando dentro do container
docker exec -it microservice-produtos sh

# Inspecionar a imagem
docker image inspect microservice-produtos:1.0.0

# Ver uso de recursos
docker stats microservice-produtos

# Reconstruir apenas se necessário
docker-compose up -d --build

# Limpar imagens não utilizadas
docker system prune -a
```

### Troubleshooting

**Problema: Porta 8080 já em uso**
```bash
# Verificar qual processo está usando a porta
sudo lsof -i :8080

# Ou alterar a porta no docker-compose.yml:
# ports:
#   - "8081:8080"  # Mapeia porta do host 8081 para 8080 do container
```

**Problema: Container para imediatamente**
```bash
# Verificar os logs para identificar o erro
docker logs microservice-produtos
```

**Problema: Imagem não encontrada**
```bash
# Verificar se a imagem foi construída
docker images | grep microservice-produtos

# Se não existir, construir novamente
docker build -t microservice-produtos:1.0.0 .
```

## Próximos Passos (Complexidade Intermediária)

Para evoluir este microserviço, podemos adicionar:

1. **Comunicação entre Serviços** - Spring Cloud OpenFeign
2. **Service Discovery** - Eureka Server/Client
3. **API Gateway** - Spring Cloud Gateway
4. **Message Queue** - RabbitMQ ou Apache Kafka
5. **Circuit Breaker** - Resilience4j
6. **Distributed Tracing** - Spring Cloud Sleuth/Zipkin
7. **Configuração Centralizada** - Spring Cloud Config
8. **Documentação da API** - Swagger/OpenAPI (já implementado)
9. **Segurança** - Spring Security com JWT
10. **Monitoramento** - Prometheus e Grafana

## Estrutura de Camadas

Este projeto segue o padrão de arquitetura em camadas:

1. **Controller** - Recebe requisições HTTP e retorna respostas
2. **Service** - Contém a lógica de negócio
3. **Repository** - Acessa o banco de dados
4. **Model** - Representa as entidades do domínio
5. **DTO** - Objetos de transferência de dados (desacopla o modelo interno da API)
6. **Exception** - Tratamento centralizado de exceções

## Autor

Desenvolvido para fins educacionais sobre microserviços com Java e Spring Boot.


