package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataHandle {
	
    private LinkedList<Product> products = new LinkedList<>();
    private LinkedList<Customer> customers = new LinkedList<>();
    private LinkedList<Order> orders = new LinkedList<>();
    private LinkedList<Review> reviews = new LinkedList<>();
    private LinkedPQ<Product> productPriority = new LinkedPQ<>(); // for the top rated product we stored them using priorty queue
    private LinkedList<Order> orderStack = new LinkedList<>();	// for orders we stored them using stack 
    
    
    
    
    
  //Loading files and sending data to their classes ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    public void loadAllData() {
        loadProducts("products.csv");
        loadCustomers("customers.csv");
        loadOrders("orders.csv");
        loadReviews("reviews.csv");
        linkReviewsToProducts(); // linking reviews with product 
        linkOrdersToCustomers(); // linking orders with customers
        System.out.println("All data loaded and linked successfully!");
    }
    
    
    private void loadProducts(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // tis will skip the the titles of each column 
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length < 4) continue;
                // sending to class product the ID, name , price and their stock from the file
                Product p = new Product(d[0].trim(), d[1].trim(), Double.parseDouble(d[2].trim()), Integer.parseInt(d[3].trim()));
                products.add(p);
            }
        } catch (IOException e) {
            System.out.println("Error reading products.csv: " + e.getMessage());
        }
    }
    
    private void loadCustomers(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length < 3) continue;
                // sending to class customer the ID, name and their email from the file
                Customer c = new Customer(d[0].trim(), d[1].trim(), d[2].trim());
                customers.add(c);
            }
        } catch (IOException e) {
            System.out.println("Error reading customers.csv: " + e.getMessage());
        }
    }
    
    
    private void loadOrders(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length < 6) continue;
                // sending to class order the ID, custmer ID, prodect ID, order price, date and status from the file
                Order o = new Order( d[0].trim(), d[1].trim(), d[2].trim(), Double.parseDouble(d[3].trim()), d[4].trim(), d[5].trim());
                orders.add(o);
            }
        } catch (IOException e) {
            System.out.println("Error reading orders.csv: " + e.getMessage());
        }
    }
    
    
    private void loadReviews(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",", 5);
                if (d.length < 5) continue;
                // sending to class review the ID, custmer ID, prodect ID, rating and comment from the file
                Review r = new Review( d[0].trim(), d[1].trim(),d[2].trim(), Integer.parseInt(d[3].trim()), d[4].trim());
                reviews.add(r);
            }
        } catch (IOException e) {
            System.out.println("Error reading reviews.csv: " + e.getMessage());
        }
    }
    
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    //linking reviews to their product and linking custmers to thier orders ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    
    private void linkReviewsToProducts() {
        for (int i = 0; i < reviews.size(); i++) {
            Review r = reviews.get(i); // gets all data from review 
            Product p = findProductById(r.getProductId()); // finds the product id in the review and looks for it the the product class
            if (p != null) p.addReview(r);
        }
    }

    private void linkOrdersToCustomers() {
        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i); // gets all data from order 
            Customer c = findCustomerById(o.getCustomerId()); // finds the custmer id in the order and looks for it in the customer class
            if (c != null) c.addOrder(o);
        }
    }
    
  //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  //Product methods: add, remove, update, search, out of stock ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    
    
    public void addNewProduct(Product p) {
        products.add(p);
        System.out.println("Product added: " + p.getName());
    }
    
    public void removeProduct(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.remove(i);
                System.out.println("Product removed: " + id);
                return;
            }
        }
        System.out.println("Product not found.");
    }
    
    public void updateProduct(String id, double newPrice, int newStock) {
        Product p = findProductById(id);
        if (p != null) {
            p.setPrice(newPrice);
            p.setStock(newStock);
            System.out.println("Product updated: " + p.getName());
        } else System.out.println("Product not found.");
    }
    
    public Product findProductById(String id) {
        for (int i = 0; i < products.size(); i++)
            if (products.get(i).getId().equals(id)) return products.get(i);
        return null;
    }
    
    public void showOutOfStock() {
        System.out.println("Out-of-stock products:");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getStock() <= 0)
                System.out.println("- " + p.getName() + " (ID: " + p.getId() + ")");
        }
    }
    
    
  //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  //Custmor methods: register, order for customer, order history of customer ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    
    
    public void registerNewCustomer(Customer c) {
        Customer.add(c);
        System.out.println("New customer registered: " + c.getName());
    }  
    
    
    public void placeOrderForCustomer(String customerId, String productIds, double totalPrice, String date) {
        Customer c = findCustomerById(customerId);
        if (c == null) {
            System.out.println("Customer not found!");
            return;
        }

        String orderId = "O" + (orders.size() + 301);
        Order newOrder = new Order(orderId, customerId, productIds, totalPrice, date, "Pending");

        createOrder(newOrder);
        System.out.println("Order placed successfully for " + c.getName());
    }
    

  
    public void viewCustomerOrderHistory(String customerId) {
        Customer c = findCustomerById(customerId);
        if (c == null) {
            System.out.println("Customer not found!");
            return;
        }

        System.out.println("Order History for " + c.getName() + ":");
        LinkedList<Order> customerOrders = c.getOrders();

        if (customerOrders == null || customerOrders.size() == 0) {
            System.out.println("No orders found for this customer.");
            return;
        }

        for (int i = 0; i < customerOrders.size(); i++) {
            Order o = customerOrders.get(i);
            System.out.println("- Order ID: " + o.getId() + "\n- Products: " + o.getProductList() + "\n- Total: " + o.getTotalPrice() +
                    "\n- Date: " + o.getOrderDate() + "\n- Status: " + o.getStatus());
        }
    }
    
  //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  //Order methods: create order, cancel order, change status, search ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 
    
    public void createOrder(Order o) {
        orders.add(o);
        orderStack.add(o);
        Customer c = findCustomerById(o.getCustomerId());
        if (c != null) c.addOrder(o);


        String[] ids = o.getProductList().split(";"); //after every order we change the stock of the product
        for (int i = 0; i < ids.length; i++) {
            String pid = ids[i].trim();
            Product p = findProductById(pid);
            if (p != null) {
                p.setStock(p.getStock() - 1);
            }
        }
        System.out.println("Order created: " + o.getId());
    }
    
    
    public void cancelOrder(String id) {
        Order o = findOrderById(id);
        if (o != null) {
            if (o.getStatus().equalsIgnoreCase("Cancelled")) {
                System.out.println("Order already cancelled.");
                return;
            }
            o.setStatus("Cancelled");
            orderStack.add(o);//add it to a stack of caceled orders for undoing later

            String[] ids = o.getProductList().split(";");//after every cancle we restore the stock of the product
            for (int i = 0; i < ids.length; i++) {
                String pid = ids[i].trim();
                Product p = findProductById(pid);
                if (p != null) {
                    p.setStock(p.getStock() + 1);
                }
            }

            System.out.println("Order cancelled: " + id);
        } else {
            System.out.println("Order not found.");
        }
    }
    
    public void undoLastCancel() {
        if (orderStack.size() == 0) {
            System.out.println("Nothing to undo.");
            return;
        }

        Order last = orderStack.remove(orderStack.size() - 1);
        if ("Cancelled".equalsIgnoreCase(last.getStatus())) {
            last.setStatus("Pending");

            String[] ids = last.getProductList().split(";"); //change the stock of the product again
            for (int i = 0; i < ids.length; i++) {
                String pid = ids[i].trim();
                Product p = findProductById(pid);
                if (p != null) p.setStock(p.getStock() - 1);
            }
            System.out.println("Undo successful: " + last.getId());
        } else {
            System.out.println("Last order was not cancelled.");
        }
    }

    
    
    public void updateOrderStatus(String orderId, String newStatus) {
        Order o = findOrderById(orderId);
        if (o == null) {
            System.out.println("Order not found.");
            return;
        }

        String oldStatus = o.getStatus();
        o.setStatus(newStatus);

        System.out.println("Order status updated:");
        System.out.println("Order ID: " + o.getId());
        System.out.println("From: " + oldStatus + "  To: " + newStatus);
    }
    
    
    public Order findOrderById(String id) {
        for (int i = 0; i < orders.size(); i++)
            if (orders.get(i).getId().equals(id)) return orders.get(i);
        return null;
    }
    
    
  //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  //Review methods: add, edit, average ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
   
    
   
    public void addReview(Review r) {
        reviews.add(r);
        Product p = findProductById(r.getProductId());
        if (p != null) p.addReview(r);
        System.out.println("Review added for product ID: " + r.getProductId());
    }

    public void editReview(String id, int newRating, String newText) {
        Review r = findReviewById(id);
        if (r != null) {
            r.setRating(newRating);
            r.setComment(newText);
            System.out.println("Review updated: " + id);
        } else System.out.println("Review not found.");
    }

    public double getAverageRatingForProduct(String pid) {
        Product p = findProductById(pid);

        if (p != null) {
            return p.getAverageRating();
        } else {
            return 0.0;
        }
    }
    

    
  //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  //Extra Requirements0^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    
    
    public void getReviewsByCustomer(String customerId) {
        System.out.println("Reviews by Customer " + customerId + ":");
        boolean found = false;
        for (int i = 0; i < reviews.size(); i++) {
            Review r = reviews.get(i);
            if (r.getCustomerId().equals(customerId)) {
                Product p = findProductById(r.getProductId());
                System.out.println("- Product: " + p.getName() + "\n- Rating: " + r.getRating() + "\n- Comment: " + r.getComment());
                found = true;
            }
        }
        if (!found) System.out.println("No reviews found for this customer.");
    }
    
    public void Top3Products() {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            double avg = p.getAverageRating();
            productPriority.enqueue(p, avg);
        }
        System.out.println("Top 3 Products by Rating:");
        for (int i = 0; i < 3 && !productPriority.isEmpty(); i++) {
            Product top = productPriority.dequeue();
            System.out.println((i + 1) + ". " + top.getName() +
                    " (⭐ " + top.getAverageRating() + ")");
        }
    }
    
    public void OrdersBetweenDates(String start, String end) {
        System.out.println("Orders between " + start + " and " + end + ":");
        boolean found = false;
        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            String date = o.getOrderDate();
            if (date.compareTo(start) >= 0 && date.compareTo(end) <= 0) {
                System.out.println("- Order ID: " + o.getId() + "\n- Customer: " + o.getCustomerId() + "\n- Total: " + o.getTotalPrice() +
                                   "\n- Date: " + date + "\n- Status: " + o.getStatus());
                found = true;
            }
        }
        if (!found) System.out.println("No orders found between these dates.");
    }
    
    
    public void HighlyRatedProducts(String c1, String c2) {
        LinkedList<String> c1Products = new LinkedList<>();
        LinkedList<String> c2Products = new LinkedList<>();

        for (int i = 0; i < reviews.size(); i++) {
            Review r = reviews.get(i);
            if (r.getRating() > 4) {
                if (r.getCustomerId().equals(c1)) c1Products.add(r.getProductId());
                else if (r.getCustomerId().equals(c2)) c2Products.add(r.getProductId());
            }
        }

        System.out.println("Common Products reviewed > 4 stars by customers " + c1 + " & " + c2 + ":");
        boolean found = false;
        for (int i = 0; i < c1Products.size(); i++) {
            String pid = c1Products.get(i);
            for (int j = 0; j < c2Products.size(); j++) {
                if (pid.equals(c2Products.get(j))) {
                    Product p = findProductById(pid);
                    System.out.println("- " + (p != null ? p.getName() : pid));
                    found = true;
                }
            }
        }
        if (!found) System.out.println("No common products found with rating > 4 stars.");
    }
    

    
    
    
    public LinkedList<Product> getProducts() {
    	return products; 
    	}
    public LinkedList<Customer> getCustomers() {
    	return customers; 
    	}
    public LinkedList<Order> getOrders() {
    	return orders; 
    	}
    public LinkedList<Review> getReviews() { 
    	return reviews; 
    	}
    
    
    
    
    
    
    
}
