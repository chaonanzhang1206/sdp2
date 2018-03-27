package team4.sdp.uconn.sdp2018_team4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import me.siegenthaler.spotify.webapi.android.model.Token;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class MusicPlayerActivity extends AppCompatActivity implements SpotifyPlayer.NotificationCallback,ConnectionStateCallback {


    private static final String CLIENT_ID = "d2b0d9c41c00483fa04a0e6ffd2517b4";
    private static final String REDIRECT_URI = "https://sptt.com/callback";
    private Player mPlayer;
    private static final int REQUEST_CODE = 1337;
    private String title = "";

    private final Player.OperationCallback mOperationCallback = new Player.OperationCallback() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(Error error) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);


        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);

        Intent intent = getIntent();
        final String Uri = intent.getStringExtra("Uri");
        final String Token = intent.getStringExtra("Token");
        title = intent.getStringExtra("Title");

        Config playerConfig = new Config(this, Token, CLIENT_ID);
        Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
            @Override
            public void onInitialized(SpotifyPlayer spotifyPlayer) {
                mPlayer = spotifyPlayer;
                mPlayer.addConnectionStateCallback(MusicPlayerActivity.this);
                mPlayer.addNotificationCallback(MusicPlayerActivity.this);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("PlayActivity", "Could not initialize player: " + throwable.getMessage());
            }
        });


        mPlayer.playUri(null,Uri,0,0);
        final boolean[] isPlaying = {true};

        final ImageButton mPlayPauseBtn = findViewById(R.id.Music_Button_Play);

        mPlayPauseBtn.setImageResource(R.mipmap.music_button_pause);
        mPlayPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying[0]) {
                    mPlayer.pause(null);
                    mPlayPauseBtn.setImageResource(R.mipmap.music_button_play);
                    isPlaying[0] = false;
                } else {
                    mPlayer.resume(mOperationCallback);
                    mPlayPauseBtn.setImageResource(R.mipmap.music_button_pause);
                    isPlaying[0] = true;
                }
            }
        });

        ImageButton FavoriteButton = findViewById(R.id.Music_Button_Favorite);

        FavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });







    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Token token = new Token();
                token.setmToken(response.getAccessToken());
                token.setmType(response.getType().toString());
                token.setmExpiresTime(response.getExpiresIn());
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(MusicPlayerActivity.this);
                        mPlayer.addNotificationCallback(MusicPlayerActivity.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("PlayActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("PlayActivity", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("PlayActivity", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("PlayActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("PlayActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Error var1) {
        Log.d("PlayActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("PlayActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("PlayActivity", "Received connection message: " + message);
    }
    public void showDialog(){
        final EditText edittext = new EditText(this);
        edittext.setText(title);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add this music to Favorite");
        builder.setMessage("Save name as");
        builder.setView(edittext);
        builder.setPositiveButton("SAVE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Put insert code at here

                        makeText(getApplicationContext(),edittext.getText().toString() + "\n is saved" , LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();

    }
    }

