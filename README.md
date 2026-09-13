<h1 align="center">Gestor de Finanzas Personales</h1>


<div align="center">
  <img src="https://skillicons.dev/icons?i=java,mysql,bootstrap,html&theme=light" alt="Tecnologías del proyecto">
</div>

<br>

Proyecto web desarrollado para registrar y controlar ingresos y gastos personales. La idea principal de este proyecto fue aprender a construir una aplicación web desde cero utilizando Java puro, sin depender de ORMs ni frameworks automáticos, para entender cómo funciona la persistencia y la arquitectura por debajo.

---

## Vista General del Sistema


<div align="center">
  <img src="https://github.com/user-attachments/assets/be232f42-8451-4503-9bbd-9281e98040d9" alt="Vista Principal del Proyecto" width="60%">
</div>

---

## ¿Por qué hice este proyecto?

Muchos desarrolladores aprenden frameworks avanzados (como Spring Boot o Hibernate) desde el primer día sin saber qué pasa realmente en el código. Con este proyecto quise hacer lo contrario:

* **Entender el flujo real:** Escribir las consultas SQL a mano y gestionar las conexiones con JDBC.
* **Aplicar patrones de diseño clásicos:** Organizar el código usando el patrón **MVC** (Modelo-Vista-Controlador), separar las consultas con **DAO**, usar objetos **DTO** para llevar los datos de un lado a otro, y aplicar **Singleton** para no saturar la conexión a la base de datos.
* **Tener bases sólidas:** Servir de puente antes de pasar a estudiar Spring Boot y microservicios.

---

## Módulos de la Aplicación

<div align="center">
  <table>
    <tr>
      <td align="center"><b>Gestión de Categorías</b></td>
      <td align="center"><b>Registro de Movimientos</b></td>
    </tr>
    <tr>
      <td><img src="https://github.com/user-attachments/assets/d5f44af0-8ce2-4de0-9725-712703072cb4" alt="Módulo Categorías" width="700"></td>
      <td><img src="https://github.com/user-attachments/assets/25b382b6-2aa0-4497-9bab-143d47efad89" alt="Módulo Movimientos" width="700"></td>
    </tr>
  </table>
</div>

---

## Cómo ejecutarlo en local

1. Clona el repositorio en tu computadora.
2. Crea una base de datos en MySQL y ejecuta el script con las tablas necesarias.
3. Actualiza los datos de conexión (usuario y contraseña) en la clase de conexión del proyecto.
4. Levanta el proyecto en tu servidor Apache Tomcat y ábrelo en el navegador.
