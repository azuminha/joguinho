import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.lang.Math;
public class Cliente extends JFrame{
    private int width = 800, height = 800;
    Jogador p1 = new Jogador(100, 400, 0, 50, Color.BLUE);
    Jogador p2 = new Jogador(600, 400, 3.1415, 50, Color.RED);
    Desenho dc = new Desenho();

    boolean tiro = false,
            anguloMais = false,
            anguloMenos = false,
            frente = false;

    class Desenho extends JPanel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            p1.drawSprite(g2d);
            p2.drawSprite(g2d);
        }
         
    }

    public void atualizar(){
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

    Cliente(){
        super("Combat");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        setSize(new Dimension(width, height));
        add(dc);
        pack();
        setResizable(false);
        setVisible(true);

        addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e){
                if(e.getExtendedKeyCode() == KeyEvent.VK_LEFT){
                    anguloMenos = false;
                    System.out.println("L Released");
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_RIGHT){
                    anguloMais = false;
                    System.out.println("R Released");
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_UP){
                    frente = false;
                    System.out.println("UP Released");
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_SPACE){
                    tiro = false;
                    System.out.println("TIRO Released " + tiro);
                }
            }

            public void keyPressed(KeyEvent e){
                if(e.getExtendedKeyCode() == KeyEvent.VK_LEFT){
                    anguloMenos = true;
                    System.out.println("L");
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_RIGHT){
                    anguloMais = true;
                    System.out.println("R");
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_UP){
                    frente = true;
                    System.out.println("UP");
                }
                if(e.getExtendedKeyCode() == KeyEvent.VK_SPACE){
                    tiro = true;
                    System.out.println("TIRO " + tiro);
                }
            }
        });
    }

    public static void main(String[] args) {
        Cliente cli = new Cliente();
        new Thread(){
            public void run(){
                while(true){
                    cli.atualizar();
                    try{
                        sleep(1);
                    }catch(Exception e){}
                }
            }
        }.start();
    }
}