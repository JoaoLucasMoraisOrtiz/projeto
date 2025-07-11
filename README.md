# Microserviço de Gestão de Aluguel para Condomínios

Este projeto é um microserviço Spring Boot para gerenciar o pagamento de aluguel em condomínios, seguindo as orientações do trabalho acadêmico. O serviço contempla:
- Requisição de cobrança do aluguel
- Emissão do boleto do aluguel
- Notificação de aluguel e pagamento do boleto ao proprietário

## Estrutura do Projeto

```
src/
  main/
    java/
      com/example/trabFinal/
        TrabFinalApplication.java
        model/
          Resident.java
          Proprietary.java
          Contact.java
          Person.java
          RentInvoice.java
        repository/
          ResidentRepository.java
          ProprietaryRepository.java
          RentInvoiceRepository.java
        service/
          NotificationService.java
          RentInvoiceService.java
        controller/
          RentController.java
        tasks/
          BillingNotificationTask.java
    resources/
      application.properties
  test/
    java/
      com/example/trabFinal/
        ... (testes unitários para todos os componentes)
```

## Principais Componentes

- **Modelos (Entidades):** Resident, Proprietary, Contact, Person, RentInvoice
- **Repositórios:** Para acesso aos dados via Spring Data JPA
- **Serviços:**
  - NotificationService: Envio de notificações (e-mail, logs)
  - RentInvoiceService: Emissão de boletos e controle de pagamentos
- **Controller:** RentController para expor endpoints REST
- **Tarefas Agendadas:** BillingNotificationTask para lembretes mensais

## Funcionalidades

1. **Requisitar cobrança do aluguel:** Endpoint REST para iniciar a cobrança
2. **Emitir boleto do aluguel:** Geração de boleto e persistência
3. **Notificar proprietário:** Envio de mensagem sobre aluguel e pagamento
4. **Upload de comprovante:** Endpoint para upload de comprovante de pagamento
5. **Agendamento de lembrete:** Tarefa mensal para notificar moradores

## Testes
- Todos os serviços, controllers e tarefas possuem testes unitários em JUnit
- Mock de dependências com Mockito

## Banco de Dados
- Utiliza Spring Data JPA (MySQL recomendado)
- Docker para subir instância do banco

## Execução
- Rodar o projeto com `mvn spring-boot:run`
- Testes: `mvn test`
- Banco de dados: Docker Compose para MySQL

## Demonstração
- Utilizar Postman ou RestClient para testar endpoints
- Vídeo demonstrando funcionalidades automatizadas

## Referências
- [Spring Scheduling Tasks](https://spring.io/guides/gs/scheduling-tasks)
- [Spring File Upload](https://spring.io/guides/gs/uploading-files)
- [Spring Async Methods](https://spring.io/guides/gs/async-method)

---

**Grupo 02**
- João Lucas Morais Ortiz
- Murilo Farinazzo Vieira
- João Vitor Redondano de Almeida

Funcionalidade: Pagar Aluguel

---

> Para dúvidas ou sugestões, consulte o arquivo HELP.md ou entre em contato com o grupo.
