package main;

public class Review {

    private String id;
    private String customerId;
    private String productId;
    private int rating;
    private String comment;

    public Review(String id, String customerId, String productId, int rating, String comment) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getId() { return id; }
    public String getProductId() { return productId; }
    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }

    @Override
    public String toString() {
        return "Review{ ID=" + id + ", Product=" + productId +
                ", Customer=" + customerId + ", Rating=" + rating +
                ", Comment='" + comment + "' }";
    }
}
