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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener
{
    private EditText editUserEmail, editUserName, editUserPassword;
    private Button btnSignUp, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setTitle("Sign Up");

        editUserEmail = findViewById(R.id.editUserEmail);
        editUserName = findViewById(R.id.editUserName);
        editUserPassword = findViewById(R.id.editUserPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        editUserPassword.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    onClick(btnSignUp);
                }

                return false;
            }
        });

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

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
            case R.id.btnSignUp:
            {
                if (editUserEmail.getText().toString().equals("") ||
                        editUserName.getText().toString().equals("") ||
                        editUserPassword.getText().toString().equals(""))
                {
                    FancyToast.makeText(SignUp.this, "Email, Username and Password is required.", FancyToast.LENGTH_LONG,
                            FancyToast.INFO, true).show();

                    break;
                }
                else
                {
                    final ParseUser newUser = new ParseUser();
                    newUser.setEmail(editUserEmail.getText().toString());
                    newUser.setUsername(editUserName.getText().toString());
                    newUser.setPassword(editUserPassword.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing up " + editUserName.getText().toString() + " in progress");
                    progressDialog.show();

                    newUser.signUpInBackground(new SignUpCallback()
                    {
                        @Override
                        public void done(ParseException e)
                        {
                            if (e == null)
                            {
                                FancyToast.makeText(SignUp.this, newUser.get("username") +
                                                " has signed up successfully.", FancyToast.LENGTH_LONG,
                                        FancyToast.SUCCESS, true).show();

                                transitionToSocialMediaActivity();
                            }

                            else
                            {
                                FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG,
                                        FancyToast.ERROR, true).show();
                            }

                            progressDialog.dismiss();
                        }
                    });

                    break;
                }
            }

            case R.id.btnLogin:
            {
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);

                break;
            }
        }
    }

    public void rootLayoutTapped (View view)
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
/*        Intent intent = new Intent(SignUp.this, SocialMediaActivity.class);
        startActivity(intent);*/
    }
}
