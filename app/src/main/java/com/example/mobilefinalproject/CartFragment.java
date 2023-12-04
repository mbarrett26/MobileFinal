package com.example.mobilefinalproject;

import static android.view.View.GONE;

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
import android.widget.CompoundButton;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(getLayoutInflater());

        // Set up toolbar
        toolbar = binding.menuBarCart.toolbar;
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            // Set custom back button on the toolbar
            Drawable drawable = getResources().getDrawable(R.drawable.back);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Drawable newDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 25, 25, true));
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(newDrawable);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");

        // Set up toolbar menu
        setToolbarMenu();

        // Set up RecyclerView and adapter
        binding.cartList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new cartAdapter(getActivity());
        adapter.setQuantityChangeListener((cartAdapter.QuantityChangeListener) this);
        binding.cartList.setAdapter(adapter);

        // Calculate subtotal and update UI
        subtotalCalc = adapter.calculateTotalPrice();
        updatePrice();

        // Show or hide elements based on cart contents
        if (subtotalCalc == 0.00) {
            binding.radioGroup.setVisibility(GONE);
            binding.cartList.setVisibility(GONE);
            binding.creditCardView.setVisibility(GONE);
        } else {
            binding.cartEmptyPrompt.setVisibility(GONE);
        }

        // Retrieve username and user ID
        username = getArguments().getString("username");
        userID = getArguments().getLong("id");

        return binding.getRoot();
    }

    @Override
    public void onQuantityChanged() {
        subtotalCalc = adapter.calculateTotalPrice(); // Recalculate subtotal
        updatePrice(); // Update prices
    }

    // Update the prices displayed on the UI
    private void updatePrice() {
        taxCalc = subtotalCalc * 0.13; // Calculate tax based on subtotal
        totalCalc = subtotalCalc + taxCalc; // Calculate total including tax

        // Display calculated values formatted as currency in the UI
        binding.subtotal.setText(String.format("$%.2f", subtotalCalc));
        binding.tax.setText(String.format("$%.2f", taxCalc));
        binding.total.setText(String.format("$%.2f", totalCalc));
    }

    // Set up the toolbar menu for this fragment
    private void setToolbarMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                // Inflate the menu layout and hide specific menu items
                menuInflater.inflate(R.menu.main_menu, menu);
                MenuItem searchView = menu.findItem(R.id.action_search);
                MenuItem cart = menu.findItem(R.id.action_cart);

                searchView.setVisible(false); // Hide search view
                cart.setVisible(false); // Hide cart view
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                // Handle menu item selection, specifically the home/up button
                if (menuItem.getItemId() == android.R.id.home) {
                    // Navigate back to the previous fragment with username and userID data
                    Bundle bundlePass = new Bundle();
                    bundlePass.putString("username", username);
                    bundlePass.putLong("id", userID);

                    NavHostFragment.findNavController(CartFragment.this)
                            .navigate(R.id.action_cartFragment_to_fourthFragment, bundlePass);
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // Set up functionality for order button click
        binding.orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if items are in the cart before placing an order
                if (adapter.getItemCount() > 0) {
                    makeOrder(); // Place the order
                    Toast.makeText(getActivity(), "Order has been placed", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Please add an item to the Cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Listen for changes in payment method selection (store/online)
        binding.radioStore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Show the credit card view if "store" payment is selected
                binding.creditCardView.setVisibility(View.VISIBLE);
            }
        });

        binding.radioOnline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Hide the credit card view if "online" payment is selected
                binding.creditCardView.setVisibility(GONE);
            }
        });
    }

    // Place an order with the current cart items
    public void makeOrder() {
        // Create an orderModel and add it to the database
        orderModel input = new orderModel(adapter.getAllItemNames().toString(), totalCalc, userID);
        db.addOrder(input); // Add order to the database

        adapter.clearCartInSharedPreferences(); // Clear cart items from SharedPreferences

        // Navigate to the order end fragment with username and userID data
        Bundle bundlePass = new Bundle();
        bundlePass.putString("username", username);
        bundlePass.putLong("id", userID);

        NavHostFragment.findNavController(CartFragment.this)
                .navigate(R.id.action_cartFragment_to_orderEndFragment, bundlePass);
    }
}