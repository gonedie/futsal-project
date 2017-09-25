package futsal.dev.yangadha.alpha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignUp;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, DashboardActivity.class));
            return; // return untuk tidak mengeksekusi line code dibawah kalau user sudah login
        }

        editTextUsername    = (EditText)findViewById(R.id.editTextUsername);
        editTextPassword    = (EditText)findViewById(R.id.editTextPassword);
        textViewSignUp      = (TextView)findViewById(R.id.textViewSignUp);
        buttonLogin         = (Button)findViewById(R.id.buttonLogin);

        progressDialog      = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        buttonLogin.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }

    private void userLogin(){
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Config.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) { //not error
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("username"),
                                        jsonObject.getString("email"),
                                        jsonObject.getString("nama"),
                                        jsonObject.getString("team"),
                                        jsonObject.getInt("age")
                                );
                                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                                finish();
                            }else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        jsonObject.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                                editTextPassword.setText("");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };

        UserRequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonLogin){
            if ((editTextUsername.length() == 0) || (editTextPassword.length() == 0)){
                Toast.makeText(getApplicationContext(), "Required fields are missing", Toast.LENGTH_LONG).show();
            }else{
                userLogin();
            }
        }
        if (v == textViewSignUp)
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }
}
