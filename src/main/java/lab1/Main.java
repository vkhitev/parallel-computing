package lab1;

public class Main {
  public static void main(String[] args) {
    System.out.println("Main thread started: " + Thread.currentThread().getName());
    new BounceFrame();
  }
}

