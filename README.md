# Tarea 2 CC4102-Diseño y Análisis de Algoritmos
Tarea 2 del curso Diseño y Análisis de Algoritmos

## Integrantes
* Pedro Belmonte
* Víctor Garrido

## Requisitos
La tarea fue resuelta en Java usando el JDK 11 de Oracle en Windows 10. También fue probada en Ubuntu 18.04.

## Compilación
Para compilar se debe ejecutar el siguiente comando:

```
$ javac Main.java
```

Esto genera los ejecutables para poder probar la tarea.

## Ejecución
Para ejecutar hay que darle 2 flags a la VM:

* El archivo Main ejecuta algunos tests que usan assert, para que se ejecute correctamente es necesario darle la flag -ea.
* La generación de gráficos necesita demasiada memoria, por lo cual hay que asignarle memoria extra al heap mediante la flag -Xmxn. Por ejemplo para que el programa funcione correctamente es mejor darle al menos unos 8gb, esto se hace con la flag -Xmx8192m.

Es decir, para ejecutar el programa hay que usar el siguiente comando:

```
$ java -ea -Xmx8192m Main
```
