package com.example.jaros.tasbih;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        TextView textView = (TextView) findViewById(R.id.editText);
        textView.setFocusable(false);

    }

    public void editNumber(View view) {
        TextView textView = (TextView) findViewById(R.id.editText);

        String s_value = textView.getText().toString();
        int i_value = Integer.parseInt(s_value);
        i_value++;

        textView.setText(String.valueOf(i_value));

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (i_value == 80) {
            textView.setTextColor(Color.parseColor("#FFF4E003"));
            vibrator.vibrate(1000);
        } else if (i_value == 100) {
            textView.setText(String.valueOf(1));
            textView.setTextColor(Color.parseColor("#FF000000"));
            vibrator.vibrate(2000);
        } else {
            vibrator.vibrate(100);
        }

        textView.setFocusable(false);
    }

    public void cleraNumber(View view) {

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Увага")
                .setMessage("Очистити лічильник?")
                .setNegativeButton("Так", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextView textView2 = (TextView) findViewById(R.id.editText);

                        textView2.setText(String.valueOf(1));
                        textView2.setTextColor(Color.parseColor("#FF000000"));
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
