package pk.Tools;

import Tools.Listener.*;

/**
 * Handler for OvalTool
 * 
 * @author Rahul B.
 * @version 0.1.
 */
public class OvalHandler extends AbstractHandler
{
    boolean flag = false;
    public  void handle(int x, int y, int x1,int y1)
    {
        boolean filled =Properties.OvalProp.filled;
        if(Properties.OvalProp.selected == Properties.OvalProp.SPEED_BASED)
            easel.drawOval(filled,Zoom.toScreen(x),Zoom.toScreen(y),Zoom.toScreen(Math.abs(x1-x)),Zoom.toScreen(Math.abs(y1-y)));

        else if(Properties.OvalProp.selected == Properties.OvalProp.POS_BASED)
            easel.drawOval(filled,Zoom.toScreen(x),Zoom.toScreen(y),Zoom.toScreen(Math.abs(x1)),Zoom.toScreen(Math.abs(y1)));
        else
        {
            int minX = Math.min(Properties.OvalProp.x,x);
            int minY = Math.min(Properties.OvalProp.y,y);

            int maxX = Math.max(Properties.OvalProp.x,x);
            int maxY = Math.max(Properties.OvalProp.y,y);

            int wid  = maxX - minX;
            int hei  = maxY - minY;

            if(filled)
                TGraph.g.fillOval(Zoom.toScreen(minX),Zoom.toScreen(minY),Zoom.toScreen(wid),Zoom.toScreen(hei));
            else
                TGraph.g.drawOval(Zoom.toScreen(minX),Zoom.toScreen(minY),Zoom.toScreen(wid),Zoom.toScreen(hei));
            if(flag)
                easel.drawOval(filled,Zoom.toScreen(minX),Zoom.toScreen(minY),Zoom.toScreen(wid),Zoom.toScreen(hei));
        }
    }

}
