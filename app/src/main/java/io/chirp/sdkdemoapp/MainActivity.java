package io.chirp.sdkdemoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button restaurant;
    Button user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        restaurant = findViewById(R.id.restaurant);
        restaurant.setOnClickListener(this);
        user = findViewById(R.id.user);
        user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restaurant:
                Toast.makeText(this, "Shifting to Restaurant View", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, restaurant.class));
                break;
            case R.id.user:
                Toast.makeText(this, "Shifting to User View", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, user_choice.class));
                break;
        }
    }
}