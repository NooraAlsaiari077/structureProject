package main;

public class Product {

    private String id;
    private String name;
    private double price;
    private int stock;

    // List of reviews linked to this product
    private LinkedList<Review> reviews = new LinkedList<>();

    public Product(String id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }

    public void addReview(Review r) {
        reviews.insert(r);
    }

    // Used in getAverageRating() in DataHandle
    public double getAverageRating() {

    if (reviews.empty()) 
        return 0.0;

    double sum = 0;
    int count = 0;

    reviews.findFirst();
    while (!reviews.last()) {
        sum += reviews.retrieve().getRating();
        reviews.findNext();
        count++;
    }

    // for last element 
    sum += reviews.retrieve().getRating();
    count++;

    return sum / count;
}


    @Override
    public String toString() {
        return "Product{ ID=" + id + ", Name=" + name +
                ", Price=" + price + ", Stock=" + stock + " }";
    }
}
