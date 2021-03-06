package pk.Tools;

import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.awt.geom.*;
import java.util.*;
/**
 * Easel class contains the BuffereImage which is used as the drawing area and all the drawing methods
 * like line drawing rectangle drawing ,etc....
 * 
 * @author Rahul B.
 * @version 0.1.
 */
public class Easel 
{
    public BufferedImage image;
    public Graphics2D g;
    public float pix =0;

    public float rWidth = 100.0f;
    public float rHeight= 100.0f;

    public int curX,curY,cX=0,cY=0;
    float pX,pY;

    int pp1x ,pp2x , pp3x ,pp4x,
    pp1y ,pp2y , pp3y ,pp4y;

    int inc =1;
    int r =1;

    int sx[] = new int[4];
    int sy[] = new int[4];

    int angle = 0;

    public String axiom,strF,strf,strX,strY;
    int level =1;
    public float xLast,yLast,dir,rotation,dirStart,fxStart,fyStart,lengthFract,reductFact,dirLast;
    public Fractal frc = new Fractal();

    double sinT = 1.0;
    
    boolean cyclic = false;

    Vector<Vector> undoLayers = new Vector<Vector>();
    
    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;
    public static final int L2R_DIAGONAL = 2;
    public static final int R2L_DIAGONAL = 3;
    
    int gradDir = 1;
    //***************************BASIC*********************************************
    //***************************BASIC*********************************************
    //***************************BASIC*********************************************
    //***************************BASIC*********************************************

    public final SizedStack<Image> undoStack = new SizedStack<>(20);
    public final SizedStack<Image> redoStack = new SizedStack<>(20);
    
    /**Returns a copy of BufferedImage created from an image object
    */
    private BufferedImage copyImage(Image img) {
        BufferedImage copyOfImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(img, 0, 0, image.getWidth(), image.getHeight(), null);
        return copyOfImage;
    }
    
    /**
     * Sets the image for the easel
     */
    public void setImage(BufferedImage im)
    {
        image = im;
        g =(Graphics2D) im.getGraphics();
    }

    /**
     * Saves the  img to the undo stack.
     */
    public void saveToUndoStack(Image img) {
        undoStack.push(copyImage(img));
    }

    /**
     * Saves the img to the redo stack.
     */
    public void saveToRedoStack(Image img) {
        redoStack.push(copyImage(img));
    }
    
    private AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return(AlphaComposite.getInstance(type, alpha));
    }

    private void drawSquares( float alpha) {
        g.setComposite(makeComposite(alpha));
    }

    /**
     * Initializes variables for the continuos co-ordinate system.
     * Normally the discrete cordinate system is used for the drawing methods
     * except for methods involving rotation etc..
     */
    void init()
    {
        Dimension d = new Dimension(image.getWidth(),image.getHeight());
        int mX = d.width-1,
        mY = d.height-1;

        pix =Math.max(rWidth/mX,rHeight/mY);
        cX = mX/2;
        cY = mY/2;
    }

    /**
     * Again methods used for only certain methods for the TurtleTool
     * It moves from current pos to new pos without drawing.
     */
    void moveTo(float x,float y)
    {
        curX = iX(x);
        curY = iY(y);
    }

    /**
     * Again methods used for only certain methods for the TurtleTool
     * It moves from current pos to new pos with drawing.
     */
    void lineTo(Graphics g,float x,float y)
    {
        int x1 = iX(x),
        y1 = iY(y);

        g.drawLine(curX,curY,x1,y1);
        curX =x1;
        curY =y1;
    }

    void drawArrow(Graphics g,float[] x,float[] y)
    {
        moveTo(x[0],y[0]);
        lineTo(g,x[1],y[1]);
        lineTo(g,x[2],y[2]);
        lineTo(g,x[3],y[3]);
        lineTo(g,x[1],y[1]);
    }

    /**
     * Converts the continuous X cordinate to discrete X Cordinate 
     */
    public int iX(float x)
    {
        return Math.round(cX+x/pix);
    }

    /**
     * Converts the continuous Y cordinate to discrete Y Cordinate 
     */
    public int iY(float y)
    {
        return Math.round(cY-y/pix);
    }

    public int cx(float x)
    {
        return (int)(Math.round(x));
    }

    public int cy(float y)
    {
        int maxY = 768;
        return (int)(Math.round(maxY -y));
    }

    /**
     * Rotates the given point about the origin
     */
    public float[] rotate(float x3,float y3,double phi)
    {
        float cos_phi =(float)Math.cos(phi),
        sin_phi =(float)Math.sin(phi);
        float xn =x3*cos_phi + y3* -sin_phi,
        yn =x3*sin_phi + y3*  cos_phi;
        x3 = xn ;y3 = yn;
        float[] array ={x3,y3};
        return array;
    }

    /**
     * Converts discrete X cordinates to continuous X cordinates
     */
    public float fx(int x)
    {
        return (x-cX)*pix;
    }

    /**
     * Converts discrete Y cordinates to continuous Y cordinates
     */
    public float fy(int y)
    {
        return (cY -y)*pix;
    }

    /**
     *  Draws the turtle for TurtleTool
     */
    public void drawTriangle(Graphics g,float[] x,float y[])
    {
        Color t = g.getColor();
        try{
            g.setXORMode(Color.black);
        }catch(Exception ex){}
        moveTo(x[0],y[0]);
        lineTo(g,x[1],y[1]);
        lineTo(g,x[2],y[2]);
        lineTo(g,x[0],y[0]);
        g.setPaintMode();
    }

    /**
     *  Rotates the given point  x3 ,  y3 about  oX ,  oY
     */
    public float[] rotate(float x3,float y3,float oX,float oY,double phi)
    {
        float cos_phi =(float)Math.cos(phi),
        sin_phi =(float)Math.sin(phi),
        matrix_31 = -oX*cos_phi + oY*sin_phi +oX,
        matrix_32 = -oX*sin_phi - oY*cos_phi+oY;

        float xn = x3*cos_phi + y3*-sin_phi + matrix_31,
        yn = x3*sin_phi + y3*cos_phi + matrix_32;
        x3 =xn;
        y3 =yn;

        float array[] = {x3,y3};

        return array;
    }
    //***************************BASIC*********************************************
    //***************************BASIC*********************************************
    //***************************BASIC*********************************************
    //***************************BASIC*********************************************
    //***************************BASIC*********************************************
    
    /**
     * Constructor 
     * width  -> Width  of the bufferedimage(Drawing Area)
     * height -> Height of the bufferedimage(Drawing Area)
     */
    public Easel(int width,int height)
    {
        image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D)image.getGraphics();
        init();
        drawSquares(1f);
    }

    /**
     *  Undo the last painting by poping the undoStack,then saves it to the redo stack. 
     */
    public void undo() {
        if (undoStack.size() > 0) {
            
            Image im = undoStack.pop();
            image.getGraphics().drawImage(im,0,0,null);
            saveToRedoStack(AbstractHandler.easel.image);
        }
    }
    
    /**
     *  Redo the last painting by poping the redoStack,then saves it to the undo stack. 
     */
    public void redo() {
        if (redoStack.size() > 0) {
            Image im = redoStack.pop();
            image.getGraphics().drawImage(im,0,0,null);
            undoStack.push(image);
        }
    }
    
    //****************************IMAGE-DRAWING METHODS********************************
    
    
    /**
     *  Draws the given bufferedimage and on the drawing area 
     */
    public void drawImage(BufferedImage im)
    {
        g.drawImage(im,0,0,null);
    }

    @Deprecated
    public void drawImage(int x,int y,int h,int w,int nx,int ny)
    {
        g.drawImage(image,nx,ny,w,h,nx,ny,w,h,null);
    }

    @Deprecated
    public void drawImage(int h,int w)
    {
        BufferedImage temp = new BufferedImage(1500,1500,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) temp.getGraphics();
        g2.drawImage(image,0,0,w,h,0,0,image.getWidth(),image.getHeight(),null);
        g.setColor(Color.white);
        g.fillRect(0,0,getImage().getWidth(),getImage().getHeight());
        g.drawImage(temp,0,0,w,h,0,0,temp.getWidth(),temp.getHeight(),null);
    }

    /**
     * Returns the drawing area ie, BufferedImage instance image
     */
    public BufferedImage getImage()
    {
        return image;
    }

    //****************************SIMPLE LINE DRAWING METHODS********************************
    
    /**
     *  Sets the color of the graphics.
     *  Sets the color variable of ColorModel to parameter c
     */
    public void setColor(Color c)
    {
        ColorModel.color = c;
        g.setColor(c);
    }

    /**
     *  Draws a line from x1,y1 to x2,y2
     */
    public void drawLine(int x1,int y1  ,  int x2,int y2)//Draws a line
    {
        g.setColor(ColorModel.color);
        g.drawLine(x1,y1,x2,y2);
    }

    /**
     *  Draws a fractal using frc.fractals() method
     */
    public   void drawFractal()
    {

        frc.xLast=frc.fxStart;
        frc.yLast=frc.fyStart ;

        g.setColor(ColorModel.color);
        frc.fractals(this,frc.axiom,frc.level,frc.lengthFract *800);
    }

    /**
     *  Draws a caligraphic line
     */
    public void drawCaligLine(int x1,int y1  ,  int x2,int y2 )//Draws a line
    {
        g.setColor(ColorModel.color);
        int x = x1,
        y = y1,
        d=0,
        dx = x2 - x1,
        dy = y2 - y1,
        c,m,
        xInc = 1,
        yInc = 1;

        if(dx <0){xInc = -1;dx =-dx;}
        if(dy <0){yInc = -1;dy =-dy;}

        if(dy<=dx)
        {
            c = 2*dx;
            m = 2*dy;

            if(xInc <0) dx++;

            for(;;)
            {
                g.drawLine(x,y,x+7,y);
                if(x == x2)break;
                x+=xInc;
                d+=m;
                if(d>=dx)
                {
                    y+=yInc;
                    d-=c;
                }

            }
        }
        else
        {
            c = 2 * dy;
            m = 2 * dx;
            if(yInc <0)dy++;

            for(;;)
            {
                g.drawLine(x,y,x+7,y);
                if( y == y2)break;
                y+=yInc;
                d+=m;

                if(d>dy)
                {
                    x+=xInc;
                    d-=c;

                }

            }
        }
    }

    /**
     *  Draws a Spike line
     */
    public void drawSpikeLine(int x1,int y1  ,  int x2,int y2)
    {
        
        g.setColor(ColorModel.color);
        int d1 = 2*(x1-x2),
        d2 = 2*(y1-y2);

        g.drawLine(x1,y1,x2,y2);
        g.drawLine(x1+d1,y1+d2,x2,y2);
        g.drawLine(x1-d1,y1-d2,x2,y2);
        g.drawLine(x2+d1,y2+d2,x1,y1);
    }

    
    public void drawALine3(int x1,int y1  ,  int x2,int y2)
    {
        System.out.println("3");
        
        g.setColor(ColorModel.color);
        float x = fx(x1);
        float y = fy(y1);

        float xx = fx(x2);
        float yy = fx(y2);

        float dx = xx-x;
        float dy = yy-y;

        float[] rx = rotate(x+dx,y,x,y,Math.PI*90/180);

        g.drawLine(iX(x),iY(y),iX(rx[0]),iY(rx[1]));
    }

    /**
     *  Draws a Tangled Line
     */
    public void drawTangledLine(int x1,int y1  ,  int x2,int y2 )
    {
        g.setColor(ColorModel.color);
        double fX = x1-x2;
        double fY = y1-y2;

        float x11 = fx(x1);
        float y11 = fy(y1);

        float x12 = fx(x2);
        float y12 = fy(y2);

        float x21 = fx(x1-10);
        float y21 = fy(y1+5);

        float phi =(float)(Math.PI * 90/180);
        float[] r1  = rotate(x12,y12,x11,y11,phi);
        float[] r2  = rotate(x21,y21,x11,y11,phi);

        int dx = (x2-x1)/2;
        int dy = (y2-y1)/2;

        ((Graphics2D)g).drawLine(x1-dx,y1-dy,iX(r1[0]+dx),iY(r1[1]+dy));
        ((Graphics2D)g).drawLine(x1-dx,y1-dy,x2-dx,y2-dy);

        if(pX !=0)
        {
            g.drawLine(iX(r1[0]+dx),iY(r1[1]+dy),iX(pX+dx),iY(pY+dy));

        }

        pX  = r1[0];
        pY  = r1[1];

        //((Graphics2D)g).drawLine(iX(r1[0]),iY(r1[1]),);
    }

    /**
     *  Draws a Wavy Line
     */
    public void drawWavyLine(int x1,int y1  ,  int x2,int y2 )
    {
        g.setColor(ColorModel.color);
        int mx = x2 - x1;
        int my = y2 - y1;

        int p2x = x2 - 2*mx;
        int p2y = y2 - 2*my;

        int p3x = x2 + 2*mx;
        int p3y = y2 + 2*my;

        int p1x = x2 -4*mx;
        int p1y = y2 -4*my;

        int p4x = x2 +4*mx;
        int p4y = y2 +4*my;

        g.drawLine(x1,y1,x2,y2);
        if(pp1x !=0)
        {
            g.drawLine(p1x,p1y,pp1x,pp1y);
            g.drawLine(p2x,p2y,pp2x,pp2y);
            g.drawLine(p3x,p3y,pp3x,pp3y);
            g.drawLine(p4x,p4y,pp4x,pp4y);
        }

        pp1x = p1x;
        pp2x = p2x;
        pp3x = p3x;
        pp4x = p4x;

        pp1y = p1y;
        pp2y = p2y;
        pp3y = p3y;
        pp4y = p4y;
    }
    
    
    public void drawALine6(int x1,int y1  ,  int x2,int y2 )
    {
        g.setColor(ColorModel.color);
        int h = 3;
        int h2 = 2*h;

        int xn = x1+2*h;
        int yn = y1+h;

        g.drawLine(x1,y1,xn,yn);
        g.drawLine(x2,y2,xn,yn);
    }

    /**
     *  Draws a Ribboned Line
     */
    public void drawRibbonLine(int x1,int y1  ,  int x2,int y2 )//Draws a caligraphic line
    {
        Random rand = new Random();
        //final float hue = random.nextFloat();
        // Saturation between 0.1 and 0.3
        //final float saturation = (random.nextInt(2000) + 1000) / 10000f;
        //final float luminance = 0.9f;
        //final Color color = Color.getHSBColor(hue, saturation, luminance);
        float rr = rand.nextFloat();
        float gg = rand.nextFloat();
        float b = rand.nextFloat();

        Color color = new Color(rr, gg, b);
        g.setColor(color);
        int k=0;
        for(int i=inc;i<=(inc*4);i+=inc)
        {
            g.drawLine(x1+i,y1+i,x2+i,y2+i);
        }

        if(inc == 5)
        {
            r = -1;
        }
        else if(inc == 1)
        {
            r= 1;
        }
        inc += r;
        g.drawLine(x1,y1,x2,y2);
    }

    
    /**
     *  Draws a Grass Line
     */
    public void drawGrassLine(int x1,int y1  ,  int x2,int y2 )
    {
        g.setColor(ColorModel.color);

        int ranx = (int)(Math.random()*20)+x2;
        int rany = y2-(int)(Math.random()*20);

        g.drawLine(x1,y1,ranx,rany);
        g.drawLine(ranx,rany,x2,y2);
    }

    /**
     *  Draws a Sploshed Line
     */
    public void drawSploshedLine(int x1,int y1  ,  int x2,int y2 )
    {
        g.setColor(ColorModel.color);

        //g.drawLine(x1,y1,x2,y2);
        for(int i=0;i<=10;i++)
        {
            boolean b = (Math.random()>=0.5?true:false);
            double cirX = Math.random()*(10*Math.abs(x1-x2));
            double cirY = Math.random()*(10*Math.abs(y1-y2));

            double rrX  = b?x1+cirX:x1-cirX;  
            double rrY  = b?y1+cirY:y1-cirY; 

            drawOval(true,(int)rrX,(int)rrY,3,3);
        }
    }

    
    //Different other line styles not fully developed.........
    
    public int rand0to2(){
        return (int)(Math.random()*100)%5;
    }

    public void drawALine648(int x1,int y1  ,  int x2,int y2 )//Draws a caligraphic line
    {
        Point triangle[]= new Point[5];
        Point fp= null,cp =new Point();
        //         int cx,cy;
        g.setColor(ColorModel.color);
        // 
        //         //g.drawLine(x1,y1,x2,y2);
        //         Point p[] ={new Point(x1-50,y1-50),new Point(x1+50,y1-50), new Point(x1-50,y1+50),new Point(x1+50,y1+50)};
        // 
        //         int a  = (int)(Math.random()*100)%4;
        //         Point fp = new Point(x1+5,y1-5);
        //         Point vp = p[a];
        //         cx = (fp.x + vp.x)/2;
        //         cy = (fp.y + vp.y)/2;
        // 
        //         Point prev = vp;
        //         g.drawLine((int)cx,(int)cy,(int)cx,(int)cy);
        //         for(int i=0;i<=400000;i++)
        //         {
        //             while(vp.x == prev.x && vp.y == prev.y)
        //             {
        //                 a  = ((int)(Math.random()*100))%4;
        //                 vp = p[a];
        //             }
        // 
        //             cx = (cx + vp.x)/2;
        //             cy = (cy + vp.y)/2;
        //             
        //             g.drawLine((int)cx,(int)cy,(int)cx,(int)cy);
        //         }
        //get the current size of the component
        Dimension d ;
        d  = new Dimension(50,50);
        //make an isocilese triangle from the size of the 
        //component for the original points
        triangle[0] = new Point(40,40);
        triangle[1] = new Point(0,(int)d.getHeight());
        triangle[2] = new Point((int)d.getWidth(),(int)d.getHeight());
        triangle[3] = new Point((int)d.getWidth(),0);
        triangle[4] = new Point((int)d.getWidth()/2,0);
        //get an arbitrary point in the middle of the screen somewhere
        fp=new Point((int)(Math.random()*d.getWidth()),(int)(Math.random()*d.getHeight()/3));

        //get a random triangle point
        Point p = triangle[rand0to2()];
        //get the first current point by going half way from the first
        //point to the chosen triangle point
        cp.x=(p.x+fp.x)/2;
        cp.y=(p.y+fp.y)/2;

        //draw the first point
        g.drawRect(cp.x+x1,cp.y+y1,0,0);

        //compute 40000 points in the same fashion but use the current 
        //point rather than the very original point

        Point prev = p;
        int itr =(int)( Math.random()*(Math.pow(10,(Math.random()*10/2))));
        for(int i = 0; i<itr/10;i++){
            p = triangle[rand0to2()];
            while(p.x == prev.x && p.y == prev.y)
            {
                p = triangle[rand0to2()];
            }

            cp.x=(p.x+cp.x)/2;
            cp.y=(p.y+cp.y)/2;
            g.drawRect(cp.x+x1,cp.y+y1,0,0);

            prev = p;
        }
    }

    public void drawALine999998(int x1,int y1  ,  int x2,int y2 )//Draws a caligraphic line
    {
        int r = 7;
        double attr=0;

        g.setColor(ColorModel.color);

        int cir =(int)( 2 * 3.14 * r);
        Point p = new Point(x1,y1);
        Point v;
        double cx=0;
        for(int i=1;i<40000;i++)
        {
            double c2x =cx ;
            while(c2x == cx)
                c2x = (Math.random()*100%15);
            cx = c2x;
            double cy = Math.sqrt(225-cx*cx);

            v = new Point((int)(x1+cx),(int)(y1+cy));

            p.x = (p.x+v.x)/2;
            p.y = (p.y+v.y)/2;

            g.drawRect(p.x,p.y,0,0);
        }
    }

    public void drawALine38(int x1,int y1  ,  int x2,int y2 )//Draws a caligraphic line
    {
        g.setColor(ColorModel.color);

        //g.drawLine(x1,y1,x2,y2);

        double rrY=y1, rrX=x1 ;
        for(int i=0;i<=10;i++)
        {
            boolean b = (Math.random()>=0.5?true:false);
            double cirX = Math.random()*(10*Math.abs(x1-x2));
            double cirY = Math.random()*(10*Math.abs(y1-y2));

            double pX = rrX;
            double pY = rrY;
            rrX = b?x1+cirX:x1-cirX;  
            rrY  = b?y1+cirY:y1-cirY; 

            g.drawLine((int)pX,(int)pY,(int)rrX,(int)rrY);
        }
    }

    public void drawALine48(int x1,int y1  ,  int x2,int y2 )//Draws a caligraphic line
    {
        g.setColor(ColorModel.color);

        //g.drawLine(x1,y1,x2,y2);
        for(int i=0;i<=10;i++)
        {
            boolean b = (Math.random()>=0.5?true:false);
            double cirX = Math.random()*(10*Math.abs(x1-x2));
            double cirY = Math.random()*(10*Math.abs(y1-y2));

            double rrX  = b?x1+cirX:x1-cirX;  
            double rrY  = b?y1+cirY:y1-cirY; 

            g.drawLine((int)x1,(int)y1,(int)rrX,(int)rrY);
        }
    }

    public void drawALine28(int x1,int y1  ,  int x2,int y2 )//Draws a caligraphic line
    {
        g.setColor(ColorModel.color);

        //g.drawLine(x1,y1,x2,y2);

        double rrY=y1, rrX=x1 ;
        for(int i=0;i<=10;i++)
        {
            boolean b = (Math.random()>=0.5?true:false);
            double cirX = Math.random()*(10*Math.abs(x1-x2));
            double cirY = Math.random()*(10*Math.abs(y1-y2));

            double pX = rrX;
            double pY = rrY;
            rrX = b?x1+cirX:x1-cirX;  
            rrY  = b?y1+cirY:y1-cirY; 

            g.drawLine((int)x2,(int)y2,(int)rrX,(int)rrY);
        }
    }

    public void drawALine18(int x1,int y1  ,  int x2,int y2 )//Draws a caligraphic line
    {
        g.setColor(ColorModel.color);
        double sinV = Math.sin(sinT*Math.PI/180);

        if(sinV*3>10)sinV=1;
        int mX = (x1+x2)/2;
        int mY = (y1+y2)/2;

        g.drawLine(x1,y1,(int)(mX+sinV*20),(int)(mY+sinV*20));
        g.drawLine(x1,y1,x2,y2);
        sinT+=4;
    }

    public void reset()
    {
        pp1x =0;
    }

    /**
     *  Draws a Rectangle from x,y with width = w, height = h and filled if isFilled = true
     */
    public void drawRect(boolean isFilled,int x,int y, int w ,int h)//Rectangle (Both filled and not-filled)
    {
        g.setColor(ColorModel.color);
        if(isFilled)
        {
            g.fillRect(x,y,w,h);
        }
        else
        {
            g.drawRect(x,y,w,h);
        }
    }

    public void reset(int x,int y)
    {
        pX = fx(x);
        pY = fy(y);
    }

     /**
     *  Draws a Oval from x,y with width = w, height = h and filled if isFilled = true
     */
    public void drawOval(boolean isFilled,int x,int y,int w,int h)
    {
        g.setColor(ColorModel.color);
        if(isFilled)
        {
            g.fillOval(x,y,w,h);
        }
        else{
            g.drawOval(x,y,w,h);
        }
    }

    //***************************************FILL METHODS*******************************
    
    
    /**
     * Fills the background with a certain color
     */
    public void fill(Color c)
    {
        Color temp =ColorModel.color;
        ColorModel.color = c;
        drawRect(true,0,0,getImage().getWidth(),getImage().getHeight());
        ColorModel.color = temp;
    }
    
    /**
     * Set the direction of gradient background
     */
    public void setGradDir(int a)
    {
        gradDir = a;
    }

     /**
     *  Draws a gradient background
     */
    public void fillGrad(Color c1,Color c2)
    {
        GradientPaint grad;
        if(gradDir == L2R_DIAGONAL)
        grad = new GradientPaint(0,0,c2,getWidth(),getHeight(),c1,cyclic);
        else if(gradDir == VERTICAL)
        grad = new GradientPaint( getWidth() / 2, 0, c2, getWidth() / 2, getHeight(), c1, cyclic );
        else if(gradDir == HORIZONTAL)
        grad = new GradientPaint( 0, getHeight()/2, c2, getWidth(), getHeight() / 2, c1, cyclic );
        else
        grad = new GradientPaint( getWidth(), 0, c2, 0, getHeight(), c1, cyclic );
        
        Graphics2D g2d  = (Graphics2D)g;

        g2d.setPaint(grad);
        g2d.fillRect(0,0,getWidth(),getHeight());
    }

    /**
     *  Sets whether the gradient background be cyclic
     */
    public void setCyclic(boolean c)
    {
        cyclic = c;
    }
    //********************************GETTERS*******************************************
    
     /**
     *  Returns width of drawing area
     */
    public int getWidth()
    {
        return (getImage().getWidth());
    }

     /**
     *  Returns height of drawing area
     */
    public int getHeight()
    {
        return (getImage().getHeight());
    }
}