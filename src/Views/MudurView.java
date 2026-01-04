package Views;

import Controllers.MudurController;
import Entities.Category;
import Entities.Product;
import Entities.User;

import javax.swing.*;
import java.awt.*;

public class MudurView extends JFrame {

    private MudurController controller = new MudurController();

    private DefaultListModel<Category> categoryModel = new DefaultListModel<>();
    private JList<Category> categoryList = new JList<>(categoryModel);

    private DefaultListModel<Product> productModel = new DefaultListModel<>();
    private JList<Product> productList = new JList<>(productModel);

    public MudurView(User user) {

        setTitle("Müdür Paneli - " + user.getUsername());
        setSize(800, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        /* ---------------- SOL: KATEGORİ ---------------- */
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.setBorder(BorderFactory.createTitledBorder("Kategoriler"));

        JTextField txtCategory = new JTextField();
        JButton btnAddCategory = new JButton("Kategori Ekle");
        JButton btnDeleteCategory = new JButton("Kategori Sil");

        btnAddCategory.addActionListener(e -> {
            controller.addCategory(txtCategory.getText());
            txtCategory.setText("");
            loadCategories();
            loadCategoryCombo();
        });

        btnDeleteCategory.addActionListener(e -> {
            controller.deleteCategory(categoryList.getSelectedValue());
            loadCategories();
            loadCategoryCombo();
        });

        JPanel categoryBottom = new JPanel(new GridLayout(2, 1));
        categoryBottom.add(btnAddCategory);
        categoryBottom.add(btnDeleteCategory);

        categoryPanel.add(txtCategory, BorderLayout.NORTH);
        categoryPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER);
        categoryPanel.add(categoryBottom, BorderLayout.SOUTH);

        /* ---------------- ORTA: ÜRÜN EKLE ---------------- */
        JPanel productAddPanel = new JPanel(new GridLayout(7, 1));
        productAddPanel.setBorder(BorderFactory.createTitledBorder("Ürün Ekle"));

        JLabel lblName = new JLabel("Ürün Adı:");
        JTextField txtName = new JTextField();

        JLabel lblPrice = new JLabel("Fiyat:");
        JTextField txtPrice = new JTextField();

        JLabel lblCategory = new JLabel("Kategori:");
        JComboBox<Category> cmbCategory = new JComboBox<>();

        JButton btnAddProduct = new JButton("Ürün Ekle");

        btnAddProduct.addActionListener(e -> {
            try {
                controller.addProduct(
                        txtName.getText(),
                        Double.parseDouble(txtPrice.getText()),
                        (Category) cmbCategory.getSelectedItem()
                );
                txtName.setText("");
                txtPrice.setText("");
                loadProducts();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Fiyat sayısal olmalı!");
            }
        });

        productAddPanel.add(lblName);
        productAddPanel.add(txtName);
        productAddPanel.add(lblPrice);
        productAddPanel.add(txtPrice);
        productAddPanel.add(lblCategory);
        productAddPanel.add(cmbCategory);
        productAddPanel.add(btnAddProduct);

        /* ---------------- SAĞ: ÜRÜNLER ---------------- */
        JPanel productListPanel = new JPanel(new BorderLayout());
        productListPanel.setBorder(BorderFactory.createTitledBorder("Ürünler"));

        JButton btnDeleteProduct = new JButton("Ürünü Sil");
        btnDeleteProduct.addActionListener(e -> {
            controller.deleteProduct(productList.getSelectedValue());
            loadProducts();
        });

        productListPanel.add(new JScrollPane(productList), BorderLayout.CENTER);
        productListPanel.add(btnDeleteProduct, BorderLayout.SOUTH);

        /* ---------------- EKLE ---------------- */
        add(categoryPanel, BorderLayout.WEST);
        add(productAddPanel, BorderLayout.CENTER);
        add(productListPanel, BorderLayout.EAST);

        loadCategories();
        loadProducts();
        loadCategoryCombo(cmbCategory);

        setVisible(true);
    }

    private void loadCategories() {
        categoryModel.clear();
        for (Category c : controller.getCategories()) {
            categoryModel.addElement(c);
        }
    }

    private void loadProducts() {
        productModel.clear();
        for (Product p : controller.getProducts()) {
            productModel.addElement(p);
        }
    }

    private void loadCategoryCombo() {
        // boş, sadece çağrı için
    }

    private void loadCategoryCombo(JComboBox<Category> cmb) {
        cmb.removeAllItems();
        for (Category c : controller.getCategories()) {
            cmb.addItem(c);
        }
    }
}
