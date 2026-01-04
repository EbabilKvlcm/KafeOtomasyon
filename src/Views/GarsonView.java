package Views;

import Controllers.GarsonController;
import Entities.*;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class GarsonView extends BaseView {

    private JList<Table> tableList;
    private DefaultListModel<Table> tableModel = new DefaultListModel<>();

    private JList<Product> productList;
    private DefaultListModel<Product> productModel = new DefaultListModel<>();

    private JList<OrderItem> orderList;
    private DefaultListModel<OrderItem> orderModel = new DefaultListModel<>();

    private JComboBox<Category> cmbCategory;

    private GarsonController controller = new GarsonController();

    private Map<Integer, List<OrderItem>> orders = new HashMap<>();
    private Table selectedTable;

    public GarsonView(User user) {
        super(user, "Garson Paneli");
    }

    @Override
    protected void init() {
        setLayout(new BorderLayout());

        createTablePanel();
        createProductPanel();
        createOrderPanel();
    }

    private void createTablePanel() {

        JPanel panel = new JPanel(new FlowLayout());

        for (Table t : controller.getTables()) {
            tableModel.addElement(t);
        }

        tableList = new JList<>(tableModel);
        tableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableList.addListSelectionListener(e -> {
            selectedTable = tableList.getSelectedValue();
            loadOrder();
        });

        panel.add(new JScrollPane(tableList));
        add(panel, BorderLayout.NORTH);
    }

    private void createProductPanel() {

        JPanel panel = new JPanel(new BorderLayout());

        cmbCategory = new JComboBox<>();
        for (Category c : controller.getCategories()) {
            cmbCategory.addItem(c);
        }

        cmbCategory.addActionListener(e -> loadProducts());

        productList = new JList<>(productModel);

        productList.addListSelectionListener(e -> {
            Product p = productList.getSelectedValue();
            if (p != null && selectedTable != null) {
                controller.addProductToOrder(
                        orders, selectedTable.getId(), p
                );
                loadOrder();
            }
        });

        panel.add(cmbCategory, BorderLayout.NORTH);
        panel.add(new JScrollPane(productList), BorderLayout.CENTER);

        add(panel, BorderLayout.WEST);
    }

    private void createOrderPanel() {
        orderList = new JList<>(orderModel);
        add(new JScrollPane(orderList), BorderLayout.CENTER);
    }

    private void loadOrder() {

        orderModel.clear();

        if (selectedTable == null) return;

        List<OrderItem> list = orders.get(selectedTable.getId());
        if (list == null) return;

        for (OrderItem item : list) {
            orderModel.addElement(item);
        }
    }

    private void loadProducts() {

        productModel.clear();

        Category selected = (Category) cmbCategory.getSelectedItem();
        if (selected == null) return;

        for (Product p : controller.getProductsByCategory(selected)) {
            productModel.addElement(p);
        }
    }
}
