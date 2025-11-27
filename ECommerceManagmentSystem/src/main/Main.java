package main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        DataHandleAVL data = new DataHandleAVL();
        data.loadAllData();

        System.out.println("--------- Ecommerce Management System ---------");

        boolean working = true;

        while (working) {
            System.out.println("Are you:");
            System.out.println("1. A Manager");
            System.out.println("2. A Customer");
            System.out.println("3. Exit");

            int choiceMain = Integer.parseInt(sc.nextLine());
            System.out.println("------------------------------------");

            switch (choiceMain) {

                // ------------------ MANAGER ------------------
                case 1:
                    boolean workingMan = true;
                    while (workingMan) {
                        System.out.println("1. List products within price range");
                        System.out.println("2. List orders between dates");
                        System.out.println("3. List customers alphabetically");
                        System.out.println("4. List customers who reviewed a product");
                        System.out.println("5. Top 3 most reviewed products");
                        System.out.println("6. Back");

                        int choiceMan = Integer.parseInt(sc.nextLine());
                        System.out.println("------------------------------------");

                        switch (choiceMan) {

                            case 1:
                                System.out.print("Enter min price: ");
                                double min = Double.parseDouble(sc.nextLine());
                                System.out.print("Enter max price: ");
                                double max = Double.parseDouble(sc.nextLine());
                                data.listProductsInPriceRange(min, max);
                                break;

                            case 2:
                                System.out.print("Enter Start Date (YYYY-MM-DD): ");
                                String start = sc.nextLine();
                                System.out.print("Enter End Date (YYYY-MM-DD): ");
                                String end = sc.nextLine();
                                data.listOrdersBetweenDates(start, end);
                                break;

                            case 3:
                                data.listCustomersAlphabetically();
                                break;

                            case 4:
                                System.out.print("Enter Product ID: ");
                                String pid = sc.nextLine();
                                data.listCustomersWhoReviewedProduct(pid);
                                break;

                            case 5:
                                data.top3MostReviewed();
                                break;

                            case 6:
                                workingMan = false;
                                break;

                            default:
                                System.out.println("Invalid choice.");
                        }
                    }
                    break;

                // ------------------ CUSTOMER ------------------
                case 2:
                    boolean workingCus = true;
                    while (workingCus) {
                        System.out.println("1. Find product by ID");
                        System.out.println("2. Find customer by ID");
                        System.out.println("3. Find order by ID");
                        System.out.println("4. Back");

                        int choiceCus = Integer.parseInt(sc.nextLine());
                        System.out.println("------------------------------------");

                        switch (choiceCus) {

                            case 1:
                                System.out.print("Enter Product ID: ");
                                String fpid = sc.nextLine();
                                System.out.println(data.findProductById(fpid));
                                break;

                            case 2:
                                System.out.print("Enter Customer ID: ");
                                String fcid = sc.nextLine();
                                System.out.println(data.findCustomerById(fcid));
                                break;

                            case 3:
                                System.out.print("Enter Order ID: ");
                                String foid = sc.nextLine();
                                System.out.println(data.findOrderById(foid));
                                break;

                            case 4:
                                workingCus = false;
                                break;

                            default:
                                System.out.println("Invalid choice.");
                        }
                    }
                    break;

                case 3:
                    working = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        }

        sc.close();
    }
}
