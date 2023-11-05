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
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(getLayoutInflater());

      /*  videoView = (VideoView) binding.vvLoginBackground;
        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.mp_video);
        //videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                mediaPlayer.setLooping(true);
            }
        }); */

        return binding.getRoot();
    }


    @Override
    public void onResume(){
       // videoView.resume();
        super.onResume();
    }

    @Override
    public void onPause(){
      //  videoView.suspend();
        super.onPause();
    }

    @Override
    public void onDestroy(){
       // videoView.stopPlayback();
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
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_secondFragment_to_firstFragment);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CALL DB AND AUTHENTICATE USER :)
                loginUser();
               // NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_secondFragment_to_fourthFragment);
            }
        });
    }

    private void loginUser()  {
        String userName = String.valueOf(userInp.getText()).trim();
        String userPass = String.valueOf(passInp.getText()).trim();
        if (!(userName.isEmpty() || userPass.isEmpty())){
            User reg = new User(userName,userPass);  //Creating a new user object.
            //Toast.makeText(getActivity(), "Valid user input", Toast.LENGTH_SHORT).show();

            if (db.checkUser(userName)){
                if(db.checkAcc(reg)){
                    Toast.makeText(getActivity(), "Successful login", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_secondFragment_to_fourthFragment);
                  //  Thread.sleep(5000);
                } else {
                    Toast.makeText(getActivity(), "Invalid Password", Toast.LENGTH_SHORT).show();
                    passInp.setError("Invalid Password");
                }
            } else{
                Toast.makeText(getActivity(), "User does not exist. Please enter valid username", Toast.LENGTH_SHORT).show();
                userInp.setError("Invalid Username");
            }

            //new Thread(reg).start();  //starting the registration thread.
        }else{
            Toast.makeText(getActivity(), "Invalid form input", Toast.LENGTH_SHORT).show();
            passInp.setError("Please enter text");
            userInp.setError("Please enter text");
        }
    }
}