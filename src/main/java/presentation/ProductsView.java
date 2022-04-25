package presentation;

import bll.ClientBLL;
import bll.ProductBLL;
import dao.ClientDAO;
import dao.ProductDAO;
import model.Client;
import model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class ProductsView extends JFrame {

    private JFrame frame = this;

    private JPanel contentPane;
    private JTextField name;
    private JTextField price;
    private JTextField stock;

    private JTable table;
    private DefaultTableModel model;
    private Product product;
    private int selectedRow;

    private JButton btnInsert;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton back;

    public ProductsView() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 883, 625);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(47, 125, 408, 400);
        contentPane.add(scrollPane);

        ProductDAO productDAO = new ProductDAO();
        table = productDAO.createTable(productDAO.findAll(), 0);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        model = (DefaultTableModel) table.getModel();
        scrollPane.setViewportView(table);

        JLabel lblNewLabel = new JLabel("Products Table");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 24));
        lblNewLabel.setBounds(367, 28, 164, 65);
        contentPane.add(lblNewLabel);

        name = new JTextField();
        name.setBounds(568, 125, 151, 47);
        contentPane.add(name);
        name.setColumns(10);

        price = new JTextField();
        price.setColumns(10);
        price.setBounds(568, 223, 151, 47);
        contentPane.add(price);

        stock = new JTextField();
        stock.setColumns(10);
        stock.setBounds(568, 318, 151, 47);
        contentPane.add(stock);

        JLabel lblNewLabel_1 = new JLabel("Name");
        lblNewLabel_1.setBounds(763, 141, 49, 14);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Price");
        lblNewLabel_2.setBounds(763, 239, 49, 14);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Stock");
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

    public JTextField getPrice() {
        return price;
    }

    public JTextField getStock() {
        return stock;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public Product getProduct() {
        return product;
    }

    public int getSelectedRow() {
        return selectedRow;
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

    public void setProduct(Product product) {
        this.product = product;
    }
}
