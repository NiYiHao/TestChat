package com.example.administrator.testchat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatAdapter extends BaseAdapter{
    private LayoutInflater layoutInflater;
    private ArrayList<ChatMessage> CMs;

    private TextView messageText;
    private TextView messageUser;
    private TextView messageTime;
    private ImageView userPhotoUrl;

    private Context mCtx;
    private ChatMessage chatMessage;
    private SQLiteDatabase mdb;
    private ContentValues values;

    public ChatAdapter(Context c,ArrayList<ChatMessage> CMs) {
        layoutInflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);

        this.mCtx = c;
    }

    @Override
    public int getCount() {
        return CMs.size();
    }

    @Override
    public Object getItem(int position) {
        return CMs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return CMs.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage cm =(ChatMessage)getItem(position);
        View v = layoutInflater.inflate(R.layout.message,null);
        messageUser = v.findViewById(R.id.message_user);
        messageText = v.findViewById(R.id.message_text);
        messageTime = v.findViewById(R.id.message_time);
        userPhotoUrl = v.findViewById(R.id.imgUserPhoto);

        messageUser.setText(cm.getMessageUser());
        messageText.setText(cm.getMessageText());
        messageTime.setText(cm.getMessageText());

        return v;
    }
}
