package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mSandwichName_tv;
    private TextView mSandwichAlsoKnownAs_tv;
    private TextView mSandwichOrigin_tv;
    private TextView mSandwichIngredients_tv;
    private TextView mSandwichDescription_tv;
    private ImageView mSandwichImage_iv;
    private TextView mAKALabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mSandwichName_tv = findViewById(R.id.sandwich_name_tv);
        mSandwichAlsoKnownAs_tv = findViewById(R.id.aka_tv);
        mSandwichOrigin_tv = findViewById(R.id.origin_tv);
        mSandwichIngredients_tv = findViewById(R.id.ingredients_tv);
        mSandwichDescription_tv = findViewById(R.id.description_tv);
        mSandwichImage_iv = findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mSandwichImage_iv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    //Method used to populate text views in Detail Activity with parsed JSON
    private void populateUI(Sandwich sandwich) {

        //Populate main name text view with Main Name
        mSandwichName_tv.setText(sandwich.getMainName());

        //Checking if Also Known As List is empty or not.
        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            StringBuilder akaString = new StringBuilder();

            //Loop to append all items in list to the string
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                akaString.append(sandwich.getAlsoKnownAs().get(i));

                //Will append a comma after every item in the list as long as it is not the last item
                if (i != sandwich.getAlsoKnownAs().size()-1) {
                    akaString.append(", ");
                }
            }
            //If not empty, populate Also Known As field with the String built by appending each item in list
            mSandwichAlsoKnownAs_tv.setText(akaString.toString());

            //If List was empty, populate text view with an Unknown message
        }else mSandwichAlsoKnownAs_tv.setText(R.string.unknown_message);


        //If Place of Origin is given, then populate Origin text view
        if (sandwich.getPlaceOfOrigin() != null && sandwich.getPlaceOfOrigin().length() > 0) {
            mSandwichOrigin_tv.setText(sandwich.getPlaceOfOrigin());

            //If empty, populate Origin text view with an Unknown message
        }else mSandwichOrigin_tv.setText(R.string.unknown_message);


        //Populate Description text view
        mSandwichDescription_tv.setText(sandwich.getDescription());


        //Checking if Ingredients list is empty or not
        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            StringBuilder ingredientsStringBuilder = new StringBuilder();

            //Loop to append all items in list to string
            for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                ingredientsStringBuilder.append(sandwich.getIngredients().get(i));

                //Will append a comma after every item in the list as long as it is not the last item
                if (i != sandwich.getIngredients().size()-1){
                    ingredientsStringBuilder.append(", ");
                }
            }
            //If not empty, populate Ingredients text view with String built by appending each item in list
            mSandwichIngredients_tv.setText(ingredientsStringBuilder.toString());

            //If List was empty, populate text view with an Unknown message
        }else mSandwichIngredients_tv.setText(R.string.unknown_message);
    }
}
