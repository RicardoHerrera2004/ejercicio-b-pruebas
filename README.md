## 🛠️ Ejecución de Pruebas con Testcontainers

Este proyecto implementa pruebas de integración utilizando la librería **Testcontainers** para validar la persistencia de datos contra una instancia real de **PostgreSQL**, tal como se solicitó en el Ejercicio B

### Justificación Técnica
Debido a restricciones de infraestructura en el entorno de desarrollo local (ausencia de Docker Desktop), la ejecución de los contenedores se delegó a **GitHub Actions**.

El archivo `.github/workflows/maven.yml` orquesta el siguiente flujo:
1.  Aprovisiona una máquina virtual con soporte para Docker.
2.  [cite_start]Levanta el contenedor de PostgreSQL automáticamente al iniciar los tests[cite: 48].
3.  Ejecuta `mvn test` para validar la lógica del repositorio `PaymentRepository`.

Esto asegura que las pruebas no sean frágiles ni dependan de la configuración de una máquina específica[cite: 93], garantizando un entorno limpio y reproducible para cada ejecució.
