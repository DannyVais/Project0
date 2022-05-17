package com.thedannyvais.Customers;

import java.sql.SQLException;

public interface CustomerDao {
    void addCustomer(Customer customer) throws SQLException;

    void customerLogin(String email, String password) throws SQLException;

    Customer customerInfoRequest(String email, String password) throws SQLException;


}
