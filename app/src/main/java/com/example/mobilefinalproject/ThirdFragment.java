package com.example.mobilefinalproject;

import android.accounts.Account;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.mobilefinalproject.databinding.FragmentSecondBinding;
import com.example.mobilefinalproject.databinding.FragmentThirdBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment {

    private FragmentThirdBinding binding;
    VideoView videoView;

    DBHandler db;
    private EditText passInp,userInp;

    public ThirdFragment() {
        // Required empty public constructor
    }


    public static ThirdFragment newInstance() {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentThirdBinding.inflate(getLayoutInflater());



        videoView = (VideoView) binding.vvRegisterBackground;
        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.mp_video);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                mediaPlayer.setLooping(true);
            }
        });


        return binding.getRoot();
    }


    @Override
    public void onResume(){
    //    videoView.resume();
        super.onResume();
    }

    @Override
    public void onPause(){
  //      videoView.suspend();
        super.onPause();
    }

    @Override
    public void onDestroy(){
    //    videoView.stopPlayback();
        super.onDestroy();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new DBHandler(getActivity());
        userInp=binding.edUsername;
        passInp=binding.edPassword;

        binding.btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               NavHostFragment.findNavController(ThirdFragment.this).navigate(R.id.action_thirdFragment_to_firstFragment);
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                NavHostFragment.findNavController(ThirdFragment.this).navigate(R.id.action_thirdFragment_to_firstFragment);
            }
        });
    }

    public void registerUser(){
        String userName = String.valueOf(userInp.getText()).trim();
        String userPass = String.valueOf(passInp.getText()).trim();

        if (!(userName.isEmpty() || userPass.isEmpty())){
            User reg = new User(userName,userPass);  //Creating a new user object.
           //Toast.makeText(getActivity(), "Valid user input", Toast.LENGTH_SHORT).show();

            if (!(db.checkUser(userName))){
                db.addUser(reg);
                Toast.makeText(getActivity(), "User Added", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(getActivity(), "User already exists. Please change username", Toast.LENGTH_SHORT).show();
            }

           //new Thread(reg).start();  //starting the registration thread.
        }else{
            Toast.makeText(getActivity(), "Invalid form input", Toast.LENGTH_SHORT).show();
        }

    }
}