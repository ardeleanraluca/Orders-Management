package presentation;

import dao.ClientDAO;
import model.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class ClientsView extends JFrame {

    private JFrame frame = this;

    private JPanel contentPane;
    private JTextField name;
    private JTextField address;
    private JTextField email;
    private JTable table;
    private DefaultTableModel model;

    private Client client;
    private int selectedRow;

    private JButton btnInsert;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton back;

    public ClientsView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 883, 625);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(47, 125, 408, 400);
        contentPane.add(scrollPane);


        ClientDAO clientDAO = new ClientDAO();
        table = clientDAO.createTable(clientDAO.findAll(), 0);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        model = (DefaultTableModel) table.getModel();
        scrollPane.setViewportView(table);

        JLabel lblNewLabel = new JLabel("Clients Table");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 24));
        lblNewLabel.setBounds(367, 28, 164, 65);
        contentPane.add(lblNewLabel);

        name = new JTextField();
        name.setBounds(568, 125, 151, 47);
        contentPane.add(name);
        name.setColumns(10);

        address = new JTextField();
        address.setColumns(10);
        address.setBounds(568, 223, 151, 47);
        contentPane.add(address);

        email = new JTextField();
        email.setColumns(10);
        email.setBounds(568, 318, 151, 47);
        contentPane.add(email);

        JLabel lblNewLabel_1 = new JLabel("Name");
        lblNewLabel_1.setBounds(763, 141, 49, 14);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Address");
        lblNewLabel_2.setBounds(763, 239, 49, 14);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Email");
        lblNewLabel_3.setBounds(763, 334, 49, 14);
        contentPane.add(lblNewLabel_3);

        btnInsert = new JButton("Insert");
        btnInsert.setBounds(465, 478, 115, 47);
        contentPane.add(btnInsert);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(604, 478, 115, 47);
        contentPane.add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(737, 478, 115, 47);
        contentPane.add(btnDelete);

        this.setVisible(true);
        this.setResizable(false);

        back = new JButton("Go Back");
        back.setBounds(2, 2, 100, 20);
        contentPane.add(back);


    }


    public JFrame getFrame() {
        return frame;
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }


    public JTextField getNameField() {
        return name;
    }

    public JTextField getAddress() {
        return address;
    }

    public JTextField getEmail() {
        return email;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public Client getClient() {
        return client;
    }

    public int getSelectedRow() {
        return this.selectedRow;
    }

    public JButton getBtnInsert() {
        return btnInsert;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnBack() {
        return back;
    }

    public void setSelectedRow(int selectedRow) {
        this.selectedRow = selectedRow;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
