package com.android.networkjson01;

import android.app.ProgressDialog;
import android.app.TaskInfo;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTask extends AsyncTask<Integer, String, Object> {

    final static String TAG = "NetworkTask";
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<JsonMember> members = null;

    public NetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.members = new ArrayList<JsonMember>();
    }

    @Override
    protected void onPreExecute() {
        Log.v(TAG,"onPreExecute()");
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialogue");
        progressDialog.setMessage("Down...");
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v(TAG,"doInBackground()");

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer(); // 받아온 json파일 저장해둘 공간

        try{
            URL url = new URL(mAddr);
            Log.v(TAG,"Address : " + mAddr);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000); // 10초
            Log.v(TAG, "Accept : " + httpURLConnection.getResponseCode()); // 코드값으로 연결이 true인지 false인지 확인

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream(); // 가져오고
                inputStreamReader = new InputStreamReader(inputStream); // 읽고
                bufferedReader = new BufferedReader(inputStreamReader); // 합친다

                while (true){
                    String strLine = bufferedReader.readLine(); // 한줄 읽고
                    if(strLine == null) break; // 읽을 게 없으면 멈춘다.
                    stringBuffer.append(strLine + "\n"); // 한줄띄면서 추가해준다.
                }
                Log.v(TAG, "StringBuffer : " + stringBuffer.toString());

                // 파싱하는 메소드 (stringBuffer을 통해 받은 값을 ArrayList에 나눠서 저장해준다)
                parser(stringBuffer.toString());
            }

        }catch (Exception e){
            Log.v(TAG,"Error");
            e.printStackTrace();
        }finally {
            try{
                if(bufferedReader != null) bufferedReader.close();
                if(inputStream != null) inputStream.close();
                if(inputStreamReader != null) inputStreamReader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return members;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Log.v(TAG, "onProgressUpdate()");
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        Log.v(TAG, "onPostExecute()");
        super.onPostExecute(o);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        Log.v(TAG, "onCancelled()");
        super.onCancelled();
    }

    private void parser(String s){ // Json파일이 읽어져서 들어온다.
        Log.v(TAG, "parser()");

        try{
            JSONObject jsonObject = new JSONObject(s); // 스마트폰으로 가져온 s를 파싱한다.
            JSONArray jsonArray = new JSONArray(jsonObject.getString("members_info")); // 제일 바깥 members_info
            Log.v(TAG,"구간 1-----------");
            members.clear(); // init하고 시작
            Log.v(TAG,"구간 2-----------");

            for (int i = 0; i < jsonArray.length(); i++){
                Log.v(TAG,"구간 3-----------");
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i); // 1구간
                Log.v(TAG,"구간 4-----------");
                String name = jsonObject1.getString("name");
                Log.v(TAG,"구간 5-----------");
                int age = jsonObject1.getInt("age");
                Log.v(TAG,"구간 6-----------");
                ArrayList<String> hobbies = new ArrayList<String>();
                Log.v(TAG,"구간 7-----------");

                JSONArray jsonArray1 = jsonObject1.getJSONArray("hobbies"); // 1구간의 hobbies
                Log.v(TAG,"구간 8-----------");

                for(int j = 0; j < jsonArray1.length(); j++){ // 1구간 hobbies의 내용을 분리해서 저장
                    Log.v(TAG,"구간 10-----------");
                    String hobby = jsonArray1.getString(j);
                    Log.v(TAG,"구간 11-----------");
                    hobbies.add(hobby);
                }
                Log.v(TAG,"구간 12-----------");
                JSONObject jsonObject2  = jsonObject1.getJSONObject("info"); // 1구간의 info json부분을 다시 찢는다....
                Log.v(TAG,"구간 13-----------");
                int no = jsonObject2.getInt("no");
                Log.v(TAG,"구간 14-----------");
                String id = jsonObject2.getString("id");
                Log.v(TAG,"구간 15-----------");
                String pw = jsonObject2.getString("pw");
                Log.v(TAG,"구간 16-----------");

                JsonMember jsonMember = new JsonMember(name, age, hobbies, no, id, pw);
                Log.v(TAG,"구간 17-----------");
                Log.v(TAG, "jsonMember : " + jsonMember.getNo());
                Log.v(TAG, "jsonMember : " + jsonMember.getAge());
                Log.v(TAG, "jsonMember : " + jsonMember.getId());
                Log.v(TAG, "jsonMember : " + jsonMember.getName());
                Log.v(TAG, "jsonMember : " + jsonMember.getPw());
                Log.v(TAG, "jsonMember : " + jsonMember.getHobbies());
                members.add(jsonMember);
                Log.v(TAG,"구간 18-----------");
            }
            Log.v(TAG,"구간 19-----------");
            for (int i = 0; i < members.size(); i++){

                Log.v(TAG, "members value : " + members.get(i));
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}// --------------
