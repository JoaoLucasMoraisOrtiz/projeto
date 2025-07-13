#!/bin/bash

# Script simplificado para desenvolvimento local
echo "=== Configuração Local dos Microsserviços ==="

# Verificar se MySQL está rodando
if ! sudo systemctl is-active --quiet mysql; then
    echo "🚀 Iniciando MySQL local..."
    sudo systemctl start mysql
    sleep 5
fi

# Executar script de inicialização dos bancos
echo "📝 Criando bancos de dados..."
mysql -u root -p < init-databases.sql

echo "✅ Bancos de dados criados!"
echo ""
echo "Para executar os microsserviços:"
echo "1. Terminal 1: cd MSResident && mvn spring-boot:run"
echo "2. Terminal 2: cd MSFinancialManagement && mvn spring-boot:run"
echo "3. Terminal 3: cd MSProprietary && mvn spring-boot:run"
echo ""
echo "Ou execute com Docker: docker-compose up --build"
