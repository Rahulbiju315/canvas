package pk.Dialogs;


import java.io.*;
import java.util.*;
import pk.Tools.*;

/**
 * Fractal Input and Output
 * Reads and writes .frc files.
 * .frc files contain the values for fractal definition<br>
 * FORMAT FOR FILE<br>
 * ====================<br>
 * AXIOM(string)<br>
 * FSTRING(string)<br>
 * fSTRING(string)<br>
 * XSTRING(string)<br>
 * YSTRING(string)<br>
 * ROTATION(double)<br>
 * INITIAL_DIRECTION(double)<br>
 * FLENGTH(double)<br>
 * REDUCTION FACTOR(double)<br>
 * LEVEL(double)<br>
 * RANDOM FACTOR(double)<br>
 * 
 * @author Rahul B.
 * @version 0.1.
 */
public class FractalIO
{
    /**
     *  Write the fractal definition consisting of the above parameters.
     *  Last parameter gives the fractal name.(Name of file)
     */
    public static void write(String [] s) 
    {
       try{
         File f = new File(System.getProperty("user.dir")+"/"+"fractals/"+s[s.length-1]+".frc");
         //f.mkdirs();
         f.createNewFile();
         PrintWriter pr = new PrintWriter(f);
         for(int i=0;i<s.length-1;i++)
         {
             pr.println(s[i]);
         }
         pr.close();
       }catch(Exception ex){System.err.println(ex);}
    }
    
    /**
     * Loads the fractal from the given file
     */
    public static void read(String name) 
    {
        String[] str;
        try{
           Scanner sc = new Scanner(new File(System.getProperty("user.dir")+"/"+"fractals/"+name));
           String axiom = sc.nextLine();
           System.out.println(axiom);
           String strF  = sc.nextLine();
           String strf  = sc.nextLine();
           String strX  = sc.nextLine();
           String strY  = sc.nextLine();
           
           double dir      = sc.nextDouble();
           double inDir    = sc.nextDouble();
           double flength  = sc.nextDouble();
           double rFact=  sc.nextDouble();
          
           
           int level       = sc.nextInt();
           
           double ran =0;
           if(sc.hasNextDouble())
               ran = sc.nextDouble();
           
           AbstractHandler.easel.frc.axiom = axiom;     
             AbstractHandler.easel.frc.strF  = strF;
             AbstractHandler.easel.frc.strf  = strf;
             AbstractHandler.easel.frc.strX  = strX;
             AbstractHandler.easel.frc.strY  = strY;
             
            AbstractHandler.easel.frc.rotation = dir;
            AbstractHandler.easel.frc.dir =inDir;
            
            AbstractHandler.easel.frc.lengthFract =flength;
            AbstractHandler.easel.frc.reductFact =rFact;
            AbstractHandler.easel.frc.level      = level;
            AbstractHandler.easel.frc.randF      = ran;
            sc.close();
         }catch(Exception ex){ex.printStackTrace();}
    }
}
