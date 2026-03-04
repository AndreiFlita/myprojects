package gui;

import bll.ClientBLL;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClientsGUI extends JFrame {
    private ClientBLL clientBLL;
    private JTable clientsTable;
    private  JScrollPane clientsScrollPane;
    /**
     * Constructs the client management GUI.
     * Initializes the UI and loads the list of clients.
     */
    public ClientsGUI() {
        clientBLL = new ClientBLL();
        initializeUI();
        loadClients();
    }
    /**
     * Initializes the GUI components such as buttons and table layout.
     */
    private void initializeUI() {
        setTitle("Manage Clients");
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
                int selectedRow = clientsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) clientsTable.getValueAt(selectedRow, 0);
                    Client client = clientBLL.findClientById(id);
                    showAddEditDialog(client);
                } else {
                    JOptionPane.showMessageDialog(ClientsGUI.this, "Please select a client to edit.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = clientsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) clientsTable.getValueAt(selectedRow, 0);
                    int confirm = JOptionPane.showConfirmDialog(
                            ClientsGUI.this,
                            "Are you sure you want to delete this client?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        clientBLL.deleteClient(id);
                        loadClients();
                    }
                } else {
                    JOptionPane.showMessageDialog(ClientsGUI.this, "Please select a client to delete.");
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        clientsTable = new JTable();
        clientsScrollPane = new JScrollPane(clientsTable);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(clientsScrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }
    /**
     * Loads all clients from the business logic layer and updates the clients table.
     */
    private void loadClients() {
        List<Client> clients = clientBLL.findAllClients();
        clientsTable.setModel(new DefaultTableModel());
        clientsTable = TableGenerator.createTable(clients);
        clientsScrollPane.setViewportView(clientsTable);
    }
    /**
     * Displays a dialog for adding a new client or editing an existing client.
     * @param client The client to edit; if null, a new client will be created.
     */
    private void showAddEditDialog(Client client) {
        JDialog dialog = new JDialog(this, client == null ? "Add Client" : "Edit Client", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField();
        JTextField addressField = new JTextField();

        if (client != null) {
            nameField.setText(client.getName());
            addressField.setText(client.getAddress());
        }

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (client == null) {
                        Client newClient = new Client(nameField.getText(), addressField.getText());
                        clientBLL.insertClient(newClient);
                    } else {
                        client.setName(nameField.getText());
                        client.setAddress(addressField.getText());
                        clientBLL.updateClient(client);
                    }
                    loadClients();
                    dialog.dispose();
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
