package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;

public class LoggedActivity extends AppCompatActivity implements View.OnClickListener{

    private User user;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fab;
    private EditText input;
    private ArrayList<String> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = getIntent().getExtras().getParcelable("user");
        tasks = user.getTasks();

        RecicleViewMaker();

        input = findViewById(R.id.input);
        input.setOnClickListener(this);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    public void RecicleViewMaker() {
        RecyclerView recyclerView = findViewById(R.id.reci);
        layoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerViewAdapter(tasks);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            tasks.remove(position);
            adapter.notifyItemRemoved(position);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.opt1:
                ModifyUser dialog = new ModifyUser(LoggedActivity.this,user);
                dialog.show(getSupportFragmentManager(),"new");
                break;

            case R.id.opt2:
                Collections.sort(tasks);
                adapter.notifyDataSetChanged();
                break;

            case R.id.opt3:
                saveData();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logged, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (input.length()>0) {
            tasks.add(input.getText().toString());
            input.setText("");

            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            adapter.notifyDataSetChanged();
        }
    }

    private void saveData() {
        user.setTasks(tasks);

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(user);

        editor.putString("MyUser", json);
        editor.apply();
    }
}