package com.cc.tables;

import java.sql.Timestamp;

public class CatchRecord {
    String table_name = "catch_record";

    long id;
    Timestamp created_time;
    Timestamp updated_time;
    int account_id;
    int doll_id;
    int room_id;
    int status;
}
