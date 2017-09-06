package lab1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import javax.swing.*;

class BounceFrame extends JFrame {
  private Pool pool;

  private JButton btnAdd;
  private JButton btnClose;
  private JRadioButton radioRed;
  private JRadioButton radioBlue;
  private JTextField inputSize;
  private JTextField inputSpeed;

  BounceFrame() {
    // Create canvas that is rendering balls
    try {
      pool = new Pool();
    } catch (IOException e) {
      System.out.println("BG image not found");
      System.exit(1);
    }

    // Button, that adds new ball
    btnAdd = new JButton("Add ball");
    btnAdd.addActionListener(e -> {
      Color color = Color.red;
      if (radioBlue.isSelected()) {
        color = Color.blue;
      }

      int ballSize;
      try {
        ballSize = Integer.parseInt(inputSize.getText());
        if (ballSize <= 0) {
          throw new Exception("Wrong size");
        }
      } catch (Exception ex) {
        ballSize = 10;
      }

      int ballSpeed;
      try {
        ballSpeed = Integer.parseInt(inputSpeed.getText());
        if (ballSpeed <= 0) {
          throw new Exception("Wrong speed");
        }
      } catch (Exception ex) {
        ballSpeed = 2;
      }

      BallController ball = new BallController(ballSize, color, ballSpeed);
      pool.addBall(ball);
      BallThread thread = new BallThread(pool, ball);
      thread.start();
      ball.setBallGotIntoPocketListener(ballController -> {
        System.out.println("In pocket");
        pool.removeBall(ballController);
        thread.interrupt();
      });
    });

    // Button, that stops application
    btnClose = new JButton("Exit");
    btnClose.addActionListener(e -> System.exit(0));

    JLabel labelSize = new JLabel("Size:");
    labelSize.setPreferredSize(new Dimension(50, 25));
    inputSize = new JTextField("10");
    inputSize.setPreferredSize(new Dimension(50, 25));

    JLabel labelSpeed = new JLabel("Speed:");
    labelSpeed.setPreferredSize(new Dimension(50, 25));
    inputSpeed = new JTextField("2");
    inputSpeed.setPreferredSize(new Dimension(50, 25));

    radioRed = new JRadioButton("Red", true);
    radioBlue = new JRadioButton("Blue", false);
    ButtonGroup group = new ButtonGroup();
    group.add(radioRed);
    group.add(radioBlue);

    // Setting layout
    JPanel controlPane = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(10, 10, 0, 10);

    c.gridx = 0;
    c.gridy = 0;
    controlPane.add(labelSize, c);

    c.gridx = 1;
    controlPane.add(inputSize, c);

    c.gridx = 0;
    c.gridy = 1;
    controlPane.add(labelSpeed, c);

    c.gridx = 1;
    controlPane.add(inputSpeed, c);

    c.gridx = 0;
    c.gridy = 2;
    controlPane.add(radioRed, c);

    c.gridx = 1;
    controlPane.add(radioBlue, c);

    c.gridx = 0;
    c.gridy = 3;
    c.gridwidth = 2;
    controlPane.add(btnAdd, c);

    c.gridy = 4;
    controlPane.add(btnClose, c);

    controlPane.setBackground(Color.lightGray);
    radioRed.setBackground(Color.lightGray);
    radioBlue.setBackground(Color.lightGray);

    Container container = this.getContentPane();

    pool.setPreferredSize(new Dimension(BallController.TABLE_WIDTH, BallController.TABLE_HEIGHT));

    container.add(pool, BorderLayout.CENTER);
    container.add(controlPane, BorderLayout.EAST);

    this.setSize(BallController.TABLE_WIDTH + 146, BallController.TABLE_HEIGHT + 29);
    this.setResizable(false);
    this.setTitle("Bounce program");
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
}
