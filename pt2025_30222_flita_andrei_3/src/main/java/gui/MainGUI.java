package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame {
    /**
     * Constructs the main GUI window for the Orders Management System.
     * Initializes buttons to open sub-windows for clients, products, and orders.
     */
    public MainGUI() {
        setTitle("Orders Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton clientsButton = new JButton("Manage Clients");
        JButton productsButton = new JButton("Manage Products");
        JButton ordersButton = new JButton("Manage Orders");

        clientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClientsGUI().setVisible(true);
            }
        });

        productsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductsGUI().setVisible(true);
            }
        });

        ordersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrdersGUI().setVisible(true);
            }
        });

        panel.add(clientsButton);
        panel.add(productsButton);
        panel.add(ordersButton);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }
}

