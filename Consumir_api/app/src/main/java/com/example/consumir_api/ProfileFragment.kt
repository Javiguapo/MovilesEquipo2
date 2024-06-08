package com.example.consumir_api

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.json.JSONException
import org.json.JSONObject

class ProfileFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mParam1 = it.getString(ARG_PARAM1)
            mParam2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var tvNameP: TextView
    private lateinit var tvEmailP: TextView
    private lateinit var tvCreatedP: TextView
    private lateinit var btnLogoutP: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        tvNameP = view.findViewById(R.id.tvName)
        tvEmailP = view.findViewById(R.id.tvEmail)
        tvCreatedP = view.findViewById(R.id.tvCreatedAt)
        btnLogoutP = view.findViewById(R.id.btnExit)

        getUser()

        btnLogoutP.setOnClickListener { logout() }

        return view
    }

    private fun getUser() {
        val url = "http://10.10.10.213:80/api/user"
        Thread {
            val http = Http(requireContext(), url)
            http.setToken(true)
            http.send()
            activity?.runOnUiThread {
                val code = http.getStatusCode()
                if (code == 200) {
                    try {
                        val response = JSONObject(http.getResponse())
                        val name = response.getString("name")
                        val email = response.getString("email")
                        val createdAt = response.getString("created_at")

                        tvNameP.text = name
                        tvEmailP.text = email
                        tvCreatedP.text = createdAt
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                } else {
                    Toast.makeText(requireContext(), "ERROR $code", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun logout() {
        val url = "http://192.168.3.10:8000/api/logout"
        Thread {
            val http = Http(requireContext(), url)
            http.setMethod("post")
            http.setToken(true)
            http.send()
            activity?.runOnUiThread {
                val code = http.getStatusCode()
                if (code == 200) {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Error $code", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }
}
/*public class ProfileFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2"

    private String mParam1;
    private String mParam2;

    public ProfileFragment(){

    }

    public static ProfileFragment newInstance(String param1, String param2){
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARM_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TextView tvNameP, tvEmailP, tvCreatedP;
    Button btnLogoutP;

    @Override
    public View onCreateView(LayoutInflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.fragment_profilem container, false);

        tvNameP=view.findViewById(R.id.tvname_profile);
        tvEmail=view.findViewById(R.id.tvemail_profile);
        tvXreatedP=view.findViewById(R.id.tvcreated_profile);
        tvLogoutP=view.findViewById(R.id.btnLogoutP);

        getUser();

        btnLogoutP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){logout();}
        });
        return view;
    }

    private void getUser(){
        String url = "http://192.168.3.10:8000/api" + "/user";
        new Theread(new Runnable(){
            @Override
            public void run(){
                Http http = new Http(requireContext(), url);
                http.setToken(true);
                http.send();
                getActivity().runOnUIThread(new Runnable(){
                    @Override
                    public vid run(){
                        Integer code = http.getStatusCode();
                        if(code == 2000){
                            try{
                                JSONObject response = new JSONObject(http.getResponse());
                                String name = response.getString("name");
                                String email = response.getString("email");
                                String create_at = response.getString("created_at");

                                tvNameP.setText(name);
                                tvEmailP.setText(email);
                                tvCreatedP.setTect(create_at);
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(requireContext(), "ERROR"+code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }
    private void logout(){
        String url="http://192.168.3.10:8000/api"+"/logout";
        new Thread(new Runnable(){
            @Override
            public void run(){
                Http http=new Http(requireContext().url);
                http.setMethod("post");
                http.setToken(true);
                http.send();
                getActivity().runOnUIThread(new Runnable(){
                    @Override
                    public void run(){
                        Integer code= http.getStatusCode();
                        if(code==200){
                            Intent intent= new Intent(requireContext().MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(requireContext(), "Error"+code, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

}*/