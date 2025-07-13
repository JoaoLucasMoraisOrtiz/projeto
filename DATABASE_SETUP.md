# Configuração dos Bancos de Dados - Microsserviços

## 🗄️ Estrutura dos Bancos de Dados

Cada microsserviço possui seu próprio banco de dados independente:

- **MSResident** → `msresident` (porta 8081)
- **MSFinancialManagement** → `msfinancial` (porta 8082)
- **MSProprietary** → `msproprietary` (porta 8083)

## 🚀 Configuração Rápida

### Opção 1: Usando Docker (Recomendado)

```bash
# 1. Execute o script de configuração
./setup-environment.sh

# 2. Inicie todos os serviços
docker-compose up
```

### Opção 2: Configuração Manual

#### 1. Instalar MySQL
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install mysql-server

# CentOS/RHEL
sudo yum install mysql-server
```

#### 2. Criar os bancos de dados
```sql
mysql -u root -p
```

```sql
CREATE DATABASE msresident;
CREATE DATABASE msfinancial;
CREATE DATABASE msproprietary;
exit;
```

#### 3. Executar cada microsserviço
```bash
# Terminal 1
cd MSResident
mvn spring-boot:run

# Terminal 2
cd MSFinancialManagement
mvn spring-boot:run

# Terminal 3
cd MSProprietary
mvn spring-boot:run
```

## 🔧 Configurações dos application.properties

### MSResident (8081)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/msresident
spring.datasource.username=root
spring.datasource.password=root
```

### MSFinancialManagement (8082)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/msfinancial
spring.datasource.username=root
spring.datasource.password=root
# Configurações de upload de arquivos habilitadas
```

### MSProprietary (8083)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/msproprietary
spring.datasource.username=root
spring.datasource.password=root
# Configurações de processamento assíncrono habilitadas
```

## 🧪 Testando a Configuração

### Verificar se os bancos foram criados
```sql
mysql -u root -p
SHOW DATABASES;
```

### Testar endpoints dos microsserviços
```bash
# MSResident
curl http://localhost:8081/residents

# MSFinancialManagement  
curl http://localhost:8082/condominiums

# MSProprietary
curl http://localhost:8083/proprietaries
```

## 🐛 Troubleshooting

### Problema: Erro de conexão com banco
```bash
# Verificar se o MySQL está rodando
sudo systemctl status mysql

# Iniciar o MySQL
sudo systemctl start mysql
```

### Problema: Porta já em uso
```bash
# Verificar processos usando as portas
lsof -i :8081
lsof -i :8082
lsof -i :8083
lsof -i :3306
```

### Problema: Permissões do MySQL
```sql
# Criar usuário específico (opcional)
CREATE USER 'microservice_user'@'%' IDENTIFIED BY 'microservice_password';
GRANT ALL PRIVILEGES ON msresident.* TO 'microservice_user'@'%';
GRANT ALL PRIVILEGES ON msfinancial.* TO 'microservice_user'@'%';
GRANT ALL PRIVILEGES ON msproprietary.* TO 'microservice_user'@'%';
FLUSH PRIVILEGES;
```

## 📁 Estrutura de Arquivos Criados

```
/home/joao/projeto/
├── init-databases.sql              # Script SQL para criar os bancos
├── setup-environment.sh           # Script de configuração automática
├── docker-compose.yml             # Configuração atualizada
├── MSResident/src/main/resources/
│   └── application.properties     # Configurações do MSResident
├── MSFinancialManagement/src/main/resources/
│   └── application.properties     # Configurações do MSFinancialManagement
└── MSProprietary/src/main/resources/
    └── application.properties     # Configurações do MSProprietary
```

## 🎯 Próximos Passos

1. Execute `./setup-environment.sh` para configurar tudo automaticamente
2. Teste cada microsserviço individualmente
3. Configure dados de teste se necessário
4. Documente os endpoints específicos de cada serviço
