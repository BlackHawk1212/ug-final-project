package com.example.dimentiacare.ui.activityLevel02;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dimentiacare.Login;
import com.example.dimentiacare.R;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;


public class Fragment_activityLevel02 extends Fragment {

    Button newgame;
    Button button1,button2,button3,button4,
            button5,button6,button7,button8,
            button9,button10,button11,button12;

    List<Integer> buttons;

    int curLevel, curGuess;

    KonfettiView konfettiView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_activity_level02, container, false);


        button1=(Button)v.findViewById(R.id.b1);
        button2=(Button)v.findViewById(R.id.b2);
        button3=(Button)v.findViewById(R.id.b3);
        button4=(Button)v.findViewById(R.id.b4);
        button5=(Button)v.findViewById(R.id.b5);
        button6=(Button)v.findViewById(R.id.b6);
        button7=(Button)v.findViewById(R.id.b7);
        button8=(Button)v.findViewById(R.id.b8);
        button9=(Button)v.findViewById(R.id.b9);
        button10=(Button)v.findViewById(R.id.b10);
        button11=(Button)v.findViewById(R.id.b11);
        button12=(Button)v.findViewById(R.id.b12);
        konfettiView = v.findViewById(R.id.viewKonfetti);
        newgame=(Button)v.findViewById(R.id.newgame2);

        button1.setTag(1);
        button2.setTag(2);
        button3.setTag(3);
        button4.setTag(4);
        button5.setTag(5);
        button6.setTag(6);
        button7.setTag(7);
        button8.setTag(8);
        button9.setTag(9);
        button10.setTag(10);
        button11.setTag(11);
        button12.setTag(12);

        disableButtons();

        buttons = new ArrayList<>();
        buttons.add(1);
        buttons.add(2);
        buttons.add(3);
        buttons.add(4);
        buttons.add(5);
        buttons.add(6);
        buttons.add(7);
        buttons.add(8);
        buttons.add(9);
        buttons.add(10);
        buttons.add(11);
        buttons.add(12);

        newgame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                newgame.setVisibility(View.INVISIBLE);
                curGuess=0;
                curLevel= 1;
                generateButtons(curLevel);
            }
        });

        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonLogic(v);
                ((Button) v).setText("0");
            }
        });

        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogic(v);
                ((Button) v).setText("0");
            }
        });

        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogic(v);
                ((Button) v).setText("0");
            }
        });

        button4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogic(v);
                ((Button) v).setText("0");
            }
        });

        button5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogic(v);
                ((Button) v).setText("0");
            }
        });

        button6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogic(v);
                ((Button) v).setText("0");
            }
        });

        button7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogic(v);
                ((Button) v).setText("0");
            }
        });

        button8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogic(v);
                ((Button) v).setText("0");
            }
        });

        button9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogic(v);
                ((Button) v).setText("0");
            }
        });

        button10.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogic(v);
                ((Button) v).setText("0");
            }
        });

        button11.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogic(v);
                ((Button) v).setText("0");
            }
        });

        button12.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogic(v);
                ((Button) v).setText("0");
            }
        });

        return v;
    }

    private void showButtons(int number){

        switch (number){
            case 1:
                button1.setText("0");
                break;
            case 2:
                button2.setText("0");
                break;
            case 3:
                button3.setText("0");
                break;
            case 4:
                button4.setText("0");
                break;
            case 5:
                button5.setText("0");
                break;
            case 6:
                button6.setText("0");
                break;
            case 7:
                button7.setText("0");
                break;
            case 8:
                button8.setText("0");
                break;
            case 9:
                button9.setText("0");
                break;
            case 10:
                button10.setText("0");
                break;
            case 11:
                button11.setText("0");
                break;
            case 12:
                button12.setText("0");
                break;
        }
    }

    private void buttonLogic(View v){

        List<Integer> tempList = new ArrayList<>();
        for(int i = 0; i<curLevel; i++){
            tempList.add(buttons.get(i));
        }

        if(tempList.contains(v.getTag())){
            curGuess++;
            checkWin();
        }else{
            lostGame();
        }
    }

    private void checkWin() {
        if(curGuess == curLevel){
            disableButtons();
            if(curLevel == 12){
                konfettiView.build()
                        .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                        .addSizes(new Size(12, 5f))
                        .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                        .streamFor(300, 5000L);

                MDToast mdToast = MDToast.makeText(getActivity(), "Successfully finished", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                mdToast.show();

                newgame.setVisibility(View.VISIBLE);
            }else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        curGuess=0;
                        curLevel++;
                        generateButtons(curLevel);
                    }
                },1000);
            }
        }
    }

    private void lostGame() {
        new AlertDialog.Builder(getContext())
                .setTitle("Game Over")
                .setMessage("You failed to complete the game. You can start a new game!!!")
                .setPositiveButton(android.R.string.ok, null)
                .show();
        disableButtons();
        newgame.setVisibility(View.VISIBLE);

    }

    private void generateButtons(int number){
        button1.setText("");
        button2.setText("");
        button3.setText("");
        button4.setText("");
        button5.setText("");
        button6.setText("");
        button7.setText("");
        button8.setText("");
        button9.setText("");
        button10.setText("");
        button11.setText("");
        button12.setText("");

        Collections.shuffle(buttons);

        for(int i = 0; i < number; i++){
            showButtons(buttons.get(i));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                button1.setText("");
                button2.setText("");
                button3.setText("");
                button4.setText("");
                button5.setText("");
                button6.setText("");
                button7.setText("");
                button8.setText("");
                button9.setText("");
                button10.setText("");
                button11.setText("");
                button12.setText("");

                enableButtons();

            }
        },1000);

    }

    private void enableButtons(){
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);
        button7.setEnabled(true);
        button8.setEnabled(true);
        button9.setEnabled(true);
        button10.setEnabled(true);
        button11.setEnabled(true);
        button12.setEnabled(true);

    }

    private void disableButtons(){
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        button7.setEnabled(false);
        button8.setEnabled(false);
        button9.setEnabled(false);
        button10.setEnabled(false);
        button11.setEnabled(false);
        button12.setEnabled(false);
    }
}
