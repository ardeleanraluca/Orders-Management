package dao;

import connection.ConnectionFactory;
import model.OrderTable;
import model.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class OrderTableDAO extends AbstractDAO<OrderTable>{


    private String joinHeader() {
        return "SELECT Client.id idClient, Client.name nameClient, Client.address address, Client.email email, Product.id idProduct, Product.name nameProduct, Orders.quantity, Product.price as price_buc FROM Orders,Client, Product WHERE Client.id = orders.idClient AND Product.id = idProduct";

    }

    private String createSelectJoin(int idC, int idP) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT Client.id idClient, Client.name nameClient, Client.address address, Client.email email, Product.id idProduct, Product.name nameProduct, Product.stock quantity, Product.price price_buc");
        sb.append(" FROM Client, Product WHERE Client.id = " + idC + " AND "+"Product.id = " +idP);
        return sb.toString();
    }

    public OrderTable ordersDates(int idC, int idP) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectJoin(idC, idP);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            resultSet = statement.executeQuery();

            return this.createObjects(resultSet).get(0);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, this.getClass().getName() + "DAO:selectJoin " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public List<OrderTable> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = joinHeader();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,  "DAO:findALLjoinafisare " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}
