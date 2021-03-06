package pk;

import javax.swing.*;
import pk.Tools.*;

/**
 *  The tools popup menu when tools button from the tool bar is pressed.
 *  Actions from the Actions class are used
 *  
 * @author Rahul B.
 * @version 0.1.
 */
class ToolsMenu extends JPopupMenu
{
    public ToolsMenu()
    {
        super();
        
       
        add(new Actions.OvalAction("OVAL"));
        add(new Actions.SquareAction("SQUARE"));
        add(new Actions.PencilAction("PENCIL"));
        add(new Actions.FWCAction("Fill With Color Tool"));
        add(new Actions.PolygonAction("Polygon"));
        add(new Actions.EraserAction("Eraser"));
        add(new Actions.MystifyAction("Mystify"));
        add(new Actions.SelectionAction("Select"));
        add(new Actions.TurtleAction("Turtle"));
        //add(new Actions.BDAction("Brighten/Darken"));
        add(new Actions.FractAction("Fractals"));
    }
    
    
}