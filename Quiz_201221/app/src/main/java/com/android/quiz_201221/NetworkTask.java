package com.android.quiz_201221;

import android.app.ProgressDialog;
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
            JSONArray jsonArray = new JSONArray(jsonObject.getString("students_info"));

            members.clear(); // init하고 시작

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i); // 1구간

                String code = jsonObject1.getString("code");
                String name = jsonObject1.getString("name");
                String dept = jsonObject1.getString("dept");
                String phone = jsonObject1.getString("phone");

                JsonMember jsonMember = new JsonMember(code, name, dept, phone);

                members.add(jsonMember);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}// --------------
