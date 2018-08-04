package com.snehi.materialmaskededittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private MaterialMaskedEditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (MaterialMaskedEditText)findViewById(R.id.test);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
