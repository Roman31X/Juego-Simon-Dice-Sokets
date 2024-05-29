package com.roman31x.juegosimondicesockets;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Roman Guerra Diego
 */

public class SimonServidor {
    
    //CREAMMOS VARIABLES GLOBALES CON EL PUERTO UN ARREGLO DONDE ALMACENARMEOS LOS COLORES PARA JUGAR
    private static final int PORT = 8080;
    private static final String[] COLORS = {"rojo", "azul", "verde", "amarillo","naranja","violeta"};
    
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor Simon Dice iniciado...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                new ControladorCliente(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ControladorCliente extends Thread {
        private Socket clientSocket;
        private Random random;

        public ControladorCliente(Socket socket) {
            this.clientSocket = socket;
            this.random = new Random();
        }

        @Override
        public void run() {
            try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                // Generar secuencia de colores aleatoria
                List<String> sequence = generadorRandomSecuencia();
                System.out.println("Secuencia generada: " + sequence);

                // Enviar secuencia al cliente
                out.println(String.join(",", sequence));

                // Recibir secuencia del cliente
                String receivedSequence = in.readLine();
                System.out.println("Secuencia recibida del cliente: " + receivedSequence);

                // Verificar secuencia de repuesta de colores
                boolean isCorrect = receivedSequence.equals(String.join(",", sequence));

                // Respuesta enviada al cliente
                out.println(isCorrect ? "CORRECTO" : "INCORRECTO");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //METODO ENCARGADO DE GENERAR LA SECUENCIA RANDOM DE COLORES PARA EL JUEGO
        private List<String> generadorRandomSecuencia() {
            List<String> sequence = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                sequence.add(COLORS[random.nextInt(COLORS.length)]);
            }
            return sequence;
        }
    }
}
