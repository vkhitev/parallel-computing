package lab1;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

class GroupButtonUtils {
  private static List<AbstractButton> getButtonList (ButtonGroup buttonGroup) {
    List<AbstractButton> buttonList = new ArrayList<>();
    Enumeration<AbstractButton> buttons = buttonGroup.getElements();
    while (buttons.hasMoreElements()) {
      buttonList.add(buttons.nextElement());
    }
    return buttonList;
  }

  static Optional<String> getSelectedButtonText(ButtonGroup buttonGroup) {
    return getButtonList(buttonGroup)
        .stream()
        .filter(AbstractButton::isSelected)
        .map(AbstractButton::getText)
        .findFirst();
  }
}
