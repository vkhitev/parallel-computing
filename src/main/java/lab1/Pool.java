package lab1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import sun.awt.Mutex;

class Pool extends JPaneWithBackground {
  private final Set<Ball> balls = new HashSet<>();
  Set<Ball> getBalls () {
    return balls;
  }

  private Consumer<Ball> onBallGotIntoPocket = null;
  void setOnBallGotIntoPocket (Consumer<Ball> onBallGotIntoPocket) {
    this.onBallGotIntoPocket = onBallGotIntoPocket;
  }

  Pool() {
    super("./src/main/resources/billiard.png");
  }

  void addBall (Ball ball) {
    synchronized (balls) {
      balls.add(ball);
    }
    BallThread thread = new BallThread(ball);
    if (ball.getBallData().color.equals(Color.red)) {
      thread.setPriority(Thread.MAX_PRIORITY);
    } else {
      thread.setPriority(Thread.MIN_PRIORITY);
    }
    thread.start();
    ball.setOnBallGotIntoPocket(b -> {
      System.out.println("Ball \"" + Thread.currentThread().getName() +  "\" is in pocket");
      thread.interrupt();
      synchronized (balls) {
        balls.remove(ball);
      }
      if (onBallGotIntoPocket != null) {
        onBallGotIntoPocket.accept(ball);
      }
    });
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g;
    synchronized (balls) {
      for (Ball ball : balls) {
        ball.render(g2d);
      }
    }
    this.repaint();
  }
}
