package futsal.dev.yangadha.alpha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import futsal.dev.yangadha.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextUsername, editTextEmail, editTextPassword, editTextName, editTextAge;
    private Button buttonRegister;
    private TextView textViewLogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, DashboardActivity.class));
            return; // return untuk tidak mengeksekusi line code dibawah kalau user sudah login
        }

        editTextUsername    = (EditText) findViewById(R.id.editTextUsername);
        editTextEmail       = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword    = (EditText) findViewById(R.id.editTextPassword);
        editTextName        = (EditText) findViewById(R.id.editTextName);
        editTextAge         = (EditText) findViewById(R.id.editTextAge);

        buttonRegister      = (Button) findViewById(R.id.buttonRegister);
        textViewLogin       = (TextView) findViewById(R.id.textViewLogin);

        progressDialog      = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

    }

    private void registerUser(){
        final String email      = editTextEmail.getText().toString().trim();
        final String password   = editTextPassword.getText().toString().trim();
        final String username   = editTextUsername.getText().toString().trim();
        final String name       = editTextName.getText().toString().trim();
        final String age       = editTextAge.getText().toString().trim();

//        final String age        = String.valueOf(editTextAge).toString().trim();
        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        final StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Config.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>  params = new HashMap<>();
                params.put("username",username);
                params.put("email",email);
                params.put("password",password);
                params.put("nama",name);
                params.put("age",age);

                return params;
            }
        };
        UserRequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        String email = editTextEmail.getText().toString();
        if (v == buttonRegister)
            if ((editTextUsername.length() == 0) || (editTextPassword.length() == 0) || (editTextEmail.length()==0)){
                Toast.makeText(getApplicationContext(), "Required fields are missing", Toast.LENGTH_LONG).show();
            }else if(editTextName.length()==0){
                Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_LONG).show();
            } else if(editTextAge.length()==0){
                Toast.makeText(getApplicationContext(), "How old are you ?", Toast.LENGTH_LONG).show();
            }else if (!email.matches(emailPattern)){
                Toast.makeText(getApplicationContext(), "Email Not Valid ?", Toast.LENGTH_LONG).show();
            }
            else{
                registerUser();
            }
        if (v == textViewLogin)
            startActivity(new Intent(this, LoginActivity.class));
    }
}
