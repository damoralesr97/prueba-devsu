# Prueba Técnica/Practica Devsu

## Resumen:

Este proyecto ha sido desarrollado en **Spring Boot** con **Java 21** y contiene la implementación de dos microservicios que se comunican tanto de manera **sincrónica** (a través de peticiones HTTP) como **asincrónica** (utilizando **Kafka**). Cada microservicio gestiona su propia base de datos **PostgreSQL**.

## Características

- **Microservicios**: Dos microservicios que se comunican sincrónicamente y asincrónicamente.
- **Bases de Datos**: Cada microservicio tiene su propia base de datos PostgreSQL.
- **Swagger**: Implementación de **Swagger** para la documentación de las API REST.
- **Manejo de Errores**: Se utiliza la anotación `@RestControllerAdvice` para gestionar y capturar errores globalmente.
- **Docker**: Despliegue de los microservicios, base de datos y kafka mediante **Docker Compose**.
- **Pruebas**: Se implementaron pruebas unitarias y de integración para garantizar la calidad del código.
- **Funcionalidades asíncronas**: Se implementaron 2 endpoints que funcionan de manera asíncrona utilizando **kafka**. Este enfoque desacoplado y asíncrono, mejora la escalabilidad, resiliencia y consistencia eventual entre los microservicios.
    - `POST /clientes`: permite crear un nuevo cliente y, de manera automática y asíncrona, realiza los siguientes pasos adicionales:
        1. **Creación del Cliente**: Registra los datos del cliente en la base de datos del microservicio de clientes.
        2. **Notificación Asíncrona**: Publica un evento en Kafka informando sobre la creación del cliente.
        3. **Creación de la Cuenta**: El microservicio de cuentas, al recibir el evento, crea una cuenta asociada al cliente con un saldo inicial.
        4. **Registro de Movimiento Inicial**: También se registra un movimiento en la cuenta correspondiente al saldo de apertura.
    - `DELETE /clientes/{dni}`: permite eliminar un cliente y, de manera automática y asíncrona, realiza los siguientes pasos adicionales:
        1. **Eliminación del Cliente**: Borra el registro del cliente (eliminación lógica) identificado por su dni en la base de datos del microservicio de clientes.
        2. **Notificación Asíncrona**: Publica un evento en Kafka notificando la eliminación del cliente.
        3. **Eliminación de Cuentas**: El microservicio de cuentas, al recibir el evento, elimina todas las cuentas asociadas al cliente (eliminación lógica).

## Requisitos

- **Java 21**
- **Spring Boot**
- **Docker y Docker Compose**
- **PostgreSQL**
- **Kafka**

## Iniciar el Proyecto

### Requisitos Previos

- Tener **Docker** y **Docker Compose** instalados.

### Pasos para Ejecutar

1. Clonar el repositorio:
    ```bash
    git clone https://github.com/damoralesr97/prueba-devsu.git
    ```

2. Navegar al directorio del proyecto:
    ```bash
    cd prueba-devsu
    ```

3. Construir la imagen Docker y desplegar el proyecto con Docker Compose:
    ```bash
    docker-compose up --build -d
    ```

4. Acceder a la API a través de **Swagger**:
    - Personas/Clientes: `http://localhost:8080/swagger-ui.html`
    - Cuentas/Movimientos: `http://localhost:8180/swagger-ui.html`

## Funcionalidades

#### F1: CRUD de Entidades

La API REST permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre las siguientes entidades:

1. **Cliente**
   - `GET /clientes`: Obtiene todos los clientes.
   - `GET /clientes/{dni}`: Obtiene un cliente por dni.
   - `POST /clientes`: Crea un nuevo cliente.
   - `PUT /clientes/{dni}`: Actualiza un cliente.
   - `DELETE /clientes/{dni}`: Elimina un cliente.

2. **Cuenta**
   - `GET /cuentas`: Obtiene todas las cuentas.
   - `GET /cuentas/{accountNumber}`: Obtiene una cuenta por el número de cuenta.
   - `POST /cuentas`: Crea una nueva cuenta.
   - `PUT /cuentas/{accountNumber}`: Actualiza una cuenta.
   - `DELETE /cuentas/{accountNumber}`: Elimina una cuenta.

3. **Movimiento**
   - `GET /movimientos`: Obtiene todos los movimientos.
   - `GET /movimientos/{id}`: Obtiene un movimiento por su código.
   - `POST /movimientos`: Registra un nuevo movimiento.

4. **Reportes**
   - `GET /reportes`: Genera un reporte de "Estado de Cuenta".

#### F2: Registro de Movimientos
- Los movimientos pueden ser positivos o negativos.
- Se actualiza el saldo disponible de la cuenta tras registrar un movimiento.
- Se lleva un registro de todas las transacciones.

#### F3: Manejo de Saldo Insuficiente
- Si un movimiento no cuenta con suficiente saldo, se retorna el mensaje: `Saldo no disponible`.
- El manejo de este error se gestiona a través de la clase anotada con `@RestControllerAdvice`.

#### F4: Reportes de Estado de Cuenta
- Endpoint: `GET /reportes?startDate=date&endDate=date&dni=dni`
- Los campos de fecha son opcionales.
- Este endpoint genera un reporte del estado de cuenta de un cliente dentro de un rango de fechas. El reporte incluye:
  - Cuentas asociadas con sus respectivos saldos.
  - Detalle de movimientos de las cuentas.
- El reporte se retorna en formato **JSON**.

#### F5: Pruebas Unitarias
- Se implementa una prueba unitaria para la entidad `Cliente`, validando la creación y manipulación de los datos.

#### F6: Pruebas de Integración
- Se implementa una prueba de integración para el endpoint `GET /cuentas/{accountNumber}` que valida la comunicación HTTP.

#### F7: Despliegue en Contenedores
- El proyecto se despliega utilizando **Docker Compose**, que configura tanto los microservicios como las bases de datos y Kafka.

## Notas Adicionales

- **Kafka** se utiliza para la comunicación asincrónica entre los microservicios.
- Se ha configurado la base de datos **PostgreSQL** para cada microservicio en el archivo `docker-compose.yml`.
- Las pruebas se realizan utilizando **JUnit** y **Mockito** para las pruebas unitarias.
