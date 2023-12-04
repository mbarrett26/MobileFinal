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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment using data binding
        binding = FragmentThirdBinding.inflate(getLayoutInflater());

        // Setting up the VideoView to play a video
        videoView = (VideoView) binding.vvRegisterBackground;
        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.mp_video);
        videoView.setVideoURI(uri);
        videoView.start();

        // Looping the video playback
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        // Resuming video playback when the fragment resumes
        super.onResume();
    }

    @Override
    public void onPause() {
        // Pausing video playback when the fragment is paused
        super.onPause();
    }

    @Override
    public void onDestroy() {
        // Stopping video playback when the fragment is destroyed
        super.onDestroy();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DBHandler(getActivity());
        userInp = binding.edUsername;
        passInp = binding.edPassword;

        // Handling click events for buttons in the layout
        binding.btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigating to the FirstFragment
                NavHostFragment.findNavController(ThirdFragment.this).navigate(R.id.action_thirdFragment_to_firstFragment);
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Registering the user when the register button is clicked
                registerUser();
                // Navigating back to the FirstFragment after registration
                NavHostFragment.findNavController(ThirdFragment.this).navigate(R.id.action_thirdFragment_to_firstFragment);
            }
        });
    }

    public void registerUser() {
        // Retrieving username and password from EditText fields
        String userName = String.valueOf(userInp.getText()).trim();
        String userPass = String.valueOf(passInp.getText()).trim();

        // Validating user input for username and password
        if (!(userName.isEmpty() || userPass.isEmpty())) {
            User reg = new User(userName, userPass); // Creating a new user object

            if (!(db.checkUser(userName))) {
                // Adding the user to the database if the username is not already taken
                db.addUser(reg);
                Toast.makeText(getActivity(), "User Added", Toast.LENGTH_SHORT).show();
            } else {
                // Displaying a message if the username already exists
                Toast.makeText(getActivity(), "User already exists. Please change the username", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Displaying a message for invalid form input
            Toast.makeText(getActivity(), "Invalid form input", Toast.LENGTH_SHORT).show();
        }
    }
}