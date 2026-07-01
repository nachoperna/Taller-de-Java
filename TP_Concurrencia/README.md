# Ejercicio 1 Tamaño de directorio (Fork/Join)

Calcula el tamaño total de un directorio usando ForkJoinPool, procesando subdirectorios en paralelo.

Requiere la ruta del directorio como argumento:

```bash
java -cp target/classes ejercicio1.ReporteTamDir /ruta/directorio
```

# Ejercicio 2 Contador de palabras concurrente

Analiza uno o mas archivos de texto en paralelo usando ExecutorService y Callable,
y muestra el ranking de palabras ordenado por frecuencia.

Requiere las rutas de los archivos como argumentos:

```bash
java -cp target/classes ejercicio2.Main test-data/QuijotePrimerParrafo.txt test-data/QuijoteSegundoParrafo.txt test-data/QuijoteTercerParrafo.txt
```

# Ejercicio 3 Cálculo de número PI

Compara el rendimiento entre una version monohilo y una multihilo para estimar Pi.

Acepta un argumento opcional con la cantidad de pruebas (a mayor cantidad de pruebas mayor la diferencia de rendimiento).

Se utilizan por defecto mil millones de pruebas debido a que la tarea a realizar por cada hilo es muy rápida y se tarda mas tiempo creando el pool de hilos y coordinando sus tareas, por lo que se necesita gran cantidad de pruebas para que el cómputo paralelo supere el costo fijo de la creación de hilos.

```bash
java -cp target/classes ejercicio3.ComparadorRendimiento
```

# Ejercicio 4 Problema del Barbero

Simula una barberia con dos implementaciones: usando monitores y usando Lock/Condition.

```bash
java -cp target/classes ejercicio4.SimuladorBarberia
```
