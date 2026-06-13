# SistemaInscripcion

Este proyecto expone los endpoints GET, POST, PUT y DELETE. Fue creado para probar integraciones y utiliza una conexion de base de datos en la nube.
Se agrego una funcionalidad con Amazon S3,en la cual hacemos uso de buckets para el almacenamiento de objetos.

---

## Endpoints disponibles para CURSO

### GET `/api/cursos`

Devuelve todos los cursos registrados en el sistema.

---

### POST `/api/cursos`

Agrega un nuevo registro de curso a la base de datos.

---

### PUT `/api/cursos/{id}`

Modifica un registro de curso segun los cambios entregados y el id.

--- 

### DELETE `/api/cursos/{id}`

Elimina el curso segun el id proporcionado.

---

## Endpoints disponibles para INSCRIPCION

### GET `/api/inscripciones`

Devuelve todos las inscripciones registrados en el sistema.

---

### POST `/api/inscripciones`

Agrega una nueva inscripcion de cursos a la base de datos.

---

### PUT `/api/inscripciones/{id}`

Modifica un registro de inscripcion segun los cambios entregados y el id.

--- 

### DELETE `/api/cursos/{id}`

Elimina la inscripcion segun el id proporcionado.

## Notas

-- Actividad para probar pipelines de integracion y despliegue.