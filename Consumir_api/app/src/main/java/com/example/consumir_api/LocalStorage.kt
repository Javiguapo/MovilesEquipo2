package com.example.consumir_api


import android.content.Context
import android.content.SharedPreferences

class LocalStorage(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("STORAGE_LOGIN_API", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    private var token: String? = null

    fun getToken(): String? {
        token = sharedPreferences.getString("TOKEN", "")
        return token
    }

    fun setToken(token: String) {
        editor.putString("TOKEN", token)
        editor.commit()
        this.token = token
    }
}


//public class LocalStorage {
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
//
//    Context context;
//    String Token;
//
//
//
//    public LocalStorage(Context context){
//        this.context = context;
//        sharedPreferences=context.getSharedPreferences("STORAGE_LOGIN_API,Context.MODE_PRIVATE");
//        editor =sharedPreferences.edit();
//    }
//
//    public string getToken(){
//        token=sharedPrefenreces.getString("TOKEN","");
//        return token;
//    }
//    public void setToken(String token){
//        editor.putString("TOKEN",token);
//        editor.commit();
//        this.token=token;
//    }

