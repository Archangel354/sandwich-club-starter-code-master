package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static final String NAME = "name";
    public static final String MAIN_NAME = "mainName";
    public static final String ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();
        try {

            JSONObject rootObject = new JSONObject(json);
            JSONObject nameObject = rootObject.getJSONObject(NAME);
            sandwich.setMainName(nameObject.getString(MAIN_NAME));
            sandwich.setPlaceOfOrigin(rootObject.getString(PLACE_OF_ORIGIN));
            sandwich.setAlsoKnownAs(getJsonArrayAsList(nameObject.getJSONArray(ALSO_KNOWN_AS)));

            sandwich.setDescription(rootObject.getString(DESCRIPTION));
            sandwich.setImage(rootObject.getString(IMAGE));
            sandwich.setIngredients(getJsonArrayAsList(rootObject.getJSONArray(INGREDIENTS)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(String.valueOf(sandwich), "GET SANDWICH");
        Log.i(String.valueOf(PLACE_OF_ORIGIN), "GET PLACE OF ORIGIN");
        return sandwich;
    }

    public static ArrayList<String> getJsonArrayAsList(JSONArray array) {
        ArrayList<String> list = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(String.valueOf(list), "getJsonArrayAsList: ");
        return list;
    }

}
