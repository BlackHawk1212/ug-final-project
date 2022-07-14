package com.example.dimentiacare.ui.music;

import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dimentiacare.R;

import java.util.ArrayList;

public class MusicFragment extends Fragment {

    ImageView play,prew,next,imageView;
    TextView songTitle;
    SeekBar mSeekBarTime,getmSeekBarVol;
    static MediaPlayer mMediaPlayer;
    private MediaController mMediaController;
    private Runnable runnable;
    private AudioManager mAudioManager;
    int currentIndex = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.music_fragment, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        play = (ImageView)v.findViewById(R.id.play);
        prew = (ImageView)v.findViewById(R.id.previous);
        next = (ImageView)v.findViewById(R.id.next);
        songTitle = (TextView) v.findViewById(R.id.songName);
        imageView=(ImageView)v.findViewById(R.id.imageViewname);
        mSeekBarTime = (SeekBar) v.findViewById(R.id.mSeekBarTime);
        getmSeekBarVol = (SeekBar) v.findViewById(R.id.seekBarVol);

        final ArrayList<Integer> songs = new ArrayList<>();

        songs.add(0,R.raw.amaizing_grace);
        songs.add(1,R.raw.harry_belafonte_day);
        songs.add(2,R.raw.hills_are_alive);
        songs.add(3,R.raw.home_home_on_the_range);
        songs.add(4,R.raw.ive_been_working_on_the_railroad);
        songs.add(5,R.raw.my_favorite_things);
        songs.add(6,R.raw.traditional_swing_low_sweet);
        songs.add(7,R.raw.what_a_wonderful_world);

        mMediaPlayer = MediaPlayer.create(getContext(),songs.get(currentIndex));

        int maxV = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curV = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        getmSeekBarVol.setMax(maxV);
        getmSeekBarVol.setProgress(curV);

        getmSeekBarVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekBarTime.setMax(mMediaPlayer.getDuration());
                if(mMediaPlayer!=null && mMediaPlayer.isPlaying()){
                    mMediaPlayer.pause();
                    play.setImageResource(R.drawable.play);
                }else{
                    mMediaPlayer.start();
                    play.setImageResource(R.drawable.pause);
                }
                songNames();
            }
        });

        prew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayer!=null){
                    play.setImageResource(R.drawable.pause);
                }
                if(currentIndex > 0){
                    currentIndex--;
                }else {
                    currentIndex = songs.size() - 1;
                }
                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                }

                mMediaPlayer = MediaPlayer.create(getContext(),songs.get(currentIndex));
                mMediaPlayer.start();
                songNames();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayer!=null){
                    play.setImageResource(R.drawable.pause);
                }
                if(currentIndex <songs.size() - 1){
                   currentIndex++;
                }else {
                    currentIndex = 0;
                }
                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                }

                mMediaPlayer = MediaPlayer.create(getContext(),songs.get(currentIndex));
                mMediaPlayer.start();
                songNames();
            }
        });


        return v;
    }

    private void songNames(){
        if(currentIndex == 0){
            songTitle.setText("Amaizing_grace.mp3");
            imageView.setImageResource(R.drawable.amaizing);
        }
        if(currentIndex == 1){
            songTitle.setText("Harry_belafonte_day.mp3");
            imageView.setImageResource(R.drawable.harry);
        }
        if(currentIndex == 2){
            songTitle.setText("Hills_are_alive.mp3");
            imageView.setImageResource(R.drawable.hills);
        }
        if(currentIndex == 3){
            songTitle.setText("Songstraditional_home_home_on_th_ range.mp3");
            imageView.setImageResource(R.drawable.range);
        }
        if(currentIndex == 4){
            songTitle.setText("I've_been_working_on_the_railroad.mp3");
            imageView.setImageResource(R.drawable.sing);
        }
        if(currentIndex == 5){
            songTitle.setText("My_favorite_things.mp3");
            imageView.setImageResource(R.drawable.sing);
        }
        if(currentIndex == 6){
            songTitle.setText("Traditional_swing_low_sweet.mp3");
            imageView.setImageResource(R.drawable.sing);
        }
        if(currentIndex == 7){
            songTitle.setText("What_a_wonderful_world.mp3");
            imageView.setImageResource(R.drawable.wonder);
        }

        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mSeekBarTime.setMax(mMediaPlayer.getDuration());
                mMediaPlayer.start();
            }
        });

        mSeekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mMediaPlayer.seekTo(progress);
                    mSeekBarTime.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mMediaPlayer!=null){
                    try {
                        if(mMediaPlayer.isPlaying()){
                            Message message = new Message();
                            message.what = mMediaPlayer.getCurrentPosition();
                            handler.sendMessage(message);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    @SuppressLint("Handler Leak") Handler handler = new Handler(){
    @Override
        public void handleMessage (Message msg){
        mSeekBarTime.setProgress(msg.what);
    }
    };
}
