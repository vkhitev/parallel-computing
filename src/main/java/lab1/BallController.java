package lab1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

class BallController {
  public static final int TABLE_WIDTH = 433;
  public static final int TABLE_HEIGHT = 236;

  private static final int HOLE_RADIUS = 19;

  private static final int LINE_TOP_OFFSET = 52 + HOLE_RADIUS;
  private static final int LINE_BOTTOM_OFFSET = 184 - HOLE_RADIUS;
  private static final int LINE_LEFT_OFFSET = HOLE_RADIUS;
  private static final int LINE_RIGHT_OFFSET = 50 + HOLE_RADIUS;

  private static final Point2D HOLE_1 = new Point(19, 19);
  private static final Point2D HOLE_2 = new Point(19, 236 - 19);

  private Ball ball;
  private Consumer<BallController> onBallGotIntoPocket;

  BallController(int radius, Color color, int speed) {
    this.ball = new Ball();
    ball.radius = radius;
    ball.color = color;
    ball.dx = ThreadLocalRandom.current().nextInt(1, speed);
    ball.dy = ThreadLocalRandom.current().nextInt(-speed, speed);
    while (ball.dy == 0) {
      ball.dy = ThreadLocalRandom.current().nextInt(-speed, speed);
    }
    ball.x = ThreadLocalRandom.current().nextInt(LINE_LEFT_OFFSET, LINE_RIGHT_OFFSET);
    ball.y = ThreadLocalRandom.current().nextInt(LINE_TOP_OFFSET, LINE_BOTTOM_OFFSET);
  }

  void setBallGotIntoPocketListener (Consumer<BallController> onBallGotIntoPocket) {
    this.onBallGotIntoPocket = onBallGotIntoPocket;
  }

  void move () {
    ball.x += ball.dx;
    ball.y += ball.dy;
    if (ball.x < 0) {
      ball.x = 0;
      ball.dx = -ball.dx;
    }
    if (ball.x + ball.radius * 2 >= TABLE_WIDTH) {
      ball.x = TABLE_WIDTH - ball.radius * 2;
      ball.dx = -ball.dx;
    }
    if (ball.y < 0) {
      ball.y = 0;
      ball.dy = -ball.dy;
    }
    if (ball.y + ball.radius * 2 >= TABLE_HEIGHT){
      ball.y = TABLE_HEIGHT - ball.radius * 2;
      ball.dy = -ball.dy;
    }

    if (isInsidePocket() && onBallGotIntoPocket != null) {
      onBallGotIntoPocket.accept(this);
    }
  }

  void render (Graphics2D g2d) {
    g2d.setColor(ball.color);
    g2d.fill(new Ellipse2D.Double(
        ball.x,
        ball.y,
        ball.radius * 2,
        ball.radius * 2));
  }

  private double distanceTo (Point2D hole) {
    return Math.sqrt(Math.pow(ball.x - hole.getX(), 2) +
        Math.pow(ball.y - hole.getY(), 2));
  }

  private boolean isInsidePocket () {
    return (
        distanceTo(HOLE_1) <= HOLE_RADIUS - ball.radius
            || distanceTo(HOLE_2) <= HOLE_RADIUS - ball.radius
    );
  }

  private static class Ball {
    private static final int DEFAULT_RADIUS = 15;
    private static final Color DEFAULT_COLOR = Color.darkGray;
    private static final int DEFAULT_SPEED = 2;

    int x = 0;
    int y = 0;
    int radius = DEFAULT_RADIUS;
    Color color = DEFAULT_COLOR;
    int dx = DEFAULT_SPEED;
    int dy = DEFAULT_SPEED;
  }
}
