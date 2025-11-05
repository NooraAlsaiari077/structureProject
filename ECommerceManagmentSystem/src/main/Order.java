package main;

public class Order {

    private String id;
    private String customerId;
    private String productList; // stored as "P01;P05;P09"
    private double totalPrice;
    private String orderDate;
    private String status;

    public Order(String id, String customerId, String productList, double totalPrice, String orderDate, String status) {
        this.id = id;
        this.customerId = customerId;
        this.productList = productList;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
    }

    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public String getProductList() { return productList; }
    public double getTotalPrice() { return totalPrice; }
    public String getOrderDate() { return orderDate; }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }
}
