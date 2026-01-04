package Views;

import Controllers.Control;
import Entities.Role;
import Entities.User;

import javax.swing.*;

public class LogIn extends JFrame {

    private JTextField txtUser = new JTextField();
    private JPasswordField txtPass = new JPasswordField();
    private JButton btnLogin = new JButton("Giriş");

    private Control controller = new Control();

    public LogIn() {

        setTitle("Kafe Otomasyonu - Login");
        setSize(300, 200);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblUser = new JLabel("Kullanıcı Adı");
        JLabel lblPass = new JLabel("Şifre");

        lblUser.setBounds(80, 15, 150, 20);
        txtUser.setBounds(78, 30, 150, 25);
        lblPass.setBounds(80, 55, 150, 20);
        txtPass.setBounds(78, 70, 150, 25);
        btnLogin.setBounds(100, 110, 100, 30);

        add(lblUser);
        add(txtUser);
        add(lblPass);
        add(txtPass);
        add(btnLogin);

        btnLogin.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {

        User user = controller.login(
                txtUser.getText(),
                new String(txtPass.getPassword())
        );

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Hatalı giriş!");
            return;
        }

        dispose();

        switch (user.getRole()) {
            case GARSON -> new GarsonView(user);
            case KASA   -> new KasaView(user);
            case MUDUR  -> new MudurView(user);
        }
    }
}
