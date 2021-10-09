package com.mascas;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String _NETSH = "C:\\Windows\\System32\\netsh.exe";

    private static int tamanioRutaInicial;
    private static String nombreRegla;
    private static boolean recursivo = false;
    private static boolean soloEntrada = false;
    private static boolean soloSalida = false;

    public static void main(String[] args) throws IOException {

//        if (!isAdmin()) {
//            System.out.println("Se necesitan permisos de administrador...");
//        }

        if (args.length < 2) {
            return;
        }

        String rutaInicial = args[0];
        tamanioRutaInicial = rutaInicial.length() + 1;
        nombreRegla = args[1];

        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                switch (args[i].toUpperCase()) {
                    case "/IO" -> soloEntrada = true;
                    case "/OO" -> soloSalida = true;
                    case "/R" -> recursivo = true;
                    default -> {
                        System.out.println("Comando desconocido: " + args[i]);
                        return;
                    }
                }
            }
        }

        File carpetaRaiz = new File(rutaInicial);

        if (!carpetaRaiz.exists()) {
            System.out.println("No existe la carpeta '" + rutaInicial + "'");
            return;
        }

        List<String> commands = recorreCarpeta(carpetaRaiz);

        if (commands.isEmpty()) {
            System.out.println("No se encontraron archivos ejecutables dentro del directorio...");
            return;
        }

        FileWriter writer = new FileWriter(rutaInicial + "\\" + nombreRegla + "FirewallBlocker.cmd");
        for(String str: commands) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();

        //Runtime.getRuntime().exec(commands);
    }

    private static ArrayList<String> recorreCarpeta(File carpeta) {
        ArrayList<String> sentencias = new ArrayList<>();

        for (File file : carpeta.listFiles()) {
            if (file.isFile()) {
                if (file.getName().toLowerCase().endsWith(".exe")) {
                    if (!soloEntrada)
                        sentencias.add(_NETSH + " advfirewall firewall add rule name=\"" + nombreRegla + " " + file.getAbsolutePath().substring(tamanioRutaInicial) + "\" dir=out action=block program=\""+file.getAbsolutePath()+"\" enable=yes profile=any");
                    if (!soloSalida)
                        sentencias.add(_NETSH + " advfirewall firewall add rule name=\"" + nombreRegla + " " + file.getAbsolutePath().substring(tamanioRutaInicial) + "\" dir=in action=block program=\""+file.getAbsolutePath()+"\" enable=yes profile=any");
                }
            } else if (recursivo) {
                sentencias.addAll(recorreCarpeta(file));
            }
        }

        return sentencias;
    }

    private static boolean isAdmin() {
        String[] groups = (new com.sun.security.auth.module.NTSystem()).getGroupIDs();
        for (String group : groups) {
            if (group.equals("S-1-5-32-544"))
                return true;
        }
        return false;
    }

}
