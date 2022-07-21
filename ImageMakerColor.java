import java.awt.*;
import javax.swing.*;

public class ImageMakerColor extends JPanel
{
  private static final int SIZE = 350;
  private static int position = 5;
  
  private int[][][] image;
  
  public ImageMakerColor(int[][][] img, String title)
  {
    super();
    image = img;
    JFrame f = new JFrame(title);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(this);
    f.setSize(SIZE + 8, SIZE + 34);
    f.setLocation(position, 25);
    f.setVisible(true);
    position += 400;
  }

  public void paint(Graphics g)
  {
    int numPixelsInRow = image[0].length;
    int numPixelsInCol = image.length;
    int pixelX = SIZE / (numPixelsInRow + 2);
    int pixelY = SIZE / (numPixelsInCol + 2);

    for(int r = 0; r < numPixelsInCol; r++)
    {
      for(int c = 0; c < numPixelsInRow; c++)
      {
        int r2 = image[r][c][0];
        int g2 = image[r][c][1];
        int b2 = image[r][c][2];
        g.setColor(new Color(r2,g2,b2));
        g.fillRect((c+1)*pixelX,(r+1)*pixelY,pixelX,pixelY);
      }
    }
  }
}