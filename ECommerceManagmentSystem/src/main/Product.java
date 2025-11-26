package main;

public class Product {
    private String id;
    private String name;
    private double price;
    private int stock;
    private LinkedList<Review> reviews = new LinkedList<>();

    public Product(String id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    
    public AVL<Review> reviewsAVL = new AVL<>();

    public int getReviewCount() {
        return reviewsAVL.toArrayList().size();
    }


    public void addReview(Review r) {
        reviews.insert(r);
    }


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
    	
    	sum += reviews.retrieve().getRating();
    	count++;

    	return sum / count;
    }


    public String getId() { 
    	return id; 
    	}
    
    public String getName() { 
    	return name; 
    	}
    
    public double getPrice() { 
    	return price; 
    	}
    
    public int getStock() { 
    	return stock; 
    	}
    
    public LinkedList<Review> getReviews() { 
    	return reviews; 
    	}

    public void setPrice(double price) { 
    	this.price = price; 
    	}
    
    public void setStock(int stock) { 
    	this.stock = stock; 
    	}
    
    @Override
    public String toString() {
        return "Product{ ID=" + id + ", Name=" + name +
                ", Price=" + price + ", Stock=" + stock + " }";
    }
}


