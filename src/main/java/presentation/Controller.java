package presentation;


import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import com.itextpdf.text.DocumentException;
import dao.ClientDAO;
import dao.ProductDAO;
import model.Client;
import model.OrderTable;
import model.Orders;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Controller {

    public void Initialize(MainView mainView) {
        mainView.getBtnClient().addActionListener(e -> {
            mainView.getFrame().dispose();
            ClientsView clientsView = new ClientsView();

            clientsView.getTable().addMouseListener(new MouseListener() {
                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    clientsView.setSelectedRow(clientsView.getTable().getSelectedRow());
                    clientsView.setClient(new ClientBLL().findAll().get(clientsView.getSelectedRow()));
                    clientsView.getNameField().setText((String) clientsView.getModel().getValueAt(clientsView.getSelectedRow(), 0));
                    clientsView.getAddress().setText((String) clientsView.getModel().getValueAt(clientsView.getSelectedRow(), 1));
                    clientsView.getEmail().setText((String) clientsView.getModel().getValueAt(clientsView.getSelectedRow(), 2));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                }
            });


            clientsView.getBtnInsert().addActionListener(e1 -> {
                System.out.println("Button was pressed");
                Client c = new Client();
                ClientBLL clientBLL = new ClientBLL();

                c.setName(clientsView.getNameField().getText());
                c.setAddress(clientsView.getAddress().getText());
                c.setEmail(clientsView.getEmail().getText());

                if (clientBLL.getValidators().get(0).validate(c) == 0) {
                    JOptionPane.showMessageDialog(null, "Invalid Mail");
                    clientsView.getEmail().setText("");
                    return;
                }

                clientBLL.insert(c);

                clientsView.getModel().addRow(new Object[]{c.getName(), c.getAddress(), c.getEmail()});

                clientsView.getNameField().setText("");
                clientsView.getAddress().setText("");
                clientsView.getEmail().setText("");

            });


            clientsView.getBtnUpdate().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ClientBLL clientBLL = new ClientBLL();
                    clientBLL.findAll().get(clientsView.getSelectedRow());

                    clientsView.getModel().setValueAt(clientsView.getNameField().getText(), clientsView.getSelectedRow(), 0);
                    clientsView.getModel().setValueAt(clientsView.getAddress().getText(), clientsView.getSelectedRow(), 1);
                    clientsView.getModel().setValueAt(clientsView.getEmail().getText(), clientsView.getSelectedRow(), 2);

                    if (clientBLL.getValidators().get(0).validate(clientsView.getClient()) == 0) {
                        JOptionPane.showMessageDialog(null, "Invalid Mail");
                        clientsView.getEmail().setText("");
                        return;
                    }

                    clientsView.getClient().setName(clientsView.getNameField().getText());
                    clientsView.getClient().setAddress(clientsView.getAddress().getText());
                    clientsView.getClient().setEmail(clientsView.getEmail().getText());

                    clientBLL.update(clientsView.getClient());
                }
            });


            clientsView.getBtnDelete().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clientsView.getModel().removeRow(clientsView.getSelectedRow());

                    clientsView.getNameField().setText("");
                    clientsView.getAddress().setText("");
                    clientsView.getEmail().setText("");

                    new ClientBLL().deleteClientById(clientsView.getClient().getId());
                }
            });


            clientsView.getBtnBack().addActionListener(e12 -> {
                clientsView.getFrame().dispose();
                MainView mainV = new MainView();
                Controller controller = new Controller();
                controller.Initialize(mainV);
            });

        });

        mainView.getBtnProduct().addActionListener(e -> {
            mainView.getFrame().dispose();
            ProductsView productsView = new ProductsView();


            productsView.getTable().addMouseListener(new MouseListener() {
                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println(productsView.getModel().getDataVector().elementAt(productsView.getTable().getSelectedRow()));
                    productsView.setSelectedRow(productsView.getTable().getSelectedRow());
                    productsView.setProduct(new ProductBLL().findAll().get(productsView.getSelectedRow()));

                    productsView.getNameField().setText((String) productsView.getModel().getValueAt(productsView.getSelectedRow(), 0));
                    productsView.getPrice().setText(Float.toString((Float) (productsView.getModel().getValueAt(productsView.getSelectedRow(), 1))));
                    productsView.getStock().setText(Integer.toString((Integer) (productsView.getTable().getValueAt(productsView.getSelectedRow(), 2))));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                }
            });

            productsView.getBtnInsert().addActionListener(e17 -> {
                System.out.println("Button was pressed");
                Product p = new Product();
                p.setName(productsView.getNameField().getText());
                p.setPrice(Float.parseFloat(productsView.getPrice().getText()));
                p.setStock(Integer.parseInt(productsView.getStock().getText()));

                ProductBLL prod = new ProductBLL();
                prod.insert(p);

                productsView.getModel().addRow(new Object[]{p.getName(), p.getPrice(), p.getStock()});

                productsView.getNameField().setText("");
                productsView.getPrice().setText("");
                productsView.getStock().setText("");

            });


            productsView.getBtnUpdate().addActionListener(e16 -> {

                productsView.getModel().setValueAt(productsView.getNameField().getText(), productsView.getSelectedRow(), 0);
                productsView.getModel().setValueAt(productsView.getPrice().getText(), productsView.getSelectedRow(), 1);
                productsView.getModel().setValueAt(productsView.getStock().getText(), productsView.getSelectedRow(), 2);

                productsView.getProduct().setName(productsView.getNameField().getText());
                productsView.getProduct().setPrice(Float.parseFloat(productsView.getPrice().getText()));
                productsView.getProduct().setStock(Integer.parseInt(productsView.getStock().getText()));
                new ProductBLL().update(productsView.getProduct());
            });


            productsView.getBtnDelete().addActionListener(e15 -> {
                int i = productsView.getTable().getSelectedRow();
                productsView.getModel().removeRow(i);

                productsView.getNameField().setText("");
                productsView.getPrice().setText("");
                productsView.getStock().setText("");
                new ProductBLL().deleteProductById(productsView.getProduct().getId());
            });


            productsView.getBtnBack().addActionListener(e14 -> {
                productsView.getFrame().dispose();
                MainView mainV = new MainView();
                Controller controller = new Controller();
                controller.Initialize(mainV);
            });

        });


        mainView.getBtnOrder().addActionListener(e -> {
            mainView.getFrame().dispose();
            OrdersView ordersView = new OrdersView();

            ordersView.getTableClients().addMouseListener(new MouseListener() {
                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    ordersView.setClientSelectedRow(ordersView.getTableClients().getSelectedRow());
                    ordersView.setClient(new ClientDAO().findAll().get(ordersView.getClientSelectedRow()));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                }
            });


            ordersView.getTableProducts().addMouseListener(new MouseListener() {
                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    ordersView.setProductSelectedRow(ordersView.getTableProducts().getSelectedRow());
                    ordersView.setProduct(new ProductDAO().findAll().get(ordersView.getProductSelectedRow()));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                }
            });

            ordersView.getTableOrders().addMouseListener(new MouseListener() {
                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    ordersView.setOrderSelectedRow(ordersView.getTableOrders().getSelectedRow());
                    ordersView.setOrder(new OrderBLL().findAll().get(ordersView.getOrderSelectedRow()));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                }
            });

            ordersView.getBtnOrder().addActionListener(e13 -> {
                OrderTable order = null;
                try {
                    order = new OrderBLL().makeOrder(ordersView.getClient(), ordersView.getProduct(), ordersView.getQuantity());
                } catch (DocumentException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (order != null) {
                    Orders o = new OrderBLL().findAll().get(ordersView.getModelOrders().getRowCount());
                    System.out.println(o);
                    try {
                        new OrderBLL().makePdf(order.toString(), o.getId());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (DocumentException ex) {
                        ex.printStackTrace();
                    }

                    ordersView.getModelOrders().addRow(new Object[]{order.getNameClient(), order.getAddress(), order.getEmail(),
                            order.getNameProduct(), order.getQuantity(), order.getPrice_buc()});

                }

                ordersView.getQuantity().setText("");

            });


            ordersView.getBtnDelete().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ordersView.getModelOrders().removeRow(ordersView.getTableOrders().getSelectedRow());
                    new OrderBLL().deleteOrder(ordersView.getOrder().getId());
                }
            });

            ordersView.getBack().addActionListener(e14 -> {
                ordersView.getFrame().dispose();
                MainView mainV = new MainView();
                Controller controller = new Controller();
                controller.Initialize(mainV);
            });


        });

    }

}
