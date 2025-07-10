# Literalura

Literalura es una aplicación Java basada en Spring Boot que permite consultar y gestionar información de libros y autores utilizando la API de Gutendex. El proyecto sigue una arquitectura modular con capas de entidad, repositorio, servicio y cliente.

## Descripción
Literalura facilita la consulta de libros y autores, permitiendo almacenar y gestionar la información obtenida desde la API pública de Gutendex. Es ideal para quienes desean explorar literatura clásica y moderna de manera sencilla y estructurada.

## Características
- Consulta de libros y autores desde la API pública de Gutendex.
- Persistencia de datos de libros y autores en base de datos relacional.
- Organización modular: client, entity, model, repository, service.
- Uso de Spring Data JPA para la gestión de repositorios.
- Arquitectura limpia y escalable.

## Tecnologías Utilizadas
- Java 17
- Spring Boot
- Spring Data JPA
- Maven
- API Gutendex

## Estructura del Proyecto
```
literalura/
├── src/
│   ├── main/
│   │   ├── java/com/literalura/literalura/
│   │   │   ├── LiteraluraApplication.java
│   │   │   ├── client/
│   │   │   ├── entity/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/literalura/literalura/
├── pom.xml
└── ...
```

## Requisitos
- Java 17 o superior
- Maven 3.8+

## Instalación y Ejecución
1. Clona el repositorio:
   ```sh
   git clone https://github.com/iPaau/LiterAlura-Challenge.git
   cd literalura
   ```
2. Compila el proyecto:
   ```sh
   ./mvnw clean install
   ```
3. Inicia la aplicación:
   - En Windows:
     ```sh
     .\mvnw spring-boot:run

   También puedes ejecutar el JAR generado:
   ```sh
   java -jar target/literalura-0.0.1-SNAPSHOT.jar
   ```

## Configuración
Edita el archivo `src/main/resources/application.properties` para configurar la conexión a la base de datos y otros parámetros.

## Ejemplo de Uso
Al iniciar la aplicación, puedes consultar libros y autores desde la API de Gutendex y almacenarlos en la base de datos local. El flujo típico es:
1. Buscar libros por título o autor.
2. Visualizar detalles y autores relacionados.
3. Guardar libros/autores de interés en la base de datos.