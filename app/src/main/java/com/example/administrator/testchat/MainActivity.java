package com.example.administrator.testchat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
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
    private String userName;
    private String userEmail;
    //OnCreate----------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

//資料設定區----------------------------------------------------------------------------------------------------------------------
        listView = findViewById( R.id.list_of_messages );

//OnCreate程式區----------------------------------------------------------------------------------------------------------------------
//Firebase確認使用者認證----------------------------------------------------------------------------------------------------------------------
        auth = FirebaseAuth.getInstance();

                    authStateListener = new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                                startActivityForResult( new Intent( MainActivity.this, LoginActivity.class ), 1 );
                            } else {
                                Toast.makeText( MainActivity.this,
                                        "Welcome " + FirebaseAuth.getInstance()
                                                .getCurrentUser()
                                                .getDisplayName(),
                                        Toast.LENGTH_LONG )
                                        .show();
                            }
                        }
                    };
                        userName = user.getDisplayName();
                        if(userName != null) {
                           // Log.i("彡ﾟ◉ω◉ )つ--==*", userName);
                        }else {
                            userEmail = user.getEmail();
                          //  Log.i("彡ﾟ◉ω◉ )つー>>)))", userEmail);
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(userEmail)
                                    .build();
                            user.updateProfile(profileUpdates);
                            userName = user.getDisplayName();
                            //Log.i("彡ﾟ◉ω◉ )つー☆*", userName);
                        }
//取得使用者圖片-------------------------------------------------------------------------------------------------------------------------
                            if (user.getPhotoUrl() != null) {
                                userPhotpUrl = user.getPhotoUrl().toString();
                              //  Log.i("༼つಠ益ಠ༽つ ─=≡ΣO))", userPhotpUrl);
                            }else{
                            //如果沒有就給預設圖片
                               // userPhotpUrl = "https://firebasestorage.googleapis.com/v0/b/teststorage-32724.appspot.com/o/Photos%2F93137?alt=media&token=0794baad-c875-45ca-8dc5-92cce5766455";
                                //Log.i("༼つಠ益ಠ༽つ ─=≡ΣO))", userPhotpUrl);
                            }


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
                final ImageView userImg = (ImageView)v.findViewById(R.id.imgUserPhoto);
                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                // Format the date before showing it
                messageTime.setText( DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
                 //Log.i( "messageText" , String.valueOf( messageText ) );
                 if(model.getUserPhotoUrl() == null){
                 //    imgurl = "https://firebasestorage.googleapis.com/v0/b/teststorage-32724.appspot.com/o/Photos%2F93137?alt=media&token=0794baad-c875-45ca-8dc5-92cce5766455";

                 }else {
                     final String imgurl = model.getUserPhotoUrl();
                     Picasso.get().load(imgurl).fetch(new Callback() {
                         @Override
                          public void onSuccess() {
                             Picasso.get().load(imgurl).fit().centerCrop().into(userImg);
                             Picasso.get().setIndicatorsEnabled(true);
                         }
                         @Override
                         public void onError(Exception e) {
                         }
                     });
                 }
            }
        };
//----------------------------------------------------------------------------------------------------------------------
        listOfMessages.setAdapter(adapter);
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
        switch (item.getItemId() ){
            case R.id.menu_sign_out:
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
            break;
            case R.id.menu_edit_name:
                final View editNameDia = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_edit_name, null);
                new AlertDialog.Builder(MainActivity.this)
                        .setView(editNameDia)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //處理更新使用者資訊
                                EditText editName = (EditText)editNameDia.findViewById(R.id.dialogEditName);

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(editName.getText().toString())
                                        .build();
                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(MainActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(MainActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        })
                        .show();
            break;
            case  R.id.menu_edit_password:
                final View editPassDia = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_edit_password,null);
                new AlertDialog.Builder(MainActivity.this).setView(editPassDia).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editPass = (EditText)editPassDia.findViewById(R.id.dialogEditPassword);
                        String newPassword = editPass.getText().toString();
                        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Log.i("༼つಠ益ಠ༽つ ─=≡ΣO))",task.getException().toString());
                                    Toast.makeText(MainActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).show();
            break;

        }
        return true;
    }



}
