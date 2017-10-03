package com.example.germanpereyra.androideatit;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.germanpereyra.androideatit.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignInActivity extends AppCompatActivity {

    EditText txtPhone, txtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        txtPassword = (MaterialEditText) findViewById(R.id.txtPassword);
        txtPhone = (MaterialEditText) findViewById(R.id.txtPhone);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference usersReference = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
                mDialog.setMessage("Loading, please wait");
                mDialog.show();

                usersReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mDialog.dismiss();
                        Log.d("GERMAN", txtPhone.getText().toString());
                        if (dataSnapshot.child(txtPhone.getText().toString()).exists()) {
                            User user = dataSnapshot.child(txtPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(txtPassword.getText().toString())) {
                                Toast.makeText(SignInActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignInActivity.this, "Wrong phone or password", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(SignInActivity.this, "User not exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
