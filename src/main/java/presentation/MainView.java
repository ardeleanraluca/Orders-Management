package presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private final JPanel contentPane;
    private JFrame frame = this;

    private JButton btnClient;
    private JButton btnOrder;
    private JButton btnProduct;

    public MainView() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 578, 499);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Choose your operation");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 24));
        lblNewLabel.setBounds(155, 26, 251, 65);
        contentPane.add(lblNewLabel);

        btnClient = new JButton("Client Operation");
        btnClient.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));

        btnClient.setBounds(187, 114, 184, 47);
        contentPane.add(btnClient);

        btnProduct = new JButton("Product Operation");
        btnProduct.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));

        btnProduct.setBounds(187, 187, 184, 47);
        contentPane.add(btnProduct);

        btnOrder = new JButton("Order Operation");
        btnOrder.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        btnOrder.setBounds(187, 269, 184, 47);
        contentPane.add(btnOrder);


        this.setVisible(true);
        this.setResizable(false);
    }

    public JButton getBtnClient() {
        return btnClient;
    }

    public JButton getBtnOrder() {
        return btnOrder;
    }

    public JButton getBtnProduct() {
        return btnProduct;
    }

    public JFrame getFrame() {
        return frame;
    }
}
