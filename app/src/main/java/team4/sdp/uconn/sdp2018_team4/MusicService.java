package team4.sdp.uconn.sdp2018_team4;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.toolbox.Volley;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import me.siegenthaler.spotify.webapi.android.ClientRestAPI;
import me.siegenthaler.spotify.webapi.android.model.Page;
import me.siegenthaler.spotify.webapi.android.model.Token;
import me.siegenthaler.spotify.webapi.android.model.Track;

public class MusicService extends AppCompatActivity implements
        SpotifyPlayer.NotificationCallback, ConnectionStateCallback {

    private ClientRestAPI mClient;
    private static final String CLIENT_ID = "d2b0d9c41c00483fa04a0e6ffd2517b4";
    private static final String REDIRECT_URI = "https://sptt.com/callback";
    private Player mPlayer;
    private static final int REQUEST_CODE = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_service);

        mClient = new ClientRestAPI(Volley.newRequestQueue(getApplicationContext()));
       // items = new ArrayList<Track>();


        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
        final ArrayList<String> testlist = new ArrayList<String>();
        ListView listview = (ListView)findViewById(R.id.listview);
        //final CustomAdapter adapter = new CustomAdapter(this,0,items);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.music_listview,
                R.id.listContent,
                testlist);
        listview.setAdapter(adapter);

        Button searchBT = (Button)findViewById(R.id.search_button);
        final EditText editText = (EditText)findViewById(R.id.search_input);

        searchBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClient.getRequestQueue().cancelAll("SEARCH");
                String input = editText.getText().toString();
                mClient.searchTrack(input).setListener(new Response.Listener<Page<Track>>() {
                    @Override
                    public void onResponse(Page<Track> tracks) {
                        final List<Track> results = tracks.getItems();
                        testlist.clear();
                        for (Track item : results) {
                            Log.d("Track", item.getArtist(0).getName() + " - " + item.getName());
                            testlist.add("Artist : "+item.getArtist(0).getName() + "\nName : " + item.getName());
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).setErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                    }
                }).setLimit(25).setTag("SEARCH").build();

            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Token token = new Token();
                token.setmToken(response.getAccessToken());
                token.setmType(response.getType().toString());
                token.setmExpiresTime(response.getExpiresIn());
                mClient.setToken(token);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(MusicService.this);
                        mPlayer.addNotificationCallback(MusicService.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }
    @Override
    public void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }


    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Error error) {
        Log.d("MainActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String s) {
        Log.d("MainActivity", "Received connection message: " + s);
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("MainActivity", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("MainActivity", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }



}
