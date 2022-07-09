package com.example.infs3605ess;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.w3c.dom.Text;

import java.io.IOException;

public class ScanActivity extends AppCompatActivity {
    private TextView output;
    //private EditText input;
    private Button extract,choose;
    private ImageView image;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        output=findViewById(R.id.output);
        extract=findViewById(R.id.extract);
        image=findViewById(R.id.imageView);
        choose=findViewById(R.id.btt_choose);

        extract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extractText(view);
            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,1);

            }
        });
    }

    public void extractText(View view){
        try {
            //String stringFileName = "/storage/emulated/0/Download/"+input.getText().toString();
            //bitmap = BitmapFactory.decodeFile(stringFileName);
            image.setImageBitmap(bitmap);
            TextRecognizer textRecognizer=new TextRecognizer.Builder(this).build();
            Frame frameImage = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlockSparseArray = textRecognizer.detect(frameImage);
            String outputText = "";
            for (int i=0;i<textBlockSparseArray.size();i++){
                TextBlock textBlock = textBlockSparseArray.get(textBlockSparseArray.keyAt(i));
                outputText = outputText+" "+textBlock.getValue();

            }
            output.setText(outputText);


        }
        catch (Exception e){
            output.setText("Failed to extract text!!");

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1){
            Uri returnUri = data.getData();
            Bitmap bitmapImage = null;
            try {
                bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), returnUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            image.setImageBitmap(bitmapImage);
            bitmap = bitmapImage;

        }
    }
}