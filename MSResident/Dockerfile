# MSResident Dockerfile
FROM eclipse-temurin:17-jdk-jammy

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

# Expor a porta
EXPOSE 8081

# Comando para executar a aplicação
CMD ["java", "-jar", "target/MSResident-0.0.1-SNAPSHOT.jar"]
