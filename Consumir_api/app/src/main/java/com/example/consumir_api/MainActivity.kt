package com.example.consumir_api
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject
import org.json.JSONException
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

//class MainActivity : AppCompatActivity() {

//override fun onCreate(savedInstanceState: Bundle?) {
//  super.onCreate(savedInstanceState)
//    enableEdgeToEdge()
//    setContentView(R.layout.activity_main)
//    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//        insets
//    }
//}
//}

/*class MainActivity : AppCompatActivity() {
    EditText etEmail, etPassword;
    Button btnLogin,btnRegister;
    String email_login, password_login;

    LocalStorage localStorage;

    @Override
    protected void OnCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localStorage=new LocalStorage(MainActivity.this);

        etEmail=findViewById(R.email_login);
        etPassword=findViewById(R.id.password_login);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener({
            @Override
            public void onClick(View view){checkLogin();}
        });
        btnRegister .setOnClickListener({
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext. RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkLogin(){
        email_loginsetEmail.getText().toString();
        password_login=etPassword.getText().toString();
        if(email_login.isEmpty()||password_login.isEmpty()){
            alertFail("Por favor llena los campos");
        }else{
            sendLogin();
        }
    }

    private void sendLogin(){
        JSONObject params=new JSONObject();
        try{
            params.put("email",email_login);
            params.put("password", password_login);
        }catch (JSONException e){
            e.printStackTrace();
        }
        String data = params.toString();
        String url=getString(R.string.api_server)+"/login";

        new Thread({
            @Override
            public void run(){
                Http http = new Http(MainActivity.this.url);
                http.setMethod("post");
                http.setData(data);
                http.send();

                runOnUiThread({
                    @Override
                    public void run(){
                        Integer code = http.getStatusCode();
                        if(code==200){
                            try{
                                JSONObject response= new JSONObject(http.getResponse());
                                String token= response.getString("token");
                                System.out.println("TOKEEEN: " +token);
                                localStorage.setToken(token);
                                System.out.println("Token almacenado en LocalStorage " + localStorage.getToken());
                                Intent intent = new Intent(MainActivity.this,PrincipalActivity.class);
                                startActivity(intent);
                                finish():
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }else if(code==402){
                            try{
                                JSONObject response=new JSONObject(http.getResponse());
                                String msg=response.getString("message");
                                alertFail(msg);
                            }catch (JSONExeption e){
                                e.printStackTrace();
                            }
                        }else if(code==401){
                            try{
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg =response.getString("message");
                                alertFail(msg);
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Error"+code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    private void alertFail(String s){
        new AlertDialog.Builder(this)
            .setTitle("ERROR")
            .setIcon(R.drawable.baseline_warning_24)
            .setMessage(s)
            .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i){

                }
            })
            .show();
    }
}*/

class MainActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var emailLogin: String
    private lateinit var passwordLogin: String

    private lateinit var localStorage: LocalStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        localStorage = LocalStorage(this)

        etEmail = findViewById(R.id.username)
        etPassword = findViewById(R.id.password)
        btnLogin = findViewById(R.id.login_button)
        btnRegister = findViewById(R.id.register_button)

        btnLogin.setOnClickListener { checkLogin() }
        btnRegister.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun checkLogin() {
        emailLogin = etEmail.text.toString()
        passwordLogin = etPassword.text.toString()
        if (emailLogin.isEmpty() || passwordLogin.isEmpty()) {
            alertFail("Por favor llena los campos")
        } else {
            sendLogin()
        }
    }

    private fun sendLogin() {
        val params = JSONObject()
        try {
            params.put("email", emailLogin)
            params.put("password", passwordLogin)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val data = params.toString()
        val url = getString(R.string.api_server) + "/login"

        Thread {
            val http = Http(this, url)
            http.setMethod("post")
            http.setData(data)
            http.send()

            runOnUiThread {
                val code = http.getStatusCode()
                if (code == 200) {
                    try {
                        val response = JSONObject(http.getResponse())
                        val token = response.getString("token")
                        println("TOKEEEN: $token")
                        localStorage.setToken(token)
                        println("Token almacenado en LocalStorage ${localStorage.getToken()}")
                        val intent = Intent(this, PrincipalActivity::class.java)
                        startActivity(intent)
                        finish()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                } else if (code == 402 || code == 401) {
                    try {
                        val response = JSONObject(http.getResponse())
                        val msg = response.getString("message")
                        alertFail(msg)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                } else {
                    Toast.makeText(this, "Error $code", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun alertFail(s: String) {
        AlertDialog.Builder(this)
            .setTitle("ERROR")
            .setIcon(R.drawable.baseline_add_alert_24)
            .setMessage(s)
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }
}
