package com.cc.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Account {
    String table_name = "account";

    int id;
    Timestamp created_time;
    Timestamp updated_time;
    String nickname;
    int gender;
    BigDecimal balance;
    int credit;
    String phone;
    int account_type;
    int is_virtual;
    int status;
    String channel;
    String package_name;

    String pltm;
    String ip;
    String did;
    String svc;
    String dm;
    String mac;
    String idfa;
    String anid;
    String vc;
    String vn;
    String pn;
}
