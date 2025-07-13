#!/bin/bash

# Script para inicializar os microsserviços com Docker
# Execute este script para configurar todo o ambiente

echo "=== Inicializando Microsserviços com Docker ==="

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

# Verificar se há processos na porta 3307
if lsof -i :3307 &> /dev/null; then
    echo "⚠️  Porta 3307 está em uso. Tentando parar processos..."
    sudo fuser -k 3307/tcp 2>/dev/null || true
    sleep 2
fi

# Limpar containers e volumes antigos
echo "🧹 Limpando containers antigos..."
sudo docker-compose down -v 2>/dev/null || true
sudo docker system prune -f 2>/dev/null || true

# Verificar se os Dockerfiles existem
echo "🔍 Verificando Dockerfiles..."
if [ ! -f "./MSResident/Dockerfile" ]; then
    echo "❌ Dockerfile não encontrado em MSResident"
    exit 1
fi

if [ ! -f "./MSFinancialManagement/Dockerfile" ]; then
    echo "❌ Dockerfile não encontrado em MSFinancialManagement"
    exit 1
fi

if [ ! -f "./MSProprietary/Dockerfile" ]; then
    echo "❌ Dockerfile não encontrado em MSProprietary"
    exit 1
fi

echo "✅ Todos os Dockerfiles encontrados"

# Construir e iniciar apenas o banco de dados primeiro
echo "🚀 Iniciando banco de dados..."
sudo docker-compose up -d db

# Aguardar o banco ficar pronto
echo "⏳ Aguardando banco de dados ficar pronto..."
sleep 30

# Verificar se o banco está saudável
echo "🔍 Verificando saúde do banco..."
for i in {1..30}; do
    if sudo docker-compose exec db mysqladmin ping -h localhost -u root -proot &>/dev/null; then
        echo "✅ Banco de dados está funcionando!"
        break
    fi
    echo "⏳ Tentativa $i/30 - Aguardando banco..."
    sleep 10
done

# Verificar se o banco realmente está funcionando
if ! sudo docker-compose exec db mysqladmin ping -h localhost -u root -proot &>/dev/null; then
    echo "❌ Erro: Banco de dados não está respondendo"
    echo "📋 Logs do banco:"
    sudo docker-compose logs db
    exit 1
fi

# Construir e iniciar todos os microsserviços
echo "🏗️  Construindo e iniciando microsserviços..."
sudo docker-compose up -d --build

# Aguardar os serviços ficarem prontos
echo "⏳ Aguardando microsserviços ficarem prontos..."
sleep 60

# Verificar status dos serviços
echo "📊 Status dos serviços:"
sudo docker-compose ps

# Verificar se os serviços estão respondendo
echo "🔍 Testando conectividade dos microsserviços..."

# Testar MSResident
if curl -s http://localhost:8081/actuator/health &>/dev/null || curl -s http://localhost:8081/ &>/dev/null; then
    echo "✅ MSResident está funcionando (porta 8081)"
else
    echo "⚠️  MSResident pode não estar respondendo ainda"
fi

# Testar MSFinancialManagement
if curl -s http://localhost:8082/actuator/health &>/dev/null || curl -s http://localhost:8082/ &>/dev/null; then
    echo "✅ MSFinancialManagement está funcionando (porta 8082)"
else
    echo "⚠️  MSFinancialManagement pode não estar respondendo ainda"
fi

# Testar MSProprietary
if curl -s http://localhost:8083/actuator/health &>/dev/null || curl -s http://localhost:8083/ &>/dev/null; then
    echo "✅ MSProprietary está funcionando (porta 8083)"
else
    echo "⚠️  MSProprietary pode não estar respondendo ainda"
fi

echo ""
echo "=== Informações dos Serviços ==="
echo "🔗 URLs dos microsserviços:"
echo "   MSResident: http://localhost:8081"
echo "   MSFinancialManagement: http://localhost:8082"
echo "   MSProprietary: http://localhost:8083"
echo ""
echo "🗄️  Banco de dados MySQL:"
echo "   Host: localhost:3307"
echo "   Usuário: root"
echo "   Senha: root"
echo "   Bancos: msresident, msfinancial, msproprietary"
echo ""
echo "📋 Comandos úteis:"
echo "   Ver logs: sudo docker-compose logs [serviço]"
echo "   Parar tudo: sudo docker-compose down"
echo "   Reconstruir: sudo docker-compose up -d --build"
echo ""
echo "✅ Inicialização concluída!"
