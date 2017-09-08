package lab1;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

class JPaneWithBackground extends JPanel {

  private Image backgroundImage;

  JPaneWithBackground(String filename) {
    try {
      backgroundImage = ImageIO.read(new File(filename));
    } catch (IOException e) {
      System.err.println("Image \"" + filename + "\" not loaded");
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (backgroundImage != null) {
      g.drawImage(backgroundImage, 0, 0, this);
    }
  }
}