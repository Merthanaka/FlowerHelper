package com.example.tfliteflower.Helper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfliteflower.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabelerOptionsBase;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ImageHelperActivity extends AppCompatActivity {
    private int REQUEST_PICK_IMAGE = 1000;
    private int REQUEST_CAPTURE_IMAGE = 1001;

    private ImageView inputImageView;
    private TextView outputTextView;
    private File photoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_helper);
        inputImageView = findViewById(R.id.imageViewInput);
        outputTextView = findViewById(R.id.textViewOutPut);

    }
    public void onPickimage(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(intent,REQUEST_PICK_IMAGE);

    }
    public void onStartCamera(View view){
        photoFile = createPhotoFile();

        Uri fileUri = FileProvider.getUriForFile(this,"com.iago.fileprovider",photoFile);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);

        startActivityForResult(intent,REQUEST_CAPTURE_IMAGE);

    }
    private File createPhotoFile (){
        File photoFileDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),"ML_IMAGE_HELPER");

        if (!photoFileDir.exists()){
            photoFileDir.mkdir();
        }
        String name =  new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File file = new File(photoFileDir.getPath()+File.separator+name);
        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_PICK_IMAGE){
                Uri uri = data.getData();
                Bitmap  bitmap = loadFromUri(uri);
                inputImageView.setImageBitmap(bitmap);
                inputImageView.setVisibility(View.VISIBLE);
                runClassification(bitmap);
            }else if (requestCode == REQUEST_CAPTURE_IMAGE){
                Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                inputImageView.setImageBitmap(bitmap);
                inputImageView.setVisibility(View.VISIBLE);
                runClassification(bitmap);
            }
        }
    }
    private Bitmap loadFromUri(Uri uri){
        Bitmap bitmap = null;

        try {
            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(),uri);
            bitmap = ImageDecoder.decodeBitmap(source);
        }catch (IOException e){
            e.printStackTrace();
        }

        return bitmap;
    }
    protected void runClassification(Bitmap bitmap){

    }
    protected TextView getOutputTextView(){
        return outputTextView;
    }
    protected ImageView getInputImageView(){
        return inputImageView;
    }
}