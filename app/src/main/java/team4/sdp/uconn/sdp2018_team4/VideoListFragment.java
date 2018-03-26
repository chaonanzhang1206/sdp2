package team4.sdp.uconn.sdp2018_team4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;


public class VideoListFragment extends Fragment {
    private RecyclerView mVideoListView;
    private Button mScanVideoBtn;

    public static final String CUR_VIDEO = "CurrentVideo";
    public static final String VIDEO_LIST= "VideoList";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video_list, container, false);

        ImageView search_bt = rootView.findViewById(R.id.Video_Button_Search);

        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), VideoService.class);
                startActivity(intent);
            }
        });


        return rootView;
    }
}
