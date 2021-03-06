package pk.Tools;


/**
 * Square Tool
 * @author Rahul B.
 * @version 0.1.
 */
public class SquareTool extends AbstractTool
{
    public static final int SQUARE_TOOL =0;
    public static final String SQUARE ="Square";
    public boolean isSelected =false;
    
     public SquareTool(){
       super.ID ="Square";
    }
    
    public boolean getSelected()
    {
        return isSelected;
    }
    

    public void setSelected(boolean sel)
    {
        isSelected = sel;
    }
    
    public void handle(int x,int y,int x1, int y1)
    {
        (new SquareHandler()).handle(x,y,x1,y1);
    }
    
    public String toString()
    {
        return SQUARE;
    }
    
    @Override
    public void setInit(int x,int y)
    {
        Properties.SquareProp.x = x;
        Properties.SquareProp.y = y;
    }
    
    @Override
    public void repeat(int x,int y)
    {
        (new SquareHandler()).handle(x,y,x,y);
    }
    
    @Override
    public void released(int x,int y)
    {
        SquareHandler h = new SquareHandler();
        h.flag = true;
        h.handle(x,y,x,y);
    }
}
