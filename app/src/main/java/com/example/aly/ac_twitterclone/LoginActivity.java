package com.example.aly.ac_twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{

    private EditText editLoginEmail, editLoginPassword;
    private Button btnSignUpActivity, btnLoginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");

        editLoginEmail = findViewById(R.id.editLoginEmail);
        editLoginPassword = findViewById(R.id.editLoginPassword);

        editLoginPassword.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    onClick(btnLoginActivity);
                }

                return false;
            }
        });

        btnSignUpActivity = findViewById(R.id.btnSignUpActivity);
        btnLoginActivity = findViewById(R.id.btnLoginActivity);

        btnSignUpActivity.setOnClickListener(this);
        btnLoginActivity.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null)
        {
            ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnLoginActivity:
            {
                if (editLoginEmail.getText().toString().equals("") ||
                        editLoginPassword.getText().toString().equals(""))
                {
                    FancyToast.makeText(LoginActivity.this,
                            "Email and Password is required.", FancyToast.LENGTH_LONG,
                            FancyToast.INFO, true).show();

                    break;
                }
                else
                {
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Logging in " + editLoginEmail.getText().toString() + " in progress");
                    progressDialog.show();

                    ParseUser.logInInBackground(editLoginEmail.getText().toString(),
                            editLoginPassword.getText().toString(), new LogInCallback()
                            {
                                @Override
                                public void done(ParseUser user, ParseException e)
                                {
                                    if (user != null && e == null) {
                                        FancyToast.makeText(LoginActivity.this, user.get("username") +
                                                        " has signed in successfully.", FancyToast.LENGTH_LONG,
                                                FancyToast.SUCCESS, true).show();

                                        transitionToSocialMediaActivity();
                                    }
                                    else
                                    {
                                        FancyToast.makeText(LoginActivity.this, e.getMessage(), FancyToast.LENGTH_LONG,
                                                FancyToast.ERROR, true).show();
                                    }
                                    progressDialog.dismiss();
                                }
                            });
                }
                break;
            }

            case R.id.btnSignUpActivity:
            {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);

                break;
            }
        }
    }

    public void loginActivityRootLayoutTapped (View view)
    {
        try
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void transitionToSocialMediaActivity()
    {
/*        Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
        startActivity(intent);*/
    }
}

