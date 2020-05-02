package com.example.jaros.tasbih;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Ярослав on 02.05.2020.
 */

public class Page2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_page2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

    }


}

