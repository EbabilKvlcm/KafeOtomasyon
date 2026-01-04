package Views;

import Entities.User;
import javax.swing.*;

public class KasaView extends BaseView {

    public KasaView(User user) {
        super(user, "Kasa Paneli");
    }

    @Override
    protected void init() {
        JLabel lbl = new JLabel("Kasa işlemleri burada yapılacak");
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(lbl);
    }
}
