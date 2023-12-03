package com.example.mobilefinalproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobilefinalproject.databinding.FragmentOrderViewBinding;
import com.example.mobilefinalproject.databinding.FragmentReviewBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link orderViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class orderViewFragment extends Fragment {


    private FragmentOrderViewBinding binding;

    DBHandler db;

    String username;
    Long userID;

    public orderViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment orderViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static orderViewFragment newInstance(String param1, String param2) {
        orderViewFragment fragment = new orderViewFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOrderViewBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        username = getArguments().getString("username");
        userID = getArguments().getLong("id");
        Toast.makeText(getActivity(), "ID: "+userID, Toast.LENGTH_SHORT).show();
        //db.addOrder(new orderModel("Taco",72.99,userID));

        binding.showOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<orderModel> list = db.getOrders(userID);

                Toast.makeText(getActivity(), ""+list.get(0), Toast.LENGTH_SHORT).show();

                //Toast.makeText(getActivity(), "List"+list.get(0).toString(), Toast.LENGTH_SHORT).show(); //make into recycler
            }
        });;
    }

}