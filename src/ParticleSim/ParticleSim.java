package ParticleSim;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

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

    //private static Ball ball;
    private static List<Ball> balls;
    private static Point bPOS;
    private static double speed;
    private static String moving;
    
    private static Rectangle westWall;
    private static Rectangle eastWall;
    private static Rectangle northWall;
    private static Rectangle southWall;



    public ParticleSim() {
        canvas = new CanvasWindow("particle sim", CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setBackground(new Color(0,0,0));

        //...Set object variables Variables
            physicsLayer = new GraphicsGroup();
            ballLayer = new GraphicsGroup();
            balls = new ArrayList<Ball>();
            speed = 5;
            gravity = -9.81;
            moving = "true";
        //...

        //...Construct game objects
            constructBounds();
            bPOS = new Point(150,100);
            //constructBall();
        //...

        //...Reset ball on click
            canvas.onMouseDown(event -> {spawnBall();});
        //...
    }

    public static void main(String[] args){
        new ParticleSim();
        
        //...Animation
            canvas.animate(() -> {
                //moving variable determines gamestate

                //...Ball is in motion
                    if(moving == "true"){
                        for(Ball ball : balls){
                            ball.move();
                            checkCollision(ball);
                            if(ball.getInBounds() == false){
                                ball.getContactsGroup().removeAll();
                                ball.getGraphicsGroup().removeAll();
                                balls.remove(ball);
                            }
                        }
                    }
                //...
                
                //...ball has gone out of bounds
                    else if(moving == "ball OOB"){
                        spawnBall();
                        moving = "true";
                    }
                //...
            });
        //...
    }

    private static void constructBounds(){
        //...places edge barriers and movable platform (southWall is the platform)
            westWall = new Rectangle(0, 0, bound, CANVAS_HEIGHT);
            eastWall = new Rectangle(CANVAS_WIDTH - bound, 0, bound, CANVAS_HEIGHT);
            northWall = new Rectangle(0, 0, CANVAS_WIDTH, bound);
            //southWall = new Rectangle(0, CANVAS_HEIGHT - bound, CANVAS_WIDTH, bound);
            southWall = new Rectangle(0, 200, CANVAS_WIDTH, bound);

            westWall.setFillColor(new Color(223,144,66));
            westWall.setStroked(false);
            eastWall.setFillColor(new Color(223,144,66));
            eastWall.setStroked(false);
            northWall.setFillColor(new Color(223,144,66));
            northWall.setStroked(false);
            southWall.setFillColor(new Color(223,144,66));
            southWall.setStroked(false);
            
            physicsLayer.add(westWall);
            physicsLayer.add(eastWall);
            physicsLayer.add(northWall);
            physicsLayer.add(southWall);
            canvas.add(physicsLayer);
        //...
    }

    private static void constructBall(){
        Ball ball = new Ball(bPOS.getX(), bPOS.getY(), 7.5);
        balls.add(ball);
        ballLayer.add(ball.getGraphicsGroup());
        ballLayer.add(ball.getContactsGroup());
        canvas.add(ballLayer);
        ball.setSpeed(speed);
        ball.setGravity(gravity);
    }

    private static void spawnBall() {
        //...Places the ball's position above platform
            bPOS = new Point(150,150);
            constructBall();
        //...
    }

    private static void checkCollision(Ball ball){
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

        //...Ball collision check & deflection | does not work properly
            boolean ballCountactWest = ballLayer.getElementAt(ball.westCanvasPos()) instanceof Ellipse;
            boolean ballCountactNorth = ballLayer.getElementAt(ball.northCanvasPos()) instanceof Ellipse;
            boolean ballCountactEast = ballLayer.getElementAt(ball.eastCanvasPos()) instanceof Ellipse;
            boolean ballCountactSouth = ballLayer.getElementAt(ball.southCanvasPos()) instanceof Ellipse;
                
            if(ballCountactWest){ball.deflection(1); System.out.println("CONTACT 1");} 
            if(ballCountactNorth){ball.deflection(2); System.out.println("CONTACT 2");}
            if(ballCountactEast){ball.deflection(3); System.out.println("CONTACT 3");}
            if(ballCountactSouth){ball.deflection(4); System.out.println("CONTACT 4");}
        //...
    }

    //...Canvas variable get methods
        public static int getCANVAS_HEIGHT(){return CANVAS_HEIGHT;}
        public static int getCANVAS_WIDTH(){return CANVAS_WIDTH;}
        public static double getBound(){return bound;}
    //...
}
