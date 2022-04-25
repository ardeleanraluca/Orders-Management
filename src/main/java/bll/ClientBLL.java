package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import dao.OrderDAO;
import dao.OrderTableDAO;
import model.Client;
import model.OrderTable;
import model.Orders;

import javax.swing.*;

public class ClientBLL {

    private ClientDAO clientDAO;
    private OrderDAO orderDAO;

    private List<Validator<Client>> validators;

    public ClientBLL() {

        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());

        clientDAO = new ClientDAO();
        orderDAO = new OrderDAO();
    }


    /**
     * insert a client in Clients Table
     * @param c
     * @return the client who was inserted
     */
    public Client insert(Client c) {
        Client st = clientDAO.insert(c);
        if (st == null) {
            throw new NoSuchElementException("The client was not inserted!");
        }
        return st;
    }

    /**
     * update a client in Clients Table
     * @param c
     * @return the client after update
     */
    public Client update(Client c) {
        Client st = clientDAO.update(c);
        if (st == null) {
            throw new NoSuchElementException("The client was not updated!");
        }
        return st;
    }

    /**
     * Delete a client with id = id from Clients Table
     * @param id
     * @return the client who was deleted
     */

    public Client deleteClientById(int id) {
        Client st = null;
        st = clientDAO.deleteById(id);
        if (st == null) {
            throw new NoSuchElementException("The client with id = " + id + " was not deleted!");
        }
        return st;
    }


    /**
     * Take all orders from Clients table
     * @return a list with all clients from table
     */
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        clients = clientDAO.findAll();
        if (clients == null) {
            throw new NoSuchElementException("nu sunt clienti");
        }
        return clients;
    }

    public List<Validator<Client>> getValidators() {
        return validators;
    }
}
