package com.darekasanga.linemessagecard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText messageEditText;
    private EditText senderEditText;
    private RadioGroup colorRadioGroup;
    private LinearLayout previewCard;
    private Button sendToLineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageEditText = findViewById(R.id.messageEditText);
        senderEditText = findViewById(R.id.senderEditText);
        colorRadioGroup = findViewById(R.id.colorRadioGroup);
        previewCard = findViewById(R.id.previewCard);
        sendToLineButton = findViewById(R.id.sendToLineButton);

        Button previewButton = findViewById(R.id.previewButton);
        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePreview();
            }
        });

        sendToLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToLine();
            }
        });
    }

    private void updatePreview() {
        String message = messageEditText.getText().toString();
        String sender = senderEditText.getText().toString();

        if (message.isEmpty()) {
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedColorId = colorRadioGroup.getCheckedRadioButtonId();
        int backgroundColor = getSelectedColor(selectedColorId);

        previewCard.setBackgroundColor(backgroundColor);
        previewCard.setVisibility(View.VISIBLE);
        sendToLineButton.setVisibility(View.VISIBLE);
    }

    private int getSelectedColor(int radioButtonId) {
        if (radioButtonId == R.id.colorBlue) {
            return Color.parseColor("#E3F2FD");
        } else if (radioButtonId == R.id.colorPink) {
            return Color.parseColor("#FCE4EC");
        } else if (radioButtonId == R.id.colorGreen) {
            return Color.parseColor("#E8F5E9");
        } else if (radioButtonId == R.id.colorYellow) {
            return Color.parseColor("#FFFDE7");
        }
        return Color.parseColor("#E3F2FD"); // Default blue
    }

    private void sendToLine() {
        String message = messageEditText.getText().toString();
        String sender = senderEditText.getText().toString();

        if (message.isEmpty()) {
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Create a bitmap of the message card
            Bitmap cardBitmap = createMessageCardBitmap(message, sender);

            // Save bitmap to file
            File imageFile = saveBitmapToFile(cardBitmap);

            if (imageFile != null) {
                // Share via LINE
                shareToLine(imageFile);
            } else {
                Toast.makeText(this, "Failed to create message card", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap createMessageCardBitmap(String message, String sender) {
        int width = 800;
        int height = 600;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // Get background color
        int selectedColorId = colorRadioGroup.getCheckedRadioButtonId();
        int backgroundColor = getSelectedColor(selectedColorId);
        canvas.drawColor(backgroundColor);

        // Draw border
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.WHITE);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(10);
        canvas.drawRect(20, 20, width - 20, height - 20, borderPaint);

        // Draw message
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(48);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setAntiAlias(true);

        // Draw message (wrapped)
        drawTextWrapped(canvas, message, textPaint, 60, 100, width - 120);

        // Draw sender
        if (!sender.isEmpty()) {
            Paint senderPaint = new Paint();
            senderPaint.setColor(Color.GRAY);
            senderPaint.setTextSize(32);
            senderPaint.setAntiAlias(true);
            canvas.drawText("- " + sender, 60, height - 60, senderPaint);
        }

        return bitmap;
    }

    private void drawTextWrapped(Canvas canvas, String text, Paint paint, float x, float y, float maxWidth) {
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        float lineY = y;

        for (String word : words) {
            StringBuilder testLine = new StringBuilder(line).append(word).append(" ");
            float testWidth = paint.measureText(testLine.toString());

            if (testWidth > maxWidth && line.length() > 0) {
                canvas.drawText(line.toString().trim(), x, lineY, paint);
                line = new StringBuilder(word + " ");
                lineY += paint.getTextSize() + 10;
            } else {
                line.append(word).append(" ");
            }
        }
        if (line.length() > 0) {
            canvas.drawText(line.toString().trim(), x, lineY, paint);
        }
    }

    private File saveBitmapToFile(Bitmap bitmap) {
        try {
            File cacheDir = new File(getCacheDir(), "images");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }

            File imageFile = new File(cacheDir, "message_card.png");
            try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
            }

            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void shareToLine(File imageFile) {
        Uri imageUri = FileProvider.getUriForFile(
                this,
                getApplicationContext().getPackageName() + ".fileprovider",
                imageFile
        );

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/png");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Try to share specifically to LINE
        shareIntent.setPackage("jp.naver.line.android");

        try {
            startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            // LINE not installed, use generic share
            shareIntent.setPackage(null);
            Intent chooser = Intent.createChooser(shareIntent, "Share message card");
            startActivity(chooser);
        }
    }
}
