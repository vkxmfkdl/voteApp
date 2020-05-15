package com.example.project_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_app.vo.ResultViewVO;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Resultview extends AppCompatActivity {
    Toolbar menuToolbar;
    private DrawerLayout mDrawLayout;
    private Context context = this;
    String howsearch = "";  //검색 방법 저장
    String searchtxt ="";
    Integer pageNumber = 1;
    Integer pageStandard =  10;
    //Adapter 관련
    private ArrayList<ResultViewVO> resultDataItem = null;
    private ListViewAdapter listViewAdapter = null; // 리스트뷰에 사용되는 ListViewAdapter
    private ListView listView;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultview);
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
                    intent.putExtra("howsearch",howsearch);
                    intent.putExtra("searchtxt",searchtxt);
                    startActivity(intent);
                }
                else if(id == R.id.help){

                }
                return true;
            }
        });
        // END 툴바

        // <!--Start Spinner 처리-->
        Spinner sdSpinner = (Spinner)findViewById(R.id.sdName);
        ArrayAdapter sdAdapter = ArrayAdapter.createFromResource(this,
                R.array.data_sdname, android.R.layout.simple_spinner_item);
        sdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sdSpinner.setAdapter(sdAdapter);

        Spinner jdSpinner = (Spinner)findViewById(R.id.jdName);
        ArrayAdapter jdAdapter = ArrayAdapter.createFromResource(this,
                R.array.data_jdname, android.R.layout.simple_spinner_item);
        jdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jdSpinner.setAdapter(jdAdapter);

        Spinner ageStart = (Spinner)findViewById(R.id.ageStart);
        Spinner ageEnd = (Spinner)findViewById(R.id.ageEnd);
        List<Integer> spinnerArray = new ArrayList<Integer>();
        List<Integer> spinnerArray2 = new ArrayList<Integer>();
        for (Integer i = 25 ; i<100 ; i++) {
            spinnerArray.add(i);
        }
        for (Integer i = 99 ; i>24 ; i--) {
            spinnerArray2.add(i);
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item,spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item,spinnerArray2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageStart.setAdapter(adapter);
        ageEnd.setAdapter(adapter2);
        // <!--End Spinner 처리-->

        // 이전창에서 검색어 받기
        Intent intent = getIntent();//전창에서 outintent받아오기

        String serachName = intent.getExtras().getString("searchtxt");//text에가다 outintent의 text받아오기
        String howsearch = intent.getExtras().getString("howsearch");
        if (!serachName.equals("")){
            if(howsearch.equals("이름")){
                EditText editName = (EditText)findViewById(R.id.name);
                editName.setText(serachName);
            }
            else if(howsearch.equals("당이름")){
                int spinnerNumber = matchingSpinner(serachName);
                Spinner spinner = (Spinner)findViewById(R.id.jdName);
                spinner.setSelection(spinnerNumber);
            }
        }

        //listview와 footer 참조회득
        View footer = getLayoutInflater().inflate(R.layout.listview_footer,null,false);
        listView = (ListView) findViewById(R.id.resultList);
        listView.addFooterView(footer);

        //어뎁터 세팅
        resultDataItem = new ArrayList<ResultViewVO>();
        listViewAdapter = new ListViewAdapter();
        listView.setAdapter(listViewAdapter); // 어댑터를 리스트뷰에 세팅

        //검색 누를 시
        Button searchBtn = (Button)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultDataItem.clear(); // 데이터 초기화시킨다.
                pageNumber=1; //페이지넘버 초기화
                pageStandard=10; //페이지기준 초기화
                Button addButton = (Button)findViewById(R.id.add);
                addButton.setVisibility(View.VISIBLE);
                new JsonLoadingTask().execute();
            }
        });

        //Footer버튼(더보기버튼) 이벤트 처리
        Button addButton = (Button) footer.findViewById(R.id.add);
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Integer curCnt = listViewAdapter.getCount();
                pageNumber+=1;
                pageStandard+=10;
                new JsonLoadingTask().execute();
            }
        });
        //기본 실행
        new JsonLoadingTask().execute();
    }

    private int matchingSpinner(String jdName){
        int number=0;
        if(jdName.equals("더불어민주당"))  number=1;
        else if(jdName.equals("미래통합당")) number=2;
        else if(jdName.equals("정의당")) number=3;
        else if(jdName.equals("무소속")) number=4;
        else if(jdName.equals("미래한국당")) number=5;
        else if(jdName.equals("더불어시민당")) number=6;
        else if(jdName.equals("국민의당")) number=7;
        else if(jdName.equals("열린민주당")) number=8;
        else number=0;
        return number;
    }

    private class JsonLoadingTask extends AsyncTask <ArrayList<ResultViewVO>, Void, ArrayList<ResultViewVO>> {
        ProgressDialog dialog;

        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            //dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected ArrayList<ResultViewVO> doInBackground(ArrayList<ResultViewVO>... resultViewVO) {
            //이름 받아오기
            EditText editName = (EditText)findViewById(R.id.name);
            String name = editName.getText().toString();
            Log.d("name", name); //공백시 출력안됨
            //성별 받아오기
            RadioGroup rg= (RadioGroup)findViewById(R.id.rg);
            RadioButton rd = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
            String gender = rd.getText().toString();
            Log.d("gender",gender);
            //연령 받아오기
            Spinner ageStartSpinner = (Spinner)findViewById(R.id.ageStart);
            Spinner ageEndSpinner = (Spinner)findViewById(R.id.ageEnd);

            String ageStart = ageStartSpinner.getSelectedItem().toString();
            Log.d("ageStart", ageStart);
            String ageEnd = ageEndSpinner.getSelectedItem().toString();
            Log.d("ageEnd", ageEnd);


            //지역받아오기
            Spinner sdNameSpinner = (Spinner)findViewById(R.id.sdName);
            String sdName = sdNameSpinner.getSelectedItem().toString();
            Log.d("sdName", sdName);

            //정당받아오기
            Spinner jdNameSpinner = (Spinner)findViewById(R.id.jdName);
            String jdName = jdNameSpinner.getSelectedItem().toString();
            Log.d("jdName", jdName);

            return getJsonObject(name, gender, ageStart, ageEnd, sdName, jdName);
        } // doInBackground : 백그라운드 작업을 진행한다.

        @Override
        protected void onPostExecute(ArrayList<ResultViewVO> resultViewVO) {
            dialog.dismiss();
            if (resultViewVO.size() <10 ){
                Button addButton = (Button)findViewById(R.id.add);
                addButton.setVisibility(View.GONE);
            }
            showList(resultViewVO);
        } // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
    } // JsonLoadingTask

    public ArrayList<ResultViewVO> getJsonObject(String name, String gender, String ageStart, String ageEnd, String sdName, String jdName) {
        String pUrl = "http://15.165.196.182:3000/resultView/?page="+pageNumber+"&";
        String parmUrl = "";
        //이름 변환
        if (name.equals("")){ }
        else{ parmUrl = "name=" +name+"&";
            pUrl +=parmUrl;}

        //성별 변환
        if (gender.equals("모두")){ }
        else if(gender.equals("남성")){ parmUrl = "gender=남&";
            pUrl +=parmUrl;}
        else{parmUrl = "gender=여&";
            pUrl +=parmUrl; }

        //지역 변환
        if (sdName.equals("전체")){ }
        else{ parmUrl = "sdName=" +sdName+"&";
            pUrl +=parmUrl;}

        //정당 변환
        if (jdName.equals("전체")){ }
        else{ parmUrl = "jdName=" +jdName+"&";
            pUrl +=parmUrl;}
        //나이 변환
        Calendar calendar = new GregorianCalendar(Locale.KOREA);
        int nYear = calendar.get(Calendar.YEAR);
        int ageStartInt = Integer.parseInt(ageStart);
        int ageEndInt = Integer.parseInt(ageEnd);
        int BirthStartInt = nYear-ageStartInt+1;
        int BirthEndInt = nYear-ageEndInt+1;
        ageStart = Integer.toString(BirthStartInt)+"0000";
        ageEnd = Integer.toString(BirthEndInt)+"9999";
        parmUrl = "ageStart=" +ageStart+"&";
        pUrl += parmUrl;
        parmUrl = "ageEnd=" +ageEnd+"&";
        pUrl += parmUrl;

        StringBuffer sb = new StringBuffer();
        Gson gson = new Gson();
        ArrayList<ResultViewVO> resultViewVOList = null;
        try {
            String jsonstring = getStringFromUrl(pUrl);
            Log.d("jsonstring", jsonstring);
            //리스트변환은 TypeToken사용
            Type listType = new TypeToken<ArrayList<ResultViewVO>>(){}.getType();
            resultViewVOList = gson.fromJson(jsonstring, listType);
        } catch (Exception e) {
            System.out.print(e);
        }
        return resultViewVOList;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{ //왼쪽 상당 버튼 눌르면
                mDrawLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
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

    protected void showList(ArrayList<ResultViewVO> resultViewVOs) {
        try {
            for (ResultViewVO vo : resultViewVOs) {
                listViewAdapter.addItem(vo.getHuboid(), vo.getName(), vo.getHanjaName(), vo.getJdName(), vo.getSdName(), vo.getBirthday(), vo.getSggName());
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 갱신된 데이터 내역을 어댑터에 알려줌
                    listViewAdapter.notifyDataSetChanged();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class ViewHolder {
        public LinearLayout child_layout;
        public ImageView personal_imageView;
        public TextView tv_name;
        public TextView tv_child_birthday;
        public TextView tv_child_jdName;
        public TextView tv_child_sdsggName;
    }

    private class ListViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return resultDataItem.size();
        }

        @Override
        public Object getItem(int position) {
            return resultDataItem.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            final Context context = viewGroup.getContext();

            // 화면에 표시될 View
            if(view == null){
                viewHolder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.person_item,viewGroup,false);

                view.setBackgroundColor(0x00FFFFFF);
                view.invalidate();

                // 화면에 표시될 View 로부터 위젯에 대한 참조 획득
                viewHolder.personal_imageView = (ImageView) view.findViewById(R.id.personal_Image);
                viewHolder.tv_name = (TextView) view.findViewById(R.id.child_name);
                viewHolder.tv_child_birthday= (TextView) view.findViewById(R.id.child_birthday);
                viewHolder.tv_child_jdName = (TextView) view.findViewById(R.id.child_jdName);
                viewHolder.tv_child_sdsggName = (TextView) view.findViewById(R.id.child_sdsggName);
                viewHolder.child_layout = (LinearLayout) view.findViewById(R.id.child_layout);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            // PersonData 에서 position 에 위치한 데이터 참조 획득
            final ResultViewVO personData = resultDataItem.get(position);
            // 아이템 내 각 위젯에 데이터 반영
            viewHolder.tv_name.setText(personData.getName());
            String getBirthday = personData.getBirthday();
            String tv_child_birthday_str = getBirthday.substring(0,4)+"년 "+getBirthday.substring(4,6)+"월 "+getBirthday.substring(6)+"일";
            viewHolder.tv_child_birthday.setText(tv_child_birthday_str);
            viewHolder.tv_child_sdsggName.setText(personData.getSdName() + " "+personData.getSggName());
            viewHolder.tv_child_jdName.setText(personData.getJdName());
            viewHolder.child_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //상세보기
                    Intent intent = new Intent(getApplicationContext(), InfoDetail.class);
                    intent.putExtra("huboid",personData.getHuboid());
                    startActivity(intent);
                }
            });

            String imgUrl = "http://15.165.196.182:3000/img/";
            String type = ".gif";
            String huboid = personData.getHuboid();
            String urls = imgUrl+huboid+type;
            Glide.with(context).load(urls).into(viewHolder.personal_imageView);
            return view;
        }

        public void addItem(String huboid, String name, String hanjaName, String jdName, String sdName, String birthday, String sggName){
            ResultViewVO item = new ResultViewVO();
            item.setName(name);
            item.setHuboid(huboid);
            item.setBirthday(birthday);
            item.setJdName(jdName);
            item.setSdName(sdName);
            item.setHanjaName(hanjaName);
            item.setSggName(sggName);
            resultDataItem.add(item);
        }
    }
}
