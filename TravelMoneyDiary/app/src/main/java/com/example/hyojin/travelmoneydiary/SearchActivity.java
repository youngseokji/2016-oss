package com.example.hyojin.travelmoneydiary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;

public class SearchActivity  extends AppCompatActivity {

    DBManager dbManager = new DBManager(this, "expense.db", null, 1);
    DBAdapter adapter;

    ArrayList<UsageList> ul = new ArrayList<>();

    Button ButtonSearch;
    ListView ExpenseListView;

    String[] xData ;
    float[] yData ;

    PieChart expense_Chart ;
    MyPieChart expense_MyPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButtonSearch = (Button) findViewById(R.id.button_Search);
        ExpenseListView = (ListView) findViewById(R.id.expenseListView);

        expense_Chart = (PieChart) findViewById(R.id.expense_Chart);
        expense_MyPieChart = new MyPieChart(expense_Chart) ;

        ButtonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                ul.clear();
                dbManager.getResult(ul);
                adapter = new DBAdapter(SearchActivity.this, ul, R.layout.expense_row);
                ExpenseListView.setAdapter(adapter);

                getXYData() ;
                expense_MyPieChart.setChartName("Expense Chart");
                expense_MyPieChart.setXYData(xData, yData);
                expense_MyPieChart.addData();

                Toast.makeText(SearchActivity.this, "조회 완료", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getXYData() {

        xData = new String[ul.size()] ;
        yData = new float[ul.size()] ;

        for (int i = 0 ; i <ul.size() ; i++) {
            xData[i] = ul.get(i).content;
            yData[i] = ul.get(i).price;
        }

    }
}
