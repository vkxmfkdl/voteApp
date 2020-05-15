package com.example.project_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {
    Button choicbutton; //검색어 설정 버튼
    Toolbar menuToolbar;
    String howsearch = "";  //검색 방법 저장
    String searchtxt ="";
    Button Searchbut;
    EditText editText;
    TabLayout tabLayout;
    private DrawerLayout mDrawLayout;
    private Context context = this;
    PieChart pieChart;
    PieChart pieChart2;
    PieChart pieChart3;
    PieChart pieChart4;
    BarChart barchart;
    FrameLayout fl1;
    FrameLayout fl2;
    FrameLayout fl3;
    FrameLayout fl4;
    FrameLayout fl5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        menuToolbar = (Toolbar) findViewById(R.id.toolbar);
        Searchbut = (Button) findViewById(R.id.searchbut);
        editText = (EditText) findViewById(R.id.editText);
        tabLayout = (TabLayout) findViewById(R.id.tap_layout);
        //fl1 = (FrameLayout)findViewById(R.id.FL1);
        //fl2 = (FrameLayout)findViewById(R.id.FL2);
        //fl3 = (FrameLayout)findViewById(R.id.FL3);
        //fl4 = (FrameLayout)findViewById(R.id.FL4);
        //fl5 = (FrameLayout)findViewById(R.id.FL5);

        setSupportActionBar(menuToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.view_menu_icon);
        mDrawLayout = (DrawerLayout) findViewById(R.id.drawer_layout1);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawLayout.closeDrawers();
                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();
                if (id == R.id.main) {
                    Toast.makeText(context, title + "ㅇㅇ", Toast.LENGTH_LONG).show();  //화면전환으로 나중에 수정
                    Intent intent = new Intent(getApplicationContext(), MainPage.class);
                    startActivity(intent);
                } else if (id == R.id.result) {
                    Toast.makeText(context, title + "ㅇㅇ", Toast.LENGTH_LONG).show();   //화면전환으로 나중에 수정
                    Intent intent = new Intent(getApplicationContext(), Resultview.class);
                    intent.putExtra("howsearch", howsearch);
                    intent.putExtra("searchtxt", searchtxt);
                    startActivity(intent);
                } else if (id == R.id.help) {
                    Toast.makeText(context, title + "ㅇㅇ", Toast.LENGTH_LONG).show();   //화면전환으로 나중에 수정

                }

                return true;
            }
        });
        //대쉬보드 정당비율
        pieChart = (PieChart) findViewById(R.id.pie_chart1);
        pieChart.setUsePercentValues(true);
        pieChart.setCenterText("정당비율");
        pieChart.setDrawEntryLabels(false);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
        yValues.add(new PieEntry(60, "더불어 민주당"));
        yValues.add(new PieEntry(34, "미래통합당"));
        yValues.add(new PieEntry(2, "정의당"));
        yValues.add(new PieEntry(1, "국민의당"));
        yValues.add(new PieEntry(1, "열린민주당"));
        yValues.add(new PieEntry(2, "무소속"));

        pieChart.animateY(1000, Easing.EaseInOutCubic);
        PieDataSet dataSet = new PieDataSet(yValues, "정당");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(Color.BLUE, Color.RED, Color.YELLOW, Color.rgb(255, 102, 000), Color.rgb(000, 051, 102), Color.GRAY);

        PieData data = new PieData((dataSet));
        data.setDrawValues(false);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);
        Description des1 = pieChart.getDescription();
        des1.setEnabled(false);
        Legend legend1 = pieChart.getLegend();
        int[] colorcodes= new int[6];
        colorcodes[0]=Color.BLUE;
        colorcodes[1]=Color.RED;
        colorcodes[2]=Color.YELLOW;
        colorcodes[3]=Color.rgb(255, 102, 000);
        colorcodes[4]=Color.rgb(000, 051, 102);
        colorcodes[5]=Color.GRAY;
        String[] legendname1=new String[6];
        legendname1[0] ="더불어 민주당 : 60%(180석)";
        legendname1[1] ="미래통합당 : 34%(103석)";
        legendname1[2] ="정의당 : 2%(6석)";
        legendname1[3] ="국민의당 : 1%(3석)";
        legendname1[4] ="열린민주당 : 1%(3석)";
        legendname1[5] ="무소속 :1.7%(5석)";
        List<LegendEntry> legendEntries = new ArrayList<>(5);
        LegendEntry legendEntry1 = new LegendEntry();
        legendEntry1.label = legendname1[0];
        legendEntry1.formColor = colorcodes[0];
        legendEntries.add(legendEntry1);
        LegendEntry legendEntry2 = new LegendEntry();
        legendEntry2.label = legendname1[1];
        legendEntry2.formColor = colorcodes[1];
        legendEntries.add(legendEntry2);
        LegendEntry legendEntry3 = new LegendEntry();
        legendEntry3.label = legendname1[2];
        legendEntry3.formColor = colorcodes[2];
        legendEntries.add(legendEntry3);
        LegendEntry legendEntry4 = new LegendEntry();
        legendEntry4.label = legendname1[3];
        legendEntry4.formColor = colorcodes[3];
        legendEntries.add(legendEntry4);
        LegendEntry legendEntry5 = new LegendEntry();
        legendEntry5.label = legendname1[4];
        legendEntry5.formColor = colorcodes[4];
        legendEntries.add(legendEntry5);
        LegendEntry legendEntry6 = new LegendEntry();
        legendEntry6.label = legendname1[5];
        legendEntry6.formColor = colorcodes[5];
        legendEntries.add(legendEntry6);
        legend1.setCustom(legendEntries);
        legend1.setWordWrapEnabled(true);
        legend1.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend1.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend1.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //대쉬보드 정당비율 끝

        pieChart2 = (PieChart) findViewById(R.id.pie_chart2);
        pieChart2.setUsePercentValues(true);
        pieChart2.setCenterText("성별 비율");
        pieChart2.setDrawEntryLabels(false);

        ArrayList<PieEntry> yValues2 = new ArrayList<PieEntry>();
        yValues2.add(new PieEntry(81, "남성"));
        yValues2.add(new PieEntry(19, "여성"));

        pieChart2.animateY(1000, Easing.EaseInOutCubic);
        PieDataSet dataSet2 = new PieDataSet(yValues2, "성별");
        dataSet2.setSliceSpace(3f);
        dataSet2.setSelectionShift(5f);
        dataSet2.setColors(Color.BLUE, Color.RED);
        PieData data2 = new PieData((dataSet2));
        data2.setDrawValues(false);
        data2.setValueTextSize(10f);
        data2.setValueTextColor(Color.BLACK);
        pieChart2.setData(data2);
        Description des2 = pieChart2.getDescription();
        des2.setEnabled(false);
        Legend legend2 = pieChart2.getLegend();
        int[] colorcodes2= new int[2];
        colorcodes2[0]=Color.BLUE;
        colorcodes2[1]=Color.RED;
        String[] legendname2=new String[2];
        legendname2[0] ="남성 : 81%(243명)";
        legendname2[1] ="여성 : 19%(57명)";
        List<LegendEntry> legendEntries2 = new ArrayList<>(5);
        LegendEntry legendEntry7 = new LegendEntry();
        legendEntry7.label = legendname2[0];
        legendEntry7.formColor = colorcodes2[0];
        legendEntries2.add(legendEntry7);
        LegendEntry legendEntry8 = new LegendEntry();
        legendEntry8.label = legendname2[1];
        legendEntry8.formColor = colorcodes2[1];
        legendEntries2.add(legendEntry8);

        legend2.setCustom(legendEntries2);
        legend2.setWordWrapEnabled(true);
        legend2.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend2.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend2.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        pieChart3 = (PieChart) findViewById(R.id.pie_chart3);
        pieChart3.setCenterText("학력별 비율");
        pieChart3.setDrawEntryLabels(false);
        ArrayList<PieEntry> yValues3 = new ArrayList<PieEntry>();
        yValues3.add(new PieEntry(113, "대졸"));
        yValues3.add(new PieEntry(24, "대학원수료"));
        yValues3.add(new PieEntry(158, "대학원졸"));
        yValues3.add(new PieEntry(5, "기타"));

        pieChart3.animateY(1000, Easing.EaseInOutCubic);
        PieDataSet dataSet3 = new PieDataSet(yValues3, "학력별");
        dataSet3.setSliceSpace(3f);
        dataSet3.setSelectionShift(5f);
        dataSet3.setColors(Color.YELLOW,Color.MAGENTA, Color.RED, Color.GREEN);
        PieData data3 = new PieData((dataSet3));
        data3.setDrawValues(false);
        data3.setValueTextSize(10f);
        data3.setValueTextColor(Color.BLACK);
        pieChart3.setData(data3);
        Description des3 = pieChart3.getDescription();
        des3.setEnabled(false);
        Legend legend3 = pieChart3.getLegend();
        int[] colorcodes3= new int[4];
        colorcodes3[0]=Color.YELLOW;
        colorcodes3[1]=Color.MAGENTA;
        colorcodes3[2]=Color.RED;
        colorcodes3[3]=Color.GREEN;

        String[] legendname3=new String[4];
        legendname3[0] ="대졸 : 113명(38%)";
        legendname3[1] ="대학원수료 : 24명(8%)";
        legendname3[2] ="대학원졸 : 158명(52%)";
        legendname3[3] ="기타 : 5명(2%)";
        List<LegendEntry> legendEntries3 = new ArrayList<>(5);
        LegendEntry legendEntry31 = new LegendEntry();
        legendEntry31.label = legendname3[0];
        legendEntry31.formColor = colorcodes3[0];
        legendEntries3.add(legendEntry31);
        LegendEntry legendEntry32 = new LegendEntry();
        legendEntry32.label = legendname3[1];
        legendEntry32.formColor = colorcodes3[1];
        legendEntries3.add(legendEntry32);
        LegendEntry legendEntry33 = new LegendEntry();
        legendEntry33.label = legendname3[2];
        legendEntry33.formColor = colorcodes3[2];
        legendEntries3.add(legendEntry33);
        LegendEntry legendEntry34 = new LegendEntry();
        legendEntry34.label = legendname3[3];
        legendEntry34.formColor = colorcodes3[3];
        legendEntries3.add(legendEntry34);

        legend3.setCustom(legendEntries3);
        legend3.setWordWrapEnabled(true);
        legend3.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend3.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend3.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        pieChart4 = (PieChart) findViewById(R.id.pie_chart4);
        pieChart4.setCenterText("연령별 비율");
        pieChart4.setDrawEntryLabels(false);
        ArrayList<PieEntry> yValues4 = new ArrayList<PieEntry>();
        yValues4.add(new PieEntry(3.6f, "30대"));
        yValues4.add(new PieEntry(12.6f, "40대"));
        yValues4.add(new PieEntry(59, "50대"));
        yValues4.add(new PieEntry(23f, "60대"));
        yValues4.add(new PieEntry(1.6f, "기타"));
        pieChart4.animateY(1000, Easing.EaseInOutCubic);
        PieDataSet dataSet4 = new PieDataSet(yValues4, "연령별");
        dataSet4.setSliceSpace(3f);
        dataSet4.setSelectionShift(5f);
        dataSet4.setColors(Color.YELLOW,Color.MAGENTA, Color.RED, Color.GREEN,Color.LTGRAY);
        PieData data4 = new PieData((dataSet4));
        data4.setDrawValues(false);
        data4.setValueTextSize(10f);
        data4.setValueTextColor(Color.BLACK);
        pieChart4.setData(data4);
        Description des4 = pieChart4.getDescription();
        des4.setEnabled(false);
        Legend legend4 = pieChart4.getLegend();
        int[] colorcodes4= new int[5];
        colorcodes4[0]=Color.YELLOW;
        colorcodes4[1]=Color.MAGENTA;
        colorcodes4[2]=Color.RED;
        colorcodes4[3]=Color.GREEN;
        colorcodes4[4]=Color.LTGRAY;
        String[] legendname4=new String[5];
        legendname4[0] ="30대 : 11명(3.6%)";
        legendname4[1] ="40대 : 38명(12.6%)";
        legendname4[2] ="50대 : 177명(59%)";
        legendname4[3] ="60대 : 69명(23%)";
        legendname4[4] ="기타 : 5명(1.6%)";
        List<LegendEntry> legendEntries4= new ArrayList<>(5);
        LegendEntry legendEntry41 = new LegendEntry();
        legendEntry41.label = legendname4[0];
        legendEntry41.formColor = colorcodes4[0];
        legendEntries4.add(legendEntry41);
        LegendEntry legendEntry42 = new LegendEntry();
        legendEntry42.label = legendname4[1];
        legendEntry42.formColor = colorcodes4[1];
        legendEntries4.add(legendEntry42);
        LegendEntry legendEntry43 = new LegendEntry();
        legendEntry43.label = legendname4[2];
        legendEntry43.formColor = colorcodes4[2];
        legendEntries4.add(legendEntry43);
        LegendEntry legendEntry44 = new LegendEntry();
        legendEntry44.label = legendname4[3];
        legendEntry44.formColor = colorcodes4[3];
        legendEntries4.add(legendEntry44);
        LegendEntry legendEntry45 = new LegendEntry();
        legendEntry45.label = legendname4[4];
        legendEntry45.formColor = colorcodes4[4];
        legendEntries4.add(legendEntry45);

        legend4.setCustom(legendEntries4);
        legend4.setWordWrapEnabled(true);
        legend4.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend4.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend4.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        barchart = (BarChart) findViewById(R.id.bar_chart);
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(0, 68.0f));
        entries.add(new BarEntry(1, 67.7f));
        entries.add(new BarEntry(2, 67.0f));
        entries.add(new BarEntry(3, 63.2f));
        entries.add(new BarEntry(4, 65.9f));
        entries.add(new BarEntry(5, 65.5f));
        entries.add(new BarEntry(6, 68.6f));
        entries.add(new BarEntry(7, 68.5f));
        entries.add(new BarEntry(8, 64.8f));
        entries.add(new BarEntry(9, 66.0f));
        entries.add(new BarEntry(10, 64.0f));
        entries.add(new BarEntry(11, 62.4f));
        entries.add(new BarEntry(12, 67.0f));
        entries.add(new BarEntry(13, 67.8f));
        entries.add(new BarEntry(14, 66.4f));
        entries.add(new BarEntry(15, 67.8f));
        entries.add(new BarEntry(16, 62.9f));

        BarDataSet depenses = new BarDataSet(entries, "지역별 투표율"); // 변수로 받아서 넣어줘도 됨
        depenses.setAxisDependency(YAxis.AxisDependency.RIGHT);
        Description description5 = barchart.getDescription();
        description5.setEnabled(false);
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("서울");
        labels.add("부산");
        labels.add("대구");
        labels.add("인천");
        labels.add("광주");
        labels.add("대전");
        labels.add("울산");
        labels.add("세종");
        labels.add("경기");
        labels.add("강원");
        labels.add("충북");
        labels.add("충남");
        labels.add("전북");
        labels.add("전남");
        labels.add("경북");
        labels.add("경남");
        labels.add("제주");

        BarData data5 = new BarData(depenses); // 라이브러리 v3.x 사용하면 에러 발생함
        depenses.setColors(ColorTemplate.LIBERTY_COLORS); //

        barchart.setData(data5);
        barchart.animateXY(1000, 1000);
        barchart.invalidate();
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(labels);
        XAxis xAxis = barchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        howsearch = "이름";  //초기값
        choicbutton = (Button) findViewById(R.id.choicebut);
        choicbutton.setText(howsearch);  //초기값으로 출력
        choicbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(1);
            }
        });
        pieChart.setVisibility(View.VISIBLE);
        pieChart2.setVisibility(View.INVISIBLE);
        pieChart3.setVisibility(View.INVISIBLE);
        pieChart4.setVisibility(View.INVISIBLE);
        barchart.setVisibility(View.INVISIBLE);
        Searchbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchtxt = editText.getText().toString();
                if (searchtxt == null) {
                } else {
                    if (howsearch == "이름") {
                        Intent intent = new Intent(getApplicationContext(), Resultview.class);
                        intent.putExtra("howsearch", howsearch);
                        intent.putExtra("searchtxt", searchtxt);
                        startActivity(intent);
                    } else if (howsearch == "당이름") {
                        Intent intent = new Intent(getApplicationContext(), Resultview.class);
                        intent.putExtra("howsearch", howsearch);
                        intent.putExtra("searchtxt", searchtxt);
                        startActivity(intent);
                    } else if (howsearch == "지역구") {
                        Intent intent = new Intent(getApplicationContext(), Resultview.class);
                        intent.putExtra("howsearch", howsearch);
                        intent.putExtra("searchtxt", searchtxt);
                        startActivity(intent);
                    }
                }
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                changeView(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void changeView(int index){
            switch (index){
                case 0:
                    pieChart.setVisibility(View.VISIBLE);
                    pieChart2.setVisibility(View.INVISIBLE);
                    pieChart3.setVisibility(View.INVISIBLE);
                    pieChart4.setVisibility(View.INVISIBLE);
                    barchart.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    pieChart.setVisibility(View.INVISIBLE);
                    pieChart2.setVisibility(View.VISIBLE);
                    pieChart3.setVisibility(View.INVISIBLE);
                    pieChart4.setVisibility(View.INVISIBLE);
                    barchart.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    pieChart.setVisibility(View.INVISIBLE);
                    pieChart2.setVisibility(View.INVISIBLE);
                    pieChart3.setVisibility(View.VISIBLE);
                    pieChart4.setVisibility(View.INVISIBLE);
                    barchart.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    pieChart.setVisibility(View.INVISIBLE);
                    pieChart2.setVisibility(View.INVISIBLE);
                    pieChart3.setVisibility(View.INVISIBLE);
                    pieChart4.setVisibility(View.VISIBLE);
                    barchart.setVisibility(View.INVISIBLE);
                    break;
                case 4:
                    pieChart.setVisibility(View.INVISIBLE);
                    pieChart2.setVisibility(View.INVISIBLE);
                    pieChart3.setVisibility(View.INVISIBLE);
                    pieChart4.setVisibility(View.INVISIBLE);
                    barchart.setVisibility(View.VISIBLE);
                    break;
            }
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
    protected Dialog onCreateDialog(int id){
        final String [] items = {"이름", "당이름", "지역구"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainPage.this);
        builder.setTitle("검색어 선택");
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                howsearch = items[which];   //검색어 저장
                choicbutton.setText(howsearch);   //검색어로 버튼값 변경
                dialog.dismiss();
            }
        });
        return builder.create();
    }
}

