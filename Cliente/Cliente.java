import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.net.*;
import java.lang.Math;

class Pontuacao{   
    int[][][] nums= {
        {
            {0, 1, 1, 0, 0},
            {1, 0, 0, 1, 0},
            {1, 0, 0, 1, 0},
            {1, 0, 0, 1, 0},
            {0, 1, 1, 0, 0}
        },
        {
            {0, 1, 1, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 1, 1, 1, 0}
        },
        {
            {1, 1, 1, 0, 0},
            {0, 0, 0, 1, 0},
            {0, 1, 1, 0, 0},
            {1, 0, 0, 0, 0},
            {1, 1, 1, 1, 0}
        },
        {
            {1, 1, 1, 0, 0},
            {0, 0, 0, 1, 0},
            {1, 1, 1, 0, 0},
            {0, 0, 0, 1, 0},
            {1, 1, 1, 0, 0}
        },
        {
            {1, 0, 1, 0, 0},
            {1, 0, 1, 0, 0},
            {1, 0, 1, 0, 0},
            {1, 1, 1, 1, 0},
            {0, 0, 1, 0, 0}
        },
        {
            {1, 1, 1, 1, 0},
            {1, 0, 0, 0, 0},
            {1, 1, 1, 0, 0},
            {0, 0, 0, 1, 0},
            {1, 1, 1, 0, 0}
        },
        {
            {0, 1, 1, 0, 0},
            {1, 0, 0, 0, 0},
            {1, 1, 1, 0, 0},
            {1, 0, 0, 1, 0},
            {0, 1, 1, 0, 0}
        },
        {
            {1, 1, 1, 1, 0},
            {0, 0, 0, 1, 0},
            {0, 0, 1, 0, 0},
            {0, 1, 0, 0, 0},
            {0, 1, 0, 0, 0}
        },
        {
            {0, 1, 1, 0, 0},
            {1, 0, 0, 1, 0},
            {0, 1, 1, 0, 0},
            {1, 0, 0, 1, 0},
            {0, 1, 1, 0, 0}
        },
        {
            {0, 1, 1, 0, 0},
            {1, 0, 0, 1, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 1, 0},
            {0, 1, 1, 0, 0}
        }
    };
    public void drawScore(Graphics2D g2d, int p1, int p2){
        g2d.setColor(new Color(94, 144, 237));
        for(int i=0; i<5; ++i){
            for(int j=0; j<5; ++j){
                if(nums[p1][j][i] == 1){
                    Rectangle2D.Double square = new Rectangle2D.Double(10 + 10*i, 10 + 10*j, 10, 10);
                    g2d.fill(square);
                }
                
            }
        }
        g2d.setColor(new Color(245, 65, 65));
        for(int i=0; i<5; ++i){
            for(int j=0; j<5; ++j){
                if(nums[p2][j][i] == 1){
                    Rectangle2D.Double square = new Rectangle2D.Double(800 - 60 + 10*i, 10 + 10*j, 10, 10);
                    g2d.fill(square);
                }
                
            }
        }
    }
}

class Mapa{
    int width = 800, height = 600;
    int size = 50;
    int height_off_set = 200;
    private Color tileColor = new Color(255, 255, 0);

    // 16x12
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

    public void drawMapa(Graphics2D g2d){
        g2d.setColor(tileColor);
        for(int i=0; i<12; ++i){
            for(int j=0; j<16; ++j){
                if(mapa[i][j] == 1){
                    Rectangle2D.Double square = new Rectangle2D.Double(j*50, height_off_set + 50*i, 50, 50);
                    g2d.fill(square);
                }
            }
        }
       // System.out.println(mapa[0][1]);
    }
}

public class Cliente extends JFrame {
    private int width = 800, height = 800, playerID;
    private Jogador p1, p2;
    private Socket socket; 
    private Desenho dc;
    private InformacoesDoServidor recebeInfo;
    private InformacoesParaOServidor mandaInfo;

    private Color azul = new Color(94, 144, 237);
    private Color vermelho = new Color(245, 65, 65);

    int ganhador;
    boolean jogoFinalizado = false;

    int pontos1 = 0, pontos2 = 0;

    private boolean anguloMais = false,
            anguloMenos = false,
            frente = false,
            podeAtirar = true;

    private Mapa map = new Mapa();
    private Pontuacao score = new Pontuacao();


    private class Desenho extends JPanel{
        private Desenho() {
            this.setBackground(new Color(209, 207, 208));
            if(playerID == 1) {
                p1 = new Jogador(60, 450, 0, 35, azul);
                p2 = new Jogador(740, 450, 3.1415, 35, vermelho);
            } else {
                p1 = new Jogador(730, 450, 3.1415, 35, vermelho);
                p2 = new Jogador(60, 450, 0, 35, azul);
            }
        }
        public void paintComponent(Graphics g){
            
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            p1.drawSprite(g2d);
            p2.drawSprite(g2d);
            map.drawMapa(g2d);
            score.drawScore(g2d, pontos1, pontos2);
        }
    }

    private void atualizar(){
        if(jogoFinalizado){
            if(ganhador == 1){
                JOptionPane.showMessageDialog(null, "Jogo finalizado: O Azul ganhou");
            }else{
                JOptionPane.showMessageDialog(null, "Jogo finalizado: O Vermelho ganhou");
            }
            
            System.exit(0);
        }

        if(frente){
            p1.posX += 0.25*Math.cos(p1.angulo);
            p1.posY += 0.25*Math.sin(p1.angulo);
            if(podeAtirar) {
                p1.xTiro += 0.25*Math.cos(p1.angulo);
                p1.yTiro += 0.25*Math.sin(p1.angulo);
            } 
        }
        if(anguloMais){
            p1.angulo += 0.005;
            if(podeAtirar) {
                p1.anguloTiro += 0.005;
            }
        }
        if(anguloMenos){
            p1.angulo -= 0.005;
            if(podeAtirar) {
                p1.anguloTiro -= 0.005;
            }
        }

        //colisao parede

        for(int i=0; i<12; ++i){
            for(int j=0; j<16; ++j){
                if(map.mapa[i][j] == 1){
                    double rx = j*50;
                    double ry = map.height_off_set + 50*i;
                    double cp1x = p1.posX + p1.size/2;
                    double cp1y = p1.posY + p1.size/2;
                    
                    boolean b1 = false, b2 = false;
                    int rws = 0;
                    
                   
                    //verificar borda
                    double test1X = cp1x, test1Y = cp1y;
                    if(cp1x < rx){test1X = rx; rws = 1;}
                    else if(cp1x > rx+50){test1X = rx + 50; rws = -1;}
                    else b2 = true;

                    if(cp1y < ry) {test1Y = ry; rws = 2;}
                    else if(cp1y > ry + 50) {test1Y = ry + 50; rws = -2;}
                    else b1 = true;
                                
                    double dist1X = cp1x - test1X;
                    double dist1Y = cp1y - test1Y;
                    double distance1 = Math.sqrt((dist1X * dist1X) + (dist1Y * dist1Y));
                    
                    if(distance1 < 35/2){
                        if(b1){
                            if(rws == -1){
                                p1.posX = test1X;
                            }
                            if(rws ==  1){
                                p1.posX = rx - 35;  
                            }
                            if(podeAtirar == true){
                                p1.xTiro = p1.posX;
                            }
                            //System.out.println("colisao l;ados " + rws);
                        }
                        if(b2){
                            if(rws == -2) p1.posY = test1Y;
                            if(rws ==  2) p1.posY = ry - 35;
                            if(podeAtirar == true) p1.yTiro = p1.posY;
                        }
                        //System.out.println("colisao " + i + " " + j);
                    }
                }
            }
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
            }

            public void keyPressed(KeyEvent e){
                if(e.getExtendedKeyCode() == KeyEvent.VK_LEFT){
                    anguloMenos = true;
//                    System.out.println("L");
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_RIGHT){
                    anguloMais = true;
//                    System.out.println("R");
                } if(e.getExtendedKeyCode() == KeyEvent.VK_UP){
                    frente = true;
//                    System.out.println("UP");
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_SPACE){
                    if(podeAtirar) {
                        new ThreadDoTiro().start(); 
                        p1.atirou = true;
                    }
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
                        pontos1 = dataIn.readInt()/2;
                        pontos2 = dataIn.readInt()/2;
                        jogoFinalizado = dataIn.readBoolean();
                        p2.posX = dataIn.readDouble();
                        p2.posY = dataIn.readDouble();
                        p2.angulo = dataIn.readDouble();
                        p2.xTiro = dataIn.readDouble();
                        p2.yTiro = dataIn.readDouble();
                        p2.anguloTiro = dataIn.readDouble();
                        //System.out.println("POSICAO DO TIRO: " + p2.xTiro + " " + p2.yTiro);
                        if(jogoFinalizado){
                            ganhador = dataIn.readInt();
                        }
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
                        dataOut.writeDouble(p1.xTiro);
                        dataOut.writeDouble(p1.yTiro);
                        dataOut.writeDouble(p1.anguloTiro);
                        dataOut.flush();
                        //System.out.println("MANDOU A POS: " + p1.xTiro + " " + p1.yTiro);
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

    private class ThreadDoTiro extends Thread {
        private int velocidade = 7;
        public void run() {
            podeAtirar = false;
            while(true) {
                //POSSIVEL LUGAR DE COLISOES???????
                boolean flag = false;
                double pontoTiroX = p1.size*Math.cos(p1.anguloTiro) + p1.xTiro + p1.size/2;
                double pontoTiroY = p1.size*Math.sin(p1.anguloTiro) + p1.yTiro + p1.size/2;
                for(int i=0; i<12; ++i){
                    for(int j=0; j<16; ++j){
                        if(map.mapa[i][j] == 1){
                            double rx = j*50;
                            double ry = map.height_off_set + 50 * i;
                           
                            if(pontoTiroX >= rx && pontoTiroX <= rx+50 && pontoTiroY >= ry && pontoTiroY <= ry + 50){
                                flag = true;
                            }
                        }
                    }
                }

                if((p2.posX + p2.size/2 - pontoTiroX) * (p2.posX + p2.size/2 - pontoTiroX) + (p2.posY + p2.size/2 - pontoTiroY) * (p2.posY + p2.size/2 - pontoTiroY) <= 17*17){
                    flag = true;
                }

                if(flag){
                    p1.yTiro = p1.posY;
                    p1.xTiro = p1.posX;
                    p1.anguloTiro = p1.angulo;
                    podeAtirar = true;
                    break;
                }
                if(p1.yTiro > 0 && p1.xTiro > 0 && p1.xTiro < 800 && p1.yTiro < 800) {
                    p1.yTiro += velocidade*Math.sin(p1.anguloTiro);
                    p1.xTiro += velocidade*Math.cos(p1.anguloTiro);
                } else {
                    p1.yTiro = p1.posY;
                    p1.xTiro = p1.posX;
                    p1.anguloTiro = p1.angulo;
                    podeAtirar = true;
                    break;
                }
                try {
                    sleep(25);
                } catch(InterruptedException e) {
                    System.out.println("Excecao ocorreu na hora de disparar.");
                };
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
                        sleep(2);
                    }catch(InterruptedException e){
                        System.out.println("Excecao ocorreu na hora de atualizar o cliente");
                    }
                }
            }
        }.start();
    }
}
