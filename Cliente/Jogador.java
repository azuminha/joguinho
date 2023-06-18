import java.awt.*;
import java.awt.geom.*;
import java.lang.Math;

import org.w3c.dom.css.Rect;

public class Jogador{
    double posX, posY, angulo, size;
    Color color;

    public Jogador(double x, double y, double a, double s, Color c){
        posX = x;
        posY = y;
        angulo = a;
        size = s;
        color = c;
    }

    public void drawSprite(Graphics2D g2d){
        //Rectangle2D.Double square = new Rectangle2D.Double(posX, posY, size, size);
        Ellipse2D.Double circle = new Ellipse2D.Double(posX, posY, size, size);
        Line2D.Double direcao = new Line2D.Double(posX+25, posY+25, size*Math.cos(angulo) + posX + 25, size*Math.sin(angulo) + posY + 25);
        g2d.setColor(color);
        g2d.fill(circle);
        g2d.setColor(Color.BLACK);
        g2d.draw(direcao);
    }
}