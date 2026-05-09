package com.cammeomeo.k234112eapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorActivity extends AppCompatActivity {

    EditText edtFormula;
    Button btnDel,btnCalculate;
    TextView txtMC,txtMR,txtMPlus,txtMinus,txtMS,txtM;

    View.OnClickListener m_onclick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get current data:
                String current_data=edtFormula.getText().toString();
                //remove last character:
                String new_value="";
                if(current_data.length()>1)
                {
                    new_value=current_data.substring(0,current_data.length()-1);
                }
                //set new value:
                edtFormula.setText(new_value);
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //step 1: get data (formular)
                String formular=edtFormula.getText().toString();
                //step 2: invoke library for formular (find internet)...
                String result="";
                //result=library_nào_đó(formular)
                //step 3:
                edtFormula.setText(result);
            }
        });

        m_onclick=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.equals(txtM))
                {
                    //khách hàng nhấn txtM
                }
                else if (view.equals(txtMinus))
                {
                    //khách hàng nhấn txtMinus
                }//không dùng dấu == để so sánh vì nó không hiểu so sánh ô nhớ khi dùng ==
            }
        };
        //m_onclick là biến có khả năng sinh sự kiện (variable as listener)
        //thường dùng để sharing sự kiện (từ 2 view trở lên)
        txtM.setOnClickListener(m_onclick);
        txtMinus.setOnClickListener(m_onclick);
        txtMR.setOnClickListener(m_onclick);
        txtMS.setOnClickListener(m_onclick);
        txtMPlus.setOnClickListener(m_onclick);
        txtMC.setOnClickListener(m_onclick);
    }

    private void addViews() {
        edtFormula=findViewById(R.id.edtFormula);
        btnDel=findViewById(R.id.btnDel);
        btnCalculate=findViewById(R.id.btnCalculate);

        txtMC=findViewById(R.id.txtMC);
        txtMR=findViewById(R.id.txtMR);
        txtMPlus=findViewById(R.id.txtMPlus);
        txtMinus=findViewById(R.id.txtMinus);
        txtMS=findViewById(R.id.txtMS);
        txtM=findViewById(R.id.txtM);
    }
    public void processInputData(View view) {
        Button btn_clicked= (Button) view;
        //old value:
        String old_value=edtFormula.getText().toString();
        //input value:
        String input_value=btn_clicked.getText().toString();
        //new value (lasted value):
        String new_value=old_value+input_value;
        //show new value for customer:
        edtFormula.setText(new_value);
    }
}