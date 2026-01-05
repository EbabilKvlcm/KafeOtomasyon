package Views;

import Controllers.MudurController;
import Entities.Category;
import Entities.Product;
import Entities.User;

import javax.swing.*;
import java.awt.*;

public class MudurView extends BaseView {

    private MudurController controller = new MudurController();

    private DefaultListModel<Category> categoryModel = new DefaultListModel<>();
    private JList<Category> categoryList = new JList<>(categoryModel);

    private DefaultListModel<Product> productModel = new DefaultListModel<>();
    private JList<Product> productList = new JList<>(productModel);

    private JComboBox<Category> cmbCategory = new JComboBox<>();

    private JTextField txtCategoryName = new JTextField();
    private JTextField txtProductName = new JTextField();
    private JTextField txtProductPrice = new JTextField();
    private JTextField txtNewPrice = new JTextField();

    public MudurView(User user) {

        setTitle("Müdür Paneli - " + user.getUsername());
        setSize(1100, 550);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        /* ---------- ÇIKIŞ ---------- */
        JButton btnLogout = new JButton("Çıkış Yap");
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(e -> logout());

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        top.add(btnLogout, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        /* ---------- KATEGORİ PANEL ---------- */
        JPanel categoryPanel = new JPanel(new BorderLayout(5, 5));
        categoryPanel.setBorder(BorderFactory.createTitledBorder("Kategoriler"));

        JButton btnAddCategory = new JButton("Kategori Ekle");
        JButton btnDeleteCategory = new JButton("Kategori Sil");

        btnAddCategory.addActionListener(e -> {
            controller.addCategory(txtCategoryName.getText());
            txtCategoryName.setText("");
            loadCategories();
            loadCategoryCombo();
        });

        btnDeleteCategory.addActionListener(e -> {
            controller.deleteCategory(categoryList.getSelectedValue());
            loadCategories();
            loadCategoryCombo();
        });

        JPanel categoryTop = new JPanel(new BorderLayout(5, 5));
        categoryTop.add(txtCategoryName, BorderLayout.CENTER);
        categoryTop.add(btnAddCategory, BorderLayout.EAST);

        JPanel categoryBottom = new JPanel(new GridLayout(1, 1, 5, 5));
        categoryBottom.add(btnDeleteCategory);

        categoryPanel.add(categoryTop, BorderLayout.NORTH);
        categoryPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER);
        categoryPanel.add(categoryBottom, BorderLayout.SOUTH);

        /* ---------- ÜRÜN EKLE PANEL ---------- */
        JPanel productAddPanel = new JPanel(new GridLayout(8, 1, 5, 5));
        productAddPanel.setBorder(BorderFactory.createTitledBorder("Ürün Ekle"));

        JButton btnAddProduct = new JButton("Ürün Ekle");

        btnAddProduct.addActionListener(e -> {
            try {
                controller.addProduct(
                        txtProductName.getText(),
                        Double.parseDouble(txtProductPrice.getText()),
                        (Category) cmbCategory.getSelectedItem()
                );
                txtProductName.setText("");
                txtProductPrice.setText("");
                loadProducts();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Fiyat sayısal olmalı!");
            }
        });

        productAddPanel.add(new JLabel("Ürün Adı"));
        productAddPanel.add(txtProductName);
        productAddPanel.add(new JLabel("Fiyat"));
        productAddPanel.add(txtProductPrice);
        productAddPanel.add(new JLabel("Kategori"));
        productAddPanel.add(cmbCategory);
        productAddPanel.add(new JLabel());
        productAddPanel.add(btnAddProduct);

        /* ---------- ÜRÜN LİSTESİ PANEL ---------- */
        JPanel productPanel = new JPanel(new BorderLayout(5, 5));
        productPanel.setBorder(BorderFactory.createTitledBorder("Ürünler"));

        JButton btnDeleteProduct = new JButton("Ürünü Sil");
        JButton btnUpdatePrice = new JButton("Fiyat Güncelle");

        btnDeleteProduct.addActionListener(e -> {
            controller.deleteProduct(productList.getSelectedValue());
            loadProducts();
        });

        btnUpdatePrice.addActionListener(e -> {
            try {
                controller.updateProductPrice(
                        productList.getSelectedValue(),
                        Double.parseDouble(txtNewPrice.getText())
                );
                txtNewPrice.setText("");
                loadProducts();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Yeni fiyat geçersiz!");
            }
        });

        JPanel productBottom = new JPanel(new GridLayout(4, 1, 5, 5));
        productBottom.add(new JLabel("Yeni Fiyat"));
        productBottom.add(txtNewPrice);
        productBottom.add(btnUpdatePrice);
        productBottom.add(btnDeleteProduct);

        productPanel.add(new JScrollPane(productList), BorderLayout.CENTER);
        productPanel.add(productBottom, BorderLayout.SOUTH);

        /* ---------- ANA YERLEŞİM ---------- */
        JPanel main = new JPanel(new GridLayout(1, 3, 15, 0));
        main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        main.add(categoryPanel);
        main.add(productAddPanel);
        main.add(productPanel);

        add(main, BorderLayout.CENTER);

        loadCategories();
        loadProducts();
        loadCategoryCombo();

        setVisible(true);
    }

    /* ---------- LOAD METOTLARI ---------- */

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
        cmbCategory.removeAllItems();
        for (Category c : controller.getCategories()) {
            cmbCategory.addItem(c);
        }
    }
}
