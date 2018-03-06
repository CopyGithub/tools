package com.cc;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {
        long start = System.currentTimeMillis();
        Business business = new Business();
        business.getPayRecord();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
