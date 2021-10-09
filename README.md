# FirewallFolderBlocker

FirewallFolderBlocker nace a raíz de que necesitaba bloquear las conexiones de todos los ejecutables ubicados en una carpeta de Windows 10, y ya que el firewall de windows no tiene esta opción, y ponerme a añadirlos uno a uno no lo es, aquí estamos.

## Cómo funciona

El programa genera un archivo CMD con las instrucciones para bloquear todos los archivos `.exe` ubicados en la carpeta seleccionada, que deberás ejecutar despues manualmente como administrador.

### Instrucciones de uso

    java -jar FirewallFolderBlocker.jar (PathFolder) (RuleName) [/R] [/IO | /OO]
    /R    -> Recursive search
    /IO   -> Input only
    /IO   -> Output only
    /H    -> Help text
    /HELP -> Help text

#### Ejemplo de ejecución:

> java -jar FirewallFolderBlocker.jar "C:\BadGames\LeagueOfLegends" "Give me back my life" /R /OO

Se compila con la [JDK14](https://jdk.java.net/archive/) o superior pero yo recomiendo usar la [JDK17](https://jdk.java.net/17/).

## Contributing
Cualquier cambio a mejor es bienvenido, soy consciente de que la aplicación es fea y cutre, pero hace lo que se le pide.

## License
[GNU AGPLv3](https://choosealicense.com/licenses/agpl-3.0/)
