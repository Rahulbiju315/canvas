package pk.Tools;


import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;

/**
 * The eraser property panel
 * Component Description<br>
 * =======================<br>
 * size   :-  JSpinner to determine size of eraser
 * 
 * @author Rahul B.
 * @version 0.1.
 */
public class EraserProp extends JPanel
{
    JSpinner size = new JSpinner(new SpinnerNumberModel(1,1,150,10));
    public EraserProp()
    {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        size.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                Properties.EraserProp.size = ((Integer)(size.getValue())).intValue();
            }
        });

        add(size);
    }
    
    
    public void update()
    {
        repaint();
    }
}
