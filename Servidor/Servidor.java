import java.net.*;
import java.io.*;
//import java.util.*;
//import java.awt.event.*;

public class Servidor{
    
    private ServerSocket serverSocket = null;
    private Socket p1Socket, p2Socket;
    private int numPlayers = 0, maxPlayers = 2;
    private double p1x = 100, p1y = 400, p2x = 600, p2y = 400;
    private double p1angulo = 0, p2angulo = 3.1415; 
    private InformacoesDoCliente p1RecebeInfo, p2RecebeInfo;
    private InformacoesParaOCliente p1MandaInfo, p2MandaInfo;
    
    public Servidor() {
        System.out.println("===== GAME SERVER =====");
        numPlayers = 0;
        maxPlayers = 2;
        try {
            serverSocket = new ServerSocket(5000);
        } catch(IOException e) {
            System.out.println("IOException ocorreu.");
        }
    }

    public void aceitarConexoes() {
        try {
           System.out.println("Esperando pelos jogadores..."); 
           while(numPlayers < maxPlayers) {
               Socket playerSocket = serverSocket.accept();
               DataInputStream in = new DataInputStream(playerSocket.getInputStream());
               DataOutputStream out = new DataOutputStream(playerSocket.getOutputStream());

               numPlayers += 1;
               out.writeInt(numPlayers);
               System.out.println("Jogador #" + numPlayers + " conectou.");

               InformacoesDoCliente recebeInfoTemp = new InformacoesDoCliente(numPlayers, in);
               InformacoesParaOCliente mandaInfoTemp = new InformacoesParaOCliente(numPlayers, out); 
               
               if(numPlayers == 1) {
                   p1Socket = playerSocket;
                   p1RecebeInfo = recebeInfoTemp;
                   p1MandaInfo = mandaInfoTemp;
               } else {
                   p2Socket = playerSocket;
                   p2RecebeInfo = recebeInfoTemp;
                   p2MandaInfo = mandaInfoTemp;
                   p2MandaInfo.mandaOJogoComecar();
                   p1MandaInfo.mandaOJogoComecar();

                   new Thread(p1RecebeInfo).start();
                   new Thread(p2RecebeInfo).start();

                   new Thread(p1MandaInfo).start();
                   new Thread(p2MandaInfo).start();
               }
           }
           System.out.println("Numero maximo de jogadores atingido.");
        } catch(IOException e) {
            System.out.println("IOException ocorreu no momento de aceitar a conexao.");
        }
    }

    private class InformacoesDoCliente implements Runnable {
        private int playerID;
        private DataInputStream dataIn;

        public InformacoesDoCliente(int pID, DataInputStream in) {
            playerID = pID;
            dataIn = in;
        }

        public void run() {
            try {
                while(true) {
                    if(playerID == 1) {
                        p1x = dataIn.readDouble();
                        p1y = dataIn.readDouble();
                        p1angulo = dataIn.readDouble();
                    //    System.out.println("RECEBEU A POS SERVER: " + p1x + " " + p1y);
                    } else {
                        p2x = dataIn.readDouble();
                        p2y = dataIn.readDouble();
                        p2angulo = dataIn.readDouble();
                    }
                }
            } catch(IOException e) {
                System.out.println("IOException quando o servidor recebe as informacoes.");
            }
        }
    }

    private class InformacoesParaOCliente implements Runnable {
        private int playerID;
        private DataOutputStream dataOut;

        public InformacoesParaOCliente(int pID, DataOutputStream out) {
            playerID = pID;
            dataOut = out;
        }

        public void run() {
            try {
                while(true) {
                    if(playerID == 1) {
                        dataOut.writeDouble(p2x);
                        dataOut.writeDouble(p2y);
                        dataOut.writeDouble(p2angulo);
                        dataOut.flush();
                    //    System.out.println("SERVIDOR MANDOU A POS: " + p2x + " " + p2y);
                    } else {
                        dataOut.writeDouble(p1x);
                        dataOut.writeDouble(p1y);
                        dataOut.writeDouble(p1angulo);
                        dataOut.flush();
                    }
                    try {
                        Thread.sleep(25);
                    } catch(InterruptedException e) {
                        System.out.println("InterruptedException no momento de mandar informacoes para o cliente");
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException no momento de mandar informacoes para o cliente.");
                e.printStackTrace();
            }
        }

        public void mandaOJogoComecar() {
            try {
                dataOut.writeInt(1);
            } catch(IOException e) {
                System.out.println("IOException na hora de o jogo comecar.");
            }
        }
    }

    public static void main(String[] args){
        Servidor server = new Servidor();
        server.aceitarConexoes();
    }
}
