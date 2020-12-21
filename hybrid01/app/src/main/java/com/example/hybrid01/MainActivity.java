package com.example.hybrid01;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    WebView webView = null;
    Button btnReload, btnPage1,btnPage2,btnPage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        btnReload = findViewById(R.id.btn_reload);
        btnPage1 = findViewById(R.id.btn_page1);
        btnPage2 = findViewById(R.id.btn_page2);
        btnPage3 = findViewById(R.id.btn_page3);


        //Listener
        addListener();


        //web Setting
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // javascript 사용가능
        webSettings.setBuiltInZoomControls(true); // 확대 축소 기능
        webSettings.setDisplayZoomControls(false); //   돋보기 없애기


        //link시 다른 browser  안보이게
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                btnReload.setText("로딩중..");

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                btnReload.setText(webView.getTitle());
            }
        });

        webView.loadUrl("http://192.168.0.75:8080/test/Arithmetic.jsp");

        //휴대폰 뒤로 가기 눌렀을 때 설정하는 곳이다.


    }

    //휴대폰 뒤로 가기 눌렀을 때 설정하는 곳이다.
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); // 뒤로가면 무조건 종료
        if(webView.canGoBack()){ // 뒤로갈 페이지가 있으면 뒤로가기
            webView.goBack();
        }else { // 없다면 종료
            finish();
        }

    }

    private void addListener(){
        btnReload.setOnClickListener(onClickListener);
        btnPage1.setOnClickListener(onClickListener);
        btnPage2.setOnClickListener(onClickListener);
        btnPage3.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_reload:
                    btnReloadClick();

                case R.id.btn_page1:
                    btnPage1Click();
                    break;

                case R.id.btn_page2:
                    btnPage2Click();
                    break;

                case R.id.btn_page3:
                    btnPage3Click();
                    break;
            }

        }
    };

    private void btnReloadClick(){
        webView.loadUrl("http://192.168.0.84:8080/test/Arithmetic.jsp");
    }

    private void btnPage1Click(){
        webView.loadUrl("http://192.168.0.84:8080/test/Arithmetic.jsp");
    }

    private void btnPage2Click(){
        webView.loadUrl("http://192.168.0.84:8080/test/ResponseAge_01.jsp");
    }

    private void btnPage3Click(){
        webView.loadUrl("http://192.168.0.84:8080/test/Quiz02.html");
    }


}