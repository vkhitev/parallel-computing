package lab1;

public class BallThread extends Thread {
  private Pool pool;
  private BallController ball;

  BallThread(Pool pool, BallController ball) {
    this.pool = pool;
    this.ball = ball;
  }

  @Override
  public void run() {
    try {
      System.out.println("Ball thread started: " + Thread.currentThread().getName());
      for(int i = 1; i < 10000; i++) {
        this.ball.move();
        this.pool.repaint();
        Thread.sleep(5);
      }
    } catch(Exception exception){
      System.out.println("Thread was interrupted for some reason.");
    }
  }
}
