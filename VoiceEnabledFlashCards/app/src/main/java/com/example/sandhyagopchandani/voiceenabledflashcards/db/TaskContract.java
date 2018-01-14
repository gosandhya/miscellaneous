package com.example.sandhyagopchandani.voiceenabledflashcards.db;

/**
 * Created by sandhya.gopchandani on 2/13/2017.
 */

import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "voiceEnabledCards";
    public static final int DB_VERSION = 5;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "FlashCards";

        public static final String COL_NOTE= "NOTE";
       // public static final String COL_DES = "DESC";
    }
}