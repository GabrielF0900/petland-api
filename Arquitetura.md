# 🏗️ Arquitetura — petland-api

> **API RESTful para Gestão de Petshop** · Spring Boot 3.3.0 · Java 21 · PostgreSQL

---

## 📦 Visão Geral

O projeto segue uma arquitetura em **três camadas principais** inspirada em **DDD (Domain-Driven Design)** combinada com os princípios de **Clean Architecture**. A separação das responsabilidades é feita pelos pacotes `api`, `domain` e `infra`.

```
┌──────────────────────────────────────────────────┐
│                   HTTP Client                    │
└─────────────────────┬────────────────────────────┘
                      │
┌─────────────────────▼────────────────────────────┐
│              api  (Camada de Entrada)             │
│            Controllers REST (@RestController)     │
└─────────────────────┬────────────────────────────┘
                      │
┌─────────────────────▼────────────────────────────┐
│           domain  (Núcleo do Negócio)             │
│  DTOs · Models · Repositories · Services         │
└──────────┬──────────────────────────┬────────────┘
           │                          │
┌──────────▼──────────┐  ┌────────────▼────────────┐
│  infra / config     │  │   infra / exception      │
│  (Configurações)    │  │   (Tratamento de Erros)  │
└─────────────────────┘  └─────────────────────────┘
                      │
┌─────────────────────▼────────────────────────────┐
│               PostgreSQL (porta 9000)             │
│             (via Docker Compose)                  │
└──────────────────────────────────────────────────┘
```

---

## 🗂️ Árvore de Arquivos Completa

```
petland-api/
│
├── 📄 pom.xml                          # Configuração Maven (dependências e build)
├── 📄 docker-compose.yaml              # Sobe PostgreSQL (porta 9000) + rede petland-network
├── 📄 mvnw / mvnw.cmd                  # Maven Wrapper (Linux / Windows)
├── 📄 .env                             # Variáveis de ambiente (ignorado pelo git)
├── 📄 .env.development                 # Variáveis de ambiente de desenvolvimento
├── 📄 .env.example                     # Exemplo de variáveis de ambiente
├── 📄 .gitignore                       # Arquivos ignorados pelo Git
├── 📄 .gitattributes                   # Configurações de atributos Git
├── 📄 README.md                        # Documentação geral do projeto
├── 📄 HELP.md                          # Ajuda gerada pelo Spring Initializr
├── 📄 requisitos_mvp.pdf               # Documento de requisitos do MVP
│
├── 📁 .mvn/                            # Configurações internas do Maven Wrapper
├── 📁 .vscode/                         # Configurações do VS Code
├── 📁 target/                          # Artefatos de build (gerado pelo Maven)
│
└── 📁 src/
    ├── 📁 main/
    │   ├── 📁 java/com/petlandapi/
    │   │   │
    │   │   ├── 📄 PetlandapiApplication.java       # [CLASS] Ponto de entrada da aplicação
    │   │   │                                        # @SpringBootApplication
    │   │   │
    │   │   ├── 📁 api/                             # CAMADA DE APRESENTAÇÃO (Controllers)
    │   │   │   └── 📁 controller/
    │   │   │       ├── 📄 PetController.java        # [CLASS] Controller REST de Pets
    │   │   │       │                                # @RestController · @RequestMapping("/pets")
    │   │   │       │                                # GET /pets → listarPets()
    │   │   │       └── 📄 OwnerController.java      # [CLASS] Controller REST de Tutores
    │   │   │                                        # (estrutura criada, sem endpoints ainda)
    │   │   │
    │   │   ├── 📁 domain/                          # CAMADA DE DOMÍNIO (Núcleo de Negócio)
    │   │   │   │
    │   │   │   ├── 📁 model/                       # Entidades de Domínio
    │   │   │   │   ├── 📄 Pet.java                 # [CLASS] Entidade Pet
    │   │   │   │   ├── 📄 Owner.java               # [CLASS] Entidade Tutor/Dono
    │   │   │   │   └── 📄 PetSpecies.java           # [INTERFACE] Contrato para espécies de Pet
    │   │   │   │
    │   │   │   ├── 📁 dto/                         # Data Transfer Objects
    │   │   │   │   ├── 📄 PetRequestDTO.java        # [RECORD] DTO de entrada para Pet
    │   │   │   │   ├── 📄 PetResponseDTO.java       # [RECORD] DTO de saída para Pet
    │   │   │   │   ├── 📄 OwnerRequestDTO.java      # [RECORD] DTO de entrada para Tutor
    │   │   │   │   └── 📄 OwnerResponseDTO.java     # [RECORD] DTO de saída para Tutor
    │   │   │   │
    │   │   │   ├── 📁 repository/                  # Contratos de Persistência (Ports)
    │   │   │   │   ├── 📄 PetRepository.java        # [INTERFACE] Repositório de Pets
    │   │   │   │   └── 📄 OwnerRepository.java      # [INTERFACE] Repositório de Tutores
    │   │   │   │
    │   │   │   └── 📁 service/                     # Serviços e Padrões de Domínio
    │   │   │       ├── 📄 PetApplicationService.java  # [CLASS] Serviço de aplicação de Pet
    │   │   │       │                                   # Orquestra casos de uso de Pet
    │   │   │       ├── 📄 OwnerApplicationService.java # [CLASS] Serviço de aplicação de Tutor
    │   │   │       │                                   # Orquestra casos de uso de Tutor
    │   │   │       ├── 📄 PetDomainService.java     # [CLASS] Serviço de domínio de Pet
    │   │   │       │                                # Lógica de negócio pura de Pet
    │   │   │       ├── 📄 PetFactory.java           # [CLASS] Padrão Factory para criação de Pet
    │   │   │       │                                # Centraliza instanciação de objetos Pet
    │   │   │       ├── 📄 PetBuilder.java           # [CLASS] Padrão Builder para montagem de Pet
    │   │   │       │                                # Construção fluente de objetos Pet
    │   │   │       ├── 📄 PetValidationStrategy.java # [CLASS] Padrão Strategy para validação
    │   │   │       │                                  # Validações intercambiáveis de Pet
    │   │   │       ├── 📄 PetCreatedEvent.java      # [CLASS] Evento de domínio: Pet criado
    │   │   │       │                                # Representa o evento após criação de Pet
    │   │   │       └── 📄 PetCreatedListener.java   # [CLASS] Listener/Observer do evento
    │   │   │                                        # Reage ao evento PetCreatedEvent
    │   │   │
    │   │   └── 📁 infra/                           # CAMADA DE INFRAESTRUTURA
    │   │       │
    │   │       ├── 📁 config/                      # Configurações da aplicação
    │   │       │   ├── 📄 ApplicationConfig.java   # [CLASS] Configurações gerais do Spring
    │   │       │   └── 📄 SwaggerConfig.java       # [CLASS] Configuração do SpringDoc/OpenAPI
    │   │       │                                   # Documentação automática da API
    │   │       │
    │   │       └── 📁 exception/                   # Tratamento global de exceções
    │   │           ├── 📄 GlobalExceptionHandler.java # [CLASS] Handler global de exceções
    │   │           │                                   # @ControllerAdvice (a implementar)
    │   │           └── 📄 ErrorResponse.java           # [RECORD] DTO de resposta de erro
    │   │                                               # Estrutura padronizada de erro HTTP
    │   │
    │   └── 📁 resources/
    │       └── 📄 application.properties           # Configurações da aplicação
    │                                               # BD: jdbc:postgresql://localhost:9000/petland
    │
    └── 📁 test/
        └── 📁 java/com/petlandapi/
            └── 📄 PetlandapiApplicationTests.java  # [CLASS] Teste de contexto Spring
                                                    # @SpringBootTest · contextLoads()
```

---

## 🧩 Detalhamento das Camadas

### 🔵 `api` — Camada de Apresentação

| Arquivo | Tipo | Responsabilidade |
|---|---|---|
| `PetController` | `@RestController` | Expõe endpoints REST para recursos de Pet. Atualmente: `GET /pets`. |
| `OwnerController` | `Class` (em desenvolvimento) | Futuros endpoints REST para recursos de Tutor/Dono. |

---

### 🟢 `domain` — Núcleo de Negócio (DDD)

#### 📌 `model` — Entidades

| Arquivo | Tipo | Responsabilidade |
|---|---|---|
| `Pet` | `Class` | Entidade principal que representa um pet no sistema. |
| `Owner` | `Class` | Entidade que representa o tutor/dono de um pet. |
| `PetSpecies` | `Interface` | Define o contrato para tipos de espécie de pet (extensibilidade). |

#### 📌 `dto` — Data Transfer Objects

| Arquivo | Tipo | Responsabilidade |
|---|---|---|
| `PetRequestDTO` | `Record` | Dados recebidos na criação/atualização de um Pet. |
| `PetResponseDTO` | `Record` | Dados devolvidos ao cliente sobre um Pet. |
| `OwnerRequestDTO` | `Record` | Dados recebidos na criação/atualização de um Tutor. |
| `OwnerResponseDTO` | `Record` | Dados devolvidos ao cliente sobre um Tutor. |

> Uso de `record` garante imutabilidade e menor verbosidade (Java 17+).

#### 📌 `repository` — Contratos de Persistência (Ports)

| Arquivo | Tipo | Responsabilidade |
|---|---|---|
| `PetRepository` | `Interface` | Define as operações de persistência para Pet (Port do DDD). |
| `OwnerRepository` | `Interface` | Define as operações de persistência para Owner (Port do DDD). |

#### 📌 `service` — Serviços e Padrões de Design

| Arquivo | Tipo | Padrão | Responsabilidade |
|---|---|---|---|
| `PetApplicationService` | `Class` | Application Service | Orquestra casos de uso relacionados a Pet. |
| `OwnerApplicationService` | `Class` | Application Service | Orquestra casos de uso relacionados a Owner. |
| `PetDomainService` | `Class` | Domain Service | Encapsula lógica de negócio que não pertence à entidade Pet. |
| `PetFactory` | `Class` | **Factory** | Centraliza e encapsula a criação de instâncias de Pet. |
| `PetBuilder` | `Class` | **Builder** | Construção fluente e passo-a-passo de objetos Pet. |
| `PetValidationStrategy` | `Class` | **Strategy** | Permite trocar algoritmos de validação de Pet em tempo de execução. |
| `PetCreatedEvent` | `Class` | **Domain Event** | Representa o evento de domínio disparado ao criar um Pet. |
| `PetCreatedListener` | `Class` | **Observer/Listener** | Reage ao evento `PetCreatedEvent` executando ações secundárias. |

---

### 🔴 `infra` — Camada de Infraestrutura

#### 📌 `config` — Configurações

| Arquivo | Tipo | Responsabilidade |
|---|---|---|
| `ApplicationConfig` | `Class` | Configurações gerais de beans e componentes Spring. |
| `SwaggerConfig` | `Class` | Configuração do SpringDoc OpenAPI para documentação interativa da API. |

#### 📌 `exception` — Tratamento de Erros

| Arquivo | Tipo | Responsabilidade |
|---|---|---|
| `GlobalExceptionHandler` | `Class` | Handler centralizado de exceções via `@ControllerAdvice`. |
| `ErrorResponse` | `Record` | DTO imutável que padroniza o formato de respostas de erro HTTP. |

---

## 🔧 Stack e Dependências

| Tecnologia | Versão | Função |
|---|---|---|
| **Java** | 21 | Linguagem principal |
| **Spring Boot** | 3.3.0 | Framework base da aplicação |
| **Spring Web** | — | Suporte a REST (`@RestController`, `@RequestMapping`) |
| **Spring Data JPA** | — | Abstração de persistência com Hibernate |
| **Spring Validation** | — | Validação de beans com Bean Validation (JSR-380) |
| **Spring Actuator** | — | Endpoints de saúde/métricas (`/actuator/health`) |
| **Micrometer Prometheus** | — | Exportação de métricas para Prometheus |
| **SpringDoc OpenAPI** | 2.5.0 | Geração automática de documentação Swagger UI |
| **PostgreSQL Driver** | — | Conexão com banco de dados PostgreSQL |
| **Spring DevTools** | — | Hot reload durante desenvolvimento |
| **Spring Boot Test** | — | Infraestrutura de testes com JUnit 5 |
| **JaCoCo** | 0.8.12 | Cobertura de código (mínimo configurado: **80%**) |
| **Docker Compose** | — | Sobe PostgreSQL na porta `9000` |

---

## 🗄️ Infraestrutura de Dados

```yaml
# docker-compose.yaml
Banco:       PostgreSQL 15 (Alpine)
Container:   petland_postgres
Porta:       9000 (host) → 5432 (container)
Database:    petland
Usuário:     postgres
Volume:      postgres_petland_volume
Rede:        petland-network (bridge)
```

```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:9000/petland
spring.datasource.username=postgres
spring.datasource.password=postgres
```

---

## 🧪 Testes

| Arquivo | Tipo | Responsabilidade |
|---|---|---|
| `PetlandapiApplicationTests` | `@SpringBootTest` | Verifica se o contexto Spring carrega sem erros (`contextLoads()`). |

> Cobertura mínima exigida pelo JaCoCo: **80% de linhas**.

---

## 🎯 Padrões de Design Identificados

```
┌─────────────────────────────────────────────────────────────────┐
│                    Padrões Utilizados                           │
├─────────────────┬───────────────────────────────────────────────┤
│  Factory        │  PetFactory       — criação de objetos        │
│  Builder        │  PetBuilder       — construção fluente        │
│  Strategy       │  PetValidation    — algoritmos intercambiáveis│
│  Observer       │  PetCreatedEvent  — eventos de domínio        │
│                 │  PetCreatedListener                           │
│  Repository     │  PetRepository    — abstração de persistência │
│  (Port/Adapter) │  OwnerRepository                              │
│  DTO / Record   │  *RequestDTO      — dados de entrada          │
│                 │  *ResponseDTO     — dados de saída            │
└─────────────────┴───────────────────────────────────────────────┘
```

---

## 📊 Diagrama de Fluxo de Requisição

```
HTTP Request
     │
     ▼
[PetController / OwnerController]   ← api.controller
     │
     ▼  (chama)
[PetApplicationService / OwnerApplicationService]  ← domain.service
     │
     ├──► [PetDomainService]        ← Regras de negócio
     ├──► [PetFactory / PetBuilder] ← Criação de entidades
     ├──► [PetValidationStrategy]   ← Validação
     │
     ▼  (persiste via)
[PetRepository / OwnerRepository]   ← domain.repository (interface)
     │
     ▼  (implementado por Spring Data JPA)
[PostgreSQL]                        ← banco de dados

     │  (ao criar um Pet)
     ▼
[PetCreatedEvent]  →  [PetCreatedListener]  ← domain.service (eventos)
```

---

*Gerado em: 23/06/2026 · petland-api v0.0.1-SNAPSHOT*
