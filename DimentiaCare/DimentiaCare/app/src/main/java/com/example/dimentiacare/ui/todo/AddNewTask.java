package com.example.dimentiacare.ui.todo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dimentiacare.Dashboard;
import com.example.dimentiacare.R;
import com.example.dimentiacare.Register;
import com.example.dimentiacare.modles.NewTasks;
import com.example.dimentiacare.modles.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.valdesekamdem.library.mdtoast.MDToast;


public class AddNewTask extends Fragment {

    EditText editText1;
    EditText editText2;
    Button add;

    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_new_task, container, false);

        editText1=(EditText)v.findViewById(R.id.topic);
        editText2=(EditText)v.findViewById(R.id.task);
        add=(Button)v.findViewById(R.id.submit);

        firebaseAuth=FirebaseAuth.getInstance();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference("ToDo List Tasks");
//                reference = rootNode.getReference("Users");

                final String topic = editText1.getText().toString();
                final String details = editText2.getText().toString();

                if (topic.isEmpty()) {
                    editText1.setError("Topic is required");
                } else if (details.isEmpty()) {
                    editText2.setError("Detail is required");
                } else {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String userid = user.getUid();

                    String id = reference.push().getKey();
                    NewTasks newTasks = new NewTasks(topic,details,id);
                    reference.child(userid).child(id).setValue(newTasks);

                    System.out.println(newTasks);

                    MDToast mdToast = MDToast.makeText(getActivity(), "Task added successfully", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                    mdToast.show();

                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setIcon(R.drawable.warning)
                            .setTitle("Do you wnt to add another task?")
                            .setMessage("If you want you can add more tasks or you can leave this page")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Fragment fragment = new AddNewTask();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Fragment fragment = new TodoList();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                }
                            })
                            .show();

                }
            }
        });

        return v;
    }
}