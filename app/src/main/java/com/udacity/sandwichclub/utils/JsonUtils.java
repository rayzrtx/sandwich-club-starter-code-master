package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public final static String NAME_KEY = "name";
    public final static String MAIN_NAME_KEY = "mainName";
    public final static String AKA_KEY = "alsoKnownAs";
    public final static String ORIGIN_KEY = "placeOfOrigin";
    public final static String DESCRIPTION_KEY = "description";
    public final static String IMAGE_KEY = "image";
    public final static String INGREDIENTS_KEY = "ingredients";

    public static Sandwich parseSandwichJson(String sandwichJSON) {
        try {

            JSONObject rootObject = new JSONObject(sandwichJSON);
            JSONObject name = rootObject.getJSONObject(NAME_KEY);
            String mainName = name.getString(MAIN_NAME_KEY);

            JSONArray alsoKnownAs = name.optJSONArray(AKA_KEY);
            List<String> allAKA = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++){
                String akaName = alsoKnownAs.getString(i);
                allAKA.add(akaName);
            }

            String origin = rootObject.getString(ORIGIN_KEY);
            String description = rootObject.getString(DESCRIPTION_KEY);
            String image = rootObject.getString(IMAGE_KEY);

            JSONArray ingredients = rootObject.optJSONArray(INGREDIENTS_KEY);
            List<String> allIngredients = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++){
                String ingredientName = ingredients.getString(i);
                allIngredients.add(ingredientName);
            }

            return new Sandwich(mainName, allAKA, origin, description, image, allIngredients);

        }catch (JSONException e){
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the Sandwich JSON results", e);
            return null;
        }
    }
}
