package com.android.networkjson01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";
    String urlAddress = "http://192.168.0.84:8080/test/json_members.json";
    Button button;
    ListView listView;
    ArrayList<JsonMember> members;
    MemberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.mainBtn_network_con);
        listView = findViewById(R.id.main_ListView_Members);

        button.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mainBtn_network_con:
                    try {
                        NetworkTask networkTask = new NetworkTask(MainActivity.this, urlAddress);
                        Object object = networkTask.execute().get();
                        members = (ArrayList<JsonMember>) object;

                        adapter = new MemberAdapter(MainActivity.this, R.layout.custom_layout, members);
                        listView.setAdapter(adapter);
                        button.setText("Result");

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };


}