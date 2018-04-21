package com.example.marry.miwok_starter_code;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    //cream un obiect al clasei MediaPlayer
    private MediaPlayer mMediaPlayer;


    /**
     * create an instance of OnAudioFocusChangeListener, so we can know when the audio focus changes
     */
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                //if we gain audio focus we play the sound
                case AudioManager.AUDIOFOCUS_GAIN:
                    mMediaPlayer.start();
                    break;
                //if we lose audio focus for a short period of time we just pause the play sound
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    if (mMediaPlayer != null) {
                        //we pause the sound
                        mMediaPlayer.pause();
                        //and we need to tell the MediaPlayer, from where we want the sound to be played,
                        //if from the begining or from the moment we lose focus
                        //in this case we want from the begining
                        mMediaPlayer.seekTo(0);
                    }
                    break;
                //if we lose the audio focus to be played in the backgroud
                // we pause, because otherwise the sound will not be played properly
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    if (mMediaPlayer != null) {
                        //we pause the sound
                        mMediaPlayer.pause();
                        //and we need to tell the MediaPlayer, from where we want the sound to be played,
                        //if from the begining or from the moment we lose focus
                        //in this case we want from the begining
                        mMediaPlayer.seekTo(0);
                    }
                    break;
                //if we lose audio focus we stop the sound and we realease resources
                case AudioManager.AUDIOFOCUS_LOSS:
                    releaseMediaPlayer();
                    break;
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer(){
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer!=null){
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();
            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            //abandon th audio focus
            releaseAudioFocusForMyApp(NumbersActivity.this);
        }
    }

    /**
     * A method to request audio focus for NumbersActivity
     * @param context will receive the class we want to gain the focus
     * @return  if we gained or not the audio focus
     */
    private boolean requestAudioFocusForMyApp(final Context context){
        //create an instance of the AudioManager - asa se creaza un obiect al clasei
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        /**
         * create an int  variable to request the audio focus for playing the words
         * @param mOnAudioFocusChangeListener the listener to be notified of audio focus changes
         * @param AudioManager.STREAM_MUSIC - Used to identify the volume of audio streams for music playback
         * @param  AudioManager.AUDIOFOCUS_GAIN_TRANSIENT - used to indicate a temporary gain or request of audio focus, anticipated to last a short amount of time
         */
        int result = am.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT );
        /**
         * if we gained the audio focus, the method will return true, else will return false
         */
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
            return true;
        }else{
            return false;
        }
    }

    /**
     * A method to release audio focus for NumbersActivity
     * @param context will receive the class we want to gain the focus
     */
        private  void  releaseAudioFocusForMyApp(final Context context){
            //create an instance of the class AudioManager
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            //abandon the audioFocus
            am.abandonAudioFocus(mOnAudioFocusChangeListener);
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //set the UP action
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //cream un ArrayList cu elemente de tip Word - este clasa creata de noi
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "telookusu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five ));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);
        final ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Word word = words.get(position);

                //if we have the audio focus
                //we create an Media Player and we play the sound
                boolean gotFocus = requestAudioFocusForMyApp(NumbersActivity.this);
                if (gotFocus){
                    mMediaPlayer = mMediaPlayer.create(NumbersActivity.this, word.getmMiwokAudio());
                    mMediaPlayer.start();
                }

                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                        //release the memory
                        releaseMediaPlayer();
                    }
                });
            }
        });
    }
}
