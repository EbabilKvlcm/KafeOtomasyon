package Views;

import Controllers.GarsonController;
import Entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

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

    private Table selectedTable;

    public GarsonView(User user) {

        setTitle("Garson Paneli - " + user.getUsername());
        setSize(1200, 600);
        setLayout(new BorderLayout(10,10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        /* ---------- ÜST BAR ---------- */
        JLabel lblTitle = new JLabel("Garson Paneli");
        lblTitle.setFont(lblTitle.getFont().deriveFont(18f));

        JButton btnLogout = new JButton("Çıkış Yap");
        btnLogout.addActionListener(e -> logout());

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        top.add(lblTitle, BorderLayout.WEST);
        top.add(btnLogout, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        /* ---------- MASALAR ---------- */
        controller.getTables().forEach(tableModel::addElement);

        tableList.setFixedCellHeight(40);
        tableList.setBorder(BorderFactory.createTitledBorder("Masalar"));

        tableList.setCellRenderer((list, table, index, isSelected, focus) -> {
            JLabel lbl = new JLabel(table.toString());
            lbl.setOpaque(true);
            lbl.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

            Color bg =
                    table.getStatus() == TableStatus.EMPTY ? new Color(40,167,69) :
                            table.getStatus() == TableStatus.ORDERED ? new Color(255,193,7) :
                                    new Color(220,53,69);

            lbl.setBackground(bg);

            if (isSelected) {
                lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            }

            return lbl;
        });

        tableList.addListSelectionListener(e -> {
            selectedTable = tableList.getSelectedValue();
            loadOrders();
        });

        /* ---------- KATEGORİ & ÜRÜNLER ---------- */
        controller.getCategories().forEach(cmbCategory::addItem);
        cmbCategory.addActionListener(e -> loadProducts());

        productList.setBorder(BorderFactory.createTitledBorder("Ürünler"));
        productList.setFixedCellHeight(35);

        productList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && selectedTable != null) {
                    Product p = productList.getSelectedValue();
                    if (p == null) return;

                    controller.addProduct(selectedTable, p);
                    loadOrders();
                    tableList.repaint();
                }
            }
        });

        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.add(cmbCategory, BorderLayout.NORTH);
        productPanel.add(new JScrollPane(productList), BorderLayout.CENTER);

        /* ---------- SİPARİŞ ---------- */
        orderList.setBorder(BorderFactory.createTitledBorder("Sipariş"));

        JButton btnSend = new JButton("Kasaya Gönder");
        btnSend.addActionListener(e -> {
            if (selectedTable == null) return;
            controller.sendToKasa(selectedTable);
            loadOrders();
            tableList.repaint();
        });

        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.add(lblTotal, BorderLayout.NORTH);
        orderPanel.add(new JScrollPane(orderList), BorderLayout.CENTER);
        orderPanel.add(btnSend, BorderLayout.SOUTH);

        /* ---------- ANA ---------- */
        JPanel main = new JPanel(new GridLayout(1,3,10,0));
        main.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

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
        controller.getProductsByCategory(c).forEach(productModel::addElement);
    }

    private void loadOrders() {
        orderModel.clear();
        lblTotal.setText("Toplam: 0 ₺");

        if (selectedTable == null) return;

        List<OrderItem> list = controller.getOrder(selectedTable);
        if (list == null) return;

        double total = 0;
        for (OrderItem i : list) {
            orderModel.addElement(i);
            total += i.getTotalPrice();
        }
        lblTotal.setText("Toplam: " + total + " ₺");
    }
}
