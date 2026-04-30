package ParticleSim;

import java.awt.Color;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;

public class ContactPoints {
    private double x;
    private double y;
    private double radius;
    private double objectRadius;
    private Color ballContactColor = new Color(255,0,0,0);

    private Ellipse west;
    private Point wPOS;
    private Ellipse east;
    private Point ePOS;
    private Ellipse north;
    private Point nPOS;
    private Ellipse south;
    private Point sPOS;

    private GraphicsGroup contacts;

    public ContactPoints(double x, double y, double radius, double objectRadius, Color color){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.objectRadius = objectRadius;

        contacts = new GraphicsGroup();

        resetContactsPOS();
        makeContacts();
    }

    public ContactPoints(Point point, double radius, Color color){
        this.x = point.getX();
        this.y = point.getY();
        this.radius = radius;

        contacts = new GraphicsGroup();

    }

    public void resetContactsPOS(){
        wPOS = new Point(x - objectRadius, y);
        ePOS = new Point(x + objectRadius, y);
        nPOS = new Point(x, y + objectRadius);
        sPOS = new Point(x, y - objectRadius);
    }

    private void makeContacts(){
        //...Contact points construction:
            //...Compass points
            west = new Ellipse(x, y, radius * 2, radius * 2);
            west.setCenter(wPOS);
            west.setFillColor(ballContactColor);
            west.setStroked(false);
            
            east = new Ellipse(x, y, radius * 2, radius * 2);
            east.setCenter(ePOS);
            east.setFillColor(ballContactColor);
            east.setStroked(false);
            
            north = new Ellipse(x, y, radius * 2, radius * 2);
            north.setCenter(nPOS);
            north.setFillColor(ballContactColor);
            north.setStroked(false);
            
            south = new Ellipse(x, y, radius * 2, radius * 2);
            south.setCenter(sPOS);
            south.setFillColor(ballContactColor);
            south.setStroked(false);
            
            contacts.add(west);
            contacts.add(east);
            contacts.add(north);
            contacts.add(south);
    }

    //...Contact point get methods
        public Ellipse west(){
            return west;
        }

        public Point westCanvasPos(){
            return wPOS;
        }

        public Ellipse east(){
            return east;
        }

        public Point eastCanvasPos(){
            return ePOS;
        }
        
        public Ellipse north(){
            return north;
        }

        public Point northCanvasPos(){
            return nPOS;
        }

        public Ellipse south(){
            return south;
        }

        public Point southCanvasPos(){
            return sPOS;
        }

        public GraphicsGroup getContactsGraphics(){
            return contacts;
        }
    //...

    public void moveContacts(double x, double y){
        this.x = x;
        this.y = y;
        contacts.setCenter(x, y);
        resetContactsPOS();
    }

}
