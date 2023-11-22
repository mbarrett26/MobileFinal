package com.example.mobilefinalproject;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.MenuProvider;
import androidx.drawerlayout.widget.DrawerLayout;
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
import android.widget.Toast;

import com.example.mobilefinalproject.databinding.FragmentFifthBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FifthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FifthFragment extends Fragment {

    private FragmentFifthBinding binding;
    DBHandler dbHandler;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    List<itemModel> items;
    Adapter adapter;
    String category;
    String username;
    Bundle bundle;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FifthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FifthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FifthFragment newInstance(String param1, String param2) {
        FifthFragment fragment = new FifthFragment();
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
        dbHandler = new DBHandler(getActivity());
        bundle = new Bundle();
        category = getArguments().getString("category");
        username = getArguments().getString("username");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFifthBinding.inflate(getLayoutInflater());

        toolbar = binding.menuBarMenu.toolbar;
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

        items = dbHandler.getItems();
        binding.menuList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new Adapter(getActivity(), items, category);
        binding.menuList.setAdapter(adapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing); // Define your spacing in pixels
        binding.menuList.addItemDecoration(new itemDecoration(spacingInPixels));

        return binding.getRoot();
    }

    private void setToolbarMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu);

                MenuItem menuItem = menu.findItem(R.id.action_search);
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
                searchView.setIconified(false);
                searchView.setIconifiedByDefault(false);
                searchView.setQueryHint("Type something...");
                searchView.clearFocus();
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case android.R.id.home:
                        //bundle.putString("username", username);
                        NavHostFragment.findNavController(FifthFragment.this).navigate(R.id.action_fifthFragment_to_fourthFragment, bundle);
                        break;

                    case R.id.action_search:
                        //Do Something
                        break;

                    case R.id.action_cart:
                        Toast.makeText(getActivity(), "Cart Button Clicked", Toast.LENGTH_SHORT).show();
                        //Do Something
                        break;
                }

                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}