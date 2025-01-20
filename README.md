# 📚 Proyecto: Sistema de Gestión de Libros

Este proyecto es una aplicación basada en Java y Spring Boot que permite gestionar información sobre libros. La aplicación incluye integración con una base de datos PostgreSQL y realiza consultas a la API de Gutendex para obtener información adicional de libros. También ofrece una interfaz de consola interactiva para realizar diversas operaciones.

---

## ✨ Funcionalidades principales

1. **Gestión de libros:**
    - Listar libros registrados.
    - Buscar libros por título.
    - Guardar nuevos libros con datos desde Gutendex.
    - Filtrar libros por idioma.

2. **Gestión de autores:**
    - Listar autores únicos registrados.
    - Filtrar autores vivos en un año específico.

3. **Integración con la API de Gutendex:**
    - Permite buscar información de libros (autor, idioma, descargas, etc.) y almacenarla localmente.

4. **Interfaz de consola:**
    - Menú interactivo para ejecutar las funcionalidades de la aplicación.

---

## 🚀 Requisitos para ejecutar el proyecto

### 1. Dependencias

- **Java 17 o superior**
- **Spring Boot** (incluido en el proyecto).
- **PostgreSQL** para gestionar la base de datos.
- **Maven** para gestionar las dependencias del proyecto.

### 2. Configuración de la base de datos

Edita el archivo `application.properties` ubicado en `src/main/resources` para definir las credenciales y configuraciones de acceso a tu base de datos PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://<DB_HOST>/<DB_NAME>
spring.datasource.username=<DB_USER>
spring.datasource.password=<DB_PASSWORD>
spring.datasource.driver-class-name=org.postgresql.Driver
hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Reemplaza:
- `<DB_HOST>` por la dirección del host de tu base de datos.
- `<DB_NAME>` por el nombre de la base de datos.
- `<DB_USER>` por el usuario de PostgreSQL.
- `<DB_PASSWORD>` por la contraseña del usuario.

---

## 🔧 Configuración inicial

1. Clona este repositorio en tu máquina local:

   ```bash
   git clone https://github.com/Segnini-Rafael/libros.git
   cd libros
   ```

2. Configura las credenciales de la base de datos en `application.properties`.

3. Asegúrate de que PostgreSQL esté corriendo y que la base de datos especificada exista.

4. Ejecuta el proyecto utilizando Maven:

   ```bash
   mvn spring-boot:run
   ```

---

## 🖥️ Uso de la aplicación

Al iniciar la aplicación, se desplegará un menú en la consola con las siguientes opciones:

1. **Buscar libro por título:** Consulta un libro en la API de Gutendex y lo guarda en la base de datos si no existe.
2. **Listar libros registrados:** Muestra todos los libros almacenados en la base de datos.
3. **Listar autores registrados:** Muestra todos los autores únicos registrados.
4. **Listar autores vivos en un determinado año:** Filtra autores que estaban vivos en el año especificado.
5. **Listar libros por idioma:** Filtra libros según el idioma especificado.
6. **Salir:** Cierra la aplicación.

### Ejemplo de uso

```text
==================================
📚 Menú Principal:
1- Buscar libro por título
2- Listar libros registrados
3- Listar autores registrados
4- Listar autores vivos en un determinado año
5- Listar libros por idioma
0- Salir
==================================
Ingrese su opción: 1
Ingrese el título del libro a buscar: Pride and Prejudice
✅ Libro guardado exitosamente:
📚 Título: Pride and Prejudice
✍️  Autor: Jane Austen
🌐 Idioma: en
⬇️  Descargas: 13456
```

---

## 🛠️ Estructura del proyecto

- **`controller/`**: Contiene las clases que manejan las solicitudes REST.
- **`model/`**: Define las entidades del sistema, como `Libro`.
- **`repository/`**: Contiene las interfaces para interactuar con la base de datos usando Spring Data JPA.
- **`service/`**: Incluye la lógica de negocio, como integración con Gutendex y validaciones.
- **`resources/`**: Archivos de configuración, como `application.properties`.

---

## 🌐 Integración con Gutendex

La integración con la API de Gutendex se realiza desde `LibroService`. Permite buscar información detallada sobre un libro dado un título.

### Ejemplo de Endpoint utilizado:

`https://gutendex.com/books?search=<titulo>`

- Extrae datos como título, autor, idioma, descargas, años de nacimiento y muerte del autor.
- Si el libro ya existe en la base de datos, se omite.

---

## 📋 Próximas mejoras

1. **Validaciones avanzadas:** Mejorar la detección de datos incompletos.
2. **Endpoints adicionales:** Agregar operaciones REST para otros casos de uso.
3. **Mejora en la interfaz:** Crear una interfaz gráfica para reemplazar el menú de consola.
4. **Test unitarios:** Asegurar el correcto funcionamiento del sistema con pruebas.

---

## 👨‍💻 Desarrollado por

[Rafael Segnini](https://github.com/Segnini-Rafael) 🚀
