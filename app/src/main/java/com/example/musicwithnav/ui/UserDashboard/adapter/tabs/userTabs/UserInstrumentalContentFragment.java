package com.example.musicwithnav.ui.UserDashboard.adapter.tabs.userTabs;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicwithnav.R;
import com.example.musicwithnav.models.Sound;
import com.example.musicwithnav.ui.UserDashboard.adapter.UserSoundFragmentAdapter;
import com.example.musicwithnav.ui.home.inHomeFragment.Adapter.SoundFragmentAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInstrumentalContentFragment extends Fragment {


    private View soundsView;
    private RecyclerView myRecylceViewSoundsList;

    ProgressDialog dialog;

    // FirebaseRecyclerAdapter<Sound, ArtistFragment.SoundsViewHolder> adapter;

    UserSoundFragmentAdapter madapter;

    // private FirebaseDatabase database; //= FirebaseDatabase.getInstance();


    private DatabaseReference soundsRef, userRef;
    private DatabaseReference samplesRef, songRef, vocalRef;

    private FirebaseAuth auth;

    //sound item properties:
   /* private String soundName;
    private String soundBeatProducerID;
    private String soundBeatProducerName;
    private String soundVocalID;
    private String soundVocalName;*/
    private static String soundImageUrl;
    private static String soundDownloadUrl;
    private String soundLength;
    private String soundID;


    private String currentUserId;

    private Button btn_play;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        soundsView = inflater.inflate(R.layout.fragment_instrumental, container, false);

        myRecylceViewSoundsList = soundsView.findViewById(R.id.for_instrumental_list_recycler);
        //   myRecylceViewSoundsList.setHasFixedSize(true);
        myRecylceViewSoundsList.setLayoutManager(new LinearLayoutManager(getContext()));

        dialog = new ProgressDialog(getContext());
        dialog.show();
        dialog.setCancelable(false);
        auth = FirebaseAuth.getInstance();
        currentUserId = auth.getCurrentUser().getUid();


        samplesRef = FirebaseDatabase.getInstance().getReference().child("sound").child("Instrumental");
        samplesRef.orderByChild("soundVocalID").equalTo(currentUserId);


        //adapterSetter();


        samplesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Sound> soundsList = new ArrayList<>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Sound sound = dataSnapshot1.getValue(Sound.class);

                    soundsList.add(sound);
                }
                dialog.dismiss();
                madapter = new UserSoundFragmentAdapter(soundsList, getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false);
                myRecylceViewSoundsList.setLayoutManager(layoutManager);
                myRecylceViewSoundsList.setAdapter(madapter);
                madapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });


        return soundsView;
    }

}
