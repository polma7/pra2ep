# 🚀 Práctica de "Enginyeria del Programari" - Micromovilidad Compartida

## 📋 Descripción
Este repositorio contiene el código desarrollado para la práctica de la asignatura **"Enginyeria del Programari"**.

El objetivo principal de la práctica es diseñar, implementar y refactorizar un sistema para la gestión de un servicio de micromovilidad compartida, aplicando los principios **SOLID**, patrones **GRASP** y técnicas de refactorización para resolver problemas de diseño (code smells) presentes en el sistema inicial, a partir del temario y los ejemplos vistos y comentados en clase.

---

## 🎯 Objetivos del Proyecto
1. Diseñar un sistema extensible y mantenible para la gestión de micromovilidad compartida.
2. Aplicar principios de diseño de software como **SOLID** y patrones **GRASP**.
3. Detectar y corregir posibles **code smells** mediante técnicas de **refactorización**.
4. Desarrollar pruebas unitarias para validar el correcto funcionamiento del sistema.

---

## 🛠️ Tecnologías Utilizadas
- **Lenguaje:** Java
- **Frameworks de testing:** JUnit 5
- **Herramientas de gestión de dependencias:** Maven
- **Editor/IDE recomendado:** IntelliJ IDEA Community Edition.

---

## 📂 Estructura del Proyecto

### Archivos principales:
El codigo fuente se encuentra en la carpeta `src/` y las clases principales son las siguientes:
- **`JourneyRealizeHandler.java`**: Controlador para gestionar la interacción entre usuarios y sistema.
- **`JourneyService.java`**: Lógica para calcular distancias, duraciones y costes de los viajes.
- **`ServerClass.java`**: Gestión del estado y conexión de los vehículos con el servidor.

### Pruebas:
Las pruebas unitarias están en la carpeta `test/`. Incluyen casos para validar:

---

## 💡 Principios y Patrones Aplicados
- **Principios SOLID:**
  - **SRP (Single Responsibility Principle):** Cada clase tiene una única responsabilidad claramente definida.
  - **OCP (Open/Closed Principle):** El sistema es extensible sin modificar el código existente.
  - **DIP (Dependency Inversion Principle):** Uso de inyección de dependencias para desacoplar módulos.
  
- **Patrones GRASP:**
  - **Controller:** Uso de controladores para gestionar las interacciones.
  - **Information Expert:** Cada clase maneja la lógica correspondiente a la información que posee.
  - **Low Coupling y High Cohesion:** Mínima dependencia entre módulos y alta cohesión interna.

---
## 👥 Autores
  - Pol Masip
  - Carlos Mazarico

