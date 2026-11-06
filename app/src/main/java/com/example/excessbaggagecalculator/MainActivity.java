package com.example.excessbaggagecalculator;
import com.example.excessbaggagecalculator.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerTicketClass;
    private EditText edtBaggageWeight, edtRatePerKg;
    private TextView txtExcessWeight, txtExcessCharge, txtTotalPayable;
    private final String GITHUB_URL = "https://github.com/username/excess-baggage-calculator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        spinnerTicketClass = findViewById(R.id.spinnerTicketClass);
        edtBaggageWeight = findViewById(R.id.edtBaggageWeight);
        edtRatePerKg = findViewById(R.id.edtRatePerKg);
        txtExcessWeight = findViewById(R.id.txtExcessWeight);
        txtExcessCharge = findViewById(R.id.txtExcessCharge);
        txtTotalPayable = findViewById(R.id.txtTotalPayable);
        Button btnCalculate = findViewById(R.id.btnCalculate);
        Button btnReset = findViewById(R.id.btnReset);

        String[] ticketClasses = {"Economy (Limit: 20 kg)", "Business (Limit: 40 kg)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, ticketClasses);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerTicketClass.setAdapter(adapter);

        btnCalculate.setOnClickListener(v -> calculateExcessFee());
        btnReset.setOnClickListener(v -> resetFields());
    }

    private void calculateExcessFee() {
        String weightInput = edtBaggageWeight.getText().toString().trim();
        String rateInput = edtRatePerKg.getText().toString().trim();

        if (weightInput.isEmpty()) {
            edtBaggageWeight.setError("Please enter baggage weight!");
            edtBaggageWeight.requestFocus();
            return;
        }

        if (rateInput.isEmpty()) {
            edtRatePerKg.setError("Please enter rate per kg!");
            edtRatePerKg.requestFocus();
            return;
        }

        double baggageWeight = Double.parseDouble(weightInput);
        double ratePerKg = Double.parseDouble(rateInput);

        double allowedLimit = (spinnerTicketClass.getSelectedItemPosition() == 0) ? 20.0 : 40.0;
        double excessWeight = Math.max(0.0, baggageWeight - allowedLimit);
        double excessCharge = excessWeight * ratePerKg;
        double totalPayable = excessCharge + (excessCharge * 0.08);

        txtExcessWeight.setText(String.format(Locale.getDefault(), "Excess Baggage Weight: %.2f kg", excessWeight));
        txtExcessCharge.setText(String.format(Locale.getDefault(), "Excess Baggage Charge: RM %.2f", excessCharge));
        txtTotalPayable.setText(String.format(Locale.getDefault(), "Total Amount Payable (+8%% Tax): RM %.2f", totalPayable));
    }

    private void resetFields() {
        edtBaggageWeight.setText("");
        edtRatePerKg.setText("");
        spinnerTicketClass.setSelection(0);
        txtExcessWeight.setText("Excess Baggage Weight: - kg");
        txtExcessCharge.setText("Excess Baggage Charge: RM 0.00");
        txtTotalPayable.setText("Total Amount Payable (+8% Tax): RM 0.00");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Excess Baggage Calculator");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out the Excess Baggage Estimator app source code: " + GITHUB_URL);
            startActivity(Intent.createChooser(shareIntent, "Share app link via"));
            return true;
        } else if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}