package main;

public class Customer {

    private String id;
    private String name;
    private String email;

    private LinkedList<Order> orders = new LinkedList<>();

    public Customer(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void addOrder(Order o) {
    orders.insert(o);
    }

    public LinkedList<Order> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "Customer{ ID=" + id + ", Name=" + name + ", Email=" + email + " }";
    }
}
