package ParticleSim;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;
import java.awt.Color;

/**
 * The game of Breakout.
 */
public class ParticleSim {
    private static double gravity;

    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 600;
    private static double bound = 10;
    private static CanvasWindow canvas;
    private static GraphicsGroup physicsLayer;
    private static GraphicsGroup ballLayer;

    private static Ball ball;
    private static Point bPOS;
    private static double speed;
    private static String moving;
    
    private static Barrier westWall;
    private static Barrier eastWall;
    private static Barrier northWall;
    private static Barrier southWall;



    public ParticleSim() {
        canvas = new CanvasWindow("Breakout!", CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setBackground(new Color(0,0,0));

        //...Set object variables Variables
            physicsLayer = new GraphicsGroup();
            ballLayer = new GraphicsGroup();
            speed = 5;
            gravity = -9.81;
            moving = "true";
        //...

        //...Construct game objects
            constructBounds();
            bPOS = new Point(southWall.getGraphics().getCenter().getX() - 50, 
                             southWall.getGraphics().getCenter().getY() - 50);
            constructBall();
        //...

        //...Reset ball on click
            canvas.onMouseDown(event -> {resetBall();});
        //...
    }

    public static void main(String[] args){
        new ParticleSim();
        
        //...Animation
            canvas.animate(() -> {
                //moving variable determines gamestate
                System.out.println("animate");

                //...Ball is in motion
                    if(moving == "true"){
                        ball.move();
                        barrierCollisionCheck();
                        outOfBoundsCheck();
                    }
                //...
                
                //...ball has gone out of bounds
                    else if(moving == "ball OOB"){
                        resetBall();
                        moving = "true";
                    }
                //...
            });
        //...
    }

    private static void constructBounds(){
        //...places edge barriers and movable platform (southWall is the platform)
            westWall = new Barrier(0, 0, bound, CANVAS_HEIGHT);
            eastWall = new Barrier(CANVAS_WIDTH - bound, 0, bound, CANVAS_HEIGHT);
            northWall = new Barrier(0, 0, CANVAS_WIDTH, bound);
            southWall = new Barrier(0, CANVAS_HEIGHT - bound, CANVAS_WIDTH, bound);
            
            physicsLayer.add(westWall.getGraphics());
            physicsLayer.add(eastWall.getGraphics());
            physicsLayer.add(northWall.getGraphics());
            physicsLayer.add(southWall.getGraphics());
            canvas.add(physicsLayer);
        //...
    }

    private static void constructBall(){
        ball = new Ball(bPOS.getX(), bPOS.getY(), 7.5);
        ballLayer.add(ball.getGraphicsGroup());
        ballLayer.add(ball.getContactsGroup());
        canvas.add(ballLayer);
        ball.setSpeed(speed);
        ball.setGravity(gravity);
    }

    private static void resetBall() {
        //...Places the ball's position above platform
            ballLayer.removeAll();
            moving = "ball OOB";
            bPOS = new Point(150,150);
            constructBall();
        //...
    }

    private static void barrierCollisionCheck(){
        //...Barrier collision check & deflection
            boolean countactWest = physicsLayer.getElementAt(ball.westCanvasPos()) instanceof Rectangle;
            boolean countactNorth = physicsLayer.getElementAt(ball.northCanvasPos()) instanceof Rectangle;
            boolean countactEast = physicsLayer.getElementAt(ball.eastCanvasPos()) instanceof Rectangle;
            boolean countactSouth = physicsLayer.getElementAt(ball.southCanvasPos()) instanceof Rectangle;
                
            if(countactWest){ball.deflection(1); System.out.println("CONTACT 1");} 
            if(countactNorth){ball.deflection(2); System.out.println("CONTACT 2");}
            if(countactEast){ball.deflection(3); System.out.println("CONTACT 3");}
            if(countactSouth){ball.deflection(4); System.out.println("CONTACT 4");}
        //...
    }

    private static void outOfBoundsCheck(){
        //...check if ball is out of bounds
            if(ball.getGraphicsGroup().getCenter().getY() > CANVAS_HEIGHT
            || ball.getGraphicsGroup().getCenter().getY() < 0
            || ball.getGraphicsGroup().getCenter().getX() > CANVAS_WIDTH
            || ball.getGraphicsGroup().getCenter().getX() < 0
                ){moving = "ball OOB"; System.out.println("OUT OF BOUNDS"); resetBall();}
        //...
    }

    //...Canvas variable get methods
        public static int getCANVAS_HEIGHT(){return CANVAS_HEIGHT;}
        public static int getCANVAS_WIDTH(){return CANVAS_WIDTH;}
        public static double getBound(){return bound;}
    //...
}
