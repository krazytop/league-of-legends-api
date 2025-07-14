# Étape 1 : Builder l'application (build stage)
FROM maven:3.9.10-eclipse-temurin-21-alpine AS builder

# Définit le répertoire de travail à l'intérieur du conteneur
WORKDIR /app

# Copie le fichier pom.xml dans le conteneur.
COPY pom.xml .

# Télécharge toutes les dépendances nécessaires et les met en cache.
RUN mvn dependency:go-offline -B

# Copie tout le code source de l'application.
COPY src ./src

# Construit l'application en un fichier JAR exécutable.
RUN mvn clean package -DskipTests

# Étape 2 : Exécuter l'application (runtime stage)
FROM eclipse-temurin:21-jre-alpine

# Définit le répertoire de travail pour l'exécution.
WORKDIR /app

# Copie le fichier JAR construit de l'étape 'builder' vers l'étape 'runtime'.
COPY --from=builder /app/target/*.jar app.jar

# Expose le port sur lequel l'application va écouter.
EXPOSE 8080

# Commande pour démarrer l'application Spring Boot.
ENTRYPOINT ["java", "-jar", "app.jar"]