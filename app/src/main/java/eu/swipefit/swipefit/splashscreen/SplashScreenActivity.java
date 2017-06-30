package eu.swipefit.swipefit.splashscreen;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import eu.swipefit.swipefit.R;
import eu.swipefit.swipefit.pinterest.MyLoginActivity;
import pl.droidsonroids.gif.GifTextView;

import static android.media.AudioManager.AUDIOFOCUS_GAIN;
import static android.media.AudioManager.AUDIOFOCUS_LOSS;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;
import static android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;

/**
 * This file represents the activity related to the splashscreen that will appear when the app is launched
 */

/** ADD COMMENTS */

public class SplashScreenActivity extends Activity {

    // fields related to audio and media player

    private MediaPlayer mediaPlayer = null;
    private AudioManager audioManager;

    // onAudioFocusChangeListener is the listener object of type OnAudioFocusChangeListener

    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i == AUDIOFOCUS_LOSS_TRANSIENT || i == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(i == AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            }
            else if(i == AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_activity);

        // fields related to UI

        final GifTextView gifTextView = (GifTextView) findViewById(R.id.gif);
        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.gif_duration);
        final HTextView hTextView = (HTextView) findViewById(R.id.text);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int audioResult = audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        if(audioResult==AUDIOFOCUS_REQUEST_GRANTED) {
            mediaPlayer = MediaPlayer.create(SplashScreenActivity.this,R.raw.splashscreen_sound);
        }

        // Set an effect for text of type SCALE

        hTextView.setAnimateType(HTextViewType.SCALE);
        hTextView.animateText(" ");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hTextView.setAnimateType(HTextViewType.LINE);
                hTextView.animateText("SwipeFit");
                mediaPlayer = MediaPlayer.create(SplashScreenActivity.this,R.raw.splashscreen_sound);
                mediaPlayer.start();
            }
        }, 2000);

        for (int i = 0; i < 1000000; i++) {

        }

        hTextView.setAnimateType(HTextViewType.ANVIL);
        hTextView.animateText("."); // animate

        hTextView.setAnimateType(HTextViewType.ANVIL);
        hTextView.animateText(". ."); // animate

        hTextView.setAnimateType(HTextViewType.ANVIL);
        hTextView.animateText(". . ."); // animate

        hTextView.setAnimateType(HTextViewType.SCALE);
        hTextView.animateText("SWIPE . PICK . BUY"); // animate

        gifTextView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MyLoginActivity.class);
                if (intent != null) {
                    startActivity(intent);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    public void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();
             // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}
