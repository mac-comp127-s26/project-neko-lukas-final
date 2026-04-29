package ParticleSim;


import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;


import java.awt.Color;



public class Ball {
//Private Variables
    //...ball quantittive variables
        private static double gravity;
        private Ellipse ball;
        private double x;
        private double y;
        private double radius;
        private double direction;
        private Double[] v;
    //...

    //...sim canvas variables
        private static final int CANVAS_HEIGHT = ParticleSim.getCANVAS_HEIGHT();
        private static final int CANVAS_WIDTH = ParticleSim.getCANVAS_WIDTH();
        private static final double CANVAS_BOUND = ParticleSim.getBound();
    //...
   
    //Contact point variabels
        private Color ballContactColor = new Color(255,0,0);
        private double cRadius = 2;
        private ContactPoints contacts;
        private GraphicsGroup ballGroup = new GraphicsGroup();
    //...


//Functions_____________________________________________________________
    public Ball(double x, double y, double radius){
        //...Constructor. Sets private variables
        this.x = x;
        this.y = y;
        this.radius = radius;
        v = new Double[2];
        v[0] = 0.0;
        v[1] = 1.0;

        makeBall();
    }
   
    private void makeBall(){
        //...Consrtructs ball and contact points
        ball = new Ellipse(x, y, 2*radius, 2*radius);
        ball.setCenter(x, y);
        ballGroup.add(ball);
        ball.setFillColor(new Color(255,229,185));
        ball.setStroked(false);

        contacts = new ContactPoints(x, y, cRadius, radius, ballContactColor);

        //...Resets ball position
        ballGroup.setCenter(x, y);
    }


    //...Graphics get methods
        public GraphicsGroup getGraphicsGroup(){
            return ballGroup;
        }

        public GraphicsGroup getContactsGroup(){
            return contacts.getContactsGraphics();
        }

        public Ellipse getGraphicsEllipse(){
            return ball;
        }

        public double getDirection(){
            return direction;
        }
    //...

    //...Contact point get methods
        public Ellipse west(){
            return contacts.west();
        }
        public Point westCanvasPos(){
            return contacts.westCanvasPos();
        }

        public Ellipse east(){
            return contacts.east();
        }
        public Point eastCanvasPos(){
            return contacts.eastCanvasPos();
        }
        
        public Ellipse north(){
            return contacts.north();
        }
        public Point northCanvasPos(){
            return contacts.northCanvasPos();
        }

        public Ellipse south(){
            return contacts.south();
        }
        public Point southCanvasPos(){
            return contacts.southCanvasPos();
        }
    //...

    //...Ball private variable set methods
        public void setSpeed(double speed){
            //this.speed = speed;
            v[0] = 1.0;
            v[1] = 0.0;
        }

        public void setGravity(double gravity){
            this.gravity = gravity;
        }

        public void setDirection(double direction){
            this.direction = direction;
        }
    //...

    //...Ball move methods
        public void setCenter(double x, double y){
            this.x = x;
            this.y = y;
        }
        
        public void move(){
            x += v[0];
            y += v[1];
            v[1] = v[1] - gravity * 0.01;
            
            checkVelocity();
            contacts.moveContacts(x, y);
            ballGroup.setCenter(x, y);
        }

        public void deflection(int contact){
            if(contact == 1 || contact == 3){v[0] = v[0] * -0.8;}
            
            if(contact == 2 || contact == 4){
                if(contact == 2){y = CANVAS_HEIGHT - (CANVAS_BOUND + radius + 0.1);}
                v[0] = v[0] * 0.9;
                v[1] = v[1] * -0.5;}
        }

        private void checkVelocity(){
            if(v[0]<0.03){v[0] = 0.0;}
        }
    //...
}



