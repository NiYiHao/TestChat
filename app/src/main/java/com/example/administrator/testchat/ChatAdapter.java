package com.example.administrator.testchat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ChatAdapter {
    private String messageText;
    private String messageUser;
    private long messageTime;
    private Context mCtx;
    private ChatMessage chatMessage;
    private SQLiteDatabase mdb;
    private ContentValues values;

    public ChatAdapter(Context mCtx) {
        this.mCtx = mCtx;
    }
}
