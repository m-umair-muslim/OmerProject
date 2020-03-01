package com.comsats.ars;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.comsats.ars.utils.AlertUtil;
import com.comsats.ars.utils.AppSettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText username = null;
    private EditText password = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username_txt);
        password = findViewById(R.id.password_txt);


        Button loginBtn = findViewById(R.id.loginbtn);
        Button signupBtn = findViewById(R.id.signupbtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String Username = username.getText().toString();
                final String Userpass = password.getText().toString();


                if (Username.isEmpty()) {
                    username.setError("Please enter username");
                } else if (Userpass.isEmpty()) {
                    password.setError("Please enter password");
                } else {

                    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
                    CollectionReference reference = mFirestore.collection("users");


                    final ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "Connecting database.", "Please wait while we connect to database.");
                    reference.document(Username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                DocumentSnapshot snapshot = task.getResult();
                                if (snapshot.exists()) {
                                    String dbPassword = snapshot.getString("password");
                                    if (Userpass.equals(dbPassword)) {
                                        AppSettings settings = new AppSettings(LoginActivity.this);
                                        settings.setIsLogin(true);
                                        settings.setLoginUsername(Username);
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        AlertUtil.showDialog(LoginActivity.this, "Incorrect password", "Please provide a correct password.");
                                    }
                                } else {
                                    AlertUtil.showDialog(LoginActivity.this, "User not Exists", "Please signup if not registered");
                                }

                            }
                        }


                    });


                }

            }


        });


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }

        });

    }
}
