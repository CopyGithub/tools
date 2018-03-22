package com.cc.tables;

import java.sql.Timestamp;

public class CreditProduct {
    String table_name = "credit_product";

    int id;
    Timestamp created_time;
    Timestamp updated_time;
    String title;
    int weight;
    int price;
    int stock;
    int p_type;
}
