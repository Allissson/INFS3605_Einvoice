package com.example.infs3605ess;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ScanDescriptionModify extends AppCompat{
    private String message, itemposition, NameI, PriceI, QuantityI, TotalI, NameII, PriceII, QuantityII, TotalII;
    private EditText Name, Price, Quantity, Total;
    private Button Save;
    private TextView Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_description_modify);

        //Find all xml objects
        Name = findViewById(R.id.Modify_Name);
        Price = findViewById(R.id.Modify_Price);
        Quantity = findViewById(R.id.Modify_Quantity);
        Total = findViewById(R.id.Modify_Total);
        Title = findViewById(R.id.Modify_Item);
        Save = findViewById(R.id.btn_modify_save);

        //Get intent
        message = getIntent().getStringExtra("Edit");
        itemposition = String.valueOf(Integer.parseInt(getIntent().getStringExtra("ItemNumber")) + 1);
        NameI = getIntent().getStringExtra("Name");
        PriceI = getIntent().getStringExtra("Price");
        QuantityI = getIntent().getStringExtra("Quantity");
        TotalI = getIntent().getStringExtra("Total");

        //Set intent value for textview
        Name.setText(NameI);
        Price.setText(PriceI);
        Quantity.setText(QuantityI);
        Total.setText(String.valueOf(TotalI));
        Title.setText(getString(R.string.Scan_description_modify_6) + itemposition);

        //OnClick listener for save button
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double priceFrom = Double.parseDouble(String.valueOf(Price.getText()));
                Double totalFrom = Double.parseDouble(String.valueOf(Total.getText()));
                int quantityFrom = Integer.parseInt(String.valueOf(Quantity.getText()));
                Double quantityF = quantityFrom * 1.0;
                Double totalCal = priceFrom * quantityF;
                if(String.valueOf(totalCal).equals(String.valueOf(totalFrom))){
                    if(TextUtils.isEmpty(Name.getText().toString())){
                        Toast.makeText(ScanDescriptionModify.this,
                                "Empty field not allowed!",
                                Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty(Price.getText().toString())){
                        Toast.makeText(ScanDescriptionModify.this,
                                "Empty field not allowed!",
                                Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty(Quantity.getText().toString())){
                        Toast.makeText(ScanDescriptionModify.this,
                                "Empty field not allowed!",
                                Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty(Total.getText().toString())){
                        Toast.makeText(ScanDescriptionModify.this,
                                "Empty field not allowed!",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Intent i = new Intent(view.getContext(), ScanResultActivity.class);
                        i.putExtra("Edit", message);
                        i.putExtra("Name1", String.valueOf(Name.getText()));
                        i.putExtra("Price1", String.valueOf(Price.getText()));
                        i.putExtra("Quantity1", String.valueOf(Quantity.getText()));
                        i.putExtra("Total1", String.valueOf(Total.getText()));
                        i.putExtra("ItemNumber1", getIntent().getStringExtra("ItemNumber"));
                        startActivity(i);
                    }
                }else{
                    Toast.makeText(ScanDescriptionModify.this,
                            "Total does not equal to Quantity * Price, Please double check input details!",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}