package com.example.dimentiacare.ui.logout;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dimentiacare.Login;
import com.example.dimentiacare.R;
import com.valdesekamdem.library.mdtoast.MDToast;

import static android.content.Context.MODE_PRIVATE;

public class Logout extends Fragment {

    Button button1;
    Button button2;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.logout_fragment, container, false);

        button1=(Button)v.findViewById(R.id.cancel);
        button2=(Button)v.findViewById(R.id.logout);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Checkbox", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("remember","false");
                editor.apply();

                Intent i = new Intent(getActivity(), Login.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);

                MDToast mdToast = MDToast.makeText(getActivity(), "Successfully Logout", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                mdToast.show();

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MDToast mdToast = MDToast.makeText(getActivity(), "Logout Canceled", MDToast.LENGTH_SHORT, MDToast.TYPE_INFO);
                mdToast.show();
            }
        });

        return v;
    }


}
