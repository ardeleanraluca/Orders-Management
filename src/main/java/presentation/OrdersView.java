package presentation;

import bll.OrderBLL;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dao.ClientDAO;
import dao.OrderTableDAO;
import dao.ProductDAO;
import model.Client;
import model.OrderTable;
import model.Orders;
import model.Product;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class OrdersView extends JFrame {

    private JFrame frame = this;

    private JPanel contentPane;
    private JTextField quantity;
    private JTable tableClients;
    private JTable tableProducts;
    private JTable tableOrders;
    private JButton btnDelete;

    private JButton btnOrder;
    private JButton back;

    private DefaultTableModel modelClients;
    private DefaultTableModel modelProducts;
    private DefaultTableModel modelOrders;

    private Client client;
    private Product product;
    private Orders order;

    private int clientSelectedRow;
    private int productSelectedRow;
    private int orderSelectedRow;

    private static int nrBill = 1;

    public OrdersView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 940, 625);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPaneTableC = new JScrollPane();
        scrollPaneTableC.setBounds(47, 125, 366, 186);
        contentPane.add(scrollPaneTableC);


        JLabel lblNewLabel = new JLabel("Orders Table");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 24));
        lblNewLabel.setBounds(359, 28, 164, 65);
        contentPane.add(lblNewLabel);

        JScrollPane scrollPaneTableP = new JScrollPane();
        scrollPaneTableP.setBounds(47, 348, 366, 186);
        contentPane.add(scrollPaneTableP);

        JScrollPane scrollPaneTableO = new JScrollPane();
        scrollPaneTableO.setBounds(461, 125, 460, 306);
        contentPane.add(scrollPaneTableO);

        JLabel lblNewLabel_1 = new JLabel("Clients");
        lblNewLabel_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        lblNewLabel_1.setBounds(47, 102, 97, 22);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Products");
        lblNewLabel_1_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        lblNewLabel_1_1.setBounds(47, 326, 97, 22);
        contentPane.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("Orders");
        lblNewLabel_1_2.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        lblNewLabel_1_2.setBounds(461, 104, 97, 22);
        contentPane.add(lblNewLabel_1_2);

        quantity = new JTextField();
        quantity.setBounds(461, 463, 97, 38);
        contentPane.add(quantity);
        quantity.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Enter Quantity");
        lblNewLabel_2.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        lblNewLabel_2.setBounds(461, 512, 107, 22);
        contentPane.add(lblNewLabel_2);

        btnOrder = new JButton("Make order");
        btnOrder.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
        btnOrder.setBounds(787, 463, 129, 53);
        contentPane.add(btnOrder);

        btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
        btnDelete.setBounds(614, 463, 129, 53);
        contentPane.add(btnDelete);

        ClientDAO clientDAO = new ClientDAO();
        tableClients = clientDAO.createTable(clientDAO.findAll(), 0);
        tableClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        modelClients = (DefaultTableModel) tableClients.getModel();
        scrollPaneTableC.setViewportView(tableClients);

        ProductDAO productDAO = new ProductDAO();
        tableProducts = productDAO.createTable(productDAO.findAll(), 1);
        tableProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        modelProducts = (DefaultTableModel) tableProducts.getModel();
        scrollPaneTableP.setViewportView(tableProducts);

        OrderTableDAO orderTableDAO = new OrderTableDAO();
        tableOrders = orderTableDAO.createTable(orderTableDAO.findAll(), 0);
        tableOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        modelOrders = (DefaultTableModel) tableOrders.getModel();
        scrollPaneTableO.setViewportView(tableOrders);

        this.setVisible(true);
        this.setResizable(false);

        back = new JButton("Go Back");
        back.setBounds(2, 2, 100, 20);
        contentPane.add(back);

    }


    public JButton getBtnDelete() {
        return btnDelete;
    }
    public JFrame getFrame() {
        return frame;
    }

    public JButton getBtnOrder() {
        return btnOrder;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Orders getOrder() {
        return order;
    }

    public JButton getBack() {
        return back;
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }

    public JTextField getQuantity() {
        return quantity;
    }

    public JTable getTableClients() {
        return tableClients;
    }

    public JTable getTableProducts() {
        return tableProducts;
    }

    public JTable getTableOrders() {
        return tableOrders;
    }

    public DefaultTableModel getModelClients() {
        return modelClients;
    }

    public DefaultTableModel getModelProducts() {
        return modelProducts;
    }

    public DefaultTableModel getModelOrders() {
        return modelOrders;
    }


    public Client getClient() {
        return client;
    }

    public Product getProduct() {
        return product;
    }

    public int getClientSelectedRow() {
        return clientSelectedRow;
    }

    public int getProductSelectedRow() {
        return productSelectedRow;
    }

    public static int getNrBill() {
        return nrBill;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setClientSelectedRow(int clientSelectedRow) {
        this.clientSelectedRow = clientSelectedRow;
    }

    public int getOrderSelectedRow() {
        return orderSelectedRow;
    }

    public void setOrderSelectedRow(int orderSelectedRow) {
        this.orderSelectedRow = orderSelectedRow;
    }

    public void setProductSelectedRow(int productSelectedRow) {
        this.productSelectedRow = productSelectedRow;
    }
}
