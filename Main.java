import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Main
{
  public static void main ( String[] args )
  {

    
    int[][][] image = imageToByteArray("B", "png");
    int[][][] smoothedImage = smooth3Dx(image, 20);

    new ImageMakerColor(image, "Original Image");
    new ImageMakerColor(smoothedImage, "20x Smoothed Image");

    //print3DArray(image);
    //print3DArray(smoothedImage);
    //print3DArray(smoothedImage2);

  }

  public static int[][][] smooth3Dx(int[][][] a, int b) {
    if (b < 1 || b > 20) {
      System.out.println("1 <= x <= 20");
      return (new int[0][0][0]);
    }
    for (int i = 0; i < b; i++) {
      a = smooth3D(a);
    }
    return a;
  }

  public static int[][][] imageToByteArray(String a, String b){
    File file = new File(a + "." + b);
    BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    try {
        image = ImageIO.read(file);
    } catch (IOException e) {
        e.printStackTrace();
    }
    int p = image.getWidth();          
    int q = image.getHeight();      
    System.out.println(q + "x" + p);   
    int r = 3;
    int[][][] data = new int[q][p][r];
    for(int y = 0; y < image.getHeight(); y++){
      for(int x = 0; x < image.getWidth(); x++){
        int px = image.getRGB(x,y);
        int red = (px >> 16) & 0xFF;
        int green = (px >> 8) & 0xFF;
        int blue = px & 0xFF;
        data[y][x][0] = red;
        data[y][x][1] = green;
        data[y][x][2] = blue;
      }
    }
    return data;
  }

  public static int[][][] smooth3D(int[][][] image) {
    int[][][] smooth = new int[image.length][image[0].length][3];
    for (int row=0; row<image.length; row++)
    {
      for (int col=0; col<image[row].length; col++)
      {
        for (int i = 0; i<3; i++) {
          int total = 2* image[row][col][i];
          double avgNums = 2;
          boolean leftExists = (col > 0);
          boolean rightExists = (col < image[row].length-1);
          boolean upExists = (row > 0);
          boolean downExists = (row < image.length-1);
          if (leftExists) {
            total += image[row][col-1][i];
            avgNums++;
          }
          if (rightExists) {
            total += image[row][col+1][i];
            avgNums++;
          }
          if (upExists) {
            total += image[row-1][col][i];
            avgNums++;
          }
          if (downExists) {
            total += image[row+1][col][i];
            avgNums++;
          }
          if (downExists && rightExists) {
            total += image[row+1][col+1][i]/2;
            avgNums+=0.5;
          }
          if (downExists && leftExists) {
            total += image[row+1][col-1][i]/2;
            avgNums+=0.5;
          }
          if (upExists && leftExists) {
            total += image[row-1][col-1][i]/2;
            avgNums+=0.5;
          }
          if (upExists && rightExists) {
            total += image[row-1][col+1][i]/2;
            avgNums+=0.5;
          }
          int avg = total/(int)avgNums;
          if (avg > 255) {
            avg = 255;
          }
          if (avg < 0) {
            avg = 0;
          }
            
          smooth[row][col][i] = avg;
        }
      }
    }
    return smooth;
  }
  public static int hexToGray(int a) {
    int r = (a / (256*256));
    int g = (a / 256) % 256;
    int b = a % 256;

    if (r >= 256 || r < 0) {
      r = 0;
      System.out.println("failed to avg");
    }
    if (g >= 256 || g < 0) {
      g = 0;
      System.out.println("failed to avg");
    }
    if (b >= 256 || b < 0) {
      b = 0;
      System.out.println("failed to avg");
    } 
      

    return (r + g + b) / 3;
  }
  public static void to2D(int[] a, int[][] b, int dimensionX) {
    int dimensionY = a.length/dimensionX;

    for (int i = 0, j = -1; ; i++) {
      if (i % dimensionX == 0) { 
        j++;
      }
      if (j == dimensionY) {
        break;
      }
      b[j][i - (j*dimensionX)] = hexToGray(a[i]);
    }
  }
  public static void to3D(int[] a, int[][][] b, int dimensionX) {
    int dimensionY = a.length/dimensionX;

    for (int i = 0, j = -1; ; i++) {
      if (i % dimensionX == 0) { 
        j++;
      }
      if (j == dimensionY) {
        break;
      }
      b[j][i - (j*dimensionX)] = hexToRGB(a[i]);
    }
  }
  public static int[] hexToRGB(int a) {
    int r = (a / (256*256));
    int g = (a / 256) % 256;
    int b = a % 256;
    int[] c = {r, g, b};
    return c;
  }
  public static void smooth(int[][] image, int[][] smooth) {
    for (int row=0; row<image.length; row++)
    {
      for (int col=0; col<image[row].length; col++)
      {
        int total = 2* image[row][col];
        double avgNums = 2;
        boolean leftExists = (col > 0);
        boolean rightExists = (col < image[row].length-1);
        boolean upExists = (row > 0);
        boolean downExists = (row < image.length-1);

        if (leftExists) {
          total += image[row][col-1];
          avgNums++;
        }
        if (rightExists) {
          total += image[row][col+1];
          avgNums++;
        }
        if (upExists) {
          total += image[row-1][col];
          avgNums++;
        }
        if (downExists) {
          total += image[row+1][col];
          avgNums++;
        }
        if (downExists && rightExists) {
          total += image[row+1][col+1]/2;
          avgNums+=0.5;
        }
        if (downExists && leftExists) {
          total += image[row+1][col-1]/2;
          avgNums+=0.5;
        }
        if (upExists && leftExists) {
          total += image[row-1][col-1]/2;
          avgNums+=0.5;
        }
        if (upExists && rightExists) {
          total += image[row-1][col+1]/2;
          avgNums+=0.5;
        }

        smooth[row][col] = total/(int)avgNums ;
      }
    }
  }

  
  public static void print2DArray(int[][] a) {
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[0].length; j++) {
        System.out.print(a[i][j] + " ");
      }
      System.out.print("\n");
    }
  }
  public static void print3DArray(int[][][] a) {
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[0].length; j++) {
        for (int k = 0; k < 3; k++) {
          System.out.print(a[i][j][k] + " ");  
        }
        System.out.print("    ");
      }
      System.out.print("\n");
    }
  }


}