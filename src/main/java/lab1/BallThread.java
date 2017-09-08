package lab1;

public class BallThread extends Thread {
  private Ball ball;

  BallThread(Ball ball) {
    this.ball = ball;
  }

  @Override
  public void run() {
    try {
      System.out.println("Ball thread started: " + Thread.currentThread().getName());
      while (true) {
        this.ball.move();
        Thread.sleep(5);
      }
    } catch(Exception e) {
      System.out.println("Thread " + Thread.currentThread().getName() + " was interrupted for some reason.");
    }
  }
}
