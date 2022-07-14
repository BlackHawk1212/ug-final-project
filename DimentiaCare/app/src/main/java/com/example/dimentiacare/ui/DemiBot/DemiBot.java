package com.example.dimentiacare.ui.DemiBot;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dimentiacare.R;

import com.example.dimentiacare.db.Sqlitedb;
import com.example.dimentiacare.modles.NewTasks;
import com.example.dimentiacare.remainders.MainRemainder;
import com.example.dimentiacare.ui.todo.TodoList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.dimentiacare.R.layout.update_task;


public class DemiBot extends Fragment {

    TextView textView1;
    TextView textView2;
    EditText editText;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v =inflater.inflate(R.layout.fragment_demi_bot, container, false);

         textView1 = (TextView) v.findViewById(R.id.ourmsg);
        textView2 = (TextView) v.findViewById(R.id.botmsg);
        editText=(EditText) v.findViewById(R.id.massage);
        imageView=(ImageView) v.findViewById(R.id.send);
        
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url ="http://192.168.1.102:5000/chat";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                textView2.setText(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView2.setText("That didn't work!");
                    }
                }){
                    protected Map<String, String> getParams(){
                        textView1.setText(editText.getText().toString());
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("chatInput", editText.getText().toString());
                        editText.getText().clear();
                        System.out.println(paramV);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });

        return v;
    }

}