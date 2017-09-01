package lab1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import javax.swing.*;

class BounceFrame extends JFrame {
  private Pool pool;

  private JButton initAddBallButton(Pool pool) {
    JButton btn = new JButton("Add ball");
    btn.addActionListener(e -> {
      BallController ball = new BallController(10, Color.red, 2);
      pool.addBall(ball);
      BallThread thread = new BallThread(pool, ball);
      thread.start();

      ball.setBallGotIntoPocketListener(ballController -> {
        System.out.println("In pocket");
        pool.removeBall(ballController);
        thread.interrupt();
      });
    });
    return btn;
  }

  private JButton initStopButton() {
    JButton btn = new JButton("Exit");
    btn.addActionListener(e -> System.exit(0));
    return btn;
  }

//  private ButtonGroup initColorGroup() {
//
//
//    ButtonGroup group = new ButtonGroup();
//    group.add(red);
//    group.add(blue);
//    return group;
//  }

  BounceFrame() {
    this.setSize(
        BallController.TABLE_WIDTH + 500,
        BallController.TABLE_HEIGHT + 39
    );
    this.setTitle("Bounce program");

    try {
      pool = new Pool();
    } catch (IOException e) {
      System.out.println("BG image not found");
      System.exit(1);
    }

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.lightGray);
    buttonPanel.add(this.initAddBallButton(pool));
    buttonPanel.add(this.initStopButton());

//    ButtonGroup group = initColorGroup();

    Container container = this.getContentPane();
    container.add(pool, BorderLayout.CENTER);
    container.add(buttonPanel, BorderLayout.EAST);

    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
}
