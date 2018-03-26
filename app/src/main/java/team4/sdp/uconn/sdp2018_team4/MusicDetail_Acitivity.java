package team4.sdp.uconn.sdp2018_team4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MusicDetail_Acitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_detail_view);
        Intent intent = getIntent();

        String title = intent.getStringExtra("Item");

        TextView tt = (TextView)findViewById(R.id.MusicDetail_Activity_spacer);

        tt.setText(title);

        Button bio_bt = (Button)findViewById(R.id.MusicDetail_Activity_artistBio);
        Button alb_bt = (Button)findViewById(R.id.MusicDetail_Activity_albumReview);

bio_bt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent it1 = new Intent(MusicDetail_Acitivity.this, Music_Bio_Review.class);
        startActivity(it1);
    }
});

alb_bt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent it2 = new Intent(MusicDetail_Acitivity.this, Music_Album_Review.class);
        startActivity(it2);
    }
});

    }
}
