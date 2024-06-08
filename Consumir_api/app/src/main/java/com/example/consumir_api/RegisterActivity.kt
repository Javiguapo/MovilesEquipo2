package com.example.consumir_api
import android.content.DialogInterface
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import org.json.JSONObject
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException

/*public class RegisterActivity extends AppCompatActivity{
    EditText etNameRegister,etEmailRegister,etPasswordRegister,etPasswordConfirmationRegister;

    Button btnRegisterRegister,btnBack;

    String NameR,EmailR, PasswordR, PasswordCR;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(saverInstanceState);
        setContentView(R.layout.activity_register);

        etNameRegister=findViewById(R.id.name_register);
        etEmailRegister=findViewById(R.id.email_register);
        etPasswordRegister=findViewById(R.id.password_register);
        etPassConfirmationRegister=findViewById(R.id.passwordConfirmation_register);
        btnRegisterRegister=findViewById(R.id.btnRegisterRegister);
        btnBack=findViewById(R.id.btnBack);

        btnRegisterRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {checkRegister();}
        });

    }

    private void checkRegister(){
        NameR=etNameRegister.getText().toString;
        EmailR=etEmailRegister.getText().toString;
        PasswordR=etPasswordRegister.getText.toString;
        PasswordCR=etPasswordConfirmationRegister.getText().toString();

        if(NameR.isEmpty()||EmailR.isEmpty()||PasswordR.isEmpty()){
            alertFail("Name,Email and Password are required");
        }else if(!PasswordR.equals(PasswordCR)){
            alertFail("The passwords dont match");
        }else{
            sendRegister;
        }
    }

    private void sendRegister(){
        JSONObject params= new JSONObject();
        try{
            params.put("name",NameR);
            params.put("email",EmailR);
            params.put("password",PasswordR);
            params.put("password_confirmation",PasswordCR);
        }catch (JSONException e){
            e.printStackTrace();
        }
        String data =params.toString;
        String url="http://192.168.3.10:8000/api"+"/register";

        new Thread(new Runnable(){
            @Override
            public void run(){
                Http http=new Http(RegisterActivity.this.url);
                http.setMethod("post");
                http.setData(data);
                http.send();

                runOnUIThread({
                    @Override
                    public void run(){
                        Integer code=http.getStatusCode();
                        if(code==201||code==200){
                            alertSuccess("Register Complete");
                        }else if (code==422){
                            try{
                                JSONObject response = new JSONObject(http.getResponse());
                                String msg=response.getString("message");
                                alertFail(msg);
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this, "Error"+code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    private void alertSuccess(String s){
        new AlertDialog.Builder(this)
            .setTitle("SUCCESS")
            .setIcon(R.drawable.baseline_add_alert_24)
            .setMessage(s)
            .setPositiveButton("Login", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) { onBackPressed();}
            })
            .show();
    }

    private void alertFail(String s){
        new AlertDialog.Builder(this)
            .setTitle("ERROR")
            .setIcon(R.drawable.baseline_add_alert_24)
            .setMessage(s)
            .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialogInterface, int i){

                }
            })
            .show();
    }

}*/

class RegisterActivity : AppCompatActivity() {
    private lateinit var etNameRegister: EditText
    private lateinit var etEmailRegister: EditText
    private lateinit var etPasswordRegister: EditText
    private lateinit var etPasswordConfirmationRegister: EditText

    private lateinit var btnRegisterRegister: Button
    private lateinit var btnBack: Button

    private var NameR: String = ""
    private var EmailR: String = ""
    private var PasswordR: String = ""
    private var PasswordCR: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registerins)

        etNameRegister = findViewById(R.id.name_register)
        etEmailRegister = findViewById(R.id.email_register)
        etPasswordRegister = findViewById(R.id.password)
        etPasswordConfirmationRegister = findViewById(R.id.password_register)
        btnRegisterRegister = findViewById(R.id.register_button)
        btnRegisterRegister.setOnClickListener { checkRegister() }
    }

    private fun checkRegister() {
        NameR = etNameRegister.text.toString()
        EmailR = etEmailRegister.text.toString()
        PasswordR = etPasswordRegister.text.toString()
        PasswordCR = etPasswordConfirmationRegister.text.toString()

        if (NameR.isEmpty() || EmailR.isEmpty() || PasswordR.isEmpty()) {
            alertFail("Name, Email and Password are required")
        } else if (PasswordR != PasswordCR) {
            alertFail("The passwords don't match")
        } else {
            sendRegister()
        }
    }

    private fun sendRegister() {
        val params = JSONObject()
        try {
            val data = JSONObject()
            data.put("type", "users")  // El tipo de recurso
            val attributes = JSONObject()
            attributes.put("name", NameR)
            attributes.put("email", EmailR)
            attributes.put("password", PasswordR)
            attributes.put("password_confirmation", PasswordCR)
            data.put("attributes", attributes)
            params.put("data", data)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val data: String = params.toString()
        val url: String = "http://10.10.10.213:80/api/register"

        Thread ( Runnable{
            val http = Http(this@RegisterActivity, url)
            http.setMethod("POST")
            http.setData(data)
            http.send()

            runOnUiThread {
                val code = http.getStatusCode()
                if (code == 201 || code == 200) {
                    alertSuccess("Register Complete")
                } else if (code == 422) {
                    try {
                        val response = JSONObject(http.getResponse())
                        val msg = response.getString("message")
                        alertFail(msg)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "Error $code", Toast.LENGTH_SHORT).show()
                }
            }
        }).start()
    }

    private fun alertSuccess(s: String) {
        AlertDialog.Builder(this@RegisterActivity)
            .setTitle("SUCCESS")
            .setIcon(R.drawable.baseline_add_alert_24)
            .setMessage(s)
            .setPositiveButton("Login") { _, _ -> onBackPressed() }
            .show()
    }

    private fun alertFail(s: String) {
        AlertDialog.Builder(this@RegisterActivity)
            .setTitle("ERROR")
            .setIcon(R.drawable.baseline_add_alert_24)
            .setMessage(s)
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }
}