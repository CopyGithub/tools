package com.cc.tables;

import java.sql.Timestamp;
import java.sql.Date;

public class DailyStats {
    String table_name = "daily_stats";

    int id;
    Timestamp created_time;
    Timestamp updated_time;
    Date date;
    String package_name;
    String channel;
    String platform;
    int active;
    int pay_count;
    Double pay_amount;
    int new_pay_count;
    Double new_pay_amount;
    Double cost;
}
