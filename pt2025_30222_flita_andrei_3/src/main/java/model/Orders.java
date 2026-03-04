package model;

public class Orders {
    private int id;
    private int client_id;
    private int product_id;
    private int quantity;
    private String is_completed;
    private String order_date;

    public Orders() {

    }

    public Orders(int id, int client_id, int product_id, int quantity, String is_completed, String order_date) {
        super();
        this.id = id;
        this.client_id = client_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.is_completed = is_completed;
        this.order_date = order_date;
    }

    public Orders(int client_id, int product_id, int quantity, String is_completed, String order_date) {
        super();
        this.client_id = client_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.is_completed = is_completed;
        this.order_date = order_date;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getClient_id() {
        return client_id;
    }
    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }
    public int getProduct_id() {
        return product_id;
    }
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getIs_completed() {
        return is_completed;
    }
    public void setIs_completed(String is_completed) {
        this.is_completed = is_completed;
    }
    public String getOrder_date() {
        return order_date;
    }
    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    @Override
    public String toString() {
        return "Orders [id=" + id + ", client_id=" + client_id + ", product_id=" + product_id + ", quantity=" + quantity + ", is_completed=" + is_completed + ", order_date=" + order_date + "]";
    }
}

