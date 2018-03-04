package com.cc.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PayRecord {
    long id;
    Timestamp created_time;
    Timestamp updated_time;
    int pay_type;
    BigDecimal price;
    int amount;
    int status;
    int account_id;
}
