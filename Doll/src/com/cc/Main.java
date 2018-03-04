package com.cc;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, UnsupportedEncodingException {
        long start = System.currentTimeMillis();
        Business business = new Business();
        business.getAccount(10000);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
