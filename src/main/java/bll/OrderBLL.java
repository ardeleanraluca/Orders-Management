package bll;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dao.OrderDAO;
import dao.OrderTableDAO;
import model.Client;
import model.OrderTable;
import model.Orders;
import model.Product;

import org.w3c.dom.Text;

import javax.swing.*;
import javax.swing.text.StyleConstants;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class OrderBLL {

    private OrderDAO orderDAO;
    private OrderTableDAO orderTableDAO;

    public OrderBLL() {
        orderDAO = new OrderDAO();
        orderTableDAO = new OrderTableDAO();
    }

    /**
     * Take all orders
     *
     * @return a list with all orders from Orders Table
     */
    public List<Orders> findAll() {
        List<Orders> orders = new ArrayList<>();
        orders = orderDAO.findAll();
        if (orders == null) {
            throw new NoSuchElementException("There are no orders");
        }
        return orders;
    }


    /**
     * take the client and the product which were selected and add in table. After that a pdf is generated
     *
     * @param c
     * @param p
     * @param quatity
     * @return an orderTable object which is my table model
     * @throws DocumentException
     * @throws IOException
     */
    public OrderTable makeOrder(Client c, Product p, JTextField quatity) throws DocumentException, IOException {

        if (c == null || p == null || Objects.equals(quatity.getText(), "")) {
            JOptionPane.showMessageDialog(null, "Insuficiente date pentru comanda");
            return null;
        } else if (Integer.parseInt(quatity.getText()) < 0) {
            JOptionPane.showMessageDialog(null, "Cantitate nevalida");
            return null;
        }

        int q = Integer.parseInt(quatity.getText());

        OrderTable orderTable = null;
        Orders order = null;
        if (q <= p.getStock()) {
            p.setStock(p.getStock() - q);
            orderTable = orderTableDAO.ordersDates(c.getId(), p.getId());
            new ProductBLL().update(p);
            orderTable.setQuantity(q);
            orderTable.setPrice_buc(orderTable.getPrice_buc());
            order = new Orders(c.getId(), p.getId(), q);

            orderDAO.insert(order);
        } else
            JOptionPane.showMessageDialog(null, "Stoc insuficient");

        return orderTable;

    }

    /**
     * create pdf of an order
     *
     * @param content
     * @param i
     * @throws IOException
     * @throws DocumentException
     */
    public void makePdf(String content, int i) throws IOException, DocumentException {
        Document doc = new Document();
        try {

            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("Bill_" + i + ".pdf"));
            System.out.println("PDF created.");

            Font fontTitle = FontFactory.getFont(FontFactory.COURIER_BOLDOBLIQUE, 20, BaseColor.BLUE);
            Font fontText = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);

            Chunk Title = new Chunk("BILL id: " + i, fontTitle);
            Chunk text = new Chunk(content, fontText);

            doc.open();

            doc.add(new Paragraph(Title));
            doc.add(new Paragraph(text));
            doc.close();

            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Orders deleteOrder(int id) {
        Orders st = null;
        st = orderDAO.deleteById(id);
        if (st == null) {
            throw new NoSuchElementException("The order with id = " + id + " was not deleted!");
        }
        return st;
    }


}
