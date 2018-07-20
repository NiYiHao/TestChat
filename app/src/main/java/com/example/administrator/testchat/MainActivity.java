package com.example.administrator.testchat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.TimeZone;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{
//----------------------------------------------------------------------------------------------------------------------
//宣告區----------------------------------------------------------------------------------------------------------------------
    private int SIGN_IN_REQUEST_CODE;
    private FirebaseListAdapter<ChatMessage> adapter;
    private ChatMessage model;
    private ListView listView;
    private Button fab;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseDatabase fireBD;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userPhotpUrl;
    private Bitmap userPic;
    private ImageView testImg;



//OnCreate----------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//資料設定區----------------------------------------------------------------------------------------------------------------------


        listView = findViewById( R.id.list_of_messages );

//OnCreate程式區----------------------------------------------------------------------------------------------------------------------
//Firebase確認使用者認證----------------------------------------------------------------------------------------------------------------------
//        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
//            // Start sign in/sign up activity
//            startActivityForResult(
//                    AuthUI.getInstance()
//                            .createSignInIntentBuilder()
//                            .build(),
//                    SIGN_IN_REQUEST_CODE
//            );
//        } else {
//            // User is already signed in. Therefore, display
//            // a welcome Toast
//            Toast.makeText(this,
//                    "Welcome " + FirebaseAuth.getInstance()
//                            .getCurrentUser()
//                            .getDisplayName(),
//                    Toast.LENGTH_LONG)
//                    .show();
//        auth = FirebaseAuth.getInstance();
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
//                    startActivityForResult( new Intent( MainActivity.this, LoginActivity.class ), 1 );
//                } else {
//                    Toast.makeText( MainActivity.this,
//                            "Welcome " + FirebaseAuth.getInstance()
//                                    .getCurrentUser()
//                                    .getDisplayName(),
//                            Toast.LENGTH_LONG )
//                            .show();
//                }
//            }
//        };
//
//
//
//        listView = findViewById(R.id.list_of_messages);
//        testImg = findViewById(R.id.imageView);

//OnCreate程式區----------------------------------------------------------------------------------------------------------------------
//Firebase確認使用者認證----------------------------------------------------------------------------------------------------------------------
        // if (user == null) {
        //     // Start sign in/sign up activity
        //     startActivityForResult(
        //             AuthUI.getInstance()
        //                     .createSignInIntentBuilder()
        //                     .build(),
        //             SIGN_IN_REQUEST_CODE
        //     );
        // } else {
        //     // User is already signed in. Therefore, display
        //     // a welcome Toast
        //     Toast.makeText(this,
        //             "Welcome " + user.getDisplayName(),
        //             Toast.LENGTH_LONG)
        //             .show();
        //     //取得使用者圖片
        //     userPhotpUrl = user.getPhotoUrl().toString();
        //     Log.i("༼つಠ益ಠ༽つ ─=≡ΣO))", userPhotpUrl);
        //
        //     new AsyncTask<String, Void, Bitmap>() {
        //         @Override
        //         protected void onPostExecute(Bitmap bitmap) {
        //             userPic = bitmap;
        //             //testImg.setImageBitmap(userPic);
        //             super.onPostExecute(bitmap);
        //         }
        //
        //         @Override
        //         protected Bitmap doInBackground(String... strings) {
        //             try {
        //                 URL url = new URL(strings[0]);
        //                 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //                 conn.setDoInput(true);
        //                 conn.connect();
        //                 InputStream is = conn.getInputStream();
        //                 Bitmap bitmap = BitmapFactory.decodeStream(is);
        //                 return bitmap;
        //             } catch (MalformedURLException e) {
        //                 e.printStackTrace();
        //                 return null;
        //             } catch (IOException e) {
        //                 e.printStackTrace();
        //                 return null;
        //             }
        //         }
        //     }.execute(userPhotpUrl);
        //     testImg.setImageBitmap(userPic);


//            listView = findViewById(R.id.list_of_messages);

//OnCreate程式區----------------------------------------------------------------------------------------------------------------------
//Firebase確認使用者認證----------------------------------------------------------------------------------------------------------------------
//        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
//            // Start sign in/sign up activity
//            startActivityForResult(
//                    AuthUI.getInstance()
//                            .createSignInIntentBuilder()
//                            .build(),
//                    SIGN_IN_REQUEST_CODE
//            );
//        } else {
//            // User is already signed in. Therefore, display
//            // a welcome Toast
//            Toast.makeText(this,
//                    "Welcome " + FirebaseAuth.getInstance()
//                            .getCurrentUser()
//                            .getDisplayName(),
//                    Toast.LENGTH_LONG)
//                    .show();
            auth = FirebaseAuth.getInstance();
            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), 1);
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Welcome " + FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                }
            };



//發送訊息按鈕----------------------------------------------------------------------------------------------------------------------
            // Load chat room contents
            fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText input = (EditText) findViewById(R.id.input);
                    // Read the input field and push a new instance
                    // of ChatMessage to the Firebase database
                    FirebaseDatabase.getInstance()
                            .getReference("contacts")
                            .push()
                            .setValue(new ChatMessage(input.getText().toString(),
                                    user
                                            .getDisplayName(), userPhotpUrl)
                            );
                    // Clear the input
                    input.setText("");
                }
            });

    displayChatMessages();
    }

//顯示訊息區----------------------------------------------------------------------------------------------------------------------
    private void displayChatMessages() {
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);
        Query query=FirebaseDatabase.getInstance().getReference("contacts");
        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(query,  ChatMessage.class)
                .setLayout(R.layout.message)
                .build();
        adapter = new FirebaseListAdapter<ChatMessage>(options) {

            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText( DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
                 Log.i( "messageText" , String.valueOf( messageText ) );
            }
        };
//----------------------------------------------------------------------------------------------------------------------
        listOfMessages.setAdapter(adapter);
//        listView.setSelection(adapter.getCount() -1);
//----------------------------------------------------------------------------------------------------------------------
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener( authStateListener );
        adapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener( authStateListener );
        adapter.stopListening();
    }

//----------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText( this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG )
                        .show();
                displayChatMessages();
            } else {
                Toast.makeText( this,
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG )
                        .show();

                // Close the app
                finish();
            }
        }
    }
    @Override
//Menu選項----------------------------------------------------------------------------------------------------------------------
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_sign_out) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this,
                                    "You have been signed out.",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // Close activity
                            finish();
                        }
                    });
        }
        return true;
    }



}
