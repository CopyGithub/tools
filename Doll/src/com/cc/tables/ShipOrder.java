package com.cc.tables;

import java.sql.Timestamp;

public class ShipOrder {
    String table_name = "ship_order";

    long id;
    Timestamp created_time;
    Timestamp updated_time;
    String remark;
    int status;
    int account_id;
    int receipt_address_id;
    String ship_no;
    String ship_way;
    int credit_product_id;
    int o_type;
}
