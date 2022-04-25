package model;

public class OrderTable {
    private String nameClient;
    private String address;
    private String email;
    private String nameProduct;
    private int quantity;
    private float price_buc;

    public OrderTable() {

    }

    public OrderTable(String nameClient, String address, String email, String nameProduct, int quantity, float price_buc) {
        this.nameClient = nameClient;
        this.address = address;
        this.email = email;
        this.nameProduct = nameProduct;
        this.quantity = quantity;
        this.price_buc = price_buc;
    }


    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice_buc() {
        return price_buc;
    }

    public void setPrice_buc(float price_buc) {
        this.price_buc = price_buc;
    }

    @Override
    public String toString() {
        return "\n\nClient: " + nameClient + "\n" +
                "Address: " + address + "\n" +
                "Email: " + email + "\n" +
                "Name Product: " + nameProduct + "\n" +
                "Quantity: " + quantity + "\n" +
                "Price/buc: " + price_buc + "\n" +
                "Total Price: " + price_buc * quantity;
    }
}


