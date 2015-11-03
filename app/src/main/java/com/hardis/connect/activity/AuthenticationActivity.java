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


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.hardis.connect.R;
import com.hardis.connect.network.CustomRequest;
import com.hardis.connect.util.AllUrls;
import com.hardis.connect.util.AuthUtil;
import com.hardis.connect.util.GlobalMethodes;
import com.hardis.connect.util.MessageUser;

import org.json.JSONObject;


public class AuthenticationActivity extends ActionBarActivity {

    private Button login;
    private Button register;
    private EditText username;
    private EditText password;
    private TextView textViewHardisConnect;
    private String action="";
    private RequestQueue requestQueue;
    String logIn;
    String hashedPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MessageUser.init();

        try {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_authentication);

            login = (Button) findViewById(R.id.buttonLogin);
            register = (Button) findViewById(R.id.buttonRegister);
            username = (EditText) findViewById(R.id.editTextId);
            password = (EditText) findViewById(R.id.editTextPassword);
            textViewHardisConnect = (TextView) findViewById(R.id.textViewHardisConnect);

            textViewHardisConnect.setText(Html.fromHtml("<font color=#3399cc>Hardis</font><font color=#ffffff>Connect</font>"));

            password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        logIn= username.getText().toString();
                        String pass = password.getText().toString();
                        if (logIn.isEmpty() || pass.isEmpty())
                            Toast.makeText(AuthenticationActivity.this, MessageUser.get("1105"), Toast.LENGTH_SHORT).show();
                        else {
                            action = "login";
                            hashedPass = GlobalMethodes.md5(pass);

                            Intent i = new Intent(getBaseContext(), MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);

                        }
                    }
                    return false;
                }
            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    logIn = username.getText().toString();
                    String pwd = password.getText().toString();
                    if (logIn.isEmpty() || pwd.isEmpty())
                        Toast.makeText(AuthenticationActivity.this, MessageUser.get("1105"), Toast.LENGTH_SHORT).show();
                    else {
                        action = "login";
                        hashedPass = GlobalMethodes.md5(pwd);

                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);


                    }
                }
            });

            register.setOnClickListener(new View.OnClickListener() {

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