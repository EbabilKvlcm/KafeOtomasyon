package Views;

import javax.swing.*;

public abstract class BaseView extends JFrame {

    protected void logout() {
        dispose();
        new LogIn();
    }
}
