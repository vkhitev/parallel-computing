package lab1;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

class BasicColorsMapping {
  private static Map<String, Color> colors = new HashMap<>();

  static {
    colors.put("red", Color.RED);
    colors.put("blue", Color.BLUE);
    colors.put("green", Color.GREEN);
    colors.put("yellow", Color.YELLOW);
    colors.put("light gray", Color.LIGHT_GRAY);
    colors.put("dark gray", Color.DARK_GRAY);
    colors.put("black", Color.BLACK);
    colors.put("yellow", Color.YELLOW);
    colors.put("white", Color.WHITE);
    colors.put("pink", Color.PINK);
    colors.put("orange", Color.ORANGE);
    colors.put("magenta", Color.MAGENTA);
    colors.put("cyan", Color.CYAN);
  }

  static Color getColor(String strColor, Color defaultValue) {
    return colors.getOrDefault(strColor, defaultValue);
  }

  static Color getColor (String strColor) {
    return getColor(strColor, Color.BLACK);
  }
}
