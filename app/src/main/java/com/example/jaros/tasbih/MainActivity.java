package com.example.jaros.tasbih;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity<checkBo1, APP_PREFERENCES_COUNTER> extends AppCompatActivity {

    TextView editText, textView;
    EditText t_enter_number;
    Vibrator vibrator;
    CheckBox checkBox;

    int mCounter;
    int i_save;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_COUNTER = "counter";
    private SharedPreferences mSettings;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        editText = (TextView) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        t_enter_number = (EditText) findViewById(R.id.t_enter_number);

        textView.setFocusable(false);
        editText.setFocusable(false);
//        t_enter_number.setFocusable(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.page1:
                Intent page1 = new Intent(MainActivity.this, Page1Activity.class);
                startActivity(page1);
                return true;
            case R.id.page2:
                Intent page2 = new Intent(MainActivity.this, Page2Activity.class);
                startActivity(page2);
                return true;
        }

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
        checkBox = (CheckBox) findViewById(R.id.checkBox); //Таhліль

        String s_value = editText.getText().toString();
        int i_value = Integer.parseInt(s_value);
        i_value++;
        editText.setText(String.valueOf(i_value));

        if (checkBox.isChecked()) {
            //зберігаємо в налаштування
            i_save = 1 + getCount();
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putInt(APP_PREFERENCES_COUNTER, i_save);
            editor.apply();
        }

        //збільшуємо лічильник
        if (i_value == 80 && (!checkBox.isChecked())) {
            vibrator.vibrate(500);
        } else if (i_value == 100) {
            editText.setText(String.valueOf(0));
            String s2_value = textView.getText().toString();
            int i2_value = Integer.parseInt(s2_value);
            i2_value++;
            textView.setText(String.valueOf(i2_value));
            vibrator.vibrate(1000);
        } else {
            vibrator.vibrate(100);
        }

        editText.setFocusable(false);
        textView.setFocusable(false);
    }

    public void cleraNumber(View view) {
        goVibrate_2000ms();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Увага")
                .setMessage("Очистити поточний лічильник?")
                .setNegativeButton("Так", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editText = (TextView) findViewById(R.id.editText);
                        textView = (TextView) findViewById(R.id.textView);

                        editText.setText(String.valueOf(0));
                        textView.setText(String.valueOf(0));
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

    public void vieweSumm(View view) {
        if (mSettings.contains(APP_PREFERENCES_COUNTER)) {
            getCountSave();
        }
    }

    int getCount() {
        int count = 0;
        if (mSettings.contains(APP_PREFERENCES_COUNTER)) {
            count = mSettings.getInt(APP_PREFERENCES_COUNTER, 0);
        }
        return count;
    }

    public void changeCheckBox(View view) {
        checkBox = (CheckBox) findViewById(R.id.checkBox); //Таhліль
        goVibrate_2000ms();
        if (checkBox.isChecked()) {
            showMessage("Підрахунок Таhліль ВІМКНЕНО!");
        } else {
            showMessage("Підрахунок Таhліль ВИМКНЕНО!");
        }

    }

    void showMessage(String string) {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }

    void goVibrate_2000ms() {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
    }

    public void resetCountsave(View view) {
        goVibrate_2000ms();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Увага")
                .setMessage("Очистити лічильник збережених раніше данних?")
                .setNegativeButton("Так", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor = mSettings.edit();
                        editor.putInt(APP_PREFERENCES_COUNTER, 0);
                        editor.apply();

                        showMessage("Очищено!");
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    void getCountSave() {
        mCounter = getCount();
        showMessage("Всього прочитано " + mCounter + " кількість раз");
    }

    public void add_t(View view) {

        changeCheckBox("add_type");

    }

    public void add_t2(View view) {

        changeCheckBox("minus_type");

    }

    void changeCheckBox(String type) {

        String enterValueString;

        try {
            enterValueString = (String) t_enter_number.getText().toString();
            if (enterValueString.isEmpty()) {
                showMessage("Введено невірний формат значення");
                goVibrate_2000ms();
                goVibrate_2000ms();
                return;
            }
        } catch (Exception e) {
            showMessage("Введено невірний формат значення");
            goVibrate_2000ms();
            goVibrate_2000ms();
            return;
        }

        int enterValueInt;

        try {
            enterValueInt = Integer.parseInt(enterValueString.trim());
        } catch (NumberFormatException e) {
            showMessage("Введено невірний формат значення");
            goVibrate_2000ms();
            goVibrate_2000ms();
            return;
        }

        if (enterValueInt == 0) {
            showMessage("Введено невірний формат значення");
            goVibrate_2000ms();
            goVibrate_2000ms();
            return;
        }

        goVibrate_2000ms();

        String typeString, typeString2, typeString3;
        if (type == "add_type") {
            typeString = "добавлено";
            typeString2 = "Добавити";
            typeString3 = "До";
        } else {
            typeString = "віднято";
            typeString2 = "Відняти";
            typeString3 = "Від";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Увага")
                .setMessage(typeString3 + " збереженого раніше значення Tahліля буде "+typeString+" значення " + enterValueInt + " "+typeString2+"?")
                .setNegativeButton("Так", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        checkBox = (CheckBox) findViewById(R.id.checkBox); //Таhліль
                        if (checkBox.isChecked()) {

                            int previousValue = getCount();
                            int intResultValue;

                            if (type == "add_type") {
                                intResultValue = previousValue + enterValueInt;
                            } else {
                                intResultValue = previousValue - enterValueInt;
                            }

                            SharedPreferences.Editor editor = mSettings.edit();
                            editor.putInt(APP_PREFERENCES_COUNTER, intResultValue);
                            editor.apply();

                            showMessage("Успішно "+typeString+"!");

                            if (mSettings.contains(APP_PREFERENCES_COUNTER)) {
                                getCountSave();
                                t_enter_number.setText("");
                            }
                        } else {
                            showMessage("З початку потрібно активувати галку Таhліль");
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}