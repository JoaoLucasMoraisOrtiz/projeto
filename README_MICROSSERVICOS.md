# Arquitetura de Microsserviços - Sistema de Gestão de Aluguel

Este projeto foi refatorado de um monolito para uma arquitetura de microsserviços composta por três serviços independentes:

## 📋 Visão Geral dos Microsserviços

### 1. MSResident (Porta 8081)
**Responsabilidade:** Gerenciamento de residentes e notificações de pagamento

**Funcionalidades:**
- CRUD completo de residentes
- Gerenciamento de dados pessoais, contatos, endereços e acesso
- Tarefa agendada mensal para envio de lembretes de pagamento
- Integração com MSFinancialManagement para consultar faturas pendentes

**Tecnologias:**
- Spring Boot
- Spring Data JPA
- Spring Scheduling (`@EnableScheduling`)
- MySQL

**Entidades:**
- `Person` (classe base)
- `Resident` (herda de Person)
- `Contact`
- `Access`
- `Address`
- `Unit`
- `ResidentType` (enum: OWNER, TENANT, OTHER)

---

### 2. MSFinancialManagement (Porta 8082)
**Responsabilidade:** Gerenciamento financeiro e upload de comprovantes

**Funcionalidades:**
- Gerenciamento de condomínios e unidades
- Geração e controle de cobranças (faturas)
- Processamento de pagamentos
- Upload de comprovantes de pagamento
- API para consulta de faturas por residente

**Tecnologias:**
- Spring Boot
- Spring Data JPA
- Spring Web (para upload de arquivos)
- MySQL

**Entidades:**
- `Condominium`
- `Unit`
- `Charge`
- `Payment`
- `Address`
- `CondominiumType` (enum: HOUSE, APARTMENT)
- `ChargeType` (enum: MONTHLY_QUOTA, FINE)

---

### 3. MSProprietary (Porta 8083)
**Responsabilidade:** Gerenciamento de proprietários e operações assíncronas

**Funcionalidades:**
- CRUD de proprietários
- Processamento assíncrono de relatórios financeiros
- Envio assíncrono de notificações em lote
- Integração com MSFinancialManagement para dados financeiros

**Tecnologias:**
- Spring Boot
- Spring Data JPA
- Spring Async (`@EnableAsync`)
- MySQL

**Entidades:**
- `Person` (classe base)
- `Proprietary` (herda de Person)
- `Contact`
- `Access`
- `Address`
- `Unit`

---

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- MySQL
- Maven

### Executando os Microsserviços

1. **MSResident:**
```bash
cd MSResident
mvn spring-boot:run
```
Acesso: http://localhost:8081

2. **MSFinancialManagement:**
```bash
cd MSFinancialManagement
mvn spring-boot:run
```
Acesso: http://localhost:8082

3. **MSProprietary:**
```bash
cd MSProprietary
mvn spring-boot:run
```
Acesso: http://localhost:8083

---

## 🔗 Comunicação Entre Microsserviços

### MSResident → MSFinancialManagement
- `GET /charges?residentId={id}&status=PENDING` - Consulta faturas pendentes para envio de lembretes

### MSProprietary → MSFinancialManagement  
- `GET /charges?unitId={id}` - Busca dados financeiros para geração de relatórios

---

## 📡 Principais Endpoints

### MSResident
- `GET /residents` - Lista todos os residentes
- `POST /residents` - Cria um novo residente
- `GET /residents/{id}` - Busca residente por ID
- `PUT /residents/{id}` - Atualiza residente
- `DELETE /residents/{id}` - Remove residente

### MSFinancialManagement
- `POST /condominiums` - Cria um condomínio
- `GET /condominiums/{id}/units` - Lista unidades de um condomínio
- `POST /charges` - Gera uma cobrança
- `GET /charges?residentId={id}` - Lista cobranças de um residente
- `POST /files/upload` - Upload de comprovante de pagamento

### MSProprietary
- `POST /proprietaries/{id}/generate-financial-report` - Gera relatório financeiro (assíncrono)
- `POST /proprietaries/send-bulk-notifications` - Envio em lote de notificações (assíncrono)

---

## 🔧 Configurações

Cada microsserviço possui seu próprio:
- `application.properties` (com porta específica)
- `pom.xml` (com dependências específicas)
- Banco de dados isolado

---

## 🎯 Benefícios da Arquitetura

1. **Escalabilidade Independente:** Cada serviço pode ser escalado conforme a demanda
2. **Tecnologias Específicas:** Cada serviço pode usar as tecnologias mais adequadas
3. **Desenvolvimento Paralelo:** Times podem trabalhar independentemente
4. **Resiliência:** Falha em um serviço não afeta os outros
5. **Deploy Independente:** Cada serviço pode ser atualizado separadamente

---

## 📚 Recursos Implementados

- **MSResident:** Scheduling Tasks (lembretes mensais automatizados)
- **MSFinancialManagement:** File Upload (comprovantes de pagamento)
- **MSProprietary:** Async Methods (relatórios e notificações assíncronas)
