package com.example.something.better;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class FirebaseUtils {

    public static void attemptLogin(String email, String password, final FirebaseAuth mAuth, final Context context, final MainActivity var) {

        Log.d("MainActivity", "Login");
        final ArrayList<Integer> bool = new ArrayList<>();
        if (!email.equals("") && !password.equals("")) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(var, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("SignIn Status", "signInWithEmail:onComplete:" );

                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();

                                if (user.isEmailVerified()) {

                                    Intent intent = new Intent(context, FeedActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                    ((Activity) context).finish();

                                } else {

                                    Log.w("context", "signInWithEmail:failure", task.getException());
                                    Intent intent = new Intent(context, VerifyEmailActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                }
                            } else {
                                Log.w("SignIn Status", "signInWithEmail+here", task.getException());
                                Toast.makeText(context, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

        }
    }

    public static void attemptRegister(String email, String password, final FirebaseAuth mAuth, final Context context, final RegisterActivity var) {
        Log.d("Check this code", "code");
        if (!email.equals("") && !password.equals("")) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(var, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("SignUp Status", "createUserWithEmail:onComplete:" + task.isSuccessful());
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user,context,mAuth);
                            } else {

                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(context, "Authentication failed."+task.getException().toString().split(":")[1],
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
    private static void updateUI(FirebaseUser user, final Context context, FirebaseAuth mAuth) {

        if (user != null) {
            user.reload();
            if (!user.isEmailVerified()) {
                Toast.makeText(context, "Please verify your email address", Toast.LENGTH_LONG).show();
                sendEmailVerification(mAuth,context);
            } else {

                Log.d("TAG", "createUserWithEmail:success");

            }


        } else {

        }
    }

    private static void sendEmailVerification(FirebaseAuth mAuth, final Context context) {

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener((Activity) context, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if (task.isSuccessful()) {

                            Log.w("context", "signInWithEmail:failure", task.getException());
                            Intent intent = new Intent(context, VerifyEmailActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else {
                            Log.e("RegisterActivity", "sendEmailVerification", task.getException());
                            Toast.makeText(context,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }



}
