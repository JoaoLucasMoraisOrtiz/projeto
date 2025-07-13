# MSFinancialManagement Dockerfile
FROM openjdk:17-jdk-slim

VOLUME /tmp

# Instalar Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos do projeto
COPY pom.xml .
COPY src ./src

# Compilar o projeto
RUN mvn clean package -DskipTests

# Criar diretório para uploads
RUN mkdir -p /app/uploads

# Expor a porta
EXPOSE 8082

# Comando para executar a aplicação
CMD ["java", "-jar", "target/MSFinancialManagement-0.0.1-SNAPSHOT.jar"]
