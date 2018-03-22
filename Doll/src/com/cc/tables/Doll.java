package com.cc.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Doll {
    String table_name = "doll";

    int id;
    Timestamp created_time;
    Timestamp updated_time;
    String title;
    int stock;
    BigDecimal price;
    int d_type;
}
