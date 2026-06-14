# Taller de Java — Bug Hunt

## Descripción del proyecto

**Collaborative ToDo** es una API REST que permite gestionar una lista de tareas compartidas entre múltiples usuarios. Está construida con Spring Boot y persiste los datos en una base de datos H2 en memoria.

La aplicación expone los siguientes servicios:

| Método   | Ruta               | Descripción                                     |
| -------- | ------------------ | ----------------------------------------------- |
| `GET`    | `/api/todo`        | Lista todos los ToDos                           |
| `POST`   | `/api/todo`        | Crea un nuevo ToDo (`{"content":"..."}`)        |
| `DELETE` | `/api/todo/{id}`   | Elimina un ToDo por UUID                        |
| `GET`    | `/api/user`        | Lista todos los usuarios                        |
| `POST`   | `/api/user`        | Inicia sesión / crea usuario (`{"name":"..."}`) |
| `GET`    | `/api/currentuser` | Retorna el usuario de la sesión actual          |
| `GET`    | `/api/log`         | Lista todos los logs en memoria                 |

Una interfaz web simple está disponible en `http://localhost:8080` al ejecutar la aplicación.

---

## Objetivo

El código fuente contiene **bugs intencionales** distribuidos en los servicios de la aplicación. Estos bugs provocan que **todos los tests fallen**.

Tu tarea es:

1. Ejecutar los tests e identificar cuáles fallan.
2. Leer el test y el código fuente para entender **qué comportamiento se espera** y **por qué no se cumple**.
3. Corregir cada bug en el código de producción (no en los tests).
4. Verificar que el test correspondiente pasa tras la corrección.
5. Documentar los hallazgos completando la tabla del entregable.

> Los tests **no deben modificarse**. Solo se puede tocar el código en `src/main/java/`.

---

## Cómo ejecutar el proyecto

### Correr la aplicación

```bash
mvn spring-boot:run
```

La aplicación queda disponible en `http://localhost:8080`.

### Ejecutar los tests

```bash
# Todos los tests
mvn test

# Una clase de test específica
mvn test -Dtest=NombreDeLaClase

# Un método de test específico
mvn test -Dtest=NombreDeLaClase#nombreDelMetodo
```

**Ejemplos:**

```bash
mvn test -Dtest=LogServiceTest
mvn test -Dtest=LogServiceTest#addLogs
mvn test -Dtest=TodoServiceTest#testCreateTodo
```

---

## Estructura del código

```
src/
├── main/java/.../
│   ├── todo/
│   │   ├── ToDo.java              — Entidad JPA
│   │   ├── TodoController.java    — Endpoints REST
│   │   ├── TodoRepository.java    — Acceso a datos
│   │   └── TodoService.java       — Lógica de negocio
│   ├── users/
│   │   ├── User.java              — Entidad JPA
│   │   ├── UserController.java    — Endpoints REST
│   │   ├── UserRepository.java    — Acceso a datos
│   │   ├── UserService.java       — Lógica de negocio
│   │   └── CurrentUserService.java — Usuario de sesión actual (scope=session)
│   └── log/
│       ├── Log.java               — Modelo (no persistido en BD)
│       ├── LogController.java     — Endpoint REST
│       └── LogService.java        — Logs en memoria (HashMap)
└── test/java/.../
    ├── log/LogServiceTest.java
    ├── todo/TodoServiceTest.java
    └── users/
        ├── UserServiceTest.java
        └── CurrentUserTest.java
```

---

## Entregable

Completar la siguiente tabla con un análisis de cada test fallido. Para cada uno indicar la causa raíz del error y la corrección aplicada.

| Test (`Clase.método`)                   | Descripción del error                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         | Descripción de la solución                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| --------------------------------------- |:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `LogServiceTest.addLogs`                | El test fallaba al comparar el tamaño de los eventos cargados en el setup() con los que fueron insertados en los logs porque sus cantidades eran distintas. El problema es que al tomar cada evento y llamar al **LogService.addLog** se insertaba dos veces cada evento porque la linea de adicióñ estaba repetida.                                                                                                                                                                                                          | Se elimió la linea de adición repetida dentro de **LogService.addLog**                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| `LogServiceTest.hasAllLogsConcurrent`   | El test fallaba porque la totalidad de eventos iniciales no coincidia con la totalidad de logs creados por los hilos. Esto ocurría porque en la funcion **LogService.addLog** no había ningún acceso de seguridad concurrente al mapa de logs, donde cada hilo obtenia la lista de logs del mismo usuario y agregaba el propio, terminando con inconsistencias porque concurrentemente un hilo tenía una lista de logs distinta al otro en el mismo momento.                                                                  | Se modificó **LogService.addLog** para que el acceso a escritura de la lista de logs del mismo usuario sea concurrente a través de un semáforo, donde cada hilo obtiene un bloqueo antes de agregar un nuevo log y lo libera al finalizar. Cabe destacar que el test tarda al rededor de 32 segundos en finalizar porque se realiza un assertTrue preguntando por la existencia de 100.000 elementos  individuales en una lista.                                                                                                                                                                  |
| `LogServiceTest.getLogsByUser`          | El test creaba dos usuarios con igual nombre, y le insertaba un log a cada usuario creado, para comprobar luego que los logs insertados a usuarios de igual nombre se acumulaban dentro de la misma estructura. El problema es que el **LogService.addLog** utiliza un Map con clave User, por lo que cada usuario creado es una entrada distinta del mapa, y por lo tanto distinta lista de logs, por lo que el test fallaba.                                                                                                | Se modificó el criterio de inserción de logs, obteniendo primero la lista de logs del usuario de igual nombre que el user que viene por parámetro, todo usando programación funcional. De esta forma, ya no se crean dos entradas en el Map por cada nuevo User, sino que sólo se crean si los nombres son distintos. Además también se modificó el **LogService.getUserLogs** porque obtenía todos los logs a través de la clave del Map con objeto User, y en relación con la modificación previa, si se añaden logs según nombre de usuario, también se deben obtener según nombre de usuario. |
| `TodoServiceTest.testCreateTodo`        | El test fallaba cuando se intentaba comparar por igual el contenido el ToDo recien creado con el contenido del primer ToDo insertado en el repositorio. El problema era que en **TodoService.createNewTodo** se generaba una nueva instancia de ToDo y se asignaba el usuario e id, pero nunca se asignaba el contenido a esa instancia, por lo tanto devolvía un null cuando se preguntaba por su getContent.                                                                                                                | Se asignó el contenido a la instancia de ToDo creada en **TodoService.createNewTodo** a través del método **ToDo.setContent**.                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| `TodoServiceTest.testTimerDelete`       | El test debia eliminar todos los mensajes que se hayan creado hace más de 5 segundos, lo cual uno sólo cumplía y la lista debía quedar con 2 elementos, pero fallaba porque **TodoService.deleteOldMessages** sólo borraba el mensaje si su antigüedad era MENOR a los segundos pasados por parámetro.                                                                                                                                                                                                                        | Se modifició el condicional de borrado de mensajes en **TodoService.deleteOldMessages**para que se eliminen todos aquellos mensajes que su antigüedad sea MAYOR a los segundos dados por parámetro.                                                                                                                                                                                                                                                                                                                                                                                               |
| `CurrentUserTest.testGetsDefaultUser`   | El test controla que se retorne un usuario por defecto cuando se consulta el **CurrentUserService.getCurrent** y no hay ninguno asignado. Y el test fallaba porque sólo creaba un nuevo usuario si la variable current era distinta de nulo, osea que pisaba siempre al usuario usuario actual con una nueva instancia, en vez de retornar una por defecto.                                                                                                                                                                   | Se modificó **CurrentUserService.getCurrent** para que solo cree una nueva instancia de usuario si la variable current es nula.                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| `CurrentUserTest.testCurrentConcurrent` | El test fallaba porque el currentUser que obtenian ambos hilos no era el mismo debido a que en la funcion **CurrentUserService.getCurrent** no había ningún cuidado en el acceso concurrente, entonces ambos hilos veían a la vez que la variable current es nula y ambos creaban una nueva instancia de usuario, haciendo que la comparación luego fallara porque los objetos son distintos.                                                                                                                                 | Se modificó **CurrentUserService.getCurrent** para que sea thread-safe volviendola **synchronized**, así cada hilo accede a la variable current esperando las modificaciones del otro.                                                                                                                                                                                                                                                                                                                                                                                                            |
| `UserServiceTest.testSortedByName`      | El test intenta comparar un arreglo de usuarios propios inicializados en el **setup()** con la lista de usuarios cargados a través del **UserService**, mediante un ordenamiento por nombre, pero el método **UserService.getUsers** ordena todos los usuarios cargados por su ID antes de retornarlos, haciendo fallar la comparación el test.                                                                                                                                                                               | Se modificó **UserService.getUsers**para que retorne una lista ordenada a través del nombre de los usuarios.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| `UserServiceTest.testAddNullUser`       | El test intenta agregar un usuario nulo captando la excepción ante un error pero el metodo **UserService.addUser** no tiene ninguna verificación, simplemente intenta agregar un log y falla al llamar el metodo **getName()** de un objeto nulo.                                                                                                                                                                                                                                                                             | Se agregó la verificacióñ de user nulo dentro de **addUser**, donde si el usuario es nulo lanzamos un **IllegalArgumentException** que está esperando el test. Si el usuario no es nulo, se opera normalmente agregando un log y guardando el usuario en el repositorio.                                                                                                                                                                                                                                                                                                                          |
| `UserServiceTest.testLoginUser`         | El test inserta 3 nuevos usuarios, sabiendo que actualmente al momento del este test hay 8 guardados en el repositorio, por lo tanto intenta comprobar que los id de los nuevos usuarios insertados osn 9, 10 y 11 respectivamente pero falla porque en la expresión "lastId = lastId++;" del metodo **UserService.login** estamos indicando que el auto incremento de la variable se haga a posteriori, por lo tanto se asigna primero el valor viejo a la variable del lado izquierdo, y luego se incrementa en una unidad. | Se modificó la asignación de conflicto de modo que el auto incremento se haga a priori, primero incrementando la variable lastId, luego guardado este nuevo valor en una variable auxiliar, y luego asignandola nuevamente a lastId, quedandose así con un valor incrementado en una unidad. También se puede solucionar simplemente modificando el operador ternario de **UserService.login** para que asigne un last.getId()+1 en el caso de que haya al menos un usuario en el repositorio.                                                                                                    |
| `UserServiceTest.testAddUsersAndClear`  | El test lanza una excepción programada porque la memoria ram asignada al programa supera los 50 MB. Esto sucede porque al agregar un usuario al **UserService** tambien se añade un log que contiene el objeto entero del usuario y estos logs nunca se eliminan, aumentando la memoria del sistema con cada ejecucion del test.                                                                                                                                                                                              | Se agregó una limpieza de logs con el método **clear() del LogService** cuando se invoca a **clearUsers()** en el UserService.                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
