package com.example.tfliteflower.Image;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.tfliteflower.Helper.ImageHelperActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions;

import java.util.List;

public class FlowerIdentificationActivity extends ImageHelperActivity {
    private ImageLabeler imageLabeler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalModel localModel = new LocalModel.Builder().setAssetFilePath("model_flowers.tflite").build();
        CustomImageLabelerOptions options = new CustomImageLabelerOptions.Builder(localModel).setConfidenceThreshold(0.7f)
                .setMaxResultCount(5).build();
        imageLabeler = ImageLabeling.getClient(options);


    }

    @Override
    protected void runClassification(Bitmap bitmap) {
        InputImage inputImage = InputImage.fromBitmap(bitmap, 0);
        imageLabeler.process(inputImage).addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
            @Override
            public void onSuccess(List<ImageLabel> imageLabels) {
                if (imageLabels.size() > 0 ){
                    StringBuilder builder = new StringBuilder();
                    for (ImageLabel label : imageLabels){
                        builder.append(label.getText());
                    }
                    String kind = builder.toString();
                    switch (kind){
                        case "daisy":
                            getOutputTextView().setVisibility(View.VISIBLE);
                            getOutputTextView().setText("How to Care for Daisies:\n\n"
                                    + "Sunlight: Full sunlight exposure for optimal blooms. Some cultivars tolerate shaded areas with reduced intensity.\n\n"
                                    + "Soil Requirements: Well-drained soil to prevent waterlogging. Roots should dry thoroughly between waterings.\n\n"
                                    + "Watering: Avoid overwatering to prevent wilting and yellowing leaves. Provide 1 to 2 inches of water per week during the growing season, and reduce frequency to every other week in winter.\n\n"
                                    + "Fertilizer: Apply balanced fertilizer monthly or opt for organic mulch to enrich the soil gradually.");

                        case "dandelion":
                            getOutputTextView().setVisibility(View.VISIBLE);
                            getOutputTextView().setText("How to Care for Dandelions:\n\n"
                                    + "Sunlight: Flourishes in areas with ample sunlight, thriving equally well in full sun and partially shaded spots.\n\n"
                                    + "Soil Requirements: Prefers well-drained soil but exhibits adaptability to various soil types. Adequate drainage is crucial to prevent waterlogging.\n\n"
                                    + "Watering: Requires consistent watering, avoiding overwatering to prevent yellowing leaves and root issues. Adjust watering frequency based on soil moisture levels.\n\n"
                                    + "Fertilizer: Generally thrives in nutrient-rich soil, reducing the need for additional fertilization. Periodic applications of organic mulch or balanced fertilizer can enhance soil quality and provide supplementary nutrients when necessary.");

                        case "sunflowers":
                            getOutputTextView().setVisibility(View.VISIBLE);
                            getOutputTextView().setText("How to Care for Sunflowers:\n\n"
                                    + "Sunlight: Sunflowers thrive in areas with ample sunlight, flourishing equally well in full sun and partially shaded spots.\n\n"
                                    + "Soil Requirements: Sunflowers prefer well-drained soil but can adapt to various soil types. Adequate drainage is essential to prevent waterlogging.\n\n"
                                    + "Watering: Provide regular watering to sunflowers, ensuring soil moisture without overwatering, which can lead to yellowing leaves and root issues. During the growing season, water sunflowers 2-3 times a week, providing enough water to moisten the soil to a depth of about 6 inches. Adjust watering frequency and amount based on weather conditions and soil moisture levels.\n\n"
                                    + "Fertilizer: Sunflowers generally grow well in nutrient-rich soil, requiring minimal additional fertilization. Periodic applications of organic mulch or balanced fertilizer can improve soil quality and provide supplementary nutrients if needed.");

                        case "roses":
                            getOutputTextView().setVisibility(View.VISIBLE);
                            getOutputTextView().setText("How to Care for Roses:\n\n"
                                    + "Sunlight: Roses thrive in areas with ample sunlight, flourishing equally well in full sun and partially shaded spots.\n\n"
                                    + "Soil Requirements: Roses prefer well-drained soil but can adapt to various soil types. Adequate drainage is essential to prevent waterlogging.\n\n"
                                    + "Watering: Provide regular watering to roses, ensuring soil moisture without overwatering, which can lead to yellowing leaves and root issues. During the growing season, water roses deeply 1-2 times a week, providing enough water to moisten the soil to a depth of about 1 inch. Adjust watering frequency and amount based on weather conditions and soil moisture levels.\n\n"
                                    + "Fertilizer: Roses generally grow well in nutrient-rich soil, requiring regular fertilization. Apply balanced fertilizer or specialized rose fertilizer monthly during the growing season to promote healthy growth and blooming. Additionally, mulching with organic material can help retain moisture and provide nutrients to the soil.");

                        case "tulips":
                            getOutputTextView().setVisibility(View.VISIBLE);
                            getOutputTextView().setText("How to Care for Tulips:\n\n"
                                    + "Sunlight: Tulips thrive in areas with ample sunlight, preferring full sun but tolerating partial shade.\n\n"
                                    + "Soil Requirements: Tulips prefer well-drained soil that is rich in organic matter. Good drainage is essential to prevent bulb rot.\n\n"
                                    + "Watering: Water tulips regularly, keeping the soil evenly moist but not waterlogged. During the growing season, provide water whenever the top inch of soil feels dry to the touch. Avoid overwatering, especially during dormancy.\n\n"
                                    + "Fertilizer: Tulips benefit from a balanced fertilizer applied in early spring as new growth emerges. Use a fertilizer specifically formulated for bulbs, following package instructions for application rates and timing.");


                    }

                }else {
                    getOutputTextView().setText("Could not classify.");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
