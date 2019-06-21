package com.example.something.better;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = (Button) findViewById(R.id.button2);
        loginButton.setOnClickListener(this);
        Button signUpButton = (Button) findViewById(R.id.button3);

        signUpButton.setPaintFlags(signUpButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        signUpButton.setOnClickListener(this);


        ImageView adix = (ImageView) findViewById(R.id.imageView);
        adix.setImageResource(R.drawable.adix);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();


        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void attemptSignup() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
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
        if (view.getId() == R.id.button2) {
            ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar4);
            pb.setVisibility(ProgressBar.VISIBLE);
            FirebaseUtils.attemptLogin(((EditText) findViewById(R.id.editText)).getText().toString(),((EditText) findViewById(R.id.editText2)).getText().toString(),mAuth,this,this);//attemptLogin();
            pb.setVisibility(View.INVISIBLE);
        }
        else {
            attemptSignup();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar4);
        pb.setVisibility(View.INVISIBLE);

    }


}
