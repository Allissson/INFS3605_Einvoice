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
        Name = findViewById(R.id.Modify_Name);
        Price = findViewById(R.id.Modify_Price);
        Quantity = findViewById(R.id.Modify_Quantity);
        Total = findViewById(R.id.Modify_Total);
        Title = findViewById(R.id.Modify_Item);
        Save = findViewById(R.id.btn_modify_save);

        message = getIntent().getStringExtra("Edit");
        itemposition = String.valueOf(Integer.parseInt(getIntent().getStringExtra("ItemNumber")) + 1);
        NameI = getIntent().getStringExtra("Name");
        PriceI = getIntent().getStringExtra("Price");
        QuantityI = getIntent().getStringExtra("Quantity");
        TotalI = getIntent().getStringExtra("Total");
        Name.setText(NameI);
        Price.setText(PriceI);
        Quantity.setText(QuantityI);
        Total.setText(String.valueOf(TotalI));
        Title.setText("Item" + itemposition);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    i.putExtra("Name", Name.getText());
                    i.putExtra("Price", Price.getText());
                    i.putExtra("Quantity", Quantity.getText());
                    i.putExtra("Total", Total.getText());
                    i.putExtra("ItemNumber", getIntent().getStringExtra("ItemNumber"));
                    startActivity(i);
                }
            }
        });





    }
}