package com.cc.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Account {
    String table_name = "account";

    int id;
    Timestamp created_time;
    Timestamp updated_time;
    String nickname;
    BigDecimal balance;
    int credit;
    String phone;
    int account_type;
    int is_virtual;
    int status;
    String package_name;
    String channel;

    String pltm;
    String vc;
    String ip;
    String pn;
    String mac;
    String idfa;
    String anid;
    String did;
}
