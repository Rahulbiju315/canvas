package pk.Tools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import javax.imageio.ImageIO;
import javax.management.Query;
import Tools.Listener.*;

/**
 * The fill with color tool handler.
 * 
 * @author Rahul B.
 * @version 0.1.
 */
public class FWCHandler extends AbstractHandler {

    /**
     * Checks whether the color at  point posX,posY is not  equal to the color of the point first clicked
     */
    public static boolean isColor( int posX, int posY,int r) {
        boolean f = ((easel.getImage()).getRGB(posX,posY) ==r);
        return f;
    }

    public void handle(int x,int y,int x1,int y1) {
        int tx = x;
        x= Zoom.toScreen(tx);
        
        int ty = y;
        y = Zoom.toScreen(ty);
        try {

            boolean[][] painted = new boolean[(easel.getImage()).getHeight()][(easel.getImage()).getWidth()];//array for storing the already filled points.

            if (isColor( x, y,x1) && !painted[x][y]) {    //if the color at point x,y is equal to the color of the point first clicked and whether it isnt already filled.

                Queue<Point> queue = new LinkedList<Point>();
                queue.add(new Point(x, y));// Add the point to be filled on a queue to fill..

                int pixelCount = 0;
                while (!queue.isEmpty()) {
                    Point p = queue.remove();

                    if ((p.x >= 0)
                    && (p.x < (easel.getImage()).getWidth() && (p.y >= 0) && (p.y < (easel.getImage())
                            .getHeight()))) {
                        if (!painted[p.y][p.x]
                        && isColor( p.x, p.y,x1)) {
                            painted[p.y][p.x] = true;
                            (easel.getImage()).setRGB(p.x,p.y,(ColorModel.color).getRGB());

                            queue.add(new Point(p.x + 1, p.y));
                            queue.add(new Point(p.x - 1, p.y));
                            queue.add(new Point(p.x, p.y + 1));
                            queue.add(new Point(p.x, p.y - 1));
                        }
                    }
                }
                
            }

            //}
            //}

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}