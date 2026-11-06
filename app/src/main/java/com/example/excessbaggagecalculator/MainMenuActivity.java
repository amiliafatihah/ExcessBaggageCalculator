package com.example.excessbaggagecalculator;
import com.example.excessbaggagecalculator.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    private final String GITHUB_URL = "https://github.com/amiliafatihah/ExcessBaggageCalculator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Toolbar toolbar = findViewById(R.id.mainMenuToolbar);
        setSupportActionBar(toolbar);

        Button btnGoCalculator = findViewById(R.id.btnGoCalculator);
        Button btnGoAbout = findViewById(R.id.btnGoAbout);

        btnGoCalculator.setOnClickListener(v -> {
            startActivity(new Intent(MainMenuActivity.this, MainActivity.class));
        });

        btnGoAbout.setOnClickListener(v -> {
            startActivity(new Intent(MainMenuActivity.this, AboutActivity.class));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Excess Baggage Calculator");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out the Excess Baggage Calculator app: " + GITHUB_URL);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        } else if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}