package com.thedannyvais.Accounts;

public class AccountDaoFactory {
    private static AccountDao dao;

    private AccountDaoFactory() {
    }

    public static AccountDao getAccountDao() {
        if (dao == null) ;
        dao = new AccountDaoImpl();
        return dao;
    }
}
