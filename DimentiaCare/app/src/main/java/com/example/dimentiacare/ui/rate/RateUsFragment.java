package com.example.dimentiacare.ui.rate;

import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dimentiacare.R;
import com.example.dimentiacare.modles.NewTasks;
import com.example.dimentiacare.modles.Ratings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.valdesekamdem.library.mdtoast.MDToast;

import static com.example.dimentiacare.R.layout.privious_ratings;
import static com.example.dimentiacare.R.layout.update_profile;

public class RateUsFragment extends Fragment {

    TextView showrateing,ratecount,commen;
    EditText review;
    Button submit,privous;
    RatingBar ratingBar;
    float rateValue;
    String temp;
    String comment;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rate_us_fragment, container, false);

        ratecount = (TextView)v.findViewById(R.id.rateCount);
        review = (EditText)v.findViewById(R.id.review);
        submit = (Button)v.findViewById(R.id.subm);
        privous = (Button)v.findViewById(R.id.see);
        ratingBar = (RatingBar)v.findViewById(R.id.ratingBar);

        firebaseAuth=FirebaseAuth.getInstance();

        privous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                View view1 = inflater.inflate(privious_ratings,null);
                dialogBuilder.setView(view1);

                final TextView textView1 = (TextView) view1.findViewById(R.id.showRating);
                final TextView textView2 = (TextView) view1.findViewById(R.id.Comment);
                Button button = (Button)view1.findViewById(R.id.ok);

                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String userid = user.getUid();

                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Ratings").child(userid);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String rate = snapshot.child("rate").getValue().toString();
                        String comment = snapshot.child("comment").getValue().toString();

                        textView1.setText(rate);
                        textView2.setText(comment);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateValue = ratingBar.getRating();

                if (rateValue<=1 && rateValue>0){
                    ratecount.setText("Bad " +rateValue + "/5");
                }else if (rateValue<=2 && rateValue>1){
                    ratecount.setText("OK " +rateValue + "/5");
                }else if (rateValue<=3 && rateValue>2){
                    ratecount.setText("Good " +rateValue + "/5");
                }else if (rateValue<=4 && rateValue>3){
                    ratecount.setText("Very Good " +rateValue + "/5");
                }else if (rateValue<=5 && rateValue>4){
                    ratecount.setText("Best " +rateValue + "/5");
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference = FirebaseDatabase.getInstance().getReference("Ratings");

                temp = ratecount.getText().toString();
                comment = review.getText().toString();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String userid = user.getUid();

                    Ratings ratings = new Ratings(temp, comment);
                    reference.child(userid).setValue(ratings);

                    System.out.println(ratings);

                    MDToast mdToast = MDToast.makeText(getActivity(), "Rate added successfully", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                    mdToast.show();
            }
        });

        return v;
    }


}
