package io.chirp.sdkdemoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class user_choice extends AppCompatActivity implements View.OnClickListener {
    Button submit;
    TextView table_text;
    EditText input;
    static int tablenum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_choice);
        initViews();
    }

    private void initViews() {
        table_text= findViewById(R.id.table_text);
        table_text.setOnClickListener(this);
        submit= findViewById(R.id.submit);
        submit.setOnClickListener(this);
        input= findViewById(R.id.input);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                Toast.makeText(this, "Entering Ordering Pane", Toast.LENGTH_SHORT).show();
                tablenum=Integer.parseInt(input.getText().toString());
                startActivity(new Intent(user_choice.this, user.class));
        }
    }

    public static int getTablenum()
    {
        return tablenum;
    }
}