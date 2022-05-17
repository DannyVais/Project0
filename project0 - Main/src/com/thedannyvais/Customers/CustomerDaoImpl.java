package com.thedannyvais.Customers;

import com.thedannyvais.BankingDisplay;
import com.thedannyvais.Connection.ConnectionFactory;

import java.sql.*;
import java.util.InputMismatchException;

public class CustomerDaoImpl implements CustomerDao {

    Connection connection;

    public CustomerDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addCustomer(Customer customer) throws SQLException {
        String sql = "insert into customers (firstName, lastName, email, password) values (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, customer.getFirstName());
        preparedStatement.setString(2, customer.getLastName());
        preparedStatement.setString(3, customer.getEmail());
        preparedStatement.setString(4, customer.getPassword());
        int count = preparedStatement.executeUpdate();
        if (count > 0)
            System.out.println("Your customer account has been created!");
        else
            System.out.println("Oops something went wrong, please try again");
    }

    @Override
    public void customerLogin(String email, String password) throws SQLException {
        String sql = "select email, password from customers where exists (select email, password from customers where email = ? and password = ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        ResultSet resultset = preparedStatement.executeQuery();
        if (resultset.next() == false) {
            System.out.println("login failed");

        } else {
            System.out.println("login successful");
            try {
                new BankingDisplay();
            } catch (InputMismatchException e) {
                System.out.println("Incorrect Input");
                new BankingDisplay();
            }
        }
    }

    @Override
    public Customer customerInfoRequest(String cust_email, String password) throws SQLException {
        Customer customer = new Customer();
        String sql = "select * from customers where email = '" + cust_email + "' and password = '" + password + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next() == false) {

            System.out.println("No record found");

        } else {
            int customerID = resultSet.getInt(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String email = resultSet.getString(4);
            String password2 = resultSet.getString(5);
            customer = new Customer(customerID, firstName, lastName, email, password2);
        }
        return customer;
    }
}
