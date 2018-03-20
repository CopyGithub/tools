package com.cc.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    String table_name = "transaction";

    long id;
    Timestamp created_time;
    Timestamp updated_time;
    int trans_type;
    String title;
    int amount;
    BigDecimal balance;
    long pay_id;
    int status;
    int account_id;
    long game_id;
    int is_win;
    int game_result;
}
