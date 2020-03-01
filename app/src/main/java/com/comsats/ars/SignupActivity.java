package com.comsats.ars;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.comsats.ars.data.User;
import com.comsats.ars.utils.AlertUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    private EditText sign_username = null;
    private EditText sign_pass = null;
    private EditText sign_conpass = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        sign_username = findViewById(R.id.signup_username_txt);
        sign_pass = findViewById(R.id.signup_pass_txt);
        sign_conpass = findViewById(R.id.signup_conpass_txt);

        Button button = findViewById(R.id.sign_btn);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = sign_username.getText().toString();
                final String userpass = sign_pass.getText().toString();
                final String userconpass = sign_conpass.getText().toString();

                if (username.isEmpty()) {
                    sign_username.setError("Please enter username");
                } else if (userpass.isEmpty()) {
                    sign_pass.setError("Please enter password");
                } else if (userconpass.isEmpty()) {
                    sign_conpass.setError("Please confirm the password");
                } else if (!userpass.equals(userconpass)) {
                    new AlertDialog.Builder(SignupActivity.this)
                            .setTitle("Invalid password")
                            .setMessage("Please enter identical password.")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            })

                            .show();
                } else {

                    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
                    CollectionReference reference = mFirestore.collection("users");

                    final ProgressDialog dialog = ProgressDialog.show(SignupActivity.this, "Connecting database.", "Please wait while we connect to database.");

                    reference.document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                DocumentSnapshot snapshot = task.getResult();
                                if (snapshot.exists()) {
                                    AlertUtil.showDialog(SignupActivity.this, "User exists", "Please try any other username.");
                                } else {
                                    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
                                    CollectionReference reference = mFirestore.collection("users");

                                    User user = new User(username, userpass);
                                    reference.document(username).set(user);
                                    Toast.makeText(SignupActivity.this, "The signup completed. Please login-in.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }
                    });
                }

            }


        });

    }
}
