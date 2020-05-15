package com.example.project_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.project_app.vo.InfoDetailVO;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InfoDetail extends AppCompatActivity {

    Toolbar menuToolbar;
    private DrawerLayout mDrawLayout;
    private String huboid="";
    private String sgTypeCodeStr="";
    private String jdNameStr="";
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_detail);

        // START 툴바
        menuToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(menuToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.view_menu_icon);
        mDrawLayout = (DrawerLayout)findViewById(R.id.drawer_layout2);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawLayout.closeDrawers();
                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();
                if(id == R.id.main){
                    Intent intent = new Intent(getApplicationContext(),MainPage.class);
                    startActivity(intent);
                }
                else if(id == R.id.result){
                    Intent intent = new Intent(getApplicationContext(),Resultview.class);
                    startActivity(intent);
                }
                else if(id == R.id.help){
                }
                return true;
            }
        });
        // END 툴바

        Intent intent = getIntent();//전창에서 outintent받아오기
        huboid = intent.getExtras().getString("huboid");//text에가다 outintent의 text받아오기

        new JsonLoadingTask().execute();
        ImageButton gongbo = findViewById(R.id.gongbo);
        gongbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sgTypeCodeStr.equals(("2"))) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://15.165.196.182:3000/pdfview/?huboid=" + huboid));
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://15.165.196.182:3000/pdfview/?huboid=" + jdNameStr));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{ //왼쪽 상단 버튼 눌르면
                mDrawLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private class JsonLoadingTask extends AsyncTask<InfoDetailVO, Void, InfoDetailVO> {
        @Override
        protected InfoDetailVO doInBackground(InfoDetailVO... infoDetailVO) {
            return getJsonObject();
        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(InfoDetailVO infoDetailVO) {
            ImageView huboimg = (ImageView) findViewById(R.id.huboimg);
            ImageView jdImg = (ImageView) findViewById(R.id.jdImg);
            TextView hangulhanjaName = (TextView) findViewById(R.id.hangulhanjaName);
            TextView birthday = (TextView) findViewById(R.id.birthday);
            TextView jdName = (TextView) findViewById(R.id.jdName);
            TextView sgTypeCode = (TextView) findViewById(R.id.sgTypeCode);
            TextView sdsggName = (TextView) findViewById(R.id.sdsggName);
            TextView edu = (TextView) findViewById(R.id.edu);
            TextView job = (TextView) findViewById(R.id.job);
            TextView career1 = (TextView) findViewById(R.id.career1);
            TextView career2 = (TextView) findViewById(R.id.career2);

            //사람 이미지
            String huboimgUrl = "http://15.165.196.182:3000/img/";
            String huboimgtype = ".gif";
            String huboid = infoDetailVO.getHuboid();
            String hubourls = huboimgUrl+huboid+huboimgtype;
            Glide.with(context).load(hubourls).into(huboimg);
            //정당 이미지
            String jdimgUrl = "http://15.165.196.182:3000/jdImg/";
            String jdimgName = infoDetailVO.getJdName();
            String jdimgtype = ".jpg";
            String jdurls = jdimgUrl+jdimgName+jdimgtype;
            sgTypeCodeStr= infoDetailVO.getSgTypeCode();
            jdNameStr = infoDetailVO.getJdName();
            Glide.with(context).load(jdurls).into(jdImg);

            //성명
            hangulhanjaName.setText((infoDetailVO.getName()+"("+infoDetailVO.getHanjaName()+")"));
            //출생월일
            String getBirthday = infoDetailVO.getBirthday();
            String birth = getBirthday.substring(0,4)+"년"+getBirthday.substring(4,6)+"월"+getBirthday.substring(6)+"일";
            birthday.setText(birth);
            //정당명
            jdName.setText((infoDetailVO.getJdName()));
            //구분
            if (infoDetailVO.getSgTypeCode().equals("2"))
                sgTypeCode.setText("지역구 국회의원");
            else if (infoDetailVO.getSgTypeCode().equals("7"))
                sgTypeCode.setText("비례대표 국회의원");
            //지역
            String loc = infoDetailVO.getSdName()+ " " +infoDetailVO.getSggName();
            sdsggName.setText(loc);
            //학력
            edu.setText(infoDetailVO.getEdu());
            //직업
            job.setText(infoDetailVO.getJob());
            //경력1
            career1.setText(infoDetailVO.getCareer1());
            //경력2
            career2.setText(infoDetailVO.getCareer2());

        } // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
    } // JsonLoadingTask

    public InfoDetailVO getJsonObject() {
        StringBuffer sb = new StringBuffer();
        Gson gson = new Gson();
        InfoDetailVO infoDetailVO = null;
        try {
            String jsonstring = getStringFromUrl("http://15.165.196.182:3000/detailInfo/?huboid="+huboid);
            jsonstring = jsonstring.replace('[',' ').replace(']',' ');
            Log.d(jsonstring, jsonstring);
            infoDetailVO = gson.fromJson(jsonstring, InfoDetailVO.class);
            Log.d("구분선", infoDetailVO.toString());
        } catch (Exception e) {
            System.out.print(e);
        }
        //return infoDetailVO;
        return infoDetailVO;
    }

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
