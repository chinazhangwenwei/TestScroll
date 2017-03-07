package com.baoyz.pullrefreshlayout.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        QSrollView qSrollView = new QSrollView(this);
//      qbSrollView.setBackgroundColor(Color.RED);

        qSrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = 20;

        LinearLayout.LayoutParams commomlayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        commomlayoutParams.bottomMargin = 20;

        Button button = new Button(this);
        button.setText("按钮");
        qSrollView.addView(button, commomlayoutParams);


        Button button2 = new Button(this);
        button2.setText("按钮");

        qSrollView.addView(button2, commomlayoutParams);

        Button button3 = new Button(this);
        button3.setText("按钮");

        qSrollView.addView(button3, commomlayoutParams);

//      for(int i= 0 ; i < 100; i ++)
        {
            Button button4 = new Button(this);
//      button4.setText("按钮"+i);

            qSrollView.addView(button4, commomlayoutParams);
        }

        Button button5 = new Button(this);

        button5.setText("按钮");

        qSrollView.addView(button5, commomlayoutParams);

        Button button6 = new Button(this);
        button6.setText("按钮");

        qSrollView.addView(button6, commomlayoutParams);

        Button button7 = new Button(this);
        button7.setText("按钮");
        qSrollView.addView(button7, commomlayoutParams);

        setContentView(qSrollView);

    }

    ;
}