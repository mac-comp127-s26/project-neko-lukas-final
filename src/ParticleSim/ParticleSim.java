package ParticleSim;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
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

    //...canvas variables
        private static final int CANVAS_WIDTH = 800;
        private static final int CANVAS_HEIGHT = 600;
        private static double bound = 10;
        private static CanvasWindow canvas;
        private static GraphicsGroup physicsLayer;
        private static GraphicsGroup ballLayer;
    //...
        
    //...canvas components
        private static Rectangle westWall;
        private static Rectangle eastWall;
        private static Rectangle northWall;
        private static Rectangle southWall;
    //...

    //...ball variables
        private static List<Ball> balls;
        private static Point bPOS;
        private static double speed;
        private static Boolean moving;
    //...
    

    



    public ParticleSim() {
        canvas = new CanvasWindow("particle sim", CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setBackground(new Color(0,0,0));

        //...Set object variables Variables
            physicsLayer = new GraphicsGroup();
            ballLayer = new GraphicsGroup();
            balls = new ArrayList<Ball>();
            speed = 5;
            gravity = -9.81;
            moving = true;
            bPOS = new Point(150,100);
        //...

        //...Construct game objects
            constructBounds();
        //...

        //...Function defintion: spawn ball on click during animation
            canvas.onMouseDown(event -> {spawnBall(event.getPosition());});
        //...
    }

    public static void main(String[] args){
        new ParticleSim();
        
        //...Animation
            canvas.animate(() -> {
                //moving variable determines gamestate

                //...Ball is in motion
                    if(moving == true){
                        for(Ball ball : balls){
                            ball.move();
                            checkCollision(ball);
                            if(ball.getInBounds() == false){
                                canvas.remove(ball.getContactsGroup());
                                canvas.remove(ball.getGraphicsGroup());
                                balls.remove(ball);
                            }
                        }
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
            southWall = new Rectangle(0, CANVAS_HEIGHT - bound, CANVAS_WIDTH, bound);

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
        ball.setxVelocity(speed);
        ball.setyVelocity(0);
        ball.setGravity(gravity);
        ball.setDirection(315);
    }

    private static void spawnBall(Point location) { // NEKO
        //...Places the ball's position above platform
            bPOS = location;
            constructBall();
        //...
    }

    private static void checkCollision(Ball ball){
        //...Barrier collision check & deflection
            boolean countactWest = physicsLayer.getElementAt(ball.westCanvasPos()) instanceof Rectangle;
            boolean countactNorth = physicsLayer.getElementAt(ball.northCanvasPos()) instanceof Rectangle;
            boolean countactEast = physicsLayer.getElementAt(ball.eastCanvasPos()) instanceof Rectangle;
            boolean countactSouth = physicsLayer.getElementAt(ball.southCanvasPos()) instanceof Rectangle;
                
            if(countactWest){ball.deflection("west");} 
            if(countactNorth){ball.deflection("north");}
            if(countactEast){ball.deflection("east");}
            if(countactSouth){ball.deflection("south");}
        //...

        //...simple Ball collision check & deflection | does not work properly
            boolean ballCountactWest = ballLayer.getElementAt(ball.westCanvasPos()) instanceof Ellipse;
            boolean ballCountactNorth = ballLayer.getElementAt(ball.northCanvasPos()) instanceof Ellipse;
            boolean ballCountactEast = ballLayer.getElementAt(ball.eastCanvasPos()) instanceof Ellipse;
            boolean ballCountactSouth = ballLayer.getElementAt(ball.southCanvasPos()) instanceof Ellipse;
                
            if(ballCountactWest){ball.deflection("bwest");} 
            if(ballCountactNorth){ball.deflection("bnorth");}
            if(ballCountactEast){ball.deflection("beast");}
            if(ballCountactSouth){ball.deflection("bsouth");}
        //...

        //...complicated Ball collision check & deflection | does not work properly
            // for(Ball otherBall : balls){
            //     if(otherBall == ball) continue;
                
            //     double dx = ball.getContactsGroup().getX() - otherBall.getContactsGroup().getX();
            //     double dy = ball.getContactsGroup().getY() - otherBall.getContactsGroup().getY();
            //     double distance = Math.sqrt(dx * dx + dy * dy);

            //     if (distance < ball.getRadius()){}
            // }
        //...
    }

    //...Canvas variable get methods
        public static int getCANVAS_HEIGHT(){return CANVAS_HEIGHT;}
        public static int getCANVAS_WIDTH(){return CANVAS_WIDTH;}
        public static double getBound(){return bound;}
    //...
}
