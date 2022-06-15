# Titulo

Networking

## Descripción

En este repositorio se encontrara un programa que crea un servidor web, permitiendo multiples solicitudes seguidas no concurrentes.

### LOC/h

Para este taller se hicieron 387 líneas de código, en 6 horas.

**64.5 LOC/h**

### Prerrequisitos

Para correr este se debe tener instalado:

- Maven
- Java

### Guía de uso

Para compilar el proyecto se debe usar:

```
mvn package
```

Para ejecutarlo, se debe hacer de la siguiente forma

```
$ java -cp "target\classes" edu.escuelaing.co.app.HttpServerController [start o stop]
```

Una vez ejecute este comando podrá ingresar al servidor web desde su navegador con la siguiente ruta http://localhost:35000/resources/index.html, en esta encontrará una página html con un script en JavaScript y 2 imagenes, una jpeg y otra gif.

Si desea visualizar cualquiera de estos por separado debe indicar la ruta completa donde se encuentran estos recursos.

En caso de que desee insertar un recurso propio, lo puede hacer, se recomienda hacerlo en la carpeta resources para mantener su orden respectivo.

## Documentación

Para visualizar la documentación se debe ejecutar el siguiente comando:

```
mvn javadoc:javadoc
```

Una vez se realice este comando, se debe buscar en la siguiente ruta "target\site\apidocs\index.html"

## Estructura de Archivos

    .
    |____pom.xml
    |____src
    | |____main
    | | |____java
    | | | |____edu
    | | | | |____escuelaing
    | | | | | |____co
    | | | | | | |____app
    | | | | | | | |____HttpServerController.java
    | | | | | | | |____HttpServer.java
    | | | | | | | |____explicacion
    | | | | | | | | |____EchoClient.java
    | | | | | | | | |____EchoServer.java
    | | | | | | | | |____MathClient.java
    | | | | | | | | |____MathServer.java
    | | | | | | | | |____URLExplorer.java
    | | | | | | | | |____URLReader.java
    | |____test
    | | |____java
    | | | |____edu
    | | | | |____escuelaing
    | | | | | |____co
    | | | | | | |____app

## Diagrama de Clases

![Diagrama de Clases](img/diagrama_de_clases.png)

## Construido con

- [Maven](https://maven.apache.org/) - Dependency Management
- [Java](https://www.java.com/es/) - Progamming Language

## Autor

- **Juan Carlos Baez Lizarazo** - [juanbaezl](https://github.com/juanbaezl)

## Fecha

15 de Junio, 2022

## Licencia

Para más información ver: [LICENSE.txt](License.txt)
