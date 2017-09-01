package lab1;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

class Pool extends JPaneWithBackground {
  private Set<BallController> balls = new HashSet<>();

  Pool() throws IOException {
    super("./src/main/resources/billiard.png");
  }

  void addBall (BallController bc) {
    balls.add(bc);
  }

  void removeBall(BallController bc) {
    balls.remove(bc);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g;
    for (BallController ball : balls) {
      ball.render(g2d);
    }
  }
}
