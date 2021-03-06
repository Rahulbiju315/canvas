package pk.Tools;


import java.awt.*;
import Tools.Listener.*;

/**
 * Handler for eraser tool
 * 
 * @author Rahul B.
 * @version 0.1.
 */
public class EraserHandler extends AbstractHandler
{
    public   void handle(int x,int y, int x1, int y1)
   {
       int  size  =Properties.EraserProp.size;
       int rgb = ColorModel.getRGB();
       ColorModel.setColor(Color.white);
       easel.drawRect(true,Zoom.toScreen(x),Zoom.toScreen(y),Zoom.toScreen(size),Zoom.toScreen(size));
       ColorModel.setColor(new Color(rgb));
   }
}
