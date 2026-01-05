package Views;

import javax.swing.*;
import java.awt.*;

public class UIStyle {

    public static void apply() {

        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        UIManager.put("Button.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("List.font", font);
        UIManager.put("ComboBox.font", font);

        UIManager.put("Panel.background", new Color(245, 246, 250));
        UIManager.put("TextField.background", Color.WHITE);

        UIManager.put("Button.background", new Color(45, 125, 210));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.focusPainted", false);
    }
}
