# 🐾 petland-api

> **REST API backend para gestão de uma petshop**, construída como projeto-âncora do Bootcamp v6 de 7 semanas. O `petland-api` não é apenas um CRUD — é o laboratório vivo onde cada padrão, ferramenta e conceito estudado é aplicado imediatamente, transformando teoria em portfólio público.

---

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Objetivos do Projeto](#objetivos-do-projeto)
- [Stack Tecnológica](#stack-tecnológica)
- [Arquitetura e Design](#arquitetura-e-design)
- [Roadmap por Semana](#roadmap-por-semana)
- [Padrões Implementados](#padrões-implementados)
- [Qualidade de Código](#qualidade-de-código)
- [Como Rodar Localmente](#como-rodar-localmente)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Conventional Commits](#conventional-commits)

---

## Sobre o Projeto

O `petland-api` é o projeto-laboratório central do Bootcamp v6, desenvolvido durante 7 semanas intensivas (Junho 2026). Ele serve como veículo prático para consolidar Java 17+ moderno, Design Patterns GoF, Domain-Driven Design, testes automatizados, containerização com Docker e orquestração com Kubernetes — tudo aplicado a um domínio real e compreensível: uma petshop.

A cada bloco de estudo, o conceito aprendido é implementado imediatamente no Petland. Isso garante que o repositório reflita não só o produto final, mas a evolução técnica semana a semana.

---

## Objetivos do Projeto

### Objetivo Principal
Construir uma API REST de produção com Java 21 e Spring Boot que demonstre domínio completo do ciclo de desenvolvimento profissional — do código limpo à entrega containerizada.

### Objetivos Técnicos Específicos

#### Java 17+ Moderno (Semana 1)
- Eliminar boilerplate com **Records** como substitutos de POJOs nas entidades do domínio
- Usar **Pattern Matching for `instanceof`** para type checking expressivo
- Aplicar **Sealed Classes** para restringir hierarquias no domínio (ex: tipos de Pet)
- Usar **Switch Expressions** retornando valores diretamente
- Utilizar **Text Blocks** para queries SQL nos repositórios
- **Meta:** entidades do Petland reimplementadas como Records com Pattern Matching, zero boilerplate getter/setter

#### Design Patterns GoF (Semana 1)
- Implementar **Singleton** para `ApplicationConfig`
- Implementar **Factory Method** para entidades polimórficas
- Implementar **Builder** para `PetBuilder`
- Refatorar o projeto com **Strategy** para validações
- Implementar **Observer** para eventos de domínio
- Aplicar todos os 5 princípios **SOLID**, especialmente Single Responsibility no Controller
- **Meta:** 5 padrões (Singleton, Factory, Builder, Strategy, Observer) implementados e testados

#### Domain-Driven Design (Semana 1 e 2)
- Identificar e modelar **Entidades**, **Value Objects** e **Aggregates** do domínio Petland no caderno
- Definir **Aggregate Roots** e seus Repositories
- Separar **Domain Services** (lógica de negócio pura) de **Application Services** (coordenação de use cases)
- Garantir que `ApplicationService` não importe repositórios diretamente
- Manter fluxo limpo: `Controller → ApplicationService → DomainService → Repository`
- **Meta:** diagrama de domínio DDD do Petland documentado; separação arquitetural validada por testes

#### Testes Automatizados (Semana 1 e 2)
- Cobertura de **JUnit 5 + Mockito** nos casos de uso principais
- Testes de exceção com `assertThrows`
- Mocks de repositório retornando vazio para cenários de erro
- Testes de controller com **MockMvc**
- Teste de integração com `@SpringBootTest` e PostgreSQL real em container
- Barreira de cobertura de **≥ 80% com JaCoCo** — badge publicado no README
- **Meta:** todos os quizzes do módulo Rocketseat concluídos + JaCoCo ≥ 80% no Petland

#### Qualidade de Código (Semana 1)
- **SonarQube** local via Docker escaneando o projeto
- **JaCoCo** configurado no `pom.xml` com relatório de cobertura
- **Quality Gate** com barreira de 80% bloqueando build se abaixo
- Corrigir no mínimo 2 issues críticos identificados pelo SonarQube
- **Meta:** pipeline de qualidade local rodando; zero issues críticos no scan

#### Git Profissional (Semana 1)
- Branches por feature: `feat/petland-domain`, nunca commitar na `main` diretamente
- **Rebase interativo** (`git rebase -i HEAD~3`): editar, squash e reordenar commits
- **Cherry-pick** de commits específicos entre branches
- Histórico limpo com 10+ **Conventional Commits** em inglês: `feat`, `fix`, `refactor`, `docs`, `test`, `chore`
- README em inglês com estrutura profissional desde o início
- **Meta:** repositório com Git Flow profissional e histórico auditável

#### Docker (Semana 1 e 2)
- **Dockerfile single-stage** manual: `FROM eclipse-temurin:21-jre-alpine`, `WORKDIR /app`, `COPY`, `RUN`
- **Non-root user** por segurança: `RUN addgroup -S app && adduser -S app -G app`, `USER app`
- `.dockerignore` para excluir artefatos desnecessários
- `CMD` vs `ENTRYPOINT`: saber quando usar cada um
- **Multi-stage build** na integração com Spring Cloud (Semana 4)
- `docker-compose.yml` completo com `app + PostgreSQL + Redis`
- **Meta:** imagem Docker do Petland com non-root user; `docker build` + `docker run` funcionando

#### Monitoramento (Semana 2)
- **Spring Actuator** com endpoint `/actuator/prometheus` exposto
- **Prometheus** em container coletando métricas do Petland
- **Grafana** com dashboard de métricas JVM: heap, GC, threads, latência de endpoints, contagem de requisições
- **Meta:** dashboard Grafana mostrando métricas reais da JVM em produção local

---

## Stack Tecnológica

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 (Records, Sealed Classes, Pattern Matching, Switch Expressions) |
| Framework | Spring Boot 3.x |
| Persistência | Spring Data JPA + PostgreSQL |
| Cache | Redis |
| Validação | Spring Validation (`@Valid`, `@RestControllerAdvice`) |
| Testes | JUnit 5, Mockito, MockMvc, Testcontainers |
| Cobertura | JaCoCo (Quality Gate ≥ 80%) |
| Qualidade | SonarQube (local via Docker) |
| Build | Maven |
| Containerização | Docker + Docker Compose |
| Orquestração | Kubernetes (Minikube) — K8s N1 |
| Monitoramento | Spring Actuator + Prometheus + Grafana |
| Versionamento | Git + GitHub (Conventional Commits, branches por feature) |

---

## Arquitetura e Design

### Fluxo de uma Requisição

```
Client
  │
  └── REST Controller
        │
        └── Application Service   ← coordena use cases, sem lógica de negócio
              │
              └── Domain Service  ← lógica pura do domínio Petland
                    │
                    └── Repository (por Aggregate Root) → PostgreSQL
```

### Modelo de Domínio DDD

O domínio do Petland é modelado seguindo DDD, com as seguintes estruturas identificadas no caderno de arquitetura:

- **Entidades:** `Pet`, `Owner`, `Appointment`, `Product`
- **Value Objects:** imutáveis, sem identidade própria (ex: `Address`, `PhoneNumber`)
- **Aggregates:** agrupamentos consistentes em torno de uma Aggregate Root
- **Aggregate Roots:** ponto único de acesso ao aggregate; repositório existe por Aggregate Root
- **Domain Services:** lógica que não pertence a uma Entidade (ex: regras de disponibilidade de horário)
- **Application Services:** coordenam use cases sem conter lógica de negócio

### Entidades como Records (Java 21)

```java
// Antes: POJO com boilerplate
public class Pet {
    private String name;
    private String species;
    // getter, setter, equals, hashCode, toString...
}

// Depois: Record imutável com Pattern Matching
public record Pet(String name, String species, Owner owner) {}
```

### Padrões de Design Aplicados

| Padrão | Onde no Petland |
|---|---|
| Singleton | `ApplicationConfig` |
| Factory Method | Criação de entidades polimórficas por tipo de pet |
| Builder | `PetBuilder` — construção fluente de objetos complexos |
| Strategy | Validações intercambiáveis (ex: regras de agendamento) |
| Observer | Eventos de domínio (ex: notificação ao criar novo Pet) |

---

## Roadmap por Semana

### Semana 1 — Fundação: Java Moderno, Design Patterns, DDD, Testes, Docker

**Segunda-feira**
- Records, Pattern Matching, Sealed Classes: entidades do Petland reimplementadas
- Design Patterns (aulas 1–4): Singleton, Factory Method, Builder implementados
- DDD completo (4 aulas Rocketseat): modelagem do domínio no caderno

**Terça-feira**
- Design Patterns (aulas 5–9) + SOLID completo: Strategy e Observer implementados
- Spring Data JPA + DDD: mapeamento de entidades, `@Valid`, `@RestControllerAdvice`
- Testes (aulas 1–5): JUnit 5 + Mockito; primeiros testes replicados no Petland

**Quarta-feira**
- Git Avançado (sessão única permanente): branches, rebase, squash, Conventional Commits
- Docker single-stage: Dockerfile com non-root user
- Qualidade de Código (6 aulas): SonarQube + JaCoCo com Quality Gate 80%

**Quinta-feira**
- DDD na Prática: Application Services vs Domain Services; refatoração do Petland
- Testes (aulas 6–9): MockMvc, testes de controller, teste de integração com banco real
- Spring Actuator + Prometheus + Grafana: dashboard JVM

**Sexta-feira**
- Active Recall: 5 padrões reproduzidos de cabeça no caderno
- Docker Active Recall: Dockerfile reconstruído do zero sem consultar material
- Testes (aulas 10–14) + JaCoCo ≥ 80% verificado

**Sábado**
- Deep Work: README do Petland em inglês com diagrama DDD, badge JaCoCo, setup Docker
- Active Recall: 2 Design Patterns no caderno sem consultar

**Meta da Semana 1:** `petland-api` com Java moderno + DDD + 5 Design Patterns + Testes + SonarQube + Docker + Git Flow profissional. JaCoCo ≥ 80%. Repositório público no GitHub.

---

### Semana 2 — Consolidação e Integração com Spring Cloud (Início)

O Petland continua como base de prática para:
- Integração com Spring Cloud Config (configuração externalizada)
- Application Services testando a separação limpa com repositórios
- Commit final com tag `v1.0.0` e README em inglês

---

### Semanas 3 e 4 — Integração com Ecossistema Microsserviços

O Petland migra para o ecossistema `Nebula-Archive` (microsserviços), onde os padrões do Petland se tornam a referência de qualidade para os demais serviços:
- Containerizado no `docker-compose.yml` com startup order via healthchecks
- Manifestos Kubernetes: `Deployment + Service + ConfigMap + Secret` — zero valores hardcoded
- Multi-stage Dockerfile para otimização de imagem
- Diagrama de arquitetura K8s publicado no README

---

## Padrões Implementados

### SOLID

| Princípio | Aplicação no Petland |
|---|---|
| **S** — Single Responsibility | Controller não contém lógica de negócio |
| **O** — Open/Closed | Aberto para extensão por novos tipos de Pet, fechado para modificação |
| **L** — Liskov | Substituição de tipos sem quebrar contratos |
| **I** — Interface Segregation | Interfaces pequenas e específicas em vez de genéricas |
| **D** — Dependency Inversion | Spring DI; controladores dependem de abstrações, não implementações |

### GoF Criacionais
- **Singleton:** `ApplicationConfig` — instância única garantida pelo Spring
- **Factory Method:** entidades polimórficas por tipo de animal
- **Builder:** `PetBuilder` — construção fluente para objetos com muitos campos opcionais

### GoF Comportamentais
- **Strategy:** validações intercambiáveis (ex: regras de disponibilidade de horário por tipo de serviço)
- **Observer:** eventos de domínio disparados ao criar/atualizar entidades (ex: notificação de agendamento)

---

## Qualidade de Código

```
📊 Cobertura de Testes (JaCoCo)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Meta mínima:  ████████░░  80%
Quality Gate: BUILD FAIL se < 80%
```

### Pipeline de Qualidade Local

```bash
# Subir SonarQube
docker run -d --name sonarqube -p 9000:9000 sonarqube:community

# Rodar análise + cobertura
./mvnw clean verify sonar:sonar \
  -Dsonar.projectKey=petland-api \
  -Dsonar.host.url=http://localhost:9000

# Relatório JaCoCo em target/site/jacoco/index.html
```

### Critérios do Quality Gate

- Cobertura de linha ≥ 80%
- Zero issues de severidade `BLOCKER`
- Zero issues de severidade `CRITICAL`
- Duplicação de código < 3%

---

## Como Rodar Localmente

### Pré-requisitos

- Java 21+
- Docker + Docker Compose
- Maven 3.9+

### Com Docker Compose

```bash
git clone https://github.com/gabriel-falcao/petland-api
cd petland-api

# Subir app + PostgreSQL + Redis
docker compose up -d

# API disponível em
curl http://localhost:8080/actuator/health
```

### Sem Docker (desenvolvimento local)

```bash
# PostgreSQL rodando localmente na porta 5432

./mvnw spring-boot:run \
  -Dspring-boot.run.profiles=local
```

### Kubernetes (Minikube)

```bash
# Aplicar todos os manifestos
kubectl apply -f k8s/

# Verificar pods
kubectl get pods

# Acessar via NodePort
minikube service petland-api --url
```

---

## Estrutura do Projeto

```
petland-api/
├── src/
│   ├── main/
│   │   ├── java/com/petland/
│   │   │   ├── application/         # Application Services (use cases)
│   │   │   ├── domain/
│   │   │   │   ├── entity/          # Entidades e Records
│   │   │   │   ├── valueobject/     # Value Objects imutáveis
│   │   │   │   ├── repository/      # Interfaces (por Aggregate Root)
│   │   │   │   └── service/         # Domain Services
│   │   │   ├── infrastructure/
│   │   │   │   ├── persistence/     # Implementações JPA
│   │   │   │   └── config/          # ApplicationConfig (Singleton)
│   │   │   └── web/
│   │   │       ├── controller/      # REST Controllers (só coordenam)
│   │   │       └── advice/          # @RestControllerAdvice global
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       ├── unit/                    # JUnit 5 + Mockito
│       └── integration/             # @SpringBootTest + Testcontainers
├── k8s/
│   ├── deployment.yaml              # Deployment + 2 réplicas
│   ├── service.yaml                 # ClusterIP
│   ├── configmap.yaml               # Configurações não-sensíveis
│   └── secret.yaml                  # Credenciais em base64 (nunca commitar)
├── monitoring/
│   ├── prometheus.yml               # Scrape config → /actuator/prometheus
│   └── grafana/                     # Dashboard JVM pré-configurado
├── Dockerfile                       # Multi-stage, non-root user
├── docker-compose.yml               # app + PostgreSQL + Redis + Prometheus + Grafana
└── pom.xml                          # JaCoCo plugin + SonarQube plugin
```

---

## Conventional Commits

Todos os commits seguem o padrão [Conventional Commits](https://www.conventionalcommits.org/):

```
feat: implement Pet entity as Java Record with Pattern Matching
feat: add Builder pattern for PetBuilder
feat: apply Strategy pattern for appointment validation rules
feat: add Observer for domain events on Pet creation
refactor: separate ApplicationService from DomainService layer
test: add integration test with Testcontainers for PostgreSQL
test: add MockMvc tests for PetController
docs: add DDD domain diagram to README
docs: add JaCoCo coverage badge
chore: configure SonarQube Quality Gate at 80% coverage
fix: correct @RestControllerAdvice global error handler response format
```

---

## Contexto do Bootcamp

Este projeto faz parte do **Bootcamp v6 — 7 Semanas**, plano de carreira de Gabriel Falcão da Cruz (5º semestre, 22 anos, AWS SAA-C03 + CLF-C02).

**Meta de carreira:** atingir o perfil **Cloud-Native Java Architect** — Java/Spring Cloud + K8s/Helm/CI-CD + AWS Professional + DDD/Design Patterns — representando menos de 0,2% dos devs Java do Brasil.

**Meta financeira:** R$ 10.000–15.000/mês em 36 meses. Probabilidade consolidada: 55–72%.

O `petland-api` é a fundação técnica desse portfólio. Cada push no GitHub é um voto a favor da versão sênior.

---

*Bootcamp v6 + Plano de Carreira sincronizado · Junho 2026 · Gabriel Falcão da Cruz*
