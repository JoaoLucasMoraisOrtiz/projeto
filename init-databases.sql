-- Script para inicializar os bancos de dados dos microsserviços
-- Execute este script no MySQL antes de iniciar os microsserviços

-- Criar banco de dados para MSResident
CREATE DATABASE IF NOT EXISTS msresident;

-- Criar banco de dados para MSFinancialManagement
CREATE DATABASE IF NOT EXISTS msfinancial;

-- Criar banco de dados para MSProprietary
CREATE DATABASE IF NOT EXISTS msproprietary;

-- Criar usuário específico para os microsserviços (opcional)
-- CREATE USER IF NOT EXISTS 'microservice_user'@'%' IDENTIFIED BY 'microservice_password';

-- Conceder permissões (opcional)
-- GRANT ALL PRIVILEGES ON msresident.* TO 'microservice_user'@'%';
-- GRANT ALL PRIVILEGES ON msfinancial.* TO 'microservice_user'@'%';
-- GRANT ALL PRIVILEGES ON msproprietary.* TO 'microservice_user'@'%';

-- Aplicar mudanças
FLUSH PRIVILEGES;

-- Usar o banco msresident para inserir dados de teste
USE msresident;

-- Usar o banco msfinancial para inserir dados de teste
USE msfinancial;

-- Usar o banco msproprietary para inserir dados de teste
USE msproprietary;

-- Verificar se os bancos foram criados
SHOW DATABASES;
