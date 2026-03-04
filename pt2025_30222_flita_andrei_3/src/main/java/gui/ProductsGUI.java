package gui;

import bll.ProductBLL;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductsGUI extends JFrame {
    private ProductBLL productBLL;
    private JTable productsTable;
    private JScrollPane productsScrollPane;
    /**
     * Constructs the product management GUI.
     * Initializes the UI and loads the list of products.
     */
    public ProductsGUI() {
        productBLL = new ProductBLL();
        initializeUI();
        loadProducts();
    }
    /**
     * Initializes the GUI layout, buttons, and product table.
     */
    private void initializeUI() {
        setTitle("Manage Products");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddEditDialog(null);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) productsTable.getValueAt(selectedRow, 0);
                    Product product = productBLL.findProductById(id);
                    showAddEditDialog(product);
                } else {
                    JOptionPane.showMessageDialog(ProductsGUI.this, "Please select a product to edit.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) productsTable.getValueAt(selectedRow, 0);
                    int confirm = JOptionPane.showConfirmDialog(
                            ProductsGUI.this,
                            "Are you sure you want to delete this product?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        productBLL.deleteProduct(id);
                        loadProducts();
                    }
                } else {
                    JOptionPane.showMessageDialog(ProductsGUI.this, "Please select a product to delete.");
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        productsTable = new JTable();
        productsScrollPane = new JScrollPane(productsTable);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(productsScrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }
    /**
     * Loads all products from the business logic layer and updates the product table.
     */
    private void loadProducts() {
        List<Product> products = productBLL.findAllProducts();
        productsTable.setModel(new DefaultTableModel());
        productsTable = TableGenerator.createTable(products);
        productsScrollPane.setViewportView(productsTable);
    }
    /**
     * Displays a dialog for adding a new product or editing an existing product.
     * @param product The product to edit; if null, a new product will be created.
     */
    private void showAddEditDialog(Product product) {
        JDialog dialog = new JDialog(this, product == null ? "Add Product" : "Edit Product", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField stockField = new JTextField();

        if (product != null) {
            nameField.setText(product.getName());
            priceField.setText(String.valueOf(product.getPrice()));
            stockField.setText(String.valueOf(product.getStock()));
        }

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Stock:"));
        panel.add(stockField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    int price = Integer.parseInt(priceField.getText());
                    int stock = Integer.parseInt(stockField.getText());

                    if (product == null) {
                        Product newProduct = new Product(name, price, stock);
                        productBLL.insertProduct(newProduct);
                    } else {
                        product.setName(name);
                        product.setPrice(price);
                        product.setStock(stock);
                        productBLL.updateProduct(product);
                    }
                    loadProducts();
                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for price and stock.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}

