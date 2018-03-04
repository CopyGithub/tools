package com.cc.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Account {
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
    String channel;

    String pltm;
    String vc;
    String ip;
    String mac;
    String idfa;
    String anid;
    String did;
}
