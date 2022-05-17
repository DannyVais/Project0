package com.thedannyvais.Employees;

public class EmployeeDaoFactory {
    private static EmployeeDao dao;

    private EmployeeDaoFactory() {
    }

    public static EmployeeDao getEmployeeDao() {
        if (dao == null) ;
        dao = new EmployeeDaoImpl();
        return dao;

    }
}
