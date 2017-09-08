package lab1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

class Ball {
  public static final int TABLE_WIDTH = 433;
  public static final int TABLE_HEIGHT = 236;

  private static final int HOLE_RADIUS = 19;

  private static final int LINE_TOP_OFFSET = 52 + HOLE_RADIUS;
  private static final int LINE_BOTTOM_OFFSET = 184 - HOLE_RADIUS;
  private static final int LINE_LEFT_OFFSET = HOLE_RADIUS;
  private static final int LINE_RIGHT_OFFSET = 50 + HOLE_RADIUS;

  private static final Point2D HOLE_1 = new Point(19, 19);
  private static final Point2D HOLE_2 = new Point(19, 236 - 19);

  private BallData ballData = new BallData();
  BallData getBallData () {
    return ballData;
  }

  private Consumer<Ball> onBallGotIntoPocket = null;
  void setOnBallGotIntoPocket (Consumer<Ball> onBallGotIntoPocket) {
    this.onBallGotIntoPocket = onBallGotIntoPocket;
  }

  Ball(int radius, int speed, Color color, boolean isRandomized) {
    ballData.radius = radius;
    ballData.color = color;
    if (isRandomized) {
      ballData.dx = ThreadLocalRandom.current().nextInt(1, speed);
      ballData.dy = ThreadLocalRandom.current().nextInt(-speed, speed);
      while (ballData.dy == 0) {
        ballData.dy = ThreadLocalRandom.current().nextInt(-speed, speed);
      }
      ballData.x = ThreadLocalRandom.current().nextInt(LINE_LEFT_OFFSET, LINE_RIGHT_OFFSET);
      ballData.y = ThreadLocalRandom.current().nextInt(LINE_TOP_OFFSET, LINE_BOTTOM_OFFSET);
    } else {
      ballData.dx = speed;
      ballData.dy = 0;
      ballData.x = LINE_RIGHT_OFFSET - LINE_LEFT_OFFSET;
      ballData.y = LINE_BOTTOM_OFFSET - LINE_TOP_OFFSET + radius;
    }
  }

  void move () {
    ballData.x += ballData.dx;
    ballData.y += ballData.dy;
    if (ballData.x < 0) {
      ballData.x = 0;
      ballData.dx = -ballData.dx;
    }
    if (ballData.x + ballData.radius * 2 >= TABLE_WIDTH) {
      ballData.x = TABLE_WIDTH - ballData.radius * 2;
      ballData.dx = -ballData.dx;
    }
    if (ballData.y < 0) {
      ballData.y = 0;
      ballData.dy = -ballData.dy;
    }
    if (ballData.y + ballData.radius * 2 >= TABLE_HEIGHT){
      ballData.y = TABLE_HEIGHT - ballData.radius * 2;
      ballData.dy = -ballData.dy;
    }

    if (isInsidePocket() && onBallGotIntoPocket != null) {
      onBallGotIntoPocket.accept(this);
    }
  }

  void render (Graphics2D g2d) {
    g2d.setColor(ballData.color);
    g2d.fill(new Ellipse2D.Double(
        ballData.x,
        ballData.y,
        ballData.radius * 2,
        ballData.radius * 2));
  }

  private double distanceTo (Point2D hole) {
    return Math.sqrt(Math.pow(ballData.x - hole.getX(), 2) +
        Math.pow(ballData.y - hole.getY(), 2));
  }

  private boolean isInsidePocket () {
    return (
        distanceTo(HOLE_1) <= HOLE_RADIUS - ballData.radius
            || distanceTo(HOLE_2) <= HOLE_RADIUS - ballData.radius
    );
  }

  static class BallData {
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
