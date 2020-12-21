package com.android.networkimage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";
    String urlAddress = "http://192.168.0.84:8080/test/img_0214.jpg"; // 해당 이미지 위치
    Button button;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.main_Btn);
        imageView = findViewById(R.id.main_ImageView);

        button.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.main_Btn:
                    //생성자가 필요로 함 => Context context, String mAddr, ImageView imageView
                    NetworkTask networkTask = new NetworkTask(MainActivity.this, urlAddress, imageView);
                    networkTask.execute(100); // 10초, excute메소드는 상속자인 AsyncTask<Integer, String, Integer>가 알고 있다.
                    button.setText("Download Complete!");
                    break;
            }
        }
    };








}// --------------