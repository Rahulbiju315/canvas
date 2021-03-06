package pk.Tools;




import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;

/**
 * The tool for drawing polygons.
 * 
 * @author Rahul B.
 * @version 0.1.
 */
public class PolygonProp extends JPanel
{
    JButton end = new JButton("END");
    public PolygonProp()
    {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        end.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                PolygonHandler.inProcess =false;
            }
        });
        add(end);
    }
    
    
    public void update()
    {
        repaint();
    }
}
