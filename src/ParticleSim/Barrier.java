package ParticleSim;

import edu.macalester.graphics.Rectangle;
import java.awt.Color;

public class Barrier {
    private Rectangle barrier;
    
    public Barrier(double x, double y, double width, double height) {
        //...Constructor
        //...Makes graphic visualization of barrier
        barrier = new Rectangle(x, y, width, height);
        barrier.setFillColor(new Color(223,144,66));
        barrier.setStroked(false);
    }

    public Rectangle getGraphics(){
        return barrier;
    }

    public double deflection(double direction, int contact){
        //...Returns a new direction for ball when ball impacts barrier
        if(contact == 1){
            double newDirection = 180 - direction;
            if(newDirection < 0){
                newDirection = 360 - direction;
            }
            return newDirection;
        } else if(contact == 2){
            double newDirection = 360 - direction;
            if(newDirection < 0){
                newDirection = 180 - direction;
            }
            return newDirection;
        } else{
            return 90;
        }
    }
}

