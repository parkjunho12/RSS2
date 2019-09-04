package com.timeline.rss2;

import android.provider.BaseColumns;

public final class Database {
    public static final class CreateDB implements BaseColumns {
        public static final String Goodoc = "구독";
        public static final String _TABLENAME0 = "usertable";
        public static final String PHONE = "PHONE";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement, "
                +PHONE+" varchar(50) not null , "
                +Goodoc+ " varchar(50) not null  "+
                " );";
    }
}
