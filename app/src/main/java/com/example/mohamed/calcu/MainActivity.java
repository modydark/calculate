package com.example.mohamed.calcu;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    textViewer txt;
    Button more , btndel ,  btnb , btnc , btndot , btn7 , btn8 , btn9 , btn1, btn2, btn3, btn4, btn5, btn6, btn0, btn00 ,button22
            ,plus,minus,multiply,divide,percent,btnequal, button2;

    boolean s = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = new textViewer(this);
        btnequal=(Button)findViewById(R.id.btnequal);
        btnequal.setOnClickListener(this);
        btnb = (Button)findViewById(R.id.btnb);
        btnb.setOnClickListener(this);
        btn00 = (Button)findViewById(R.id.btn00);
        btn00.setOnClickListener(this);
        btn0 = (Button)findViewById(R.id.btn0);
        btn0.setOnClickListener(this);
        btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (Button)findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (Button)findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = (Button)findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6 = (Button)findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        btn7 = (Button)findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        btn8 = (Button)findViewById(R.id.btn8);
        btn8.setOnClickListener(this);
        btn9 = (Button)findViewById(R.id.btn9);
        btn9.setOnClickListener(this);
        button22 = (Button)findViewById(R.id.button22);
        button22.setOnClickListener(this);
        more = (Button)findViewById(R.id.btnmore);
        more.setOnClickListener(this);
        btndel=(Button)findViewById(R.id.btndel);
        btndel.setOnClickListener(this);
        btndot = (Button)findViewById(R.id.btndot);
        btndot.setOnClickListener(this);
        btnc = (Button)findViewById(R.id.btnc);
        btnc.setOnClickListener(this);
        plus = (Button)findViewById(R.id.btnplus);
        plus.setOnClickListener(this);
        minus = (Button)findViewById(R.id.btnminus);
        minus.setOnClickListener(this);
        multiply = (Button)findViewById(R.id.btnmulti);
        multiply.setOnClickListener(this);
        divide = (Button)findViewById(R.id.btndivide);
        divide.setOnClickListener(this);
        percent = (Button)findViewById(R.id.btnv);
        percent.setOnClickListener(this);
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(this);



    }


    @Override
    public void onClick(View v){ txt.insert(v.getId()); }
}


