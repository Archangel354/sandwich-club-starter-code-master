package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView placeOfOrigin;
    private TextView alsoKnownAs;
    private TextView ingredients;
    private TextView description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        placeOfOrigin = findViewById(R.id.origin_tv);
        alsoKnownAs = findViewById(R.id.also_known_tv);
        ingredients = findViewById(R.id.ingredients_tv);
        description = findViewById(R.id.description_tv);

        List<String> alsoknownas = sandwich.getAlsoKnownAs();
        String alsoKnownAsString = "";
        alsoKnownAsString = StringBuilder(alsoknownas);

        List<String> ingredients = sandwich.getIngredients();
        String ingredientsString = "";
        ingredientsString = StringBuilder(ingredients);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich, alsoKnownAsString, ingredientsString);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich, String alsoKnownAsString, String ingredientsString) {

        // the place of origin of the sandwich
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        alsoKnownAs.setText(alsoKnownAsString);
        ingredients.setText(ingredientsString);
        description.setText(sandwich.getDescription());
    }

    private String StringBuilder(List<String> list){
        String resultString = "";

        for (String s : list)
        {
            resultString += s + "," + "\t";
        }

        // Remove the last comma at the end of the super string
        // Note: be sure that there is a string with a length greater than zero or
        // you will get an out of bounds exception
        if (resultString.length() > 0){
            resultString = resultString.substring(0, resultString.length() - 2);
        }
        return resultString;
    }
}
