package com.hardis.connect.activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.hardis.connect.R;
import com.hardis.connect.controller.UserController;
import com.hardis.connect.controller.VolleyCallBack;
import com.hardis.connect.model.User;
import com.hardis.connect.util.GlobalMethodes;
import com.hardis.connect.util.MessageUser;



public class AuthenticationActivity extends ActionBarActivity {

    private Button signIn;
    private Button signUp;
    private EditText username;
    private EditText password;
    private TextView textViewHardisConnect;
    String login;
    String hashedPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_authentication);

            signIn = (Button) findViewById(R.id.buttonLogin);
            signUp = (Button) findViewById(R.id.buttonRegister);
            username = (EditText) findViewById(R.id.editTextId);
            password = (EditText) findViewById(R.id.editTextPassword);
            textViewHardisConnect = (TextView) findViewById(R.id.textViewHardisConnect);

            textViewHardisConnect.setText(Html.fromHtml("<font color=#3399cc>Hardis</font><font color=#ffffff>Connect</font>"));

            password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        login= username.getText().toString();
                        String pwd = password.getText().toString();
                        if (login.isEmpty() || pwd.isEmpty())
                            Toast.makeText(AuthenticationActivity.this, MessageUser.get("1105"), Toast.LENGTH_SHORT).show();
                        else {
                            hashedPass = GlobalMethodes.md5(pwd);
                            User user = new User(login,hashedPass);

                            UserController.authenticateUser(getApplicationContext(), user, new VolleyCallBack() {
                                @Override
                                public void onSuccess(String result) {
                                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                }

                                @Override
                                public void onFailed(String result) {
                                    Toast.makeText(AuthenticationActivity.this, MessageUser.get("1103"), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                    return false;
                }
            });

            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    login = username.getText().toString();
                    String pwd = password.getText().toString();
                    if (login.isEmpty() || pwd.isEmpty())
                        Toast.makeText(AuthenticationActivity.this, MessageUser.get("1105"), Toast.LENGTH_SHORT).show();
                    else {
                        hashedPass = GlobalMethodes.md5(pwd);
                        User user = new User(login,hashedPass);

                         UserController.authenticateUser(getApplicationContext(), user, new VolleyCallBack() {
                            @Override
                            public void onSuccess(String result) {
                                Toast.makeText(AuthenticationActivity.this, MessageUser.get("2102"), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getBaseContext(), MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            }

                            @Override
                            public void onFailed(String result) {
                                Toast.makeText(AuthenticationActivity.this, MessageUser.get("1103"), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

            signUp.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }
            });

        }
        catch(Throwable t)
        {
            Log.v("Problem :", t.getMessage());
        }


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}