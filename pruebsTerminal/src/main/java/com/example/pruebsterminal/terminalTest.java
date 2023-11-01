package com.example.pruebsterminal;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.FileWriter;
import java.io.IOException;

public class terminalTest {
    public static void main(String[] args){
        try {
           /** // Comando y argumentos para abrir la terminal
            String os = System.getProperty("os.name").toLowerCase();
            String command = "";
            if (os.contains("win")) {
                command = "cmd.exe";
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                command = "bash";
            } else {
                System.out.println("No se pudo determinar el sistema operativo compatible.");
                return;
            }
            System.out.println("La terminal es: " + command);
            // Crear un proceso y configurarlo con el comando de la terminal
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            // Iniciar el proceso
            Process process = processBuilder.start();

            // Obtener el flujo de salida del proceso para escribir en la terminal
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

            // Escribir comandos en la terminal
            writer.write("echo 'Hola Mundo'"); // Ejecuta el comando 'echo' para imprimir 'Hola Mundo'
            writer.flush();
            writer.newLine();
            writer.write("touch archivo.txt"); // Ejecuta el comando 'touch' para crear un archivo llamado 'archivo.txt'
            writer.newLine();
            writer.write("exit"); // Cierra la terminal
            writer.newLine();
            writer.flush();

            // Esperar a que el proceso termine
            int exitCode = process.waitFor();
            System.out.println("Proceso terminado con código de salida: " + exitCode);*/

                        // Crear archivo de script
                        String scriptContent = "echo 'Hola Mundo desde segunda prueba'\n\n" +
                                "sleep 3\n" +
                                "echo 'no esta siendo hackeado... es una prueba'\n\n" +
                                "echo 'imprimiendo con echo\n'" +
                                "touch archivo.txt\n" +
                                "echo 'text for test' >> archivo.txt\n" +
                                //"echo 'text for test' > archivo.txt\n" +
                                "sleep 10\n";
                        String scriptFileName = "script.sh"; // Nombre del archivo de script

                        FileWriter fileWriter = new FileWriter(scriptFileName);
                        fileWriter.write(scriptContent);
                        fileWriter.close();

                        // Dar permisos de ejecución al archivo de script
                        //ProcessBuilder chmodProcessBuilder = new ProcessBuilder("chmod", "+x", scriptFileName);

                        ProcessBuilder chmodProcessBuilder = new ProcessBuilder("cmd.exe", "/c", scriptFileName);
                        chmodProcessBuilder.start().waitFor();

                        ProcessBuilder ch= new ProcessBuilder("cmd.exe", "/c", scriptFileName);
                        ch.start().waitFor();

                        // Ejecutar el archivo de script
                        //ProcessBuilder scriptProcessBuilder = new ProcessBuilder("./" + scriptFileName);
                        //scriptProcessBuilder.start().waitFor();

                        // Eliminar el archivo de script después de su ejecución
                        //ProcessBuilder rmProcessBuilder = new ProcessBuilder("rm", scriptFileName);
                        //rmProcessBuilder.start().waitFor();

                        System.out.println("Proceso terminado correctamente.");



        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
