package team4.sdp.uconn.sdp2018_team4;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText search_edit;
    Button search_button;
    ListView search_result_list;
    private static final int FRAGMENT_MUSIC = 0;
    private static final int FRAGMENT_VIDEO = 1;
    private static final int FRAGMENT_HOME = 2;
    private ViewPager _ViewPager;
    private List<Fragment> fragmentList;
    private ImageButton MusicButton;
    private ImageButton VideoButton;
    private ImageButton HomeButton;

    ArrayList<String> list_itemArrayList;

    // For database
    EditText editid, editName, editTime, editfavoriteid;
    private ImageButton MusicButtonList;
    private Button btnlogin;
    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        initData();
        initViews();

        //MusicButtonList = (ImageButton)findViewById(R.id.Music_Button_List);
        //btnlogin = (Button)findViewById(R.id.btn_login);

    }


    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new MusicListFragment());
        fragmentList.add(new VideoListFragment());
        fragmentList.add(new HomeFragment());
    }


    private void initViews() {
        _ViewPager = findViewById(R.id.vp_container);
        _ViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });

        _ViewPager.setCurrentItem(FRAGMENT_MUSIC);

        MusicButton = findViewById(R.id.MainActivity_Button_MusicIcon);
        MusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _ViewPager.setCurrentItem(FRAGMENT_MUSIC);
            }
        });
        VideoButton = findViewById(R.id.MainActivity_Button_VideoIcon);
        VideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _ViewPager.setCurrentItem(FRAGMENT_VIDEO);
            }
        });
        HomeButton = findViewById(R.id.MainActivity_Button_HomeIcon);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _ViewPager.setCurrentItem(FRAGMENT_HOME);


            }
        });
    }


}