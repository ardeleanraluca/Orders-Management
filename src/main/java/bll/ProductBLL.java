package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import dao.ProductDAO;
import model.Product;

public class ProductBLL {

    private ProductDAO productDAO;

    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    /**
     * insert a product in Products Table
     * @param p
     * @return the product who was inserted
     */
    public Product insert(Product p) {
        Product st = productDAO.insert(p);
        if (st == null) {
            throw new NoSuchElementException("The product was not inserted!");
        }
        return st;
    }


    /**
     * Take all products from Clients table
     * @return a list with all products from table
     */
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        products = productDAO.findAll();
        if (products == null) {
            throw new NoSuchElementException("Doesn't exist products!");
        }
        return products;
    }

    /**
     * update a product in Products Table
     * @param
     * @return the product after update
     */
    public Product update(Product p) {
        Product st = new Product();
        System.out.println(p.getStock());
        st = productDAO.update(p);

        if (st == null) {
            throw new NoSuchElementException("The client was not updated!");
        }

        return st;
    }

    /**
     * Delete a product with id = id from Clients Table
     * @param id
     * @return the product who was deleted
     */
    public Product deleteProductById(int id) {
        Product st = productDAO.deleteById(id);
        if (st == null) {
            throw new NoSuchElementException("The product with id = " + id + " was not deleted!");
        }
        return st;
    }

}
