package pk;

import java.awt.*;
import javax.swing.*;
/**
 *  Displays splash screen
 *  @author Rahul B.
 * @version 0.1.
 */
public class SplashScreen extends JWindow {
  private int duration;
  public SplashScreen(int d) {
    duration = d;
  }

  // A simple little method to show a title screen in the center
  // of the screen for the amount of time given in the constructor
  public void showSplash() {
    JPanel content = (JPanel)getContentPane();
    content.setBackground(Color.white);

    // Set the window's bounds, centering the window
    int width = 560;
    int height =275;
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width-width)/2;
    int y = (screen.height-height)/2;
    setBounds(x,y,width,height);

    // Build the splash screen
    JLabel label = new JLabel(new ImageIcon("splash/splash.png"));
    JLabel copyrt = new JLabel();
 
    content.add(label, BorderLayout.CENTER);
    content.add(copyrt, BorderLayout.SOUTH);
    //content.setBorder(BorderFactory.createLineBorder(oraRed, 10));

    // Display it
    setVisible(true);

    // Wait a little while, maybe while loading resources
    try { Thread.sleep(5000); } catch (Exception e) {}

    setVisible(false);
  }

  public void showSplashAndExit() {
    showSplash();

  }

  public static void main() {
    SplashScreen splash = new SplashScreen(10000);
    splash.showSplashAndExit();
  }
}