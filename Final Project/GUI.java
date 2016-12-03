import java.awt.*;       // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import javax.swing.*;    // Using Swing's components and containers
 
// A Swing application extends javax.swing.JFrame
public class GUI extends JFrame {
   // Define constants
   public static final int CANVAS_WIDTH  = 640;
   public static final int CANVAS_HEIGHT = 480;
 private static final int roomSize = 32;
 public static Graphics g;
   // Declare an instance of the drawing canvas,
   // which is an inner class called DrawCanvas extending javax.swing.JPanel.
   public static DrawCanvas canvas;
 
   // Constructor to set up the GUI components and event handlers
   public GUI() {
      canvas = new DrawCanvas();    // Construct the drawing canvas
      canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
 
      // Set the Drawing JPanel as the JFrame's content-pane
      Container cp = getContentPane();
      cp.add(canvas);
      // or "setContentPane(canvas);"
 
      setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
      pack();              // Either pack() the components; or setSize()
      setTitle("Game GUI");  // "super" JFrame sets the title
      setVisible(true);    // "super" JFrame show
   }
 
   /**
    * Define inner class DrawCanvas, which is a JPanel used for custom drawing.
    */
   private class DrawCanvas extends JPanel {
      // Override paintComponent to perform your own painting
      @Override
      public void paintComponent(Graphics g) {
         super.paintComponent(g);     // paint parent's background
         setBackground(Color.BLACK);  // set background color for this JPanel
 
         // Your custom painting codes. For example,
         // Drawing primitive shapes
         g.setColor(Color.YELLOW);    // set the drawing color
         //g.drawLine(30, 40, 100, 200);
         Room R;
		 int x;
		 int y;
		 for (int i=0; i<Game.roomList.size(); i++)
		 {
			 R=Game.roomList.get(i);
			 x=R.getX();
			 y=R.getY();
			 g.drawRect(x*roomSize, y*roomSize, roomSize, roomSize);
			 
			 g.setFont(new Font("Monospaced", Font.PLAIN, 12));
         g.setColor(Color.WHITE);
         g.drawString(Game.currentRoom.getRoomName(), 10, 20);
		
g.fillOval(Game.x*roomSize, Game.y*roomSize, roomSize, roomSize);
         
		
			 canvas.repaint();
      }
         	
		 
		 }
		 
		 //g.setColor(Color.RED);       // change the drawing color
         //g.fillOval(300, 310, 30, 50);
         //g.fillRect(400, 350, 60, 50);
         // Printing texts
         
   }
 

 
   // The entry main method
   public void runGUI() {
      // Run the GUI codes on the Event-Dispatching thread for thread safety
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new GUI(); // Let the constructor do the job
         }
      });
   }
   
   }