# 🚀 Demonstração do Sistema de Microserviços

## 📋 Visão Geral do Sistema

O sistema é composto por 3 microserviços independentes:
- **MSResident** (Porta 8081) - Gerenciamento de moradores
- **MSFinancialManagement** (Porta 8082) - Gestão financeira e cobrança
- **MSProprietary** (Porta 8083) - Administração de proprietários

## 🔧 Comandos de Teste Rápido

### 1. Verificar Status dos Serviços

```bash
# Verificar se todos os serviços estão rodando
sudo docker ps

# Testar conectividade básica
curl -f http://localhost:8081/health
curl -f http://localhost:8082/health  
curl -f http://localhost:8083/health
```

## 📊 Exemplos de Requisições por Microserviço

### 🏠 MSResident - Gerenciamento de Moradores

#### Endpoints de Saúde e Info
```bash
# Status do serviço
curl -X GET http://localhost:8081/health

# Informações detalhadas (se disponível)
curl -X GET http://localhost:8081/actuator/info
```

#### Operações CRUD de Moradores
```bash
# Listar todos os moradores
curl -X GET http://localhost:8081/residents

# Buscar morador por ID
curl -X GET http://localhost:8081/residents/1

# Criar novo morador
curl -X POST http://localhost:8081/residents \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao.silva@email.com",
    "phone": "(11) 99999-9999",
    "apartment": "101",
    "building": "Bloco A"
  }'

# Atualizar morador
curl -X PUT http://localhost:8081/residents/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva Santos",
    "email": "joao.santos@email.com",
    "phone": "(11) 99999-8888",
    "apartment": "101",
    "building": "Bloco A"
  }'

# Deletar morador
curl -X DELETE http://localhost:8081/residents/1
```

### 💰 MSFinancialManagement - Gestão Financeira

#### Endpoints de Saúde
```bash
# Status do serviço
curl -X GET http://localhost:8082/health

# Informações do sistema
curl -X GET http://localhost:8082/actuator/health
```

#### Operações Financeiras
```bash
# Upload de arquivo
curl -X POST http://localhost:8082/files/upload \
  -F "file=@exemplo.pdf" \
  -F "paymentId=1"

# Listar arquivos (página web)
curl -X GET http://localhost:8082/files/

# Fazer download de arquivo
curl -X GET http://localhost:8082/files/files/exemplo.pdf
```

### 🏢 MSProprietary - Administração de Proprietários

#### Endpoints de Saúde
```bash
# Status do serviço
curl -X GET http://localhost:8083/health

# Métricas do sistema
curl -X GET http://localhost:8083/actuator/metrics
```

#### Operações de Proprietários
```bash
# Listar todos os proprietários
curl -X GET http://localhost:8083/proprietaries

# Buscar proprietário por ID
curl -X GET http://localhost:8083/proprietaries/1

# Criar novo proprietário
curl -X POST http://localhost:8083/proprietaries \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Maria Santos",
    "email": "maria@email.com",
    "phone": "(11) 98888-7777"
  }'

# Atualizar proprietário
curl -X PUT http://localhost:8083/proprietaries/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Maria Santos Silva",
    "email": "maria.silva@email.com",
    "phone": "(11) 98888-6666"
  }'

# Gerar relatório financeiro
curl -X POST http://localhost:8083/proprietaries/1/generate-financial-report \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Maria Santos",
    "email": "maria@email.com",
    "phone": "(11) 98888-7777"
  }'

# Deletar proprietário
curl -X DELETE http://localhost:8083/proprietaries/1
```

## 🎯 Cenários de Demonstração Completos

### Cenário 1: Cadastro Completo de Novo Morador

```bash
# 1. Criar proprietário
echo "=== Criando Proprietário ==="
curl -X POST http://localhost:8083/api/proprietaries \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Carlos Oliveira",
    "email": "carlos@email.com",
    "phone": "(11) 97777-6666"
  }'

# 2. Criar morador
echo -e "\n=== Criando Morador ==="
curl -X POST http://localhost:8081/api/residents \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Ana Costa",
    "email": "ana@email.com",
    "phone": "(11) 96666-5555",
    "apartment": "305",
    "building": "Bloco C"
  }'

# 3. Gerar cobrança
echo -e "\n=== Gerando Cobrança ==="
curl -X POST http://localhost:8082/api/charges \
  -H "Content-Type: application/json" \
  -d '{
    "residentId": 1,
    "amount": 950.00,
    "description": "Condomínio Janeiro 2025",
    "dueDate": "2025-01-15"
  }'
```

### Cenário 2: Consultas e Relatórios

```bash
# 1. Listar todos os moradores
echo "=== Moradores Cadastrados ==="
curl -X GET http://localhost:8081/api/residents

# 2. Verificar cobranças pendentes
echo -e "\n=== Cobranças Pendentes ==="
curl -X GET http://localhost:8082/api/charges

# 3. Relatório de proprietários
echo -e "\n=== Relatório de Proprietários ==="
curl -X GET http://localhost:8083/api/proprietaries
```

### Cenário 3: Monitoramento do Sistema

```bash
# Status de todos os serviços
echo "=== Status dos Microserviços ==="
echo "MSResident:"
curl -s http://localhost:8081/health | jq .

echo -e "\nMSFinancialManagement:"
curl -s http://localhost:8082/health | jq .

echo -e "\nMSProprietary:"
curl -s http://localhost:8083/health | jq .

# Verificar logs em tempo real
echo -e "\n=== Logs dos Serviços ==="
sudo docker compose logs --tail=5 msresident
sudo docker compose logs --tail=5 msfinancial
sudo docker compose logs --tail=5 msproprietary
```

## 📱 Testando via Navegador

Você também pode testar alguns endpoints diretamente no navegador:

- **MSResident Health**: http://localhost:8081/health
- **MSFinancialManagement Health**: http://localhost:8082/health
- **MSProprietary Health**: http://localhost:8083/health

## 🔍 Comandos de Diagnóstico

```bash
# Verificar containers rodando
sudo docker ps

# Ver logs específicos
sudo docker compose logs msresident
sudo docker compose logs msfinancial
sudo docker compose logs msproprietary

# Verificar uso de recursos
sudo docker stats

# Reiniciar serviço específico
sudo docker compose restart msresident

# Parar e reiniciar tudo
sudo docker compose down
sudo docker compose up -d
```

## 📊 Métricas de Performance

```bash
# Tempo de resposta dos serviços
time curl -s http://localhost:8081/health > /dev/null
time curl -s http://localhost:8082/health > /dev/null
time curl -s http://localhost:8083/health > /dev/null

# Verificar conectividade com banco
sudo docker compose exec db mysql -u root -proot -e "SHOW DATABASES;"
```

## 🚀 Script de Demonstração Automatizada

Execute este script para uma demonstração completa:

```bash
#!/bin/bash
echo "🚀 Iniciando demonstração do sistema..."

# Verificar se todos os serviços estão rodando
if ! curl -s http://localhost:8081/health > /dev/null; then
    echo "❌ MSResident não está respondendo"
    exit 1
fi

if ! curl -s http://localhost:8082/health > /dev/null; then
    echo "❌ MSFinancialManagement não está respondendo"
    exit 1
fi

if ! curl -s http://localhost:8083/health > /dev/null; then
    echo "❌ MSProprietary não está respondendo"
    exit 1
fi

echo "✅ Todos os serviços estão funcionando!"
echo "🎯 Sistema pronto para demonstração"
```

---

## 💡 Dicas para Apresentação

1. **Inicie pela verificação de status** - Mostre que todos os serviços estão rodando
2. **Demonstre a independência** - Cada microserviço funciona separadamente
3. **Mostre a escalabilidade** - Cada serviço pode ser escalado independentemente
4. **Enfatize a arquitetura** - Microserviços + Docker + MySQL
5. **Destaque a facilidade de deploy** - Um comando para subir tudo

## 🔧 Solução de Problemas

Se algum serviço não estiver respondendo:
```bash
# Verificar logs
sudo docker compose logs [nome-do-serviço]

# Reiniciar serviço específico
sudo docker compose restart [nome-do-serviço]

# Reconstruir se necessário
sudo docker compose up -d --build [nome-do-serviço]
```
