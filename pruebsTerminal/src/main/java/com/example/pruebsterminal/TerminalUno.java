package com.example.pruebsterminal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class TerminalUno {
    public static void main(String args[]){
        String command= "echo '1vanHack'\n" +
                "sleep 5\n" +
                "echo '************'\n" +
                "echo 'Fuiste intevenido'\n" +
                "sleep 10\n";
        String archivo= "test.sh";
        try {
            PrintWriter p = new PrintWriter(archivo);
            p.write(command);
            p.flush();
            p.close();
            ProcessBuilder pr= new ProcessBuilder("cmd.exe","/c",archivo);
            pr.start().waitFor();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
