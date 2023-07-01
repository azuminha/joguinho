import java.net.*;
import java.io.*;
//import java.util.*;
//import java.awt.event.*;

public class Servidor{
    
    private ServerSocket serverSocket = null;
    private Socket p1Socket, p2Socket;
    private int numPlayers = 0, maxPlayers = 2;
    private double p1x = 60, p1y = 450, p2x = 730, p2y = 450;
    private double p1angulo = 0, p2angulo = 3.1415; 
    private double p1TiroX = 60, p1TiroY = 450, p2TiroX = 730, p2TiroY = 450;
    private double p1anguloTiro = 0, p2anguloTiro = 3.1415;
    private InformacoesDoCliente p1RecebeInfo, p2RecebeInfo;
    private InformacoesParaOCliente p1MandaInfo, p2MandaInfo;
    
    private boolean jogoFinalizado = false;

    int pontos1 = 0, pontos2 = 0;


    int tamJogador = 35;
    int mapaWidth = 800, mapaHeight = 600;
    int tileSize = 50;
    int mapaHeight_offset = 200;
    public int[][] mapa = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}     
    };

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
        private boolean contou1 = false, contou2 = false;

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
                        p1TiroX = dataIn.readDouble();
                        p1TiroY = dataIn.readDouble();
                        p1anguloTiro = dataIn.readDouble();
                    //    System.out.println("RECEBEU A POS SERVER: " + p1x + " " + p1y);
                    } else {
                        p2x = dataIn.readDouble();
                        p2y = dataIn.readDouble();
                        p2angulo = dataIn.readDouble();
                        p2TiroX = dataIn.readDouble();
                        p2TiroY = dataIn.readDouble();
                        p2anguloTiro = dataIn.readDouble();
                    }
                    
                    
                    double pontoTiroX = tamJogador*Math.cos(p1anguloTiro) + p1TiroX+ tamJogador/2;
                    double pontoTiroY = tamJogador*Math.sin(p1anguloTiro) + p1TiroY + tamJogador/2;

                    double distTiroP1 = (p2x + tamJogador/2 - pontoTiroX) * (p2x + tamJogador/2 - pontoTiroX)+
                                        (p2y + tamJogador/2 - pontoTiroY) * (p2y + tamJogador/2 - pontoTiroY);
                     
                    if(distTiroP1 <= 17 * 17 && !contou1){
                        pontos1++;
                        contou1 = true;
                    }

                    pontoTiroX = tamJogador*Math.cos(p2anguloTiro) + p2TiroX+ tamJogador/2;
                    pontoTiroY = tamJogador*Math.sin(p2anguloTiro) + p2TiroY + tamJogador/2;

                    double distTiroP2 = (p1x + tamJogador/2 - pontoTiroX) * (p1x + tamJogador/2 - pontoTiroX) +
                                        (p1y + tamJogador/2 - pontoTiroY) * (p1y + tamJogador/2 - pontoTiroY);
                    if(distTiroP2 <= 17 * 17 && !contou2){
                        pontos2++;
                        contou2 = true;
                    }
                    
                    if(pontos1 >= 18 || pontos2 >= 18){
                        jogoFinalizado = true;
                    }
                    if(distTiroP1 >= 50*50){
                        contou1 = false;
                    }
                    if(distTiroP2 >= 50*50){
                        contou2 = false;
                    }
                    System.out.println("p1 : " + pontos1 + " p2 : " + pontos2);
                   
                    
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
                    dataOut.writeInt(pontos1);
                    dataOut.writeInt(pontos2);
                    dataOut.writeBoolean(jogoFinalizado);

                    if(playerID == 1) {
                        dataOut.writeDouble(p2x);
                        dataOut.writeDouble(p2y);
                        dataOut.writeDouble(p2angulo);
                        dataOut.writeDouble(p2TiroX);
                        dataOut.writeDouble(p2TiroY);
                        dataOut.writeDouble(p2anguloTiro);
                        dataOut.flush();
                    //    System.out.println("SERVIDOR MANDOU A POS: " + p2x + " " + p2y);
                    } else {
                        dataOut.writeDouble(p1x);
                        dataOut.writeDouble(p1y);
                        dataOut.writeDouble(p1angulo);
                        dataOut.writeDouble(p1TiroX);
                        dataOut.writeDouble(p1TiroY);
                        dataOut.writeDouble(p1anguloTiro);
                        dataOut.flush();
                    }

                    if(jogoFinalizado){
                        if(pontos1 > pontos2){
                            dataOut.writeInt(1);
                        }else{
                            dataOut.writeInt(2);
                        }
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
