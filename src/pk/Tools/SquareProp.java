package pk.Tools;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;

/**
 * Properties panel of SquareTool<br>
 * Component Description
 * ----------------------
 * speedBased   :- Checkbox for enabling speed based drawing method.
 * posBased     :- Checkbox for enabling position based drawing method.
 * @author Rahul B.
 * @version 0.1.
 */
public class SquareProp extends JPanel
{
    JRadioButton speedBased = new JRadioButton("SPEED BASED",false);
    JRadioButton posBased   = new JRadioButton("POSITION BASED",true);
    JRadioButton normal     = new JRadioButton("NORMAL",false);
    JRadioButton filled = new JRadioButton("FILLED?",false);
    public SquareProp()
    {
        posBased.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {  
                    Properties.SquareProp.selected = Properties.SquareProp.POS_BASED;
                }
            });
        speedBased.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {  
                    Properties.SquareProp.selected = Properties.SquareProp.SPEED_BASED;
                }
            });
        normal.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {  
                    Properties.SquareProp.selected = Properties.SquareProp.NORMAL;
                }
            });
        filled.addChangeListener(new ChangeListener()
            {
                public void stateChanged(ChangeEvent e)
                {
                    Properties.SquareProp.filled = !(Properties.SquareProp.filled);
                }
            });

        ButtonGroup g = new ButtonGroup();
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        g.add(posBased);
        g.add(speedBased);
        g.add(normal);

        add(posBased);
        add(speedBased);
        add(normal);
        add(filled);
        
        
        setBorder(new BevelBorder(BevelBorder.RAISED));
    }

    public int getSelected()
    {
        if(posBased.isSelected()) return 1;
        else return 2;
    }

    public void update()
    {
        repaint();
    }
}
