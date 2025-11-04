package main;

import java.util.Scanner;

public class Main {
	public static void main (String [] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Hello out there. \n I will add two numbers for you.");
		System.out.println("Enter two whole numbers on a line:");
		int num1 = input.nextInt();
		int num2 = input.nextInt();
		System.out.println("The sum of those two numbers is: " + (num1+num2));
		System.out.println(1.0/3.0 + 1.0/3.0 + 1.0/3.0 );
		for (int no = 1; no < 5 || no++ % 2 == 0; no++) 
		    System.out.print(no++);
	}
	
	

}
