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

import com.example.mobilefinalproject.databinding.FragmentCartBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements cartAdapter.QuantityChangeListener {

    private FragmentCartBinding binding;
    private cartAdapter adapter;
    private Toolbar toolbar;
    private double subtotalCalc = 0.00;
    private double taxCalc = 0.00;
    private double totalCalc = 0.00;
    private String username;
    private Long userID;

    private DBHandler db;

    public CartFragment() {
        // Required empty public constructor
    }


    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        binding = FragmentCartBinding.inflate(getLayoutInflater());

        toolbar = binding.menuBarCart.toolbar;
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

        binding.cartList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new cartAdapter(getActivity());
        adapter.setQuantityChangeListener((cartAdapter.QuantityChangeListener) this);
        binding.cartList.setAdapter(adapter);

        subtotalCalc = adapter.calculateTotalPrice();
        updatePrice();

        username = getArguments().getString("username");
        userID = getArguments().getLong("id");

        return binding.getRoot();
    }

    @Override
    public void onQuantityChanged() {
        subtotalCalc = adapter.calculateTotalPrice(); // Recalculate subtotal
        updatePrice(); // Update prices
    }

    private void updatePrice() {
        taxCalc = subtotalCalc * 0.13;
        totalCalc = subtotalCalc + taxCalc;

        binding.subtotal.setText(String.format("$%.2f", subtotalCalc));
        binding.tax.setText(String.format("$%.2f", taxCalc));
        binding.total.setText(String.format("$%.2f", totalCalc));
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

                    NavHostFragment.findNavController(CartFragment.this).navigate(R.id.action_cartFragment_to_fourthFragment,bundlePass);
                }

                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        binding.orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getItemCount()>0) {
                    makeOrder();
                } else {
                    Toast.makeText(getActivity(), "Please add an item to the Cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void makeOrder(){
        Toast.makeText(getActivity(), "Item Names"+adapter.getAllItemNames().toString(), Toast.LENGTH_SHORT).show();

        orderModel input = new orderModel(adapter.getAllItemNames().toString(),totalCalc,userID);

        db.addOrder(input);

        adapter.clearCartInSharedPreferences();

        Bundle bundlePass = new Bundle();
        bundlePass.putString("username", username);
        bundlePass.putLong("id",userID);

        NavHostFragment.findNavController(CartFragment.this).navigate(R.id.action_cartFragment_to_fourthFragment,bundlePass);
    }

}