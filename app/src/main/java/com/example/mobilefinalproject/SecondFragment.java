package com.example.mobilefinalproject;

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
import android.widget.Toast;
import android.widget.VideoView;

import com.example.mobilefinalproject.databinding.FragmentSecondBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    VideoView videoView;

    private DBHandler db;

    private EditText passInp,userInp;

    public SecondFragment() {
        // Required empty public constructor
    }


    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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
        // Inflating the layout for this fragment using data binding
        binding = FragmentSecondBinding.inflate(getLayoutInflater());

        // Setting up the VideoView to play a video
        videoView = (VideoView) binding.vvLoginBackground;
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
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_secondFragment_to_firstFragment);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Authenticating user credentials when the login button is clicked
                loginUser();
            }
        });
    }

    private void loginUser() {
        // Retrieving username and password from EditText fields
        String userName = String.valueOf(userInp.getText()).trim();
        String userPass = String.valueOf(passInp.getText()).trim();

        // Validating user input for username and password
        if (!(userName.isEmpty() || userPass.isEmpty())) {
            User reg = new User(userName, userPass); // Creating a new user object

            if (db.checkUser(userName)) {
                if (db.checkAcc(reg)) {
                    // Navigating to FourthFragment if the user is authenticated successfully
                    Bundle bundle = new Bundle();
                    bundle.putString("username", userName);
                    bundle.putLong("id", db.getUserID(userName));
                    NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_secondFragment_to_fourthFragment, bundle);
                } else {
                    // Handling invalid password
                    Toast.makeText(getActivity(), "Invalid Password", Toast.LENGTH_SHORT).show();
                    passInp.setError("Invalid Password");
                }
            } else {
                // Handling non-existent user
                Toast.makeText(getActivity(), "User does not exist. Please enter a valid username", Toast.LENGTH_SHORT).show();
                userInp.setError("Invalid Username");
            }
        } else {
            // Handling empty form input
            Toast.makeText(getActivity(), "Invalid form input", Toast.LENGTH_SHORT).show();
            passInp.setError("Please enter text");
            userInp.setError("Please enter text");
        }
    }
}