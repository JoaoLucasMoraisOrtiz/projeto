#!/bin/bash

# Script para inicializar os bancos de dados dos microsserviços
# Execute este script para configurar todo o ambiente

echo "=== Configurando Ambiente dos Microsserviços ==="

# Verificar se o Docker está instalado
if ! command -v docker &> /dev/null; then
    echo "❌ Docker não está instalado. Por favor, instale o Docker primeiro."
    exit 1
fi

# Verificar se o Docker Compose está instalado
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose não está instalado. Por favor, instale o Docker Compose primeiro."
    exit 1
fi

echo "✅ Docker e Docker Compose encontrados"

# Verificar se MySQL local está rodando e parar se necessário
echo "🔍 Verificando MySQL local..."
if sudo lsof -i :3306 > /dev/null 2>&1; then
    echo "🛑 Parando MySQL local para liberar porta 3306..."
    sudo systemctl stop mysql
    sleep 5
fi

# Parar containers existentes
echo "🛑 Parando containers existentes..."
docker-compose down

# Remover volumes antigos (opcional - descomente se quiser limpar dados)
# echo "🗑️  Removendo volumes antigos..."
# docker volume rm projeto_mysql_data 2>/dev/null || true

# Iniciar apenas o banco de dados primeiro
echo "🚀 Iniciando banco de dados..."
docker-compose up -d db

# Aguardar o banco ficar pronto
echo "⏳ Aguardando banco de dados ficar pronto..."
sleep 30

# Verificar se o banco está respondendo
echo "🔍 Verificando conectividade do banco..."
docker-compose exec db mysql -u root -proot -e "SHOW DATABASES;" > /dev/null 2>&1

if [ $? -eq 0 ]; then
    echo "✅ Banco de dados está funcionando!"
else
    echo "❌ Erro ao conectar com o banco de dados"
    exit 1
fi

# Executar script de inicialização adicional se necessário
echo "📝 Executando script de inicialização..."
docker-compose exec db mysql -u root -proot < init-databases.sql

echo "=== Instruções para Executar os Microsserviços ==="
echo ""
echo "1. Para executar todos os microsserviços via Docker:"
echo "   docker-compose up"
echo ""
echo "2. Para executar individualmente (desenvolvimento):"
echo "   cd MSResident && mvn spring-boot:run"
echo "   cd MSFinancialManagement && mvn spring-boot:run"
echo "   cd MSProprietary && mvn spring-boot:run"
echo ""
echo "3. URLs dos microsserviços:"
echo "   MSResident: http://localhost:8081"
echo "   MSFinancialManagement: http://localhost:8082"
echo "   MSProprietary: http://localhost:8083"
echo ""
echo "4. Banco de dados MySQL:"
echo "   Host: localhost:3306"
echo "   Usuario: root"
echo "   Senha: root"
echo "   Bancos: msresident, msfinancial, msproprietary"
echo ""
echo "✅ Configuração concluída com sucesso!"
