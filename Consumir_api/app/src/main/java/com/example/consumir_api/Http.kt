package com.example.consumir_api

//import java.io.BufferedReader
//import java.net.HttpURLConnection
//import java.net.URL
//import java.io.IOException
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//
//class Http {
//
//
//    Context context;
//    private String url,method="GET",data=null,response=null;
//
//    private Integer statusCode=0;
//    private Boolean  token=false;
//    private LocalStorage localStorage;
//
//    public Http(Context context, String url){
//        this.context =context;
//        this.url = url;
//        localStorage=new LocalStorage(context);
//
//    }
//    public boolean getToken(){
//        return token;
//
//    }
//
//    public void setMethod(String method){this.method = method.toUpperCase();}
//
//    public void setData(String data){this.data = data; }
//
//    public void setToken(Boolean token){this.token=token};
//
//    public String getResponse(){return response;}
//
//    public Integer getStatusCode(){return statusCode;}
//
//    public void send(){
//        try{
//            URK sUrl = new URL(url);
//            HttpURLConnection conection=(HttpUrlConnection) sUrl.penConnection();
//            connection.setRequestMethod(method);
//            connection.setRequestProperty("Content-Type","aplication/json");
//            conecction.setRequesProperty("X.Requested-Width","XMLHttpRequest");
//
//
//            if(token==true){
//                connection.setRequestProperty("Authorization","Bearer",localStorage,getToken());
//            }if(!method.equals("Get")){
//                connection.setDoOutput(true);
//            }if(data!=null){
//                OutputStream os=connection.getOutputStream();
//                os.write(data.getBytes());
//                os.flush();
//                os.close();
//            }
//        }
//        statusCode=connection.getResponseCode();
//
//
//        InputStreamReader isr;
//        if(statusCode>=200 && statusCode<=299){
//            isr=new InputStreamReader(connection.getInputStream());
//        }else{
//            isr=new InputStreamReader(connection.getErrorStream());
//        }
//        BufferedReader br=new BufferedReader(isr);
//        StringBuffer sb = new StringBuffer();
//        String line;
//        while((line=br.readline())!=null){
//            sb.append(line);
//        }
//        br.close();
//        response=sb.toString();
//
//    }catch(IOException e){
//        e.printStackTrace();
//
//    }
//}
//}


import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.io.IOException

class Http(private val context: Context, private val url: String) {

    private var method: String = "GET"
    private var data: String? = null
    private var response: String? = null
    private var statusCode: Int = 0
    private var token: Boolean = false
    private val localStorage: LocalStorage = LocalStorage(context)

    fun getToken(): Boolean {
        return token
    }

    fun setMethod(method: String) {
        this.method = method.uppercase()
    }

    fun setData(data: String) {
        this.data = data
    }

    fun setToken(token: Boolean) {
        this.token = token
    }

    fun getResponse(): String? {
        return response
    }

    fun getStatusCode(): Int {
        return statusCode
    }

    fun send() {
        try {
            val sUrl = URL(url)
            val connection = sUrl.openConnection() as HttpURLConnection
            connection.requestMethod = method
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest")

            if (token) {
                connection.setRequestProperty("Authorization", "Bearer ${localStorage.getToken()}")
            }

            if (method != "GET") {
                connection.doOutput = true
            }

            data?.let {
                val os: OutputStream = connection.outputStream
                os.write(it.toByteArray())
                os.flush()
                os.close()
            }

            statusCode = connection.responseCode

            val isr = if (statusCode in 200..299) {
                InputStreamReader(connection.inputStream)
            } else {
                InputStreamReader(connection.errorStream)
            }

            val br = BufferedReader(isr)
            val sb = StringBuffer()
            var line: String?

            while (br.readLine().also { line = it } != null) {
                sb.append(line)
            }

            br.close()
            response = sb.toString()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
