package com.example.mobilefinalproject;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobilefinalproject.databinding.FragmentFourthBinding;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FourthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FourthFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentFourthBinding binding;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    String output;
    FifthFragment ldf;
    Bundle bundle;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FourthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FourthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FourthFragment newInstance(String param1, String param2) {
        FourthFragment fragment = new FourthFragment();
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

        /*
        DBHandler db = new DBHandler(getActivity());
        byte[] burger = bitmapToByte(getResources().getDrawable(R.drawable.burger));
        db.addItem(new itemModel("Burger", 5.99, burger, "Very Boorger Nom Nom", 500, "burger"));
        db.addItem(new itemModel("Cheese Burger", 5.99, burger, "Very Boorger Nom Nom", 500, "burger"));
        db.addItem(new itemModel("Bacon Burger", 5.99, burger, "Very Boorger Nom Nom", 500, "burger"));
        db.addItem(new itemModel("Bacon Cheese Burger", 5.99, burger, "Very Boorger Nom Nom", 500, "burger"));
        db.addItem(new itemModel("Fries", 2.99, burger, "No boorger :(", 360, "nope"));
        db.addItem(new itemModel("Pizza", 15.99, burger, "No boorger :(", 360, "nope"));
        db.addItem(new itemModel("Chicken", 25.99, burger, "No boorger :(", 360, "nope"));
        */
    }

    public byte[] bitmapToByte(Drawable image){
        Bitmap bitmap = ((BitmapDrawable)image).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();

        return bitmapdata;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFourthBinding.inflate(getLayoutInflater());

        toolbar = binding.menuBarMain.toolbar;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");

        drawerLayout = binding.drawerMain;
        drawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        navigationView = binding.navigationViewMain;
        navigationView.setNavigationItemSelectedListener(this);

        setToolbarMenu();
        
        return binding.getRoot();
    }

    private void setToolbarMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu);

                MenuItem searchView = menu.findItem(R.id.action_search);
                searchView.setVisible(false);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_search:
                        //Do Something

                    case R.id.action_cart:
                        Toast.makeText(getActivity(), "Cart Button Clicked", Toast.LENGTH_SHORT).show();
                        //Do Something
                }

                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.itemLocator:
                Toast.makeText(getActivity(), "Locator Button Clicked", Toast.LENGTH_SHORT).show();
                //Do Something

            case R.id.userOrders:
                Toast.makeText(getActivity(), "Order Button Clicked", Toast.LENGTH_SHORT).show();
                //Do Something

            case R.id.userLogout:
                Toast.makeText(getActivity(), output + " has Logged out", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(FourthFragment.this).navigate(R.id.action_fourthFragment_to_firstFragment);
        }

        return false;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        output = getArguments().getString("username");
        Toast.makeText(getActivity(), "Logged In as: " + output, Toast.LENGTH_SHORT).show();

        binding.imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("category", "burger");
                NavHostFragment.findNavController(FourthFragment.this).navigate(R.id.action_fourthFragment_to_fifthFragment, bundle);
            }
        });

        binding.imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("category", "nope");
                NavHostFragment.findNavController(FourthFragment.this).navigate(R.id.action_fourthFragment_to_fifthFragment, bundle);
            }
        });
    }
}