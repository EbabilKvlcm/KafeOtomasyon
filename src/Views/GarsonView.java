package Views;

import Controllers.GarsonController;
import DataBase.OrderStore;
import Entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GarsonView extends BaseView {

    private GarsonController controller = new GarsonController();

    private DefaultListModel<Table> tableModel = new DefaultListModel<>();
    private JList<Table> tableList = new JList<>(tableModel);

    private JComboBox<Category> cmbCategory = new JComboBox<>();

    private DefaultListModel<Product> productModel = new DefaultListModel<>();
    private JList<Product> productList = new JList<>(productModel);

    private DefaultListModel<OrderItem> orderModel = new DefaultListModel<>();
    private JList<OrderItem> orderList = new JList<>(orderModel);

    private JLabel lblTotal = new JLabel("Toplam: 0 ₺");

    private Map<Integer, List<OrderItem>> orders = new HashMap<>();
    private Table selectedTable;

    public GarsonView(User user) {

        setTitle("Garson Paneli - " + user.getUsername());
        setSize(1200, 600);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        /* ---------- ÜST BAR ---------- */
        JButton btnLogout = new JButton("Çıkış Yap");
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(e -> logout());

        JLabel lblTitle = new JLabel("Garson Paneli");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        top.add(lblTitle, BorderLayout.WEST);
        top.add(btnLogout, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        /* ---------- MASALAR ---------- */
        for (Table t : controller.getTables()) {
            tableModel.addElement(t);
        }

        tableList.setFixedCellHeight(40);
        tableList.setBorder(BorderFactory.createTitledBorder("Masalar"));
        tableList.addListSelectionListener(e -> {
            selectedTable = tableList.getSelectedValue();
            loadOrders();
        });

        /* ---------- KATEGORİ & ÜRÜNLER ---------- */
        for (Category c : controller.getCategories()) {
            cmbCategory.addItem(c);
        }

        cmbCategory.addActionListener(e -> loadProducts());

        productList.setBorder(BorderFactory.createTitledBorder("Ürünler"));
        productList.setFixedCellHeight(35);

        productList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && selectedTable != null) {
                    Product p = productList.getSelectedValue();
                    controller.addProductToOrder(
                            orders,
                            selectedTable.getId(),
                            p
                    );
                    loadOrders();
                }
            }
        });

        JPanel productPanel = new JPanel(new BorderLayout(5, 5));
        productPanel.add(cmbCategory, BorderLayout.NORTH);
        productPanel.add(new JScrollPane(productList), BorderLayout.CENTER);

        /* ---------- SİPARİŞ PANEL ---------- */
        orderList.setBorder(BorderFactory.createTitledBorder("Sipariş"));
        orderList.setFixedCellHeight(35);

        JButton btnDecrease = new JButton("–1");
        JButton btnRemove = new JButton("Sil");
        JButton btnSendToKasa = new JButton("Kasaya Gönder");

        btnSendToKasa.setBackground(new Color(40, 167, 69));

        btnDecrease.addActionListener(e -> {
            if (selectedTable == null) return;
            controller.decreaseProduct(
                    orders,
                    selectedTable.getId(),
                    orderList.getSelectedValue()
            );
            loadOrders();
        });

        btnRemove.addActionListener(e -> {
            if (selectedTable == null) return;
            controller.removeProduct(
                    orders,
                    selectedTable.getId(),
                    orderList.getSelectedValue()
            );
            loadOrders();
        });

        btnSendToKasa.addActionListener(e -> {
            if (selectedTable == null) return;

            OrderStore.kasaOrders.put(
                    selectedTable.getId(),
                    orders.get(selectedTable.getId())
            );

            orders.remove(selectedTable.getId());
            loadOrders();

            JOptionPane.showMessageDialog(this,
                    "Masa " + selectedTable.getId() + " kasaya gönderildi");
        });

        JPanel orderButtons = new JPanel(new GridLayout(1, 3, 5, 5));
        orderButtons.add(btnDecrease);
        orderButtons.add(btnRemove);
        orderButtons.add(btnSendToKasa);

        JPanel orderPanel = new JPanel(new BorderLayout(5, 5));
        orderPanel.add(lblTotal, BorderLayout.NORTH);
        orderPanel.add(new JScrollPane(orderList), BorderLayout.CENTER);
        orderPanel.add(orderButtons, BorderLayout.SOUTH);

        /* ---------- ANA YERLEŞİM ---------- */
        JPanel main = new JPanel(new GridLayout(1, 3, 10, 0));
        main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        main.add(new JScrollPane(tableList));
        main.add(productPanel);
        main.add(orderPanel);

        add(main, BorderLayout.CENTER);

        setVisible(true);
    }

    private void loadProducts() {
        productModel.clear();
        Category c = (Category) cmbCategory.getSelectedItem();
        if (c == null) return;

        for (Product p : controller.getProductsByCategory(c)) {
            productModel.addElement(p);
        }
    }

    private void loadOrders() {
        orderModel.clear();

        if (selectedTable == null) {
            lblTotal.setText("Toplam: 0 ₺");
            return;
        }

        List<OrderItem> list = orders.get(selectedTable.getId());
        if (list == null) {
            lblTotal.setText("Toplam: 0 ₺");
            return;
        }

        double total = 0;
        for (OrderItem item : list) {
            orderModel.addElement(item);
            total += item.getTotalPrice();
        }

        lblTotal.setText("Toplam: " + total + " ₺");
    }
}
