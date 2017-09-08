package lab1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;
import java.util.Set;
import javax.swing.*;

class BounceFrame extends JFrame {
  private ButtonGroup colorGroup = new ButtonGroup();
  private JTextField inputSize = new JTextField("10");
  private JTextField inputSpeed = new JTextField("2");
  private JLabel ballCount = new JLabel("Balls: 0");
  private JLabel errorMsg = new JLabel("");
  private JCheckBox checkBoxRandomized = new JCheckBox("Rand", true);

  private Ball createBallFromInput() {
    Color ballColor = BasicColorsMapping.getColor(
        GroupButtonUtils
            .getSelectedButtonText(colorGroup)
            .orElse("black")
            .toLowerCase()
    );

    int ballSize = Optional.of(inputSize.getText())
        .map(Integer::parseInt)
        .filter(size -> size > 0 && size < 50)
        .orElse(10);

    int ballSpeed = Optional.of(inputSpeed.getText())
        .map(Integer::parseInt)
        .filter(speed -> speed > 0 && speed < 5)
        .orElse(2);

    boolean isRandomized = checkBoxRandomized.isSelected();

    return new Ball(ballSize, ballSpeed, ballColor, isRandomized);
  }

  private void updateBallCount (Pool pool) {
    Set<Ball> balls = pool.getBalls();
    synchronized (balls) {
      ballCount.setText("Balls: " + balls.size());
    }
  }

  BounceFrame() {
    Pool pool = new Pool();
    pool.setPreferredSize(new Dimension(Ball.TABLE_WIDTH, Ball.TABLE_HEIGHT));

    JButton btnAdd = new JButton("Add ball");
    btnAdd.addActionListener(e -> {
      try {
        pool.addBall(createBallFromInput());
        pool.setOnBallGotIntoPocket(ball -> updateBallCount(pool));
        updateBallCount(pool);
        errorMsg.setText("");
      } catch (RuntimeException err) {
        errorMsg.setText("Wrong input");
      }
    });

    JButton btnClose = new JButton("Exit");
    btnClose.addActionListener(e -> System.exit(0));

    JLabel labelSize = new JLabel("Size:");
    labelSize.setPreferredSize(new Dimension(50, 25));
    inputSize.setPreferredSize(new Dimension(50, 25));

    JLabel labelSpeed = new JLabel("Speed:");
    labelSpeed.setPreferredSize(new Dimension(50, 25));
    inputSpeed.setPreferredSize(new Dimension(50, 25));

    JRadioButton radioRed = new JRadioButton("Red", true);
    JRadioButton radioBlue = new JRadioButton("Blue", false);
    colorGroup.add(radioRed);
    colorGroup.add(radioBlue);

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
    c.gridy = 5;
    controlPane.add(ballCount, c);
    c.gridx = 1;
    controlPane.add(checkBoxRandomized, c);
    c.gridx = 0;
    c.gridy = 6;
    errorMsg.setForeground(Color.red);
    controlPane.add(errorMsg, c);

    controlPane.setBackground(Color.lightGray);
    radioRed.setBackground(Color.lightGray);
    radioBlue.setBackground(Color.lightGray);
    checkBoxRandomized.setBackground(Color.lightGray);

    Container container = this.getContentPane();
    container.add(pool, BorderLayout.CENTER);
    container.add(controlPane, BorderLayout.EAST);

    this.setSize(Ball.TABLE_WIDTH + 150, Ball.TABLE_HEIGHT + 29);
    this.setResizable(false);
    this.setTitle("Pool");
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
}
