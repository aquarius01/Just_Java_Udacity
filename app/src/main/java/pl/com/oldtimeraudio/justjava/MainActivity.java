package pl.com.oldtimeraudio.justjava;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.checked;
import static android.R.attr.duration;
import static android.R.id.message;
import static android.os.Build.VERSION_CODES.N;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    boolean hasWhippedCream = false;
    boolean hasChocolateCream = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox WippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCream = WippedCreamCheckbox.isChecked();

        CheckBox ChocolateCreamCheckbox = (CheckBox) findViewById(R.id.chocolate_cream_checkbox);
        hasChocolateCream = ChocolateCreamCheckbox.isChecked();

        EditText odjNameEditText = (EditText) findViewById(R.id.cream_edit_text);
        String strName = odjNameEditText.getText().toString();

        int price = calculatePrice();

        String priceMessaage = createOrderSummary(price, hasWhippedCream, hasChocolateCream, strName);

        composeEmail ( getString(R.string.order_subiect)+ " " +strName, priceMessaage);

     //   displayMessage(String.valueOf(price));
    }

    /**
     * This method is called when the + button is clicked and increments the quantity variable
     */
    public void increment(View view) {
        if (quantity>=100) {
            Toast toast = Toast.makeText(this, getString(R.string.to_high_number_of_coffies), Toast.LENGTH_SHORT);
            toast.show();
            return;
        } else {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method is called when the - button is clicked and decrements the quantity variable
     */
    public void decrement(View view) {
        if (quantity<=1) {
            Toast toast = Toast.makeText(this, getString(R.string.to_low_number_of_coffies), Toast.LENGTH_SHORT);
            toast.show();
            return;
        } else {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice() {

        int basePrice = 5;

        if (hasWhippedCream) {

            basePrice = basePrice + 1;
        }

        if (hasChocolateCream) {

            basePrice = basePrice + 2;
        }

        return basePrice * quantity;
    }

    private String createOrderSummary(int price, boolean addWippedCream, boolean addChocolateCream, String strName) {

            String orderMessage = getString(R.string.name, strName);
            orderMessage += "\n" + getString(R.string.add_whiped_cream) + " " + addWippedCream;
            orderMessage += "\n" + getString(R.string.add_chocolate) + " " + addChocolateCream;
            orderMessage += "\n" + getString(R.string.quantity) + " " + quantity;
            orderMessage += "\n" + getString(R.string.summary, NumberFormat.getCurrencyInstance().format(price));
            orderMessage += "\n" + getString(R.string.Thank_You);
            return orderMessage;
    }

    public void composeEmail(String subject, String textEmail) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, textEmail);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
