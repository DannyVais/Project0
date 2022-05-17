package com.thedannyvais;

import com.thedannyvais.Customers.Customer;
import com.thedannyvais.Customers.CustomerDao;
import com.thedannyvais.Customers.CustomerDaoFactory;
import com.thedannyvais.Employees.Employee;
import com.thedannyvais.Employees.EmployeeDao;
import com.thedannyvais.Employees.EmployeeDaoFactory;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, InputMismatchException {
        CustomerDao customerDao = CustomerDaoFactory.getCustomerDao();
        EmployeeDao employeeDao = EmployeeDaoFactory.getEmployeeDao();
        Scanner scanner = new Scanner(System.in);

        boolean flag = true;

        while (flag) {
            System.out.println();
            System.out.println("Welcome to Danny's Banking App");
            System.out.println("*******************************");
            System.out.println("Select from options below");
            System.out.println("*******************************");
            System.out.println("Enter 1: Create new Customer account");
            System.out.println("Enter 2: Customer Login");
            System.out.println("Enter 3: Employee Login");
            System.out.println("Enter 4: Exit");

            int input = scanner.nextInt();
            switch (input) {
                case 1: {
                    //creating new customer account
                    System.out.println("Enter first name: ");
                    String firstName = scanner.next();
                    System.out.println("Enter last name: ");
                    String lastName = scanner.next();
                    System.out.println("Enter email: ");
                    String email = scanner.next();
                    System.out.println("Enter password: ");
                    String password = scanner.next();
                    Customer newCustomer = new Customer();
                    newCustomer.setFirstName(firstName);
                    newCustomer.setLastName(lastName);
                    newCustomer.setEmail(email);
                    newCustomer.setPassword(password);
                    customerDao.addCustomer(newCustomer);
                    break;
                }

                case 2: {
                    //Customer Login
                    System.out.println("Enter email: ");
                    String email = scanner.next();
                    System.out.println("Enter password: ");
                    String password = scanner.next();
                    customerDao.customerLogin(email, password);
                    break;
                }

                case 3: {
                    //Employee Login
                    System.out.println("Enter email: ");
                    String email = scanner.next();
                    System.out.println("Enter password: ");
                    String password = scanner.next();
                    employeeDao.employeeLogin(email, password);
                    break;
                }

                case 4: {
                    //exit
                    System.out.println("Thank you");
                    System.out.println("Exiting...");
                    flag = false;
                    break;
                }

                default:
                    System.out.println("Please enter number between 1-4");
            }
        }
    }
}
