package pk;

import javax.swing.*;
import java.awt.event.*;
import pk.Tools.KeyboardAction.*;
import pk.Tools.*;
/**
 * KeyBoard Action Class.
 * This class Defines keyboard action for turtle tool.
 * 
 * @author Rahul B.
 * @version 0.1.
 */
public class KAction
{
    Handler h = new Handler();
    public KAction(JFrame f)
    {
        JPanel content = (JPanel)f.getContentPane();
        KeyStroke strokeU = KeyStroke.getKeyStroke("UP");
        KeyStroke strokeL = KeyStroke.getKeyStroke("LEFT");
        KeyStroke strokeD = KeyStroke.getKeyStroke("DOWN");
        KeyStroke strokeR = KeyStroke.getKeyStroke("RIGHT");
        KeyStroke strokeP = KeyStroke.getKeyStroke("P");
        KeyStroke stroke_minus = KeyStroke.getKeyStroke("SUBTRACT");
        KeyStroke stroke_plus = KeyStroke.getKeyStroke("ADD");
        KeyStroke cw = KeyStroke.getKeyStroke("X");
        KeyStroke acw = KeyStroke.getKeyStroke("Z");
        KeyStroke home = KeyStroke.getKeyStroke("H");
        Action up = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
              h.handle(38);
            }
        };
        Action left = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
              h.handle(37);
            }
        };
        Action down = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
              h.handle(40);
            }
        };
        Action right = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
              h.handle(39);
            }
        };
        Action pu = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                Resource.turtle.pu = !Resource.turtle.pu;
            }
        };
        Action plus = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            { 
                Resource.turtle.speed +=1;
            }
        };
        Action minus = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(Resource.turtle.speed -1 != 0)
                Resource.turtle.speed -=1;
            }
        };
        Action x = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                 h.handle(88);//TutleHandler could handle this..
            }
        };
        Action z = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                 h.handle(90);//TutleHandler could handle this..
            }
        };
        Action h = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                 //reset turtle position
                 Resource.turtle.x = 50;
                 Resource.turtle.y = 50;
                 Resource.frame.repaint();
            }
        };
        InputMap inputMap = content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(strokeU, "UP");
        inputMap.put(strokeL, "LEFT");
        inputMap.put(strokeD, "DOWN");
        inputMap.put(strokeR, "RIGHT");
        inputMap.put(strokeP,"PU");
        inputMap.put(stroke_plus,"SPP");
        inputMap.put(stroke_minus,"SPM");
        inputMap.put(cw,"CW");
        inputMap.put(acw,"ACW");
        inputMap.put(home,"HOME");
        
        content.getActionMap().put("UP", up);
        content.getActionMap().put("LEFT", left);
        content.getActionMap().put("RIGHT", right);
        content.getActionMap().put("DOWN", down);
        content.getActionMap().put("PU", pu);
        content.getActionMap().put("SPP", plus);
        content.getActionMap().put("SPM", minus);
        content.getActionMap().put("CW", x);
        content.getActionMap().put("ACW", z);
        content.getActionMap().put("HOME",h);
    }
}
