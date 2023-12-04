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

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FifthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FifthFragment extends Fragment {

    private FragmentFifthBinding binding;
    private DBHandler dbHandler;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private List<itemModel> items;
    private List<itemModel> filteredItems;
    private Adapter adapter;
    private String category;
    private String username;
    private Long userID;
    private Bundle bundle;
    private List<itemModel> cart;




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

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHandler = new DBHandler(getActivity());
        bundle = new Bundle();
        category = getArguments().getString("category");
        username = getArguments().getString("username");
        userID = getArguments().getLong("id");
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
        filteredItems = new ArrayList<>();
        for (itemModel item : items) {
            if (item.getCategory().equals(category)) {
                filteredItems.add(item);
            }
        }

        binding.menuList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new Adapter(getActivity(), filteredItems);
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

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        // Filter the items based on the search query
                        filterItems(newText);
                        return true;
                    }
                });
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case android.R.id.home:
                        //bundle.putString("cart", Adapter.cart);
                        NavHostFragment.findNavController(FifthFragment.this).navigate(R.id.action_fifthFragment_to_fourthFragment,makeBundle());
                        break;

                    case R.id.action_cart:
                        NavHostFragment.findNavController(FifthFragment.this).navigate(R.id.action_fifthFragment_to_cartFragment,makeBundle());
                        //Do Something
                        break;
                }

                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void filterItems(String query) {
        List<itemModel> filteredList = new ArrayList<>();
        for (itemModel item : filteredItems) {
            // Filter logic based on item attributes (e.g., name, description)
            if (item.getItemName().toLowerCase().contains(query.toLowerCase())
                    || item.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }

        // Update the adapter with the filtered items
        adapter.filterList(filteredList);
    }

    private Bundle makeBundle(){ //used to make the bundle that is passed between fragments
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putLong("id",userID);

        return bundle;
    }
}