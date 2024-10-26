# Participación 2

Repositorio para el ejercicio de participación 2 del Módulo 10.

### Integrantes

- **Integrante 1**: David Israel Gutiérrez Núñez
- **Integrante 2**: Alejandro Noyola Nazario
- **Integrante 3**: Matías Suárez
- **Integrante 4**: Francisco Williams Jiménez Hernández
- **Integrante 5**: Guillermo Enrique Romero Zuñiga

---

## Descripción de Endpoints - Estados

- **GET** `/api/v2/estados/`  
  Devuelve una lista de todos los estados en formato `EstadoDto`.
  
- **GET** `/api/v2/estados/{id}`  
  Devuelve un estado específico en formato `EstadoDto` basado en su ID. Si no se encuentra el estado, responde con un código 404.

- **POST** `/api/v2/estados/`  
  Crea un nuevo estado a partir de un objeto `EstadoDto` proporcionado en el cuerpo de la solicitud. Si el ID ya existe, devuelve un error 400 (Bad Request).

- **PUT** `/api/v2/estados/{id}`  
  Actualiza completamente un estado existente con el ID especificado usando los datos proporcionados en el cuerpo de la solicitud. Si el estado no existe, lo crea y devuelve un código 201 (Created).

- **PATCH** `/api/v2/estados/{id}`  
  Actualiza parcialmente los datos de un estado específico. Solo los campos presentes en el cuerpo de la solicitud serán actualizados. Si el estado no existe, devuelve un código 404 (Not Found).

- **DELETE** `/api/v2/estados/{id}`  
  Elimina un estado específico basado en su ID. Si el estado no existe, responde con un código 404.

- **GET** `/api/v2/estados/paginado?page=0&size=2&dir=asc&sort=idEstado`  
  Devuelve una lista paginada de estados en formato `Estado`, permitiendo la configuración de los parámetros de página, tamaño, dirección (`asc` o `desc`) y campo de orden (`sort`). 
