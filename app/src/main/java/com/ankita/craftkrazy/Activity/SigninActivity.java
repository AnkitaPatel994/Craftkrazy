package com.ankita.craftkrazy.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ankita.craftkrazy.Common;
import com.ankita.craftkrazy.HttpHandler;
import com.ankita.craftkrazy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class SigninActivity extends AppCompatActivity {
    EditText txtEmailPhone,txtPassword;
    TextView txtForPwd;
    Button btnSignin,btnSignUp;
    String emailPhone="",pwd="",mobileno="",emailidDb,passwordDb;
    String url= Common.SERVICE_URL;
    ArrayList<HashMap<String,String>> loginList=new ArrayList<>();
    private TextInputLayout inputLayoutEmail,inputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        txtEmailPhone = (EditText)findViewById(R.id.txtEmailPhone);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtForPwd = (TextView)findViewById(R.id.txtForPwd);
        btnSignin = (Button)findViewById(R.id.btnSignin);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

        txtEmailPhone.addTextChangedListener(new MyTextWatcher(txtEmailPhone));
        txtPassword.addTextChangedListener(new MyTextWatcher(txtPassword));

        txtForPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Forget Password",Toast.LENGTH_SHORT).show();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);
            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailPhone = txtEmailPhone.getText().toString();
                pwd = txtPassword.getText().toString();
                if (( !emailPhone.equals("")) && ( !pwd.equals("")) )
                {
                    getLogin l = new getLogin();
                    l.execute();
                }
                else if (!validateEmail())
                {
                    return;
                }
                else if (!validatePassword())
                {
                    return;
                }
            }
        });
    }

    private boolean validateEmail() {
        String email = txtEmailPhone.getText().toString().trim();

        if (email.isEmpty()) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(txtEmailPhone);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validatePassword() {
        if (txtPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(txtPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {

                case R.id.txtEmailPhone:
                    validateEmail();
                    break;
                case R.id.txtPassword:
                    validatePassword();
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    class getLogin extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            String response;
            HttpHandler h=new HttpHandler();
            response= h.serverConnection(url);
            if(response!=null)
            {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray login=jsonObject.getJSONArray("Registration");
                    for (int l=0;l<login.length();l++)
                    {
                        HashMap<String,String > log = new HashMap<>();
                        JSONObject jl=login.getJSONObject(l);
                        mobileno=jl.getString("r_mobileno");
                        emailidDb=jl.getString("r_emailid");
                        passwordDb=jl.getString("r_password");
                        log.put("mobileno",mobileno);
                        log.put("emailidDb",emailidDb);
                        log.put("passwordDb",passwordDb);
                        loginList.add(log);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Server Connection Not Found..",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (int i = 0; i <loginList.size(); i++) {

                if (loginList.get(i).get("emailidDb").equals(emailPhone)) {
                    if (loginList.get(i).get("passwordDb").equals(pwd)){
                        Toast.makeText(getApplicationContext(), "Sucessfully Login...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                        break;
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Wrong Password...", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                else if (loginList.get(i).get("mobileno").equals(emailPhone)) {
                    if (loginList.get(i).get("passwordDb").equals(pwd)){
                        Toast.makeText(getApplicationContext(), "Sucessfully Login...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                        break;
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Wrong Password...", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                else if(!loginList.get(i).get("mobileno").equals(emailPhone)){
                    if(i==loginList.size()-1){
                        Toast.makeText(getApplicationContext(), "Not Existing User,Register First", Toast.LENGTH_SHORT).show();
                    }
                    if(i<=loginList.size()){
                        continue;
                    }
                }
                else if(!loginList.get(i).get("emailidDb").equals(emailPhone)){
                    if(i==loginList.size()-1){
                        Toast.makeText(getApplicationContext(), "Not Existing User,Register First", Toast.LENGTH_SHORT).show();
                    }
                    if(i<=loginList.size()){
                        continue;
                    }
                }
            }
        }
    }
}
