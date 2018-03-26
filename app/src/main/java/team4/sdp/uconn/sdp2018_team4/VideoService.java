package team4.sdp.uconn.sdp2018_team4;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by huskyang on 2018/3/3.
 */

public class VideoService extends YouTubeBaseActivity  {

    private YouTubePlayerView youTubePlayerView;
    ImageButton b;
    private YouTubePlayer.OnInitializedListener onInitializedListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                youTubePlayer.loadVideo("wg8zAvZDs2c");

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }

        };
        b = (ImageButton) findViewById(R.id.Music_Button_Play);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                youTubePlayerView.initialize("AIzaSyCbPA6jyrdn1j3YOqtTGdbNbb0QKwVc-ho", onInitializedListener);

            }
        });

    }
    }