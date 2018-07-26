package com.example.administrator.testchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UserActivity extends AppCompatActivity {
//----------------------------------------------------------------------------------------------------------------------
//宣告區----------------------------------------------------------------------------------------------------------------------
    private FirebaseAuth auth;
    private FirebaseDatabase fireDB;
    private ChatMessage model;
    private FirebaseListAdapter<ChatMessage> adapter;
    private android.support.v7.widget.Toolbar toolbar;

//----------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user );
//----------------------------------------------------------------------------------------------------------------------
        toolbar = findViewById(R.id.userToolbar);
        setSupportActionBar(toolbar);
//----------------------------------------------------------------------------------------------------------------------
        Useritem();
    }
//----------------------------------------------------------------------------------------------------------------------
    private void Useritem() {
        ListView listofuser = (ListView) findViewById( R.id.listOfUser );
        Query query = FirebaseDatabase.getInstance().getReference( "user" );
        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery( query, ChatMessage.class )
                .setLayout( R.layout.useritem )
                .build();
        adapter = new FirebaseListAdapter<ChatMessage>( options ) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView UserName = (TextView) v.findViewById( R.id.UserName );
                TextView Userlogintime = (TextView) v.findViewById( R.id.Userlogintime );
                // Set their text
                UserName.setText( model.getMessageUser() );
                // Format the date before showing it
                Userlogintime.setText( DateFormat.format( "dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime() ) );
            }
        };
        listofuser.setAdapter( adapter );
    }
//----------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
//----------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_name_item,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.back:
                Intent i = new Intent( );
                i.setClass( this,MainActivity.class );
                startActivity( i );
                finish();
                break;
        }
        return super.onOptionsItemSelected( item );
    }
}
