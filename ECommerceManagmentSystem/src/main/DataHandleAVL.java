package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class DataHandleAVL {
	
	private AVL<Product> productsAVL = new AVL<>();
    private AVL<Customer> customersAVL = new AVL<>();
    private AVL<Order> ordersAVL = new AVL<>();
    private AVL<Customer> customersByNameAVL = new AVL<>();

    // LOAD ALL DATA^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    public void loadAllData() {
        loadProducts();
        loadCustomers();
        loadOrders();
        loadReviews();
        System.out.println("All data loaded successfully (AVL version).");
    }

    private void loadProducts() {//PRODUCTS
        try (BufferedReader br = new BufferedReader(new FileReader("products.csv"))) {
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");

                Product p = new Product(
                    d[0].trim(),
                    d[1].trim(),
                    Double.parseDouble(d[2].trim()),
                    Integer.parseInt(d[3].trim())
                );

                int key = Integer.parseInt(p.getId());
                productsAVL.insert(key, p);
            }
        } catch (Exception e) {
            System.out.println("Error loading products.");
        }
    }


    private void loadCustomers() {//CUSTOMERS
        try (BufferedReader br = new BufferedReader(new FileReader("customers.csv"))) {
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");

                Customer c = new Customer(
                    d[0].trim(),
                    d[1].trim(),
                    d[2].trim()
                );

                int key = Integer.parseInt(c.getId());
                customersAVL.insert(key, c);

                int nameKey = c.getName().toLowerCase().hashCode();
                customersByNameAVL.insert(nameKey, c);
            }
        } catch (Exception e) {
            System.out.println("Error loading customers.");
        }
    }

    private void loadOrders() {//ORDERS
        try (BufferedReader br = new BufferedReader(new FileReader("orders.csv"))) {
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");

                Order o = new Order(
                    d[0].trim(),
                    d[1].trim(),
                    d[2].trim(),
                    Double.parseDouble(d[3].trim()),
                    d[4].trim(),
                    d[5].trim()
                );

                int key = Integer.parseInt(o.getId());
                ordersAVL.insert(key, o);

                // add to customer orders AVL
                Customer c = findCustomerById(o.getCustomerId());
                if (c != null) {
                    int orderKey = Integer.parseInt(o.getId());
                    c.ordersAVL.insert(orderKey, o);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading orders.");
        }
    }


    private void loadReviews() {//REVIEWS
        try (BufferedReader br = new BufferedReader(new FileReader("reviews.csv"))) {
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",", 5);

                Review r = new Review(
                    d[0].trim(),
                    d[1].trim(),
                    d[2].trim(),
                    Integer.parseInt(d[3].trim()),
                    d[4].trim()
                );
             // add to products reviews AVL
                Product p = findProductById(r.getProductId());
                if (p != null) {
                    int reviewKey = Integer.parseInt(r.getId());
                    p.reviewsAVL.insert(reviewKey, r);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading reviews.");
        }
    }


    // FIND METHODS^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    public Product findProductById(String id) {
        int key = Integer.parseInt(id);
        if (productsAVL.findkey(key)) return productsAVL.retrieve();
        return null;
    }

    public Customer findCustomerById(String id) {
        int key = Integer.parseInt(id);
        if (customersAVL.findkey(key)) return customersAVL.retrieve();
        return null;
    }

    public Order findOrderById(String id) {
        int key = Integer.parseInt(id);
        if (ordersAVL.findkey(key)) return ordersAVL.retrieve();
        return null;
    }


    //PRODUCTS IN A PRICE RANGE^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    public void listProductsInPriceRange(double min, double max) {
        ArrayList<Product> list = productsAVL.toArrayList();

        System.out.println("Products within price range:");
        for (Product p : list) {
            if (p.getPrice() >= min && p.getPrice() <= max)
                System.out.println(p);
        }
    }


    //ORDERS BETWEEN DATES^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    public void listOrdersBetweenDates(String start, String end) {
        ArrayList<Order> list = ordersAVL.toArrayList();

        System.out.println("Orders between dates:");
        for (Order o : list) {
            if (o.getOrderDate().compareTo(start) >= 0 &&
                o.getOrderDate().compareTo(end) <= 0)
                System.out.println(o);
        }
    }


    // LIST CUSTOMERS ALPHABETICALLY^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    public void listCustomersAlphabetically() {
        ArrayList<Customer> list = customersByNameAVL.toArrayList();

        System.out.println("Customers A → Z:");
        for (Customer c : list)
            System.out.println(c);
    }


    //LIST CUSTOMERS WHO REVIEWED A PRODUCT^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    public void listCustomersWhoReviewedProduct(String pid) {
        Product p = findProductById(pid);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }

        ArrayList<Review> reviews = p.reviewsAVL.toArrayList();

        System.out.println("Customers who reviewed product:");
        for (Review r : reviews) {
            Customer c = findCustomerById(r.getCustomerId());
            if (c != null)
                System.out.println(c + " | Rating: " + r.getRating());
        }
    }

    // TOP 3 MOST REVIEWED PRODUCTS using RECURSIVE^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    // --- helper class
    private class TopThree {
        Product first = null;
        Product second = null;
        Product third = null;

        void consider(Product p) {
            int count = p.getReviewCount();

            if (first == null || count > first.getReviewCount()) {
                third = second;
                second = first;
                first = p;
            }
            else if (second == null || count > second.getReviewCount()) {
                third = second;
                second = p;
            }
            else if (third == null || count > third.getReviewCount()) {
                third = p;
            }
        }
    }


    private void findTop3Recursive(AVLNode<Product> node, TopThree top) {
        if (node == null) return;

        findTop3Recursive(node.left, top);
        top.consider(node.data);
        findTop3Recursive(node.right, top);
    }

    public void top3MostReviewed() {
        TopThree top = new TopThree();
        findTop3Recursive(productsAVL.root, top);

        System.out.println("Top 3 Most Reviewed Products:");

        if (top.first != null)
            System.out.println("1. " + top.first);

        if (top.second != null)
            System.out.println("2. " + top.second);

        if (top.third != null)
            System.out.println("3. " + top.third);
    }
}
