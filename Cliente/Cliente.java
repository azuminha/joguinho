import java.awt.*;
import javax.swing.*;

public class Cliente extends JFrame{
    private int width = 800, height = 800;
    Jogador p1 = new Jogador(100, 400, 0, 50, Color.BLUE);
    Jogador p2 = new Jogador(600, 400, 3.1415, 50, Color.RED);
    Desenho dc = new Desenho();

    class Desenho extends JPanel{
         public void paintComponent(Graphics g){
            Graphics2D g2d = (Graphics2D) g;
            p1.drawSprite(g2d);
            p2.drawSprite(g2d);
         }
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
    }

    public static void main(String[] args) {
        new Cliente();
    }
}