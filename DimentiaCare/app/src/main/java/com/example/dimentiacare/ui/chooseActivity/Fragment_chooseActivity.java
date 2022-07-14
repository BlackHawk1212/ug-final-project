package com.example.dimentiacare.ui.chooseActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dimentiacare.R;
import com.example.dimentiacare.ui.activities.Activities;
import com.example.dimentiacare.ui.activityLevel01.Fragment_ActivityLevel01;


public class Fragment_chooseActivity extends Fragment {

    Button recall;
    Button recognize;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_choose_activity, container, false);

        recall = (Button)v.findViewById(R.id.recall);
        recognize = (Button)v.findViewById(R.id.recognize);

        recall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Activities();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return v;
    }
}
