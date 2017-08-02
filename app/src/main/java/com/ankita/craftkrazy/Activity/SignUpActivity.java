package com.ankita.craftkrazy.Activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ankita.craftkrazy.R;

public class SignUpActivity extends AppCompatActivity {
    EditText edFname,edLname,edAdd1,edAdd2,edPincode,edMobileno,edEmail,edPassword,edConpwd;
    Button btnRegSignUp;
    String fName="",lName="",add1="",add2="",pincode="",mobileno="",email="",pwd="",cpwd="";
    private TextInputLayout inputLayoutFname,inputLayoutLname,inputLayoutAdd1,inputLayoutAdd2,
            inputLayoutPin,inputLayoutMobile,inputLayoutEmail,inputLayoutPassword,inputLayoutCPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        edFname=(EditText)findViewById(R.id.edFname);
        edLname=(EditText)findViewById(R.id.edLname);
        edAdd1=(EditText)findViewById(R.id.edAdd1);
        edAdd2=(EditText)findViewById(R.id.edAdd2);
        edPincode=(EditText)findViewById(R.id.edPincode);
        edMobileno=(EditText)findViewById(R.id.edMobileno);
        edEmail=(EditText)findViewById(R.id.edEmail);
        edPassword=(EditText)findViewById(R.id.edPassword);
        edConpwd=(EditText)findViewById(R.id.edConpwd);
        btnRegSignUp=(Button)findViewById(R.id.btnRegSignUp);

        inputLayoutFname = (TextInputLayout) findViewById(R.id.input_layout_Fname);
        inputLayoutLname = (TextInputLayout) findViewById(R.id.input_layout_Lname);
        inputLayoutAdd1 = (TextInputLayout) findViewById(R.id.input_layout_add1);
        inputLayoutAdd2 = (TextInputLayout) findViewById(R.id.input_layout_add2);
        inputLayoutPin = (TextInputLayout) findViewById(R.id.input_layout_pincode);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.input_layout_mobile);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutCPassword = (TextInputLayout) findViewById(R.id.input_layout_Cpassword);

        edFname.addTextChangedListener(new SignUpActivity.MyTextWatcher(edFname));
        edLname.addTextChangedListener(new SignUpActivity.MyTextWatcher(edLname));
        edAdd1.addTextChangedListener(new SignUpActivity.MyTextWatcher(edAdd1));
        edAdd2.addTextChangedListener(new SignUpActivity.MyTextWatcher(edAdd2));
        edPincode.addTextChangedListener(new SignUpActivity.MyTextWatcher(edPincode));
        edMobileno.addTextChangedListener(new SignUpActivity.MyTextWatcher(edMobileno));
        edEmail.addTextChangedListener(new SignUpActivity.MyTextWatcher(edEmail));
        edPassword.addTextChangedListener(new SignUpActivity.MyTextWatcher(edPassword));
        edConpwd.addTextChangedListener(new SignUpActivity.MyTextWatcher(edConpwd));

        btnRegSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fName = edFname.getText().toString();
                lName = edLname.getText().toString();
                add1 = edAdd1.getText().toString();
                add2 = edAdd2.getText().toString();
                pincode = edPincode.getText().toString();
                mobileno = edMobileno.getText().toString();
                email = edEmail.getText().toString();
                pwd = edPassword.getText().toString();
                cpwd = edConpwd.getText().toString();

                if ((!fName.equals("")) && (!lName.equals("") && (!add1.equals("")) && (!add2.equals("")) && (!pincode.equals(""))
                        && (!mobileno.equals("")) && (!email.equals("")) && (!pwd.equals("")) && (!cpwd.equals(""))))
                {
                    Toast.makeText(getApplicationContext(),"Registration Successfull",Toast.LENGTH_SHORT).show();
                }
                else if (!validateFname())
                {
                    return;
                }
                else if (!validateLname())
                {
                    return;
                }
                else if (!validateAdd1())
                {
                    return;
                }
                else if (!validateAdd2())
                {
                    return;
                }
                else if (!validatePincode())
                {
                    return;
                }
                else if (!validateMobile())
                {
                    return;
                }
                else if (!validateEmail())
                {
                    return;
                }

                else if (!validatePassword())
                {
                    return;
                }
                else if (!validateCPassword())
                {
                    return;
                }
            }
        });

    }
    private boolean validateFname() {
        if (edFname.getText().toString().trim().isEmpty()) {
            inputLayoutFname.setError(getString(R.string.err_msg_fname));
            requestFocus(edFname);
            return false;
        } else {
            inputLayoutFname.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateLname() {
        if (edLname.getText().toString().trim().isEmpty()) {
            inputLayoutLname.setError(getString(R.string.err_msg_lname));
            requestFocus(edLname);
            return false;
        } else {
            inputLayoutLname.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateAdd1() {
        if (edAdd1.getText().toString().trim().isEmpty()) {
            inputLayoutAdd1.setError(getString(R.string.err_msg_add1));
            requestFocus(edAdd1);
            return false;
        } else {
            inputLayoutAdd1.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateAdd2() {
        if (edAdd2.getText().toString().trim().isEmpty()) {
            inputLayoutAdd2.setError(getString(R.string.err_msg_add2));
            requestFocus(edAdd2);
            return false;
        } else {
            inputLayoutAdd2.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validatePincode() {
        if (edPincode.getText().toString().trim().isEmpty()) {
            inputLayoutPin.setError(getString(R.string.err_msg_pin));
            requestFocus(edPincode);
            return false;
        } else {
            inputLayoutPin.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateMobile() {
        if (edMobileno.getText().toString().trim().isEmpty()) {
            inputLayoutMobile.setError(getString(R.string.err_msg_mobile));
            requestFocus(edMobileno);
            return false;
        } else {
            inputLayoutMobile.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateEmail() {
        String email = edEmail.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_remail));
            requestFocus(edEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validatePassword() {
        if (edPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(edPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateCPassword() {
        if (edConpwd.getText().toString().trim().isEmpty()) {
            inputLayoutCPassword.setError(getString(R.string.err_msg_cpassword));
            requestFocus(edConpwd);
            return false;
        }
        else if(!edPassword.getText().toString().equals(edConpwd.getText().toString())){
            inputLayoutCPassword.setError(getString(R.string.err_msg_c1password));
            requestFocus(edConpwd);
            return false;
        }
        else {
            inputLayoutCPassword.setErrorEnabled(false);
        }
        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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

                case R.id.edFname:
                    validateFname();
                    break;
                case R.id.edLname:
                    validateLname();
                    break;
                case R.id.edAdd1:
                    validateAdd1();
                    break;
                case R.id.edAdd2:
                    validateAdd2();
                    break;
                case R.id.edPincode:
                    validatePincode();
                    break;
                case R.id.edMobileno:
                    validateMobile();
                    break;
                case R.id.edEmail:
                    validateEmail();
                    break;
                case R.id.edPassword:
                    validatePassword();
                    break;
                case R.id.edConpwd:
                    validateCPassword();
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
}
