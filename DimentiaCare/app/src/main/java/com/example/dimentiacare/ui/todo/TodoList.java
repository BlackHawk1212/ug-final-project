package com.example.dimentiacare.ui.todo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.CheckBoxPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dimentiacare.R;
import com.example.dimentiacare.modles.NewTasks;
import com.example.dimentiacare.ui.activityLevel01.Fragment_ActivityLevel01;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import static com.example.dimentiacare.R.layout.update_task;

public class TodoList extends Fragment {

    Button todo;
    ListView listView;
    List<NewTasks> user;
    DatabaseReference ref;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_todo_list, container, false);

        todo = (Button)v.findViewById(R.id.addTask);
        listView = (ListView)v.findViewById(R.id.listview);

        user = new ArrayList<>();

        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddNewTask();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        FirebaseUser userr = FirebaseAuth.getInstance().getCurrentUser();
        String userid = userr.getUid();

        ref = FirebaseDatabase.getInstance().getReference("ToDo List Tasks").child(userid).getRef();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot taskDatasnap : dataSnapshot.getChildren()){

                    NewTasks newTasks = taskDatasnap.getValue(NewTasks.class);
                    user.add(newTasks);
                }

                MyAdapter adapter = new MyAdapter(getContext(), R.layout.task_history, (ArrayList<NewTasks>) user);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

    static class ViewHolder {

        TextView COL1;
        TextView COL2;
        ImageButton imageButton1;
        ImageButton imageButton2;

    }

    class MyAdapter extends ArrayAdapter<NewTasks> {
        LayoutInflater inflater;
        Context myContext;
        List<Map<String, String>> newList;
        List<NewTasks> user;


        public MyAdapter(Context context, int resource, ArrayList<NewTasks> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.task_history, null);

                holder.COL1 = (TextView) view.findViewById(R.id.topicc);
                holder.COL2 = (TextView) view.findViewById(R.id.detaill);
                holder.imageButton1=(ImageButton)view.findViewById(R.id.complete);
                holder.imageButton2=(ImageButton)view.findViewById(R.id.edit);

                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText(user.get(position).getTopic());
            holder.COL2.setText(user.get(position).getDetails());

            System.out.println(holder);

            final String idd = user.get(position).getId();

            holder.imageButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setIcon(R.drawable.warning)
                            .setTitle("Do you want to mark as complete this task?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String userid = user.getUid();

                                    FirebaseDatabase.getInstance().getReference("ToDo List Tasks").child(userid).child(idd).removeValue();
                                    System.out.println(FirebaseDatabase.getInstance().getReference("ToDo List Tasks").child(userid).child(idd));
                                    //remove function not written
                                    MDToast mdToast = MDToast.makeText(getActivity(), "Task completed", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                                    mdToast.show();

                                    Fragment fragment = new TodoList();
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
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            });

            holder.imageButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view1 = inflater.inflate(update_task,null);
                    dialogBuilder.setView(view1);

                    final EditText editText1 = (EditText)view1.findViewById(R.id.topicup);
                    final EditText editText2 = (EditText)view1.findViewById(R.id.taskup);
                    Button button = (Button)view1.findViewById(R.id.update);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String userid = user.getUid();

                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ToDo List Tasks").child(userid).child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String Topic = snapshot.child("topic").getValue().toString();
                            String Detail = snapshot.child("details").getValue().toString();

                            editText1.setText(Topic);
                            editText2.setText(Detail);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String Topic = editText1.getText().toString();
                            String Deail = editText2.getText().toString();


                            if(Topic.equals("")) {
                                editText1.setError("Topic is required");
                            }if(Deail.equals("")){
                                editText2.setError("Detail is required");
                            }else {

                                HashMap map = new HashMap();
                                map.put("topic", Topic);
                                map.put("details",Deail);
                                reference.updateChildren(map);

                                MDToast mdToast = MDToast.makeText(getActivity(), "Task Updated Successfully", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                                mdToast.show();

                                alertDialog.dismiss();
                            }
                        }
                    });
                }
            });

            return view;
        }

    }


}
