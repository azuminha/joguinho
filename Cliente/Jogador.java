import java.awt.*;
import java.awt.geom.*;
import java.lang.Math;

import org.w3c.dom.css.Rect;

public class Jogador{
    double posX, posY, angulo, size;
    double yTiro, xTiro, anguloTiro;
    private Color color;
    boolean atirou = false;

    public Jogador(double x, double y, double a, double s, Color c){
        posX = x;
        posY = y;
        angulo = a;
        size = s;
        color = c;

        xTiro = x;
        yTiro = y;
        anguloTiro = a;
    }

    public void drawSprite(Graphics2D g2d){
        //Rectangle2D.Double square = new Rectangle2D.Double(posX, posY, size, size);
        double meio = size/2;
        Line2D.Double bala = new Line2D.Double(xTiro+meio, yTiro+meio, size*Math.cos(anguloTiro) + xTiro + meio, size*Math.sin(anguloTiro) + yTiro + meio);
        Ellipse2D.Double circle = new Ellipse2D.Double(posX, posY, size, size);
        Line2D.Double direcao = new Line2D.Double(posX+meio, posY+meio, size*Math.cos(angulo) + posX + meio, size*Math.sin(angulo) + posY + meio);
        g2d.setColor(color);
        g2d.fill(circle);
        g2d.setColor(Color.BLACK);
        g2d.draw(bala);
        g2d.setColor(new Color(62, 147, 24));
        g2d.draw(direcao);
    }

    public void setX(double x) {
        posX = x;
    }
    public void setY(double y) {
        posY = y;
    }
    public void setAngulo(double a) {
        angulo = a;
    }
}
