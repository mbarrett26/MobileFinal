package com.example.mobilefinalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private Toolbar toolbar;
    private reviewAdapter adapter;
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

        toolbar = binding.menuBarReviews.toolbar;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        if(((AppCompatActivity)getActivity()).getSupportActionBar()!=null){
            Drawable drawable= getResources().getDrawable(R.drawable.back);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 25, 25, true));
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(newdrawable);
        }
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");

        setToolbarMenu();

        List<reviewModel> list = db.getReview(5);
        binding.reviewList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new reviewAdapter(getActivity(), list);
        binding.reviewList.setAdapter(adapter);

        return binding.getRoot();
    }

    private void setToolbarMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {

            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu);
                MenuItem searchView = menu.findItem(R.id.action_search);
                MenuItem cart = menu.findItem(R.id.action_cart);

                searchView.setVisible(false);
                cart.setVisible(false);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == android.R.id.home){
                    Bundle bundlePass = new Bundle();
                    bundlePass.putString("username", username);
                    bundlePass.putLong("id",userID);

                    NavHostFragment.findNavController(reviewFragment.this).navigate(R.id.action_reviewFragment_to_fourthFragment,bundlePass);
                }

                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
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
    }

    private void getReviews() {

    }

    protected void onCreateDialog(int id) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        LinearLayout lila1 = new LinearLayout(getActivity());
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
                if (!(String.valueOf(input.getText()).trim().isEmpty() || input1.getText().toString().trim().isEmpty())) {
                    int rating = Math.min(Integer.parseInt(input.getText().toString().trim()), 5);
                    String reviewText = input1.getText().toString().trim();

                    Toast.makeText(getActivity(), "Rating " + rating, Toast.LENGTH_SHORT).show();
                    db.addReview(userID,rating,reviewText);
                    adapter.refresh(db.getReview(5));
                } else {
                    Toast.makeText(getActivity(), "Please ensure fields aren't empty", Toast.LENGTH_SHORT).show();
                }

            }
        });
        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

        alert.create().show();

    }

}