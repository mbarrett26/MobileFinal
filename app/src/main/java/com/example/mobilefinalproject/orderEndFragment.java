package com.example.mobilefinalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilefinalproject.databinding.FragmentFourthBinding;
import com.example.mobilefinalproject.databinding.FragmentOrderEndBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link orderEndFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class orderEndFragment extends Fragment {

    private Handler handler;
    private FragmentOrderEndBinding binding;
    String username;
    long userID;
    private Bundle bundle;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public orderEndFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment orderEndFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static orderEndFragment newInstance(String param1, String param2) {
        orderEndFragment fragment = new orderEndFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        bundle = new Bundle();
        username = getArguments().getString("username");
        userID = getArguments().getLong("id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOrderEndBinding.inflate(getLayoutInflater());

        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToFourthFragment();
            }
        }, 5000);

        return binding.getRoot();
    }

    private void navigateToFourthFragment() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_orderEndFragment_to_fourthFragment, makeBundle());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Remove any pending callbacks to prevent leaks
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private Bundle makeBundle(){ //used to make the bundle that is passed between fragments
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putLong("id",userID);

        return bundle;
    }
}