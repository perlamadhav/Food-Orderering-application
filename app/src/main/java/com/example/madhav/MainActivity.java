package com.example.madhav;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private List<String> selectedFoodItems = new ArrayList<>();
    private ListView listView;
    private Map<String, Double> foodPrices = new HashMap<>(); // Map to hold food prices
    private double totalCost = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] foodItems = {"Pizza", "Samosa", "Burger", "Dosa"};
        double[] prices = {150, 15, 300, 50}; // Prices of the items

        // Map the food items to their respective prices
        for (int i = 0; i < foodItems.length; i++) {
            foodPrices.put(foodItems[i], prices[i]);
        }

        listView = findViewById(R.id.foodListView);
        Button placeOrderButton = findViewById(R.id.placeOrderButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, foodItems);
        listView.setAdapter(adapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = foodItems[position];
                if (listView.isItemChecked(position)) {
                    selectedFoodItems.add(item);
                    totalCost += foodPrices.get(item); // Add the item's price to the total cost
                } else {
                    selectedFoodItems.remove(item);
                    totalCost -= foodPrices.get(item); // Remove the item's price from the total cost
                }
            }
        });

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFoodItems.isEmpty()) {
                    showAlertDialog("No Items Selected", "Please select at least one item to place the order.");
                } else {

                    getUserInfo();
                }
            }
        });
    }

    private void getUserInfo() {

        showOrderConfirmation();
    }

    private void showOrderConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Order Confirmation");
        builder.setMessage("Your order for the following items is placed:\n" + selectedFoodItems.toString() +
                "\nTotal Cost: $" + totalCost +
                "\nYour order will be delivered within 5 minutes. Thank you!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}