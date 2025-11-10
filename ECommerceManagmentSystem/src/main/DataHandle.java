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
    private Stack<Order> orderStack = new Stack<>();	// for orders we stored them using stack 
    
    
    
    
    
  //Loading files and sending data to their classes ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    public void loadAllData() {
        loadProducts();
        loadCustomers();
        loadOrders();
        loadReviews();
        linkReviewsToProducts(); // linking reviews with product 
        linkOrdersToCustomers(); // linking orders with customers
        System.out.println("All data loaded");
    }
    
    
    private void loadProducts() {
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\UNI\\JavaWorkShop\\ECommerceManagmentSystem\\src\\main\\products.csv"))) {
            String line;
            br.readLine(); // tis will skip the the titles of each column 
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length < 4) continue;
                // sending to class product the ID, name , price and their stock from the file
                Product p = new Product(d[0].trim(), d[1].trim(), Double.parseDouble(d[2].trim()), Integer.parseInt(d[3].trim()));
                products.insert(p);
            }
        } catch (IOException e) {
            System.out.println("Error reading products.csv: " + e.getMessage());
        }
    }
    
    private void loadCustomers() {
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\UNI\\JavaWorkShop\\ECommerceManagmentSystem\\src\\main\\customers.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length < 3) continue;
                // sending to class customer the ID, name and their email from the file
                Customer c = new Customer(d[0].trim(), d[1].trim(), d[2].trim());
                customers.insert(c);
            }
        } catch (IOException e) {
            System.out.println("Error reading customers.csv: " + e.getMessage());
        }
    }
    
    
    private void loadOrders() {
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\UNI\\JavaWorkShop\\ECommerceManagmentSystem\\src\\main\\orders.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length < 6) continue;
                // sending to class order the ID, custmer ID, prodect ID, order price, date and status from the file
                Order o = new Order( d[0].trim(), d[1].trim(), d[2].trim(), Double.parseDouble(d[3].trim()), d[4].trim(), d[5].trim());
                orders.insert(o);
            }
        } catch (IOException e) {
            System.out.println("Error reading orders.csv: " + e.getMessage());
        }
    }
    
    
    private void loadReviews() {
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\UNI\\JavaWorkShop\\ECommerceManagmentSystem\\src\\main\\reviews.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",", 5);
                if (d.length < 5) continue;
                // sending to class review the ID, custmer ID, prodect ID, rating and comment from the file
                Review r = new Review( d[0].trim(), d[1].trim(),d[2].trim(), Integer.parseInt(d[3].trim()), d[4].trim());
                reviews.insert(r);
            }
        } catch (IOException e) {
            System.out.println("Error reading reviews.csv: " + e.getMessage());
        }
    }
    
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    //linking reviews to their product and linking custmers to thier orders ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    
    private void linkReviewsToProducts() {
        if (reviews.empty()) return;
        reviews.findFirst();
        while (!reviews.last()) {
            Review r = reviews.retrieve();
            Product p = findProductById(r.getProductId());
            if (p != null) {
                p.getReviews().insert(r);
            }
            reviews.findNext(); 
        }
        Review r = reviews.retrieve();
        Product p = findProductById(r.getProductId());
        if (p != null) {
            p.getReviews().insert(r);
        }
    }
    

    private void linkOrdersToCustomers() {
        if (orders.empty()) return;
        orders.findFirst();
        while (!orders.last()) {
            Order o = orders.retrieve();
            Customer c = findCustomerById(o.getCustomerId());
            if (c != null) {
                c.getOrders().insert(o);
            }
            orders.findNext();
        }
        Order o = orders.retrieve();
        Customer c = findCustomerById(o.getCustomerId());
        if (c != null) {
            c.getOrders().insert(o);
        }
    }
    
  //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  //Product methods: add, remove, update, search, out of stock ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    
    
    public void addNewProduct(Product p) {
    	products.insert(p);
        System.out.println("Product added: " + p.getName());
    }
    
    public void removeProduct(String id) {
        if (products.empty()) {
            System.out.println("No products available.");
            return;
        }

        products.findFirst();
        boolean found = false;
        while (!products.last()) {
            Product p = products.retrieve();
            if (p.getId().equals(id)) {
                products.remove();
                System.out.println("Product removed: " + id);
                found = true;
                break;
            }
            products.findNext();
        }
        if (!found) {
            Product p = products.retrieve();
            if (p.getId().equals(id)) {
                products.remove();
                System.out.println("Product removed: " + id);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Product not found: " + id);
        }
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
        if (products.empty()) {
            return null;
        }
        products.findFirst();
        while (!products.last()) { 
            Product p = products.retrieve();
            if (p.getId().equals(id)) {
                return p;
            }
            products.findNext();  
        }

        Product p = products.retrieve();
        if (p.getId().equals(id)) {
            return p;
        }

        return null;  
    }
    
    public void showOutOfStock() {
        System.out.println("Out of stock products:");

        if (products.empty()) {
            System.out.println("No products available.");
            return;
        }
        boolean found = false;
        products.findFirst();
        while (!products.last()) {  
            Product p = products.retrieve();
            if (p.getStock() <= 0) {
                System.out.println("ID: " + p.getId() + ", product name: "+ p.getName() );
                found = true;
            }
            products.findNext();
        }
        Product p = products.retrieve();
        if (p.getStock() <= 0) {
            System.out.println("ID: " + p.getId() + ", product name: "+ p.getName());
            found = true;
        }

        if (!found) {
            System.out.println("All products are in stock.");
        }
    }

    
    
  //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  //Custmor methods: register, order for customer, order history of customer ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    
    
    public void registerNewCustomer(Customer c) {
        customers.insert(c);
        System.out.println("New customer registered: " + c.getName());
    }  
    
    
    public void placeOrderForCustomer(String cid, Order o) {
        Customer c = findCustomerById(cid);
        if (c == null) {
            System.out.println("Customer not found!");
            return;
        }

        orders.insert(o);
        c.getOrders().insert(o);
        System.out.println("Order placed successfully for " + c.getName());
    }
    

  
    public void viewCustomerOrderHistory(String customerId) {
        Customer c = findCustomerById(customerId);
        if (c == null) {
            System.out.println("Customer not found!");
            return;
        }

        System.out.println("Order History for " + c.getName() + ":");
        
        LinkedList<Order> list = c.getOrders();

        if (list.empty()) {
            System.out.println("No orders found.");
            return;
        }

        list.findFirst();
        while (!list.last()) {
            Order o = list.retrieve();
            System.out.println("Order ID: " + o.getId() + " | Status: " + o.getStatus());
            list.findNext();
        }
        Order o = list.retrieve();
        System.out.println("Order ID: " + o.getId() + " | Status: " + o.getStatus());
    }
    
    
    public Customer findCustomerById(String id) {
    	if (customers.empty()) {
            return null;
        }
        customers.findFirst();
        while (!customers.last()) {
            Customer c = customers.retrieve();
            if (c.getId().equals(id)) {
                return c; 
            }
            customers.findNext();
        }

        Customer c = customers.retrieve();
        if (c.getId().equals(id)) {
            return c;
        }

        return null; 
    }

    
  //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  //Order methods: create order, cancel order, change status, search ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 
    
    public void createOrder(Order o) {
    	orders.insert(o);
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
            orderStack.push(o);//add it to a stack of caceled orders for undoing later

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
        if (orderStack.empty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        Order last = orderStack.pop();
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
        if (orders.empty()) {
            return null;
        }
        orders.findFirst();
        while (!orders.last()) {
            Order o = orders.retrieve();
            if (o.getId().equals(id)) {
                return o;
            }
            orders.findNext();
        }

        Order o = orders.retrieve();
        if (o.getId().equals(id)) {
            return o;
        }

        return null;
    }
    
    
  //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  //Review methods: add, edit, average ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
   
    
   
    public void addReview(Review r) {
        reviews.insert(r);
        Product p = findProductById(r.getProductId());
        if (p != null) p.getReviews().insert(r);
        System.out.println("Review added for product ID: " + r.getProductId());
    }

    public void editReview(String id, int newRating, String newText) {
        if (reviews.empty()) {
            System.out.println("No reviews available.");
            return;
        }

        boolean found = false;
        reviews.findFirst();

        while (!reviews.last()) {
            Review r = reviews.retrieve();
            if (r.getId().equals(id)) {
                r.setRating(newRating);
                r.setComment(newText);
                System.out.println("Review updated: " + id);
                found = true;
                break;
            }
            reviews.findNext();
        }

        if (!found) {
            Review r = reviews.retrieve();
            if (r.getId().equals(id)) {
                r.setRating(newRating);
                r.setComment(newText);
                System.out.println("Review updated: " + id);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Review not found: " + id);
        }}


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

        if (reviews.empty()) {
            System.out.println("No reviews available.");
            return;
        }

        boolean found = false;
        reviews.findFirst();
        while (!reviews.last()) {
            Review r = reviews.retrieve();

            if (r.getCustomerId().equals(customerId)) {
                Product p = findProductById(r.getProductId());
                System.out.println("- Product: " + p.getName());
                System.out.println("  Rating: " + r.getRating());
                System.out.println("  Comment: " + r.getComment());
                System.out.println("--------------------------------");
                found = true;
            }
            reviews.findNext();
        }
        Review r = reviews.retrieve();
        if (r.getCustomerId().equals(customerId)) {
            Product p = findProductById(r.getProductId());
            System.out.println("- Product: " + p.getName());
            System.out.println("  Rating: " + r.getRating());
            System.out.println("  Comment: " + r.getComment());
            System.out.println("--------------------------------");
            found = true;
        }

        if (!found) {
            System.out.println("No reviews found for this customer.");
        }
    }

    
    public void Top3Products() {
        if (products.empty()) {
            System.out.println("No products available.");
            return;
        }

        products.findFirst();
        while (!products.last()) {
            Product p = products.retrieve();
            float avg = (float) p.getAverageRating();
            productPriority.enqueue(p, avg);
            products.findNext();
        }

        Product p = products.retrieve();
        float avg = (float) p.getAverageRating();
        productPriority.enqueue(p, avg);


        System.out.println("Top 3 Products by Rating");
        for (int i = 0; i < 3; i++) {
            PQElement<Product> top = productPriority.dequeue();
            if (top == null) break;
            System.out.println((i + 1) + ". " + top.data.getName() + " | Rating: " + top.priority);
        }
    }

    
    public void OrdersBetweenDates(String start, String end) {
        System.out.println("Orders between " + start + " and " + end + ":");

        if (orders.empty()) {
            System.out.println("No orders available.");
            return;
        }

        boolean found = false;
        orders.findFirst();
        while (!orders.last()) {
            Order o = orders.retrieve();
            if (o.getOrderDate().compareTo(start) >= 0 &&
                o.getOrderDate().compareTo(end) <= 0) {

                System.out.println("- Order ID: " + o.getId());
                System.out.println("- Customer: " + o.getCustomerId());
                System.out.println("- Total: " + o.getTotalPrice());
                System.out.println("- Date: " + o.getOrderDate());
                System.out.println("- Status: " + o.getStatus());
                System.out.println("-------------------");
                found = true;
            }
            orders.findNext();
        }
        Order o = orders.retrieve();
        if (o.getOrderDate().compareTo(start) >= 0 &&
            o.getOrderDate().compareTo(end) <= 0) {

            System.out.println("- Order ID: " + o.getId());
            System.out.println("- Customer: " + o.getCustomerId());
            System.out.println("- Total: " + o.getTotalPrice());
            System.out.println("- Date: " + o.getOrderDate());
            System.out.println("- Status: " + o.getStatus());
            System.out.println("-------------------");
            found = true;
        }

        if (!found) {
            System.out.println("No orders found between these dates.");
        }
    }

    
    
    public void HighlyRatedProducts(String c1, String c2) {
        LinkedList<String> common = new LinkedList<>();

        if (reviews.empty()) {
            System.out.println("No reviews available.");
            return;
        }

        reviews.findFirst();
        while (!reviews.last()) {
            Review r = reviews.retrieve();
            if ((r.getCustomerId().equals(c1) || r.getCustomerId().equals(c2)) && r.getRating() > 4) {
                common.insert(r.getProductId());
            }
            reviews.findNext();
        }
        Review r = reviews.retrieve();
        if ((r.getCustomerId().equals(c1) || r.getCustomerId().equals(c2)) && r.getRating() > 4) {
            common.insert(r.getProductId());
        }

        System.out.println("Common Products reviewed > 4 stars by customers " + c1 + " & " + c2 + ":");

        if (common.empty()) {
            System.out.println("None found.");
        } else {
            common.findFirst();
            while (!common.last()) {
                System.out.println("- Product ID: " + common.retrieve());
                common.findNext();
            }
            System.out.println("- Product ID: " + common.retrieve());
        }
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
