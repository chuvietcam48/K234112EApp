package com.cammeomeo.k234112eapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

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
                String formula = edtFormula.getText().toString();
                if (formula.isEmpty()) return;

                try {
                    //step 2: invoke library for formular
                    // Replace special characters if necessary (e.g., 'x' to '*', '÷' to '/')
                    String expressionText = formula.replace('x', '*').replace('÷', '/');
                    Expression expression = new ExpressionBuilder(expressionText).build();
                    double result = expression.evaluate();

                    // Handle division by zero or invalid results
                    if (Double.isInfinite(result) || Double.isNaN(result)) {
                        Toast.makeText(CalculatorActivity.this, "Lỗi: Chia cho 0", Toast.LENGTH_SHORT).show();
                    } else {
                        //step 3: show result
                        if (result == (long) result) {
                            edtFormula.setText(String.valueOf((long) result));
                        } else {
                            edtFormula.setText(String.valueOf(result));
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(CalculatorActivity.this, "Lỗi định dạng toán học", Toast.LENGTH_SHORT).show();
                }
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
    @Override
    protected void onPause() {
        super.onPause();
        // Save edtFormula content to SharedPreferences
        SharedPreferences preferences = getSharedPreferences("CalculatorPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("saved_formula", edtFormula.getText().toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restore edtFormula content from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("CalculatorPrefs", MODE_PRIVATE);
        String savedFormula = preferences.getString("saved_formula", "");
        edtFormula.setText(savedFormula);
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