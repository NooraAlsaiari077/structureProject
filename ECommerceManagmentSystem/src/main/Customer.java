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
  public AVL<Order> ordersAVL = new AVL<>();

    public void addOrder(Order o) {
    orders.insert(o);
    }

    public void showOrders() {
        if (orders.empty()) {
            System.out.println("No orders found for this customer.");
            return;
        }

        orders.findFirst(); 
        do {
            Order o = orders.retrieve();
            System.out.println("- Order ID: " + o.getId() + " | Total: " + o.getTotalPrice() +" | Date: " + o.getOrderDate() +" | Status: " + o.getStatus());
            if (!orders.last()){
                orders.findNext(); 
            }
        } while (!orders.last());
    }
        public String getId() {
    	return id; 
    	}
    
    public String getName() {
    	return name; 
    	}
    
    public String getEmail() {
    	return email;
    	}
    
    public LinkedList<Order> getOrders() { 
    	return orders; 
    	}

    public void setName(String name) {
    	this.name = name; 
    	}
    
    public void setEmail(String email) { 
    	this.email = email;
    	}

    

    @Override
    public String toString() {
        return "Customer ID=" + id + ", Name=" + name + ", Email=" + email;
    }
}


