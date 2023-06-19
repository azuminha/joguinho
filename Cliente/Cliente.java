import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import java.lang.Math;

public class Cliente extends JFrame{
    private int width = 800, height = 800, playerID;
    private Jogador p1, p2;
    private Socket socket; 
    private Desenho dc;
    private InformacoesDoServidor recebeInfo;
    private InformacoesParaOServidor mandaInfo;

    private boolean tiro = false,
            anguloMais = false,
            anguloMenos = false,
            frente = false;

    private class Desenho extends JPanel{
        private Desenho() {
            if(playerID == 1) {
                p1 = new Jogador(100, 400, 0, 50, Color.BLUE);
                p2 = new Jogador(600, 400, 3.1415, 50, Color.RED);
            } else {
                p1 = new Jogador(600, 400, 3.1415, 50, Color.RED);
                p2 = new Jogador(100, 400, 0, 50, Color.BLUE);
            }
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            p1.drawSprite(g2d);
            p2.drawSprite(g2d);
        }
    }

    private void atualizar(){
        if(frente){
            p1.posX += 0.25*Math.cos(p1.angulo);
            p1.posY += 0.25*Math.sin(p1.angulo);
        }
        if(anguloMais){
            p1.angulo += 0.005;
        }
        if(anguloMenos){
            p1.angulo -= 0.005;
        }
        dc.repaint();
    }

    private void interfaceGrafica() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        setSize(new Dimension(width, height));
        dc = new Desenho();
        add(dc);
        pack();
        setResizable(false);
        setVisible(true); 
    }

    public Cliente(){
        super("Combat");
                        
        this.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e){
                if(e.getExtendedKeyCode() == KeyEvent.VK_LEFT){
                    anguloMenos = false;
//                    System.out.println("L Released");
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_RIGHT){
                    anguloMais = false;
//                    System.out.println("R Released");
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_UP){
                    frente = false;
//                    System.out.println("UP Released");
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_SPACE){
                    tiro = false;
//                    System.out.println("TIRO Released " + tiro);
                }
            }

            public void keyPressed(KeyEvent e){
                if(e.getExtendedKeyCode() == KeyEvent.VK_LEFT){
                    anguloMenos = true;
//                    System.out.println("L");
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_RIGHT){
                    anguloMais = true;
//                    System.out.println("R");
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_UP){
                    frente = true;
//                    System.out.println("UP");
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_SPACE){
                    tiro = true;
//                    System.out.println("TIRO " + tiro);
                }
            }
        });
    }

    private void conectarAoServidor() {
        try {
            socket = new Socket("localhost", 5000);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            playerID = in.readInt();
            System.out.println("Voce eh o jogador " + playerID);
            if(playerID == 1) {
                System.out.println("Esperando o jogador 2 conectar...");
            }


            recebeInfo = new InformacoesDoServidor(in);
            mandaInfo = new InformacoesParaOServidor(out);
            recebeInfo.esperarOJogoComecar();
            
            this.interfaceGrafica();

            new Thread(recebeInfo).start();
            new Thread(mandaInfo).start();
        } catch(IOException e) {
            System.out.println("IOException ao se conectar ao servidor.");
            System.exit(1);
        }
    }

    private class InformacoesDoServidor implements Runnable {
        private DataInputStream dataIn;

        public InformacoesDoServidor(DataInputStream in) {
            dataIn = in;
        }
        
        public void run() {
            try {
                while(true) {
                    if(p2 != null){ 
                        p2.posX = dataIn.readDouble();
                        p2.posY = dataIn.readDouble();
                        p2.angulo = dataIn.readDouble();
                        //System.out.println("POSICAO DO OUTRO: " + p2.posX + " " + p2.posY);
                    }
                }
            } catch(IOException e) {
                System.out.println("IOException quando o cliente recebe as informacoes.");
            }
        }
        public void esperarOJogoComecar() {
            try {
                int comeco = dataIn.readInt();
            } catch(IOException e) {
                System.out.println("IOException na hora de esperar o jogo comecar");
            }
        }
    }

    private class InformacoesParaOServidor implements Runnable {
        private DataOutputStream dataOut;

        public InformacoesParaOServidor(DataOutputStream out) {
            dataOut = out;
        }
        
        public void run() {
            try {
                while(true) {
                    if(p1 != null) {
                        dataOut.writeDouble(p1.posX);
                        dataOut.writeDouble(p1.posY);
                        dataOut.writeDouble(p1.angulo);
                        dataOut.flush();
                //        System.out.println("MANDOU A POS: " + p1.posX + " " + p1.posY);
                    }
                    try {
                        Thread.sleep(25);
                    } catch(InterruptedException e) {
                        System.out.println("InterruptedException no momento de mandar informacoes para o servidor.");
                    }
                }
            } catch(IOException e) {
                System.out.println("IOException no momento de mandar informacoes para o servidor.");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Cliente cli = new Cliente();
        cli.conectarAoServidor();
        new Thread(){
            public void run(){
                while(true){
                    if(cli != null){
                        cli.atualizar();
                    }
                    try{
                        sleep(1);
                    }catch(Exception e){}
                }
            }
        }.start();
    }
}
