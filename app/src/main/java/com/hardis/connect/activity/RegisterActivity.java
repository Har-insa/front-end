package com.hardis.connect.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import com.hardis.connect.R;
import com.hardis.connect.controller.AgencyController;
import com.hardis.connect.controller.UserController;
import com.hardis.connect.controller.VolleyCallBack;
import com.hardis.connect.model.User;
import com.hardis.connect.util.AllUrls;
import com.hardis.connect.util.GlobalMethodes;
import com.hardis.connect.util.MessageUser;
import static com.hardis.connect.util.GlobalMethodes.isNumeric;

/**
 * Created by Hassan on 11/3/2015.
 */
public class RegisterActivity extends ActionBarActivity{

    private Button accept;
    private Button cancel;

    private TextView textView;
    private EditText usernameField;
    private EditText passwordField;
    private EditText confirmpasswordField;
    private EditText firstnameField;
    private EditText lastnameField;
    private EditText phonenumberField;
    private CheckBox termAcceptation;
    private ProgressDialog progressDialog;
    private Spinner agencies;


    private String username;
    private String password;
    private String confirmpassword;
    private String firstname;
    private String lastname;
    private String phonenumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textView = (TextView) findViewById(R.id.label);
        accept = (Button) findViewById(R.id.button_validate);
        cancel = (Button) findViewById(R.id.button_cancel);
        usernameField = (EditText) findViewById(R.id.username);
        passwordField = (EditText) findViewById(R.id.password);
        confirmpasswordField = (EditText) findViewById(R.id.confirmpassword);
        firstnameField = (EditText) findViewById(R.id.prenom);
        lastnameField = (EditText) findViewById(R.id.nom);
        phonenumberField = (EditText) findViewById(R.id.phonenumber);
        termAcceptation = (CheckBox) findViewById(R.id.terms);
        agencies = (Spinner) findViewById(R.id.agency);
        String[] lRegion= AgencyController.getAgencies(getApplicationContext());

        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,lRegion);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        agencies.setAdapter(dataAdapterR);

        textView.setText(Html.fromHtml("<font color=#3399cc>Sign</font> <font color=#ffffff>Up</font>"));

        accept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                overridePendingTransition(R.anim.right_in, R.anim.left_out);

                username = usernameField.getText().toString();
                password = passwordField.getText().toString();
                confirmpassword = confirmpasswordField.getText().toString();
                firstname = firstnameField.getText().toString();
                lastname = lastnameField.getText().toString();
                phonenumber = phonenumberField.getText().toString();

                if (username.isEmpty() || password.isEmpty() || confirmpassword.isEmpty() || firstname.isEmpty() || lastname.isEmpty())
                    Toast.makeText(RegisterActivity.this, MessageUser.get("1106"), Toast.LENGTH_SHORT).show();
                else if (!password.equals(confirmpassword))
                    Toast.makeText(RegisterActivity.this, MessageUser.get("1107"), Toast.LENGTH_SHORT).show();
                else if (!termAcceptation.isChecked())
                    Toast.makeText(RegisterActivity.this, MessageUser.get("1108"), Toast.LENGTH_SHORT).show();
                else {
                    UserController.addUser(getApplicationContext(), new User(firstname, lastname, username, GlobalMethodes.md5(password)),
                            new VolleyCallBack() {
                                @Override
                                public void onSuccess(String result) {
                                    Toast.makeText(getApplicationContext(), MessageUser.get("2101"), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailed(String result) {
                                    Toast.makeText(getApplicationContext(), MessageUser.get("1104"), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AuthenticationActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });
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