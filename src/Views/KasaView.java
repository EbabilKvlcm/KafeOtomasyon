package Views;

import DataBase.OrderStore;
import Entities.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class KasaView extends BaseView {

    private DefaultListModel<Integer> tableModel = new DefaultListModel<>();
    private JList<Integer> tableList = new JList<>(tableModel);

    private DefaultListModel<OrderItem> orderModel = new DefaultListModel<>();
    private JList<OrderItem> orderList = new JList<>(orderModel);

    private JLabel lblTotal = new JLabel("Toplam: 0 ₺");

    public KasaView(User user) {

        setTitle("Kasa Paneli - " + user.getUsername());
        setSize(1000, 550);
        setLayout(new BorderLayout(10,10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        /* ---------- ÜST BAR ---------- */
        JLabel lblTitle = new JLabel("Kasa Paneli");
        lblTitle.setFont(lblTitle.getFont().deriveFont(18f));

        JButton btnLogout = new JButton("Çıkış Yap");
        btnLogout.addActionListener(e -> logout());

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        top.add(lblTitle, BorderLayout.WEST);
        top.add(btnLogout, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        /* ---------- SOL (MASALAR) ---------- */
        JScrollPane left = new JScrollPane(tableList);
        left.setPreferredSize(new Dimension(260, 0));
        tableList.setBorder(BorderFactory.createTitledBorder("Kasadaki Masalar"));
        add(left, BorderLayout.WEST);

        /* ---------- ORTA (SİPARİŞ) ---------- */
        orderList.setBorder(BorderFactory.createTitledBorder("Sipariş Detayı"));
        add(new JScrollPane(orderList), BorderLayout.CENTER);

        /* ---------- ALT ---------- */
        JButton btnPay = new JButton("Ödeme Al");
        btnPay.addActionListener(e -> pay());

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(lblTotal, BorderLayout.WEST);
        bottom.add(btnPay, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        refreshTables();
        tableList.addListSelectionListener(e -> loadOrders());

        setVisible(true);
    }

    private void refreshTables() {
        tableModel.clear();
        OrderStore.kasaOrders.keySet().forEach(tableModel::addElement);
    }

    private void loadOrders() {
        orderModel.clear();
        lblTotal.setText("Toplam: 0 ₺");

        Integer id = tableList.getSelectedValue();
        if (id == null) return;

        List<OrderItem> list = OrderStore.kasaOrders.get(id);
        double total = 0;

        for (OrderItem i : list) {
            orderModel.addElement(i);
            total += i.getTotalPrice();
        }
        lblTotal.setText("Toplam: " + total + " ₺");
    }

    private void pay() {
        Integer id = tableList.getSelectedValue();
        if (id == null) return;

        OrderStore.kasaOrders.remove(id);
        OrderStore.tableMap.get(id).setStatus(TableStatus.EMPTY);

        refreshTables();
        orderModel.clear();
        lblTotal.setText("Toplam: 0 ₺");
    }
}
