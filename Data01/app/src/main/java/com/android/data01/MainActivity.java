package com.android.data01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "Status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parser();
        parser2();
    }

    private void parser(){
        Log.v(TAG, "parser()");

        InputStream inputStream = getResources().openRawResource(R.raw.jsonex);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader); // 처리를 빠르게 하기 위함

        StringBuffer stringBuffer = new StringBuffer(); // 담는 그릇
        String line = null;

        try {
            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            Log.v(TAG, "String Buffer : " + stringBuffer.toString());

            JSONObject jsonObject = new JSONObject(stringBuffer.toString()); // json형식으로 바꿔서 나누어 불러오기
            String name = jsonObject.getString("name");
            Log.v(TAG,"name : " + name);
            int age = jsonObject.getInt("age");
            Log.v(TAG,"age : " + age);

            JSONArray jsonArray = jsonObject.getJSONArray("hobbies"); // []안의 배열값 가져오기
            for(int i = 0; i < jsonArray.length(); i++){
                String hobby = jsonArray.getString(i);
                Log.v(TAG, "hobbies[" + i + "] : " + hobby);
            }

                JSONObject jsonObject1 = jsonObject.getJSONObject("info"); // info라는 json을 풀어준다.
                int no = jsonObject1.getInt("no");
                String id = jsonObject1.getString("id");
                String pw = jsonObject1.getString("pw");
                Log.v(TAG, "no : " + no);
                Log.v(TAG, "id : " + id);
                Log.v(TAG, "pw : " + pw);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(inputStream != null) inputStream.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(bufferedReader != null) bufferedReader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void parser2(){
        Log.v(TAG,"parser2()");

        InputStream inputStream = getResources().openRawResource(R.raw.jsonex2);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream); // 읽어 오는 애
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader); // 쌓아서 가져와야 메모리 부하가 적다.

        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        try {
            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);

            }
            Log.v(TAG, "String Buffer : " + stringBuffer.toString()); // json 모양 그대로 가져온다.

            JSONObject jsonObject = new JSONObject(stringBuffer.toString());
            JSONArray jsonArray = new JSONArray(jsonObject.getString("members_info"));
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String name = jsonObject1.getString("name");
                int age = jsonObject1.getInt("age");
                Log.v(TAG, "name : " + name);
                Log.v(TAG, "age : " + age);

                JSONArray jsonArray1 = new JSONArray(jsonObject1.getString("hobbies"));
                for (int j = 0; j < jsonArray1.length(); j++){
                    String hobby = jsonArray1.getString(j);
                    Log.v(TAG, "hobby[" + j + "] : " + hobby);
                }

                JSONObject jsonObject2 = jsonObject1.getJSONObject("info"); // jsonObject1 의 info를 받아서 처리하는 것
                int no = jsonObject2.getInt("no");
                String id = jsonObject2.getString("id");
                String pw = jsonObject2.getString("pw");
                Log.v(TAG, "no : " + no);
                Log.v(TAG, "id : " + id);
                Log.v(TAG, "pw : " + pw);

            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(inputStream != null) inputStream.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(bufferedReader != null) bufferedReader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}// -----------------