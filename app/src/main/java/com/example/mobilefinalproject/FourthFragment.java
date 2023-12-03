package com.example.mobilefinalproject;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import androidx.viewpager2.widget.ViewPager2;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FourthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FourthFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentFourthBinding binding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private String output;
    private FifthFragment ldf;
    private Bundle bundle;
    private List<itemModel> cart;

    private String username;
    private Long userID;

    private SensorManager mSensorManager;
    private float mAccel,mAccelCurrent , mAccelLast;



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

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        bundle = new Bundle();

        mSensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        DBHandler db = new DBHandler(getActivity());

        byte[] burger = bitmapToByte(getResources().getDrawable(R.drawable.burger));
/*
        db.addItem(new itemModel("Tex-Mex Burger", 11.49, burger, "Beef patty topped with spicy jalapeños, pepper jack cheese, guacamole, and salsa", 690, "Specialties"));
        db.addItem(new itemModel("Cajun Sweet Potato Fries", 5.49, burger, "Sweet potato fries seasoned with Cajun spices, served with a zesty dipping sauce", 340, "Sides"));
        db.addItem(new itemModel("Garlic Herb Knots", 6.99, burger, "Soft garlic knots brushed with herb-infused butter, served with marinara sauce", 280, "Appetizers & Salads"));
        db.addItem(new itemModel("Berry Blast Smoothie", 6.29, burger, "A refreshing blend of mixed berries, yogurt, and honey", 220, "Beverages"));
        db.addItem(new itemModel("Red Velvet Cupcake", 4.99, burger, "Moist red velvet cupcake topped with cream cheese frosting", 280, "Desserts"));




        // Assuming the itemModel structure in the code accepts parameters in the order: name, price, image, description, calories, category
// Specialties
        db.addItem(new itemModel("Double Trouble Burger", 12.49, burger, "Two beef patties, double cheese, lettuce, and secret sauce", 850, "Specialties"));
        db.addItem(new itemModel("Hawaiian Luau Burger", 11.29, burger, "Grilled pineapple, teriyaki-glazed chicken, and coleslaw", 690, "Specialties"));
        db.addItem(new itemModel("The Firecracker Burger", 13.99, burger, "Spicy jalapeño, pepper jack cheese, and sriracha mayo", 780, "Specialties"));
        db.addItem(new itemModel("Breakfast Burger", 10.99, burger, "Beef patty topped with bacon, fried egg, and maple aioli", 720, "Specialties"));
        db.addItem(new itemModel("Sriracha Shroom Burger", 11.79, burger, "Spicy sriracha, Swiss cheese, and sautéed mushrooms", 670, "Specialties"));

// Chicken Burgers
        db.addItem(new itemModel("Crispy Chicken Club", 9.49, burger, "Crispy chicken, bacon, lettuce, tomato, and mayo", 610, "Chicken Burgers"));
        db.addItem(new itemModel("Buffalo Chicken Burger", 10.29, burger, "Buffalo sauce, blue cheese dressing, and crispy chicken patty", 680, "Chicken Burgers"));
        db.addItem(new itemModel("Ranchero Chicken Burger", 10.99, burger, "Grilled chicken, ranch dressing, avocado, and tomato", 540, "Chicken Burgers"));
        db.addItem(new itemModel("Honey Mustard Chicken", 9.79, burger, "Grilled chicken, honey mustard, lettuce, and pickles", 520, "Chicken Burgers"));
        db.addItem(new itemModel("Cajun Chicken Burger", 10.79, burger, "Spicy Cajun-seasoned chicken, chipotle mayo, and coleslaw", 600, "Chicken Burgers"));

// Vegetarian Options
        db.addItem(new itemModel("Portobello Burger", 8.99, burger, "Grilled portobello mushroom, roasted red peppers, and pesto", 480, "Vegetarian Options"));
        db.addItem(new itemModel("Quinoa Black Bean Burger", 9.49, burger, "Quinoa-black bean patty, avocado, and lime-cilantro mayo", 420, "Vegetarian Options"));
        db.addItem(new itemModel("Falafel Burger", 8.79, burger, "Crispy falafel patty, hummus, cucumber, and tahini sauce", 550, "Vegetarian Options"));
        db.addItem(new itemModel("Caprese Burger", 9.99, burger, "Mozzarella, tomato, basil pesto, and balsamic glaze", 490, "Vegetarian Options"));
        db.addItem(new itemModel("Mediterranean Veggie Burger", 9.79, burger, "Chickpea patty, feta cheese, and tzatziki sauce", 520, "Vegetarian Options"));

        // Gourmet Burgers
        db.addItem(new itemModel("Truffle Mushroom Burger", 12.99, burger, "Beef patty with truffle aioli, wild mushrooms, and arugula", 680, "Gourmet Burgers"));
        db.addItem(new itemModel("Pesto Turkey Burger", 11.49, burger, "Turkey patty with basil pesto, sundried tomatoes, and provolone", 560, "Gourmet Burgers"));
        db.addItem(new itemModel("Blue Cheese Bacon Burger", 13.29, burger, "Beef patty, blue cheese crumbles, crispy bacon, and caramelized onions", 720, "Gourmet Burgers"));
        db.addItem(new itemModel("Avocado Ranch Burger", 11.99, burger, "Beef patty with avocado, ranch dressing, lettuce, and tomato", 650, "Gourmet Burgers"));
        db.addItem(new itemModel("Miso Glazed Salmon Burger", 14.49, burger, "Grilled salmon patty, miso glaze, cucumber, and wasabi mayo", 590, "Gourmet Burgers"));

// Vegan Options
        db.addItem(new itemModel("BBQ Jackfruit Burger", 10.99, burger, "Pulled jackfruit in BBQ sauce, coleslaw, and vegan mayo", 480, "Vegan Options"));
        db.addItem(new itemModel("Tempeh Teriyaki Burger", 10.79, burger, "Tempeh patty with teriyaki glaze, grilled pineapple, and lettuce", 520, "Vegan Options"));
        db.addItem(new itemModel("Chickpea Spinach Burger", 9.99, burger, "Chickpea patty with spinach, tahini dressing, and pickled onions", 450, "Vegan Options"));
        db.addItem(new itemModel("Vegan Portobello Stack", 11.29, burger, "Grilled portobello, vegan cheese, roasted red peppers, and balsamic glaze", 510, "Vegan Options"));
        db.addItem(new itemModel("Black Bean Beet Burger", 10.49, burger, "Black bean and beetroot patty, avocado, and spicy aioli", 490, "Vegan Options"));

        //---------------------------
        db.addItem(new itemModel("Garlic Parmesan Fries", 4.49, burger, "Crispy fries tossed in garlic butter and Parmesan cheese", 350, "Sides"));
        db.addItem(new itemModel("Sweet Potato Fries", 4.99, burger, "Crispy sweet potato fries served with chipotle mayo", 320, "Sides"));
        db.addItem(new itemModel("Onion Rings", 5.29, burger, "Golden-brown battered onion rings with a side of ranch", 380, "Sides"));
        db.addItem(new itemModel("Chili Cheese Fries", 6.49, burger, "Fries smothered in beef chili and melted cheddar cheese", 480, "Sides"));
        db.addItem(new itemModel("Garlic Breadsticks", 4.79, burger, "Warm garlic-infused breadsticks served with marinara sauce", 280, "Sides"));

        db.addItem(new itemModel("Loaded Nachos", 7.99, burger, "Crispy tortilla chips topped with cheese, jalapeños, salsa, and sour cream", 450, "Appetizers & Salads"));
        db.addItem(new itemModel("Mac and Cheese Bites", 5.99, burger, "Creamy macaroni and cheese bites served with marinara sauce", 410, "Appetizers & Salads"));
        db.addItem(new itemModel("Coleslaw", 3.49, burger, "Freshly shredded cabbage and carrots in a tangy dressing", 150, "Appetizers & Salads"));
        db.addItem(new itemModel("Bacon Jalapeño Poppers", 6.99, burger, "Jalapeños stuffed with cream cheese and wrapped in bacon", 320, "Appetizers & Salads"));
        db.addItem(new itemModel("Seasonal Veggie Salad", 7.49, burger, "Mixed greens, seasonal vegetables, and vinaigrette dressing", 180, "Appetizers & Salads"));

        db.addItem(new itemModel("Classic Milkshake", 4.99, burger, "Creamy vanilla milkshake topped with whipped cream", 350, "Beverages"));
        db.addItem(new itemModel("Iced Caramel Latte", 5.49, burger, "Iced coffee with caramel syrup and frothy milk", 210, "Beverages"));
        db.addItem(new itemModel("Freshly Squeezed Lemonade", 3.99, burger, "Refreshing lemonade made with freshly squeezed lemons", 120, "Beverages"));
        db.addItem(new itemModel("Green Tea Smoothie", 5.79, burger, "Blended green tea with spinach, banana, and honey", 280, "Beverages"));
        db.addItem(new itemModel("Craft Soda", 4.29, burger, "Locally brewed artisanal soda in various flavors", 180, "Beverages"));

        db.addItem(new itemModel("Decadent Chocolate Brownie", 4.49, burger, "Rich chocolate brownie served warm with vanilla ice cream", 420, "Desserts"));
        db.addItem(new itemModel("Classic Apple Pie", 5.99, burger, "Homemade apple pie with a flaky crust, served with whipped cream", 320, "Desserts"));
        db.addItem(new itemModel("Gourmet Cheesecake", 6.49, burger, "Creamy cheesecake topped with seasonal fruit compote", 380, "Desserts"));
        db.addItem(new itemModel("Cinnamon Sugar Churros", 4.79, burger, "Crispy churros coated in cinnamon sugar, served with chocolate sauce", 340, "Desserts"));
        db.addItem(new itemModel("Fruit Sorbet Sampler", 7.29, burger, "Assorted fruit sorbets served in a trio of flavors", 250, "Desserts"));
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

        List<Integer> imageResources = new ArrayList<>();
        imageResources.add(R.drawable.banner1);
        imageResources.add(R.drawable.banner2);

        ImageAdapter imageAdapter = new ImageAdapter(imageResources);
        binding.vpDisplay.setAdapter(imageAdapter);

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
                        break;

                    case R.id.action_cart:


                        NavHostFragment.findNavController(FourthFragment.this).navigate(R.id.action_fourthFragment_to_cartFragment,makeBundle());
                        break;
                }

                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private Bundle makeBundle(){ //used to make the bundle that is passed between fragments
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putLong("id",userID);

        return bundle;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.itemLocator:
                Toast.makeText(getActivity(), "Locator Button Clicked", Toast.LENGTH_SHORT).show();
                //Do Something
                break;

            case R.id.userOrders:
                NavHostFragment.findNavController(FourthFragment.this).navigate(R.id.action_fourthFragment_to_orderViewFragment,makeBundle());
                //Do Something
                break;

            case R.id.userReview:
                NavHostFragment.findNavController(FourthFragment.this).navigate(R.id.action_fourthFragment_to_reviewFragment,makeBundle());
                break;

            case R.id.userLogout:
                Toast.makeText(getActivity(), output + " has Logged out", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(FourthFragment.this).navigate(R.id.action_fourthFragment_to_firstFragment);
                break;
        }

        return false;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        //output = getArguments().getString("username");
        //Toast.makeText(getActivity(), "Logged In as: " + output, Toast.LENGTH_SHORT).show();

        if(getArguments() != null){
            username = getArguments().getString("username");
            userID = getArguments().getLong("id");
            Toast.makeText(getActivity(), "ID "+userID+" name:"+username, Toast.LENGTH_SHORT).show();
        }

        View.OnClickListener categoryClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = "";
                switch (v.getId()) {
                    case R.id.mainsSpecials:
                        category = "Specialties";
                        break;
                    case R.id.mainsChicken:
                        category = "Chicken Burgers";
                        break;
                    case R.id.mainsVeggie:
                        category = "Vegetarian Options";
                        break;
                    case R.id.mainsGourmet:
                        category = "Gourmet Burgers";
                        break;
                    case R.id.mainsVegan:
                        category = "Vegan Options";
                        break;
                    case R.id.sidesSides:
                        category = "Sides";
                        break;
                    case R.id.sidesApps:
                        category = "Appetizers & Salads";
                        break;
                    case R.id.sidesBeverages:
                        category = "Beverages";
                        break;
                    case R.id.sidesDessert:
                        category = "Dessert";
                        break;
                }

                bundle.putString("category", category);
                bundle.putString("username", username);
                bundle.putLong("id",userID);
                NavHostFragment.findNavController(FourthFragment.this).navigate(R.id.action_fourthFragment_to_fifthFragment, bundle);
            }
        };

        binding.mainsSpecials.setOnClickListener(categoryClickListener);
        binding.mainsChicken.setOnClickListener(categoryClickListener);
        binding.mainsVeggie.setOnClickListener(categoryClickListener);
        binding.mainsGourmet.setOnClickListener(categoryClickListener);
        binding.mainsVegan.setOnClickListener(categoryClickListener);

        binding.sidesSides.setOnClickListener(categoryClickListener);
        binding.sidesApps.setOnClickListener(categoryClickListener);
        binding.sidesBeverages.setOnClickListener(categoryClickListener);
        binding.sidesDessert.setOnClickListener(categoryClickListener);
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 10) {
                NavHostFragment.findNavController(FourthFragment.this).navigate(R.id.action_fourthFragment_to_cartFragment,makeBundle());
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    public void onResume() {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    @Override
    public void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}