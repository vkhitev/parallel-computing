package lab1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.Setter;
import org.pcollections.HashTreePSet;
import org.pcollections.PSet;

class Pool extends JPaneWithBackground {
  @Getter
  private PSet<Ball> balls = HashTreePSet.empty();

  @Setter
  private Consumer<Ball> onBallGotIntoPocket = null;

  Pool() {
    super("./src/main/resources/billiard.png");
  }

  void addBall (Ball ball, boolean isDestroyable) {
    balls = balls.plus(ball);
    BallThread thread = new BallThread(ball);

    if (ball.getBallData().getColor().equals(Color.red)) {
      thread.setPriority(Thread.MAX_PRIORITY);
    } else {
      thread.setPriority(Thread.MIN_PRIORITY);
    }

    thread.start();

    if (isDestroyable) {
      ball.setOnBallGotIntoPocket(b -> {
        thread.interrupt();
        balls = balls.minus(ball);
        if (onBallGotIntoPocket != null) {
          onBallGotIntoPocket.accept(ball);
        }
      });
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g;
    balls.forEach(ball -> ball.render(g2d));
    this.repaint();
  }
}
