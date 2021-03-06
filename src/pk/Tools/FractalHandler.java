package pk.Tools;

import java.awt.*;
import Tools.Listener.*;

/**
 * Handler for fractal tool
 * 
 * @author Rahul B.
 * @version 0.1.
 */
public class FractalHandler extends AbstractHandler
{
    public static boolean cont = false;
    public static boolean autoLen = false;
    static int k =800;
    public void handle(int x,int y,int x1, int y1)
    {
        easel.frc.fxStart = easel.frc.fx(Zoom.toScreen(x));
        easel.frc.fyStart = easel.frc.fy(Zoom.toScreen(y));
        double temp = easel.frc.lengthFract;
        if(autoLen)
        easel.frc.lengthFract = Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1))/k;
        if(!cont)
            AbstractHandler.easel.frc.dir =AbstractHandler.easel.frc.dirStart;
        Thread th = null;
        easel.drawFractal();
        easel.frc.lengthFract = temp;

    }
}
