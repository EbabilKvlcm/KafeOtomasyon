package Views;

import DataBase.OrderStore;
import Entities.OrderItem;
import Entities.User;

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
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        /* ---------- ÜST ---------- */
        JButton btnLogout = new JButton("Çıkış Yap");
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(e -> logout());

        JLabel lblTitle = new JLabel("Kasa Paneli");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        top.add(lblTitle, BorderLayout.WEST);
        top.add(btnLogout, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        /* ---------- MASALAR ---------- */
        tableList.setBorder(BorderFactory.createTitledBorder("Kasadaki Masalar"));
        tableList.setFixedCellHeight(40);
        tableList.addListSelectionListener(e -> loadOrders());

        JButton btnRefresh = new JButton("Yenile");
        btnRefresh.addActionListener(e -> refreshTables());

        JPanel left = new JPanel(new BorderLayout(5, 5));
        left.add(btnRefresh, BorderLayout.NORTH);
        left.add(new JScrollPane(tableList), BorderLayout.CENTER);

        /* ---------- SİPARİŞ ---------- */
        orderList.setBorder(BorderFactory.createTitledBorder("Sipariş Detayı"));
        orderList.setFixedCellHeight(35);

        JButton btnPay = new JButton("Ödeme Al");
        btnPay.setBackground(new Color(40, 167, 69));

        btnPay.addActionListener(e -> pay());

        JPanel right = new JPanel(new BorderLayout(5, 5));
        right.add(lblTotal, BorderLayout.NORTH);
        right.add(btnPay, BorderLayout.SOUTH);

        add(left, BorderLayout.WEST);
        add(new JScrollPane(orderList), BorderLayout.CENTER);
        add(right, BorderLayout.EAST);

        refreshTables();
        setVisible(true);
    }

    private void refreshTables() {
        tableModel.clear();
        for (Integer id : OrderStore.kasaOrders.keySet()) {
            tableModel.addElement(id);
        }
    }

    private void loadOrders() {
        orderModel.clear();

        Integer tableId = tableList.getSelectedValue();
        if (tableId == null) return;

        List<OrderItem> list = OrderStore.kasaOrders.get(tableId);
        double total = 0;

        for (OrderItem item : list) {
            orderModel.addElement(item);
            total += item.getTotalPrice();
        }

        lblTotal.setText("Toplam: " + total + " ₺");
    }

    private void pay() {
        Integer tableId = tableList.getSelectedValue();
        if (tableId == null) return;

        OrderStore.kasaOrders.remove(tableId);
        refreshTables();
        orderModel.clear();
        lblTotal.setText("Toplam: 0 ₺");

        JOptionPane.showMessageDialog(this,
                "Masa " + tableId + " ödeme alındı");
    }
}
