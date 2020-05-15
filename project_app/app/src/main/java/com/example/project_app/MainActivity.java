package com.example.project_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonLoadingTask().execute();
            }
        });
    }

    private class JsonLoadingTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strs) {
            return getJsonText();
        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        } // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
    } // JsonLoadingTask
    public String getJsonText() {

        StringBuffer sb = new StringBuffer();
        try {

            //주어진 URL 문서의 내용을 문자열로 얻는다.
            String jsonstring = getStringFromUrl("http://15.165.196.182:3000/candidateInfo/?page=1&sdName=경기도&wiwName=부천시");
            System.out.println("4444");
            //읽어들인 JSON포맷의 데이터를 JSON객체로 변환
            //JSONObject object = new JSONObject(jsonstring);
            System.out.println("3333");

            //ksk_list의 값은 배열로 구성 되어있으므로 JSON 배열생성
            System.out.println("2222");
            JSONArray jArr = new JSONArray(jsonstring);
            //배열의 크기만큼 반복하면서, ksNo과 korName의 값을 추출함
            for (int i=0; i<jArr.length(); i++){

                //i번째 배열 할당
                JSONObject object = jArr.getJSONObject(i);

                //ksNo,korName의 값을 추출함
                String huboid = object.getString("huboid");
                String name = object.getString("name");

                System.out.println("id:"+huboid+"/name:"+name);

                //StringBuffer 출력할 값을 저장
                sb.append("[ "+huboid+" ]\n");
                sb.append(name+"\n");
                sb.append("\n");
            }

        } catch (JSONException e) {
            System.out.print("에러");
            // TODO: handle exception
        }

        return sb.toString();
    }//getJsonText()-----------
    public String getStringFromUrl(String pUrl){

        BufferedReader bufreader=null;
        HttpURLConnection urlConnection = null;

        StringBuffer page=new StringBuffer(); //읽어온 데이터를 저장할 StringBuffer객체 생성

        try {

            URL url= new URL(pUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream contentStream = urlConnection.getInputStream();

            bufreader = new BufferedReader(new InputStreamReader(contentStream,"UTF-8"));
            String line = null;

            //버퍼의 웹문서 소스를 줄단위로 읽어(line), Page에 저장함
            while((line = bufreader.readLine())!=null){
                Log.d("line11:",line);
                page.append(line);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //자원해제
            try {
                bufreader.close();
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return page.toString();
    }// getStringFromUrl()-------------------------
}