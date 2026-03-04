package gui;

import bll.ClientBLL;
import bll.OrdersBLL;
import bll.ProductBLL;
import model.Client;
import model.Orders;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OrdersGUI extends JFrame {
    private OrdersBLL ordersBLL;
    private ClientBLL clientBLL;
    private ProductBLL productBLL;
    private JTable ordersTable;
    private JLabel statusLabel;
    private JScrollPane ordersScrollPane;
    /**
     * Constructs the orders management GUI.
     * Initializes the UI and loads the list of orders.
     */
    public OrdersGUI() {
        ordersBLL = new OrdersBLL();
        clientBLL = new ClientBLL();
        productBLL = new ProductBLL();
        initializeUI();
        loadOrders();
    }
    /**
     * Initializes the GUI components including buttons and order table.
     */
    private void initializeUI() {
        setTitle("Manage Orders");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        statusLabel = new JLabel(" ");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddEditDialog(null);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = ordersTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) ordersTable.getValueAt(selectedRow, 0);
                    Orders order = ordersBLL.findOrderById(id);
                    showAddEditDialog(order);
                } else {
                    JOptionPane.showMessageDialog(OrdersGUI.this, "Please select an order to edit.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = ordersTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) ordersTable.getValueAt(selectedRow, 0);
                    int confirm = JOptionPane.showConfirmDialog(
                            OrdersGUI.this,
                            "Are you sure you want to delete this order?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            Orders order = ordersBLL.findOrderById(id);
                            Product product = productBLL.findProductById(order.getProduct_id());
                            product.setStock(product.getStock() + order.getQuantity());
                            productBLL.updateProduct(product);

                            ordersBLL.deleteOrder(id);
                            loadOrders();
                            statusLabel.setText("Order deleted successfully. Stock restored.");
                        } catch (Exception ex) {
                            statusLabel.setText("Error deleting order: " + ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(OrdersGUI.this, "Please select an order to delete.");
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(statusLabel);

        ordersTable = new JTable();
        ordersScrollPane = new JScrollPane(ordersTable);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(ordersScrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }
    /**
     * Loads all orders from the business logic layer and updates the orders table.
     */
    private void loadOrders() {
        List<Orders> orders = ordersBLL.findAllOrders();
        ordersTable.setModel(new DefaultTableModel());
        ordersTable = TableGenerator.createTable(orders);
        ordersScrollPane.setViewportView(ordersTable);
    }
    /**
     * Displays a dialog for adding a new order or editing an existing one.
     * Handles validation, stock checking, and updates to product stock.
     * @param order The order to edit; if null, a new order will be created.
     */
    private void showAddEditDialog(Orders order) {
        String title = order == null ? "Add New Order" : "Edit Order";
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(500, 350);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        List<Client> clients = clientBLL.findAllClients();
        List<Product> products = productBLL.findAllProducts();

        JComboBox<Client> clientCombo = new JComboBox<>(clients.toArray(new Client[0]));
        JComboBox<Product> productCombo = new JComboBox<>(products.toArray(new Product[0]));
        JTextField quantityField = new JTextField();
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"pending", "completed"});
        JTextField dateField = new JTextField();

        if (order != null) {
            for (int i = 0; i < clientCombo.getItemCount(); i++) {
                if (clientCombo.getItemAt(i).getId() == order.getClient_id()) {
                    clientCombo.setSelectedIndex(i);
                    break;
                }
            }

            for (int i = 0; i < productCombo.getItemCount(); i++) {
                if (productCombo.getItemAt(i).getId() == order.getProduct_id()) {
                    productCombo.setSelectedIndex(i);
                    break;
                }
            }

            quantityField.setText(String.valueOf(order.getQuantity()));
            statusCombo.setSelectedItem(order.getIs_completed());
            dateField.setText(order.getOrder_date());
        } else {
            dateField.setText("yyyy-mm-dd");
        }

        panel.add(new JLabel("Client:"));
        panel.add(clientCombo);
        panel.add(new JLabel("Product:"));
        panel.add(productCombo);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(new JLabel("Status:"));
        panel.add(statusCombo);
        panel.add(new JLabel("Date (yyyy-mm-dd):"));
        panel.add(dateField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Client selectedClient = (Client) clientCombo.getSelectedItem();
                    Product selectedProduct = (Product) productCombo.getSelectedItem();
                    int quantity = Integer.parseInt(quantityField.getText());
                    String status = (String) statusCombo.getSelectedItem();
                    String date = dateField.getText();

                    if (order == null) {
                        if (quantity > selectedProduct.getStock()) {
                            statusLabel.setText("Under-stock: Only " + selectedProduct.getStock() + " items available.");
                            return;
                        }

                        Orders newOrder = new Orders(
                                selectedClient.getId(),
                                selectedProduct.getId(),
                                quantity,
                                status,
                                date
                        );
                        ordersBLL.insertOrder(newOrder);

                        selectedProduct.setStock(selectedProduct.getStock() - quantity);
                        productBLL.updateProduct(selectedProduct);

                        statusLabel.setText("Order placed successfully!");
                    } else {
                        int stockChange = quantity - order.getQuantity();

                        if (selectedProduct.getStock() + order.getQuantity() < quantity) {
                            statusLabel.setText("Under-stock: Cannot increase quantity beyond available stock.");
                            return;
                        }

                        order.setClient_id(selectedClient.getId());
                        order.setProduct_id(selectedProduct.getId());
                        order.setQuantity(quantity);
                        order.setIs_completed(status);
                        order.setOrder_date(date);
                        ordersBLL.updateOrder(order);

                        selectedProduct.setStock(selectedProduct.getStock() - stockChange);
                        productBLL.updateProduct(selectedProduct);

                        statusLabel.setText("Order updated successfully!");
                    }

                    loadOrders();
                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    statusLabel.setText("Please enter a valid quantity.");
                } catch (Exception ex) {
                    statusLabel.setText("Error: " + ex.getMessage());
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

