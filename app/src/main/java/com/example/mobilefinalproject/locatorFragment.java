package com.example.mobilefinalproject;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
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
import android.widget.SearchView;

import com.example.mobilefinalproject.databinding.FragmentLocatorBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link locatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class locatorFragment extends Fragment {

    private FragmentLocatorBinding binding;
    private Toolbar toolbar;
    String username;
    Long userID;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public locatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment locatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static locatorFragment newInstance(String param1, String param2) {
        locatorFragment fragment = new locatorFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLocatorBinding.inflate(getLayoutInflater());

        toolbar = binding.menuBarLocator.toolbar;
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

                    NavHostFragment.findNavController(locatorFragment.this).navigate(R.id.action_locatorFragment_to_fourthFragment,bundlePass);
                }

                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        username = getArguments().getString("username");
        userID = getArguments().getLong("id");
    }
}