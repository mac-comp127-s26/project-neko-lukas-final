package ParticleSim;


import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;

import java.awt.Color;



public class Ball {
//Private Variables
    //...ball quantittive variables
        private static double gravity;
        private double x;
        private double y;
        private double radius;
        private double direction; //unused currently. intended to be used in a more complicated movement system
        private Double[] v; //will contain ball x and y velocities. v[0] = x velocity and v[1] = y velocity
    //...

    //...ball other variables
        private Ellipse ball;
        private static Color ballColor;
        private Boolean inBounds;
        private Boolean moving = true; //Unused currently. Intended to be used to determine if a ball is not in motion so it can be removed at least temporarily from the balls list to reduce computational load when there are many balls
    //...

    //...sim canvas variables
        private static final int CANVAS_HEIGHT = ParticleSim.getCANVAS_HEIGHT();
        private static final int CANVAS_WIDTH = ParticleSim.getCANVAS_WIDTH();
        private static final double CANVAS_BOUND = ParticleSim.getBound();
    //...
   
    //Contact point variables
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

        inBounds = true;

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

    //...ball variable get methods
        public Boolean getInBounds(){return inBounds;}
        public Double getRadius(){return radius;}
        public Boolean isMoving(){return moving;}
    //...

    //...get Graphics methods
        public GraphicsGroup getGraphicsGroup(){return ballGroup; }
        public GraphicsGroup getContactsGroup(){return contacts.getContactsGraphics();}
        public Ellipse getGraphicsEllipse(){ return ball;}
        public double getDirection(){return direction;}
    //...

    //...Contact point get methods
        public Rectangle west(){return contacts.west();}
        public Point westCanvasPos(){return contacts.westCanvasPos();}
       
        public Rectangle east(){return contacts.east();}
        public Point eastCanvasPos(){return contacts.eastCanvasPos();}
        
        public Rectangle north(){return contacts.north();}
        public Point northCanvasPos(){ return contacts.northCanvasPos();}

        public Rectangle south(){return contacts.south();}
        public Point southCanvasPos(){return contacts.southCanvasPos();}
    //...

    //...Ball private variable set methods
        public void setxVelocity(double speed){v[0] = speed;}
        public void setyVelocity(double speed){v[1] = speed;}
        public static void setGravity(double igravity){ gravity = igravity;}
        public void setDirection(double direction){this.direction = direction;}
    //...

    //...Ball move methods
        public void setCenter(double x, double y){
            this.x = x;
            this.y = y;
        }
        
        public void move(){
            //moves ball according to its x and y velocities. Gravity
            x += v[0];
            y += v[1];
            v[1] = v[1] - gravity * 0.01;
            
            checkVelocity();
            checkOutOfBounds();

            contacts.moveContacts(x, y);
            ballGroup.setCenter(x, y);
        }

        public void deflection(String contact){
            //...bound bouncing
                if(contact == "west" || contact == "east"){
                    x -= v[0]; //prevents clipping after contact by moving the ball back to its last x position
                    v[0] = v[0] * -0.5; //actual bounce. reverts direction and loses energy through imperfect elasticity
                    v[1] = v[1] * 0.9;} //vertical energy lost through friction
                if(contact == "north" || contact == "south"){
                    y -= v[1]; //prevents clipping after contact by moving the ball back to its last y position
                    v[0] = v[0] * 0.9; //horizontal energy lost through friction
                    v[1] = v[1] * -0.5;} //actual bounce. reverts direction and loses energy through imperfect elasticity
            //...

            //...ball bouncing
                if(contact == "bwest"){ 
                    if(v[0]<0){ //if this ball initiates contact
                        x -= v[0]; //prevents clipping after contact
                        v[0] = v[0] * -0.5; //change direction and lose energy through friction & imperfect elasticity
                        v[1] = v[1] * 0.9;}} //vertical energy lost through friction
                if(contact == "beast"){ 
                    if(v[0]>0){ //if this ball initiates contact
                        x -= v[0]; //prevents clipping after contact
                        v[0] = v[0] * -0.5; //change direction and lose energy through friction & imperfect elasticity
                        v[1] = v[1] * 0.9;}} //vertical energy lost through friction
                if(contact == "bnorth"){
                    if(v[1]<0){//If this ball initiates contact
                        y -= v[1]; //prevents clipping after contact
                        v[0] = v[0] * 0.9; //horizontal energy lost through friction
                        v[1] = v[1] * -0.5;}} //change direction and lose energy through friction and imperfect elasticity
                if(contact == "bsouth"){
                    if(v[1]>0){//If this ball initiates contact
                        y -= v[1]; //prevents clipping after contact
                        v[0] = v[0] * 0.9; //horizontal energy lost through friction
                        v[1] = v[1] * -0.5;}} //change direction and lose energy through friction and imperfect elasticity
            //...
        }

        private void checkVelocity(){
            //...Stops movement if ball is going slow enough
            if(v[0]<0.03 && v[0]>-0.03){v[0] = 0.0;}
            if(v[1]<0.03 && v[1]>-0.03){v[1] = 0.0;}
        }
    //...

    //...Ball position checks
        private void checkOutOfBounds(){
            //...check if ball is out of bounds
                if(y > CANVAS_HEIGHT || y < 0 || x > CANVAS_WIDTH || x < 0)
                    {inBounds = false; System.out.println("OUT OF BOUNDS");}
            //...
        }
    //...
}
