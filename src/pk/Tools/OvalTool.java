package pk.Tools;


/**
 *  OvalTool
 * @author Rahul B.
 * @version 0.1.
 */
public class OvalTool extends AbstractTool
{
    public static final int OVAL_TOOL =1;
    public static final String OVAL ="Oval";
    public boolean isSelected =false;

    public OvalTool(){
           super.ID ="Oval";
    }
    
    public boolean getSelected()
    {
        return isSelected;
    }
    
    public void setSelected(boolean sel)
    {
        isSelected = sel;
    }
    
    public String toString()
    {
        return OVAL;
    }
    
    public void handle(int x,int y,int x1, int y1)
    {
        (new OvalHandler()).handle(x,y,x1,y1);
    }

    @Override
    public void setInit(int x,int y)
    {
        Properties.OvalProp.x = x;
        Properties.OvalProp.y = y;
    }
    
     @Override
    public void repeat(int x,int y)
    {
        (new OvalHandler()).handle(x,y,x,y);
    }
    
    @Override
    public void released(int x,int y)
    {
        OvalHandler h = new OvalHandler();
        h.flag = true;
        h.handle(x,y,x,y);
    }
}
