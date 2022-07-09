package com.example.infs3605ess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class ScanActivity extends AppCompatActivity {

    private static final int IMAGE_CAPTURE_CODE = 1001;
    private TextView output;
    //private EditText input;
    private Button extract,choose,scanfromcamera;
    private ImageView image;
    private Bitmap bitmap;
    private Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                PackageManager.PERMISSION_GRANTED);

        output=findViewById(R.id.output);
        extract=findViewById(R.id.extract);
        image=findViewById(R.id.imageView);
        choose=findViewById(R.id.btt_choose);
        scanfromcamera=findViewById(R.id.scanfromcamera);

        scanfromcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });

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

    private void openCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From Camera");
        image_uri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        System.out.println(MediaStore.EXTRA_OUTPUT);
        startActivityForResult(cameraIntent,1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1 && data!=null){

            Uri returnUri = data.getData();
            System.out.println("Return URI"+returnUri);
            if(returnUri==null){
                returnUri=image_uri;
            }

            //Uri returnUri;
            //if(data==null){
            //    returnUri=(Uri)data.getExtras().get("output");
            //}
            //else {
            //    returnUri = data.getData();
            //}
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