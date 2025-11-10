package main;

import java.util.Scanner;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        DataHandle data = new DataHandle();
        data.loadAllData();

        System.out.println("---------Ecommerce Managment System.---------");


        boolean working = true;

        while (working) {
        	System.out.println("Are you:");
        	System.out.println("1. A Manager");
        	System.out.println("2. A Cutomer");
        	System.out.println("3. Exit");
        	
            int choiceMain = Integer.parseInt(sc.nextLine());
            System.out.println("------------------------------------");

            switch (choiceMain) {
            
	            case 1:
	            	boolean workingMan = true;
	            	while (workingMan) {
	            		System.out.println("1. Products");
	            		System.out.println("2. Undo last cancelled order");
	            		System.out.println("3. View customer order history");
	            		System.out.println("4. Show orders between dates");
	            		System.out.println("5. Show common high-rated products (> 4 stars)");
	                    System.out.println("6. Show reviews by specific customer");
	                    System.out.println("7. Back");
	                    
	                    int choiceMan = Integer.parseInt(sc.nextLine());
	                    System.out.println("------------------------------------");
	                    
	                    switch (choiceMan) {
	                    case 1:
	    	            	boolean workingPro = true;
	    	            	while (workingPro) {
	    	            		System.out.println("1. Add new product");
	    	            		System.out.println("2. Remove a product");
	    	            		System.out.println("3. Update a product");
	    	            		System.out.println("4. Back");
	    	                    int choicePro = Integer.parseInt(sc.nextLine());
	    	                    System.out.println("------------------------------------");
	    	                    
	    	                    switch (choicePro) {
	    	                    case 1: 
	    	                        System.out.print("Enter Product ID: ");
	    	                        String pid = sc.nextLine();
	    	                        System.out.print("Enter Product Name: ");
	    	                        String pname = sc.nextLine();
	    	                        System.out.print("Enter Product Price: ");
	    	                        double price = Double.parseDouble(sc.nextLine());
	    	                        System.out.print("Enter Product Stock: ");
	    	                        int stock = Integer.parseInt(sc.nextLine());

	    	                        Product p = new Product(pid, pname, price, stock);
	    	                        data.addNewProduct(p);
	    	                        System.out.println("Product added successfully.");
	    	                        System.out.println(data.findProductById(pid));
	    	                        break;
	    	                        
	    	                    case 2:
	    	                    	System.out.print("Enter Product ID: ");
		    	                    String prid = sc.nextLine();
		    	                    System.out.println(data.findProductById(prid));
		    	                    data.removeProduct(prid);
		    	                    System.out.println("Product removed successfully.");
		    	                    break;
		    	                    
	    	                    case 3: 
	    	                        System.out.print("Enter Product ID: ");
	    	                        String puid = sc.nextLine();
	    	                        System.out.print("Enter Product New Price: ");
	    	                        double purice = Double.parseDouble(sc.nextLine());
	    	                        System.out.print("Enter Product New Stock: ");
	    	                        int sutock = Integer.parseInt(sc.nextLine());
	    	                        System.out.print("Before updating: ");
	    	                        System.out.println(data.findProductById(puid));
	    	                        data.updateProduct(puid, purice, sutock);
	    	                        System.out.print("After updating: ");
	    	                        System.out.println(data.findProductById(puid));
	    	                        break;
	    	                        
	    	                    case 4:
	    	                    	workingPro = false;
	    	                    	break;
	    	                    default:
	    	                        System.out.println("Invalid choice. Please try again.");
 	
	    	                    }
	    	                    }
	                    	break;
	                        
	                    case 2: 
	                        data.undoLastCancel();
	                        break;
	                        
	                    case 3:
	                        System.out.print("Enter Customer ID: ");
	                        String histCid = sc.nextLine();
	                        data.viewCustomerOrderHistory(histCid);
	                        break;
	                        
	                    case 4: 
	                        System.out.print("Enter Start Date (YYYY-MM-DD): ");
	                        String start = sc.nextLine();
	                        System.out.print("Enter End Date (YYYY-MM-DD): ");
	                        String end = sc.nextLine();
	                        data.OrdersBetweenDates(start, end);
	                        break;
	                        
	                    case 5: 
	                        System.out.print("Enter First Customer ID: ");
	                        String c1 = sc.nextLine();
	                        System.out.print("Enter Second Customer ID: ");
	                        String c2 = sc.nextLine();
	                        data.HighlyRatedProducts(c1, c2);
	                        break;
	                        
	                    case 6:
	                        System.out.print("Enter Customer ID: ");
	                        String revCid = sc.nextLine();
	                        data.getReviewsByCustomer(revCid);
	                        break;
	                        
	                    case 7: 
	                    	workingMan = false;
	                    	break;
	                    	
	                    default:
	                        System.out.println("Invalid choice. Please try again.");
      
	                    }
	            	}
	            	
	            	break;


                case 2:
	            	boolean workingCus = true;
	            	while (workingCus) {
	            		System.out.println("1. Register new customer");
	                    System.out.println("2. Place new order");
	                    System.out.println("3. Cancel an order");
	                    System.out.println("4. Add a review");
	                    System.out.println("5. View customer order history");
	                    System.out.println("6. Show top 3 rated products");
	                    System.out.println("7. Show out-of-stock products");
	                    System.out.println("8. Back");
	                    
	                    int choiceCus = Integer.parseInt(sc.nextLine());
	                    System.out.println("------------------------------------");
	                    
	                    switch (choiceCus) {
	                    
	                    case 1: 
	    	                System.out.print("Enter your ID: ");
	    	                String cid = sc.nextLine();
	    	                System.out.print("Enter your Name: ");
	    	                String cname = sc.nextLine();
	    	                System.out.print("Enter your Email: ");
	    	                String cemail = sc.nextLine();
	    	
	    	                Customer c = new Customer(cid, cname, cemail);
	    	                data.registerNewCustomer(c);
	    	                System.out.println("Customer registered successfully.");
	    	                System.out.println("ID:"+cid+" | Name:"+cname+" | Email:"+cemail );
	    	                break;
	    	                
	                    case 2: 
	                        System.out.print("Enter Order ID: ");
	                        String oid = sc.nextLine();
	                        System.out.print("Enter Customer ID: ");
	                        String ocid = sc.nextLine();
	                        System.out.print("Enter Product IDs (separate by ';'): ");
	                        String plist = sc.nextLine();
	                        System.out.print("Enter Total Price: ");
	                        double total = Double.parseDouble(sc.nextLine());
	                        System.out.print("Enter Order Date (YYYY-MM-DD): ");
	                        String date = sc.nextLine();

	                        Order order = new Order(oid, ocid, plist, total, date, "Pending");
	                        data.createOrder(order);
	                        System.out.println("Order placed successfully.");
	                        break;
	                        
	                    case 3: 
	                        System.out.print("Enter Order ID to cancel: ");
	                        String cancelId = sc.nextLine();
	                        data.cancelOrder(cancelId);
	                        break;
	                        
	                    case 4: 
	                        System.out.print("Enter Review ID: ");
	                        String rid = sc.nextLine();
	                        System.out.print("Enter Product ID: ");
	                        String rpid = sc.nextLine();
	                        System.out.print("Enter Customer ID: ");
	                        String rcid = sc.nextLine();
	                        System.out.print("Enter Rating (1-5): ");
	                        int rating = Integer.parseInt(sc.nextLine());
	                        System.out.print("Enter Comment: ");
	                        String comment = sc.nextLine();

	                        Review r = new Review(rid, rpid, rcid, rating, comment);
	                        data.addReview(r);
	                        System.out.println("Review added successfully.");
	                        break;
	                        
	                    case 5: 
	                        System.out.print("Enter Customer ID: ");
	                        String histCid = sc.nextLine();
	                        data.viewCustomerOrderHistory(histCid);
	                        break;
	                        
	                    case 6:
	                        data.Top3Products();
	                        break;
	                        
	                    case 7:               
	                    	data.showOutOfStock();
	                    	break;
	                    	
	                    case 8:
	                    	workingCus = false;
	                    	break;
	                    	
	                    default:
	                        System.out.println("Invalid choice. Please try again.");

	                    }
	            	} 
	            	break;
	            	
                case 3: 
                	working=false;
                	break;
                	
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        
    }
            sc.close();
}}

