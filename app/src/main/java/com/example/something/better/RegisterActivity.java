package com.example.something.better;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button register = (Button) findViewById(R.id.button4);
        register.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public void onClick(View view) {
        if (view.getId() == R.id.button4) {


            String email = ((EditText) findViewById(R.id.editText6)).getText().toString();
            String password = ((EditText) findViewById(R.id.editText5)).getText().toString();
            FirebaseUtils.attemptRegister(email,password,mAuth,this,this);

        }
    }
}
