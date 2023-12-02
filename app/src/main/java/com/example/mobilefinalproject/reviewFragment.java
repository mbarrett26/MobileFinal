package com.example.mobilefinalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mobilefinalproject.databinding.FragmentReviewBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link reviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class reviewFragment extends Fragment {


    private FragmentReviewBinding binding;
    DBHandler db;

    String username;
    Long userID;

    public reviewFragment() {
        // Required empty public constructor
    }


    public static reviewFragment newInstance(String param1, String param2) {
        reviewFragment fragment = new reviewFragment();
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

        binding = FragmentReviewBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {


        username = getArguments().getString("username");
        userID = getArguments().getLong("id");

        //String reviewText = "This is another review";
       // db.addReview(1,4,reviewText);
       // db.addReview(1,5,reviewText);

        //List<String> lReviews = db.getReview(5);

        //Toast.makeText(getActivity(), "Review"+lReviews, Toast.LENGTH_LONG).show();

        binding.addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if(db.checkOrder(userID)){
                    onCreateDialog(0);
                } else {
                    Toast.makeText(getActivity(), "You must make at least one order before placing a review", Toast.LENGTH_SHORT).show();
                }
            }
         });

        binding.goOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                bundle.putLong("id",userID);


                NavHostFragment.findNavController(reviewFragment.this).navigate(R.id.review_to_orderView,bundle);
            }
        });;
    }

    private void getReviews() {

    }

    protected void onCreateDialog(int id)
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        LinearLayout lila1= new LinearLayout(getActivity());
        lila1.setOrientation(LinearLayout.VERTICAL); //1 is for vertical orientation
        final EditText input = new EditText(getActivity());
        final EditText input1 = new EditText(getActivity());
        input.setHint("Enter a rating (1-5)");
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        input1.setHint("Enter a review");
        lila1.addView(input);
        lila1.addView(input1);
        alert.setView(lila1);

        alert.setIcon(R.drawable.add);
        alert.setTitle("Add a Review");

        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int rating = Integer.parseInt(input.getText().toString().trim());
                String reviewText = input1.getText().toString().trim();

                db.addReview(userID,rating,reviewText);


            } });
        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();    }     });

        alert.create().show();

    }

}