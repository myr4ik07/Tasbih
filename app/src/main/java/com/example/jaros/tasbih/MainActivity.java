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
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView editText, textView;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        editText = (TextView) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);

        textView.setFocusable(false);
        editText.setFocusable(false);

    }

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
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStackImmediate();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Дійсно закрити програму?")
                    .setCancelable(false)
                    .setPositiveButton("Так",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    finish();
                                }
                            }).setNegativeButton("Ні", null).show();
        }
    }

    public void editNumber(View view) {

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        editText = (TextView) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);

        String s_value = editText.getText().toString();
        int i_value = Integer.parseInt(s_value);
        i_value++;
        editText.setText(String.valueOf(i_value));

        if (i_value == 80) {
//            editText.setTextColor(Color.parseColor("#FFF4E003"));
//            vibrator.vibrate(1000);
        } else if (i_value == 100) {
            editText.setText(String.valueOf(0));

            String s2_value = textView.getText().toString();
            int i2_value = Integer.parseInt(s2_value);
            i2_value++;
            textView.setText(String.valueOf(i2_value));
            textView.setTextColor(Color.parseColor("#FFF4E003"));

            vibrator.vibrate(1000);
        } else {
            vibrator.vibrate(100);
        }

        editText.setFocusable(false);
        textView.setFocusable(false);
    }

    public void cleraNumber(View view) {

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Увага")
                .setMessage("Очистити лічильник?")
                .setNegativeButton("Так", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        editText = (TextView) findViewById(R.id.editText);
                        textView = (TextView) findViewById(R.id.textView);

                        editText.setText(String.valueOf(0));
                        textView.setText(String.valueOf(0));

                        textView.setTextColor(Color.parseColor("#FF000000"));
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editText.setText(savedInstanceState.getString("textView"));
        textView.setText(savedInstanceState.getString("textView"));
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("textView", editText.getText().toString());
        outState.putString("textView", textView.getText().toString());
    }

}
