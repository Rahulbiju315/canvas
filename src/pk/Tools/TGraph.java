package pk.Tools;


import java.awt.*;

/**
 * TGraph stores the temporary drawing area ie draws to canvas rather than easel
 * 
 * @author Rahul B.
 * @version 0.1.
 */
public class TGraph
{
    public static Graphics g;
    
    /**
     * Sets the temporary graphics variable
     */
    public static  void set(Graphics gs)
    {
        g = gs;
    }
}