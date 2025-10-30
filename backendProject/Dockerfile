# =========================================
# Etapa 1: Construcción del frontend (Angular)
# =========================================
FROM node:18-alpine AS build-frontend
WORKDIR /app

# Copiamos el código del frontend
COPY ../frontendProject/package*.json ./
RUN npm install
COPY ../frontendProject .
RUN npm run build --prod

# =========================================
# Etapa 2: Construcción del backend (Spring Boot)
# =========================================
FROM maven:3.9.4-eclipse-temurin-21 AS build-backend
WORKDIR /app

# Copiamos el código del backend
COPY . .

# Copiamos el frontend compilado al backend
COPY --from=build-frontend /app/dist/frontalm14/ src/main/resources/static

# Compilamos el backend
RUN mvn clean package -DskipTests

# =========================================
# Etapa 3: Imagen final de ejecución
# =========================================
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiamos el jar generado
COPY --from=build-backend /app/target/*.jar app.jar

# Railway usa variable dinámica $PORT
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
