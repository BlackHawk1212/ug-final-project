package com.example.dimentiacare.remainders;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dimentiacare.R;
import com.example.dimentiacare.db.Sqlitedb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainRemainder extends AppCompatActivity {

    public static ArrayList<Map<String, String>> myArray;
    ListView listView;
    FloatingActionButton floatingActionButton;
    String date,time,task;
    String timeTonotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_remainder);

        listView=(ListView)findViewById(R.id.listvieww);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab1);

        myArray = Sqlitedb.Search(MainRemainder.this, "SELECT * FROM MyEvents", 4);
        MyAdapter myAdapter = new MyAdapter(MainRemainder.this, R.layout.remainder_update, myArray);
        listView.setAdapter(myAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainRemainder.this, AddRemainder.class);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                        MainRemainder.this);

                // set title
                alertDialogBuilder.setTitle("Change List Items");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to delete or update item?")
                        .setCancelable(false)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainRemainder.this);
                                builder1.setMessage("Are you sure want to delete this item ?");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                Sqlitedb.DeleteData(MainRemainder.this, "MyEvents WHERE ID_NO = '" + myArray.get(position).get("0") + "'");
                                                myArray = Sqlitedb.Search(MainRemainder.this, "SELECT * FROM MyEvents", 4);
                                                MyAdapter myAdapter = new MyAdapter(MainRemainder.this, R.layout.remainder_update, myArray);
                                                listView.setAdapter(myAdapter);

                                            }
                                        });

                                builder1.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();


                            }
                        })

                        .setNeutralButton("Update Details", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,  int id) {
                                // if this button is clicked, close
                                // current activity
                                final View customLayout = getLayoutInflater().inflate(R.layout.remaindertaskupdate, null);
                                final EditText editText1 = customLayout.findViewById(R.id.update_editext_message);
                                final ImageView imageView = customLayout.findViewById(R.id.btn_record_update);
                                final Button button1 = customLayout.findViewById(R.id.btn_time_update);
                                final Button button2 = customLayout.findViewById(R.id.btn_date_update);


                                task=myArray.get(position).get("1");
                                time=myArray.get(position).get("3");
                                date=myArray.get(position).get("2");

                                editText1.setText(task);
                                button1.setText(time);
                                button2.setText(date);
                                
                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                                        try {

                                            startActivityForResult(intent, 1);
                                        } catch (Exception e) {
                                            Toast.makeText(MainRemainder.this, "Your device does not support Speech recognizer", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                button1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Calendar calendar = Calendar.getInstance();
                                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                                        int minute = calendar.get(Calendar.MINUTE);
                                        TimePickerDialog timePickerDialog = new TimePickerDialog(MainRemainder.this, new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                                timeTonotify = i + ":" + i1;
                                                button1.setText(AddRemainder.FormatTime(i, i1));
                                            }

                                        }, hour, minute, false);
                                        timePickerDialog.show();
                                    }
                                });

                                button2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Calendar calendar = Calendar.getInstance();
                                        int year = calendar.get(Calendar.YEAR);
                                        int month = calendar.get(Calendar.MONTH);
                                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                                        DatePickerDialog datePickerDialog = new DatePickerDialog(MainRemainder.this, new DatePickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                                button2.setText(day + "-" + (month + 1) + "-" + year);
                                            }
                                        }, year, month, day);
                                        datePickerDialog.show();
                                    }
                                });


                                AlertDialog.Builder builder = new AlertDialog.Builder(MainRemainder.this);
                                builder.setTitle("Change Item");

                                builder.setView(customLayout);
                                // add a button
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog1, int which) {
                                        String text = editText1.getText().toString().trim();
                                        if (text.isEmpty()) {
                                            Toast.makeText(MainRemainder.this, "Please Enter or record the text", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (button1.getText().toString().equals("Select Time") || button2.getText().toString().equals("Select date")) {
                                                Toast.makeText(MainRemainder.this, "Please select date and time", Toast.LENGTH_SHORT).show();
                                            } else {

                                                String value = (editText1.getText().toString().trim());
                                                String date = (button2.getText().toString().trim());
                                                String time = (button1.getText().toString().trim());

                                                Sqlitedb.UpdateData(MainRemainder.this,
                                                        "MyEvents",
                                                        " eventname = '" + editText1.getText().toString().trim()
                                                                + "',eventdate = '" + button1.getText().toString().trim()
                                                                + "',eventtime = '" + button2.getText().toString().trim()
                                                                + "'", "  ID_NO = '"+myArray.get(position).get("0")+"'");
                                                setAlarm(value, date, time);
                                                myArray = Sqlitedb.Search(MainRemainder.this, "SELECT * FROM MyEvents", 4);
                                                System.out.println(myArray);
                                                MyAdapter myAdapter = new MyAdapter(MainRemainder.this, R.layout.remainder_update, myArray);
                                                listView.setAdapter(myAdapter);

                                                Intent intent = new Intent (MainRemainder.this, MainRemainder.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                });
                                // create and show the alert dialog
                                AlertDialog dialog1 = builder.create();
                                dialog1.show();
                            }
                            // do something with the data coming from the AlertDialog

                        })

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing

                                dialog.cancel();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

                return false;
            }
        });

    }



    static class ViewHolder {

        TextView COL1;
        TextView COL2;
        TextView COL3;
        ImageButton imageButton1;
        ImageButton imageButton2;

    }


    class MyAdapter extends ArrayAdapter<Map<String, String>> {
        LayoutInflater inflater;
        Context myContext;
        List<Map<String, String>> newList;

        public MyAdapter(Context context, int resource, ArrayList<Map<String, String>> objects) {
            super(context, resource, objects);
            myContext = context;
            newList = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.remainder_update, null);

                holder.COL1 = (TextView) view.findViewById(R.id.taskk);
                holder.COL2 = (TextView) view.findViewById(R.id.timee);
                holder.COL3 = (TextView) view.findViewById(R.id.datee);
                holder.imageButton1=(ImageButton)view.findViewById(R.id.delete);
                holder.imageButton2=(ImageButton)view.findViewById(R.id.editt);

                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText(newList.get(position).get("1"));
            holder.COL2.setText(newList.get(position).get("2"));
            holder.COL3.setText(newList.get(position).get("3"));
            System.out.println(holder);

            holder.imageButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Do you want to delete this item?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Sqlitedb.DeleteData(MainRemainder.this, "MyEvents WHERE ID_NO = '" + myArray.get(position).get("0") + "'");
                                    myArray = Sqlitedb.Search(MainRemainder.this, "SELECT * FROM MyEvents", 4);
                                    MyAdapter myAdapter = new MyAdapter(MainRemainder.this, R.layout.remainder_update, myArray);
                                    listView.setAdapter(myAdapter);
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            });

            holder.imageButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(myContext, "hhhhhhhhhhhhhhh", Toast.LENGTH_SHORT).show();
                    View view = inflater.inflate(R.layout.remaindertaskupdate, null);
                    view.isShown();
                }
            });

            return view;
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                View customLayout = getLayoutInflater().inflate(R.layout.remaindertaskupdate, null);
                EditText editText1 = customLayout.findViewById(R.id.update_editext_message);
                editText1.setText(text.get(0));
            }
        }

    }

    public void setAlarm(String text, String date, String time) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmBrodcast.class);
        intent.putExtra("event", text);
        intent.putExtra("time", date);
        intent.putExtra("date", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String dateandtime = date + " " + timeTonotify;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        finish();

    }

}