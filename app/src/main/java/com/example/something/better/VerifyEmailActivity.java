package com.example.something.better;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyEmailActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        Button btnResend = (Button) findViewById(R.id.btnResend);
        btnResend.setOnClickListener(this);
        Button btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);


        TextView txtEmail=findViewById(R.id.txtEmail);

        mAuth = FirebaseAuth.getInstance();
        txtEmail.setText("Please verify your email address \n"+mAuth.getCurrentUser().getEmail());
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

        switch (view.getId()){
            case R.id.btnResend:
                updateUI(mAuth.getCurrentUser(),true);
                break;

            case R.id.btnRefresh:
                updateUI(mAuth.getCurrentUser(),false);
                break;


        }

    }
    private void updateUI(FirebaseUser user,boolean resend) {

        if (user != null) {
            user.reload();
            if (!user.isEmailVerified()) {
                Toast.makeText(VerifyEmailActivity.this, "Please verify your email address", Toast.LENGTH_LONG).show();
                if(resend)
                sendEmailVerification();
            } else {
                Log.d("TAG", "createUserWithEmail:success");
                Intent intent = new Intent(this, FeedActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }


        } else {

        }
    }

    private void sendEmailVerification() {

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if (task.isSuccessful()) {
                            Toast.makeText(VerifyEmailActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("RegisterActivity", "sendEmailVerification", task.getException());
                            Toast.makeText(VerifyEmailActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
