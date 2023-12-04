package com.example.mobilefinalproject;

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

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private orderAdapter adapter;
    private Toolbar toolbar;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOrderViewBinding.inflate(getLayoutInflater());

        // Set up the toolbar
        toolbar = binding.menuBarOrders.toolbar;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        if(((AppCompatActivity)getActivity()).getSupportActionBar()!=null){
            // Set custom back button
            Drawable drawable= getResources().getDrawable(R.drawable.back);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 25, 25, true));
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(newdrawable);
        }
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");

        setToolbarMenu(); // Set up the toolbar menu

        return binding.getRoot();
    }

    // Method to set up the toolbar menu
    private void setToolbarMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                // Inflate the menu items
                menuInflater.inflate(R.menu.main_menu, menu);
                MenuItem searchView = menu.findItem(R.id.action_search);
                MenuItem cart = menu.findItem(R.id.action_cart);

                // Hide search and cart items
                searchView.setVisible(false);
                cart.setVisible(false);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == android.R.id.home) {
                    // Navigate back to the fourth fragment when the home button is clicked
                    Bundle bundlePass = new Bundle();
                    bundlePass.putString("username", username);
                    bundlePass.putLong("id", userID);

                    NavHostFragment.findNavController(orderViewFragment.this)
                            .navigate(R.id.action_orderViewFragment_to_fourthFragment, bundlePass);
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    // Set up the fragment view when it's created
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // Retrieve username and userID from arguments
        username = getArguments().getString("username");
        userID = getArguments().getLong("id");

        // Retrieve orders for the user from the database
        List<orderModel> list = db.getOrders(userID);

        // Show a message if the order list is empty
        if(list.isEmpty()){
            binding.noOrder.setVisibility(View.VISIBLE);
        }

        // Set up RecyclerView to display orders using the adapter
        binding.ordersList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new orderAdapter(getActivity(), list);
        binding.ordersList.setAdapter(adapter);
    }

}