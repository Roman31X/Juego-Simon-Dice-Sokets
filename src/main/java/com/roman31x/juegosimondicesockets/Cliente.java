package com.roman31x.juegosimondicesockets;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Roman Guerra Diego
 */

public class Cliente {
    
    //CREAMOS VARIABLE GLOBALES CON LA RUTA Y PUERTO DE CONEXION
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;
        
    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));){
            
            
            // Recibir la secuencia de colores de Simon Servidor
            String secuenciaRecibida = in.readLine();

            // Se solicitar al usuario que ingrese la secuencia de colores enviada por el servidor
            String userSequence = JOptionPane.showInputDialog("Simon Servidor Dice: [ "+secuenciaRecibida+" ]\n"+
                                                                "Ingrese la secuencia recibida incluir (,)");

            // Se enviar la secuencia al Simon Servidor
            out.println(userSequence);

            // Recibir y mostrar la respuesta del servidor
            String respuestaServidor = in.readLine();
            JOptionPane.showMessageDialog(null,"Simon Servidor dice : [ "+respuestaServidor+" ]");
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
