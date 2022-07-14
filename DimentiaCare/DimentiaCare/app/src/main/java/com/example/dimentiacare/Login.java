package com.example.dimentiacare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.valdesekamdem.library.mdtoast.MDToast;

public class Login extends AppCompatActivity {

    Button reg;
    EditText editText1;
    EditText editText2;
    Button log;
    CheckBox checkBox;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText1=(EditText)findViewById(R.id.Lemail);
        editText2=(EditText)findViewById(R.id.Lpass);
        log=(Button)findViewById(R.id.login);
        reg=(Button)findViewById(R.id.regi);
        checkBox=(CheckBox)findViewById(R.id.remember);

        firebaseAuth=FirebaseAuth.getInstance();

        SharedPreferences sharedPref = getSharedPreferences("Checkbox",MODE_PRIVATE);
        String Checkbox = sharedPref.getString("remember","");
        if(Checkbox.equals("true")){
            Intent intent = new Intent(Login.this, Dashboard.class);
            startActivity(intent);
        }
        else if(Checkbox.equals("false")){
            MDToast mdToast = MDToast.makeText(Login.this, "Please sign in", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
            mdToast.show();
        }

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(buttonView.isChecked()){
                    SharedPreferences sharedPref = getSharedPreferences("Checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    MDToast mdToast = MDToast.makeText(Login.this, "Checked", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                    mdToast.show();
                }
                else if(!buttonView.isChecked()){
                    SharedPreferences sharedPref = getSharedPreferences("Checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    MDToast mdToast = MDToast.makeText(Login.this, "Unchecked", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                    mdToast.show();
                }
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emaill = editText1.getText().toString();
                final String passwordd = editText2.getText().toString();

                if (emaill.isEmpty()) {
                    editText1.setError("Correct email required");
                } if (passwordd.isEmpty()) {
                    editText2.setError("Correct Password required");
                }else{



                firebaseAuth.signInWithEmailAndPassword(emaill, passwordd)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    MDToast mdToast = MDToast.makeText(Login.this, "Successfully sign in", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                                    mdToast.show();
                                    Intent intent = new Intent(Login.this, Dashboard.class);
                                    startActivity(intent);
                                }else {
                                    MDToast mdToast = MDToast.makeText(Login.this, "Sign in failed", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                                    mdToast.show();
                                }

                            }
                        });
                }
            }
        });
    }

}
