package com.example.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, NewUser.MakeNew {

    private EditText password;
    private TextView register;
    private EditText email;
    private User user;
    private Button remnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        email = findViewById(R.id.name);
        password = findViewById(R.id.passwd);
        remnum = findViewById(R.id.remnum);
        register = findViewById(R.id.register);

        remnum.setOnClickListener(e -> {
            if (password.length()>0)
                password.setText(password.getText().subSequence(0,password.length()-1));
        });

        register.setOnClickListener(v -> {
            NewUser dialog = new NewUser(MainActivity.this);
            dialog.show(getSupportFragmentManager(),"new");
        });

        ((RadioGroup)findViewById(R.id.rg1)).setOnCheckedChangeListener(this);
        ((RadioGroup)findViewById(R.id.rg2)).setOnCheckedChangeListener(this);

        ((RadioGroup)findViewById(R.id.rg3)).setOnCheckedChangeListener((group, id) -> {
            switch (id){
                case R.id.refresh:
                        email.setText("");
                        password.setText("");
                        break;

                case R.id.exit:
                    Snackbar.make(findViewById(R.id.exit),"See you soon!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    new Handler().postDelayed(
                            this::finish, 2000);
                        break;

                case R.id.next:
                    if (authenticate()){
                        Intent I = new Intent (getApplicationContext(), LoggedActivity.class);
                        I.putExtra("user", user);
                        startActivity(I);
                        finish();
                    }
                    else
                        Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
            ((RadioButton)findViewById(id)).setChecked(false);
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int id) {
        if (password.length() < 12)
            password.append(((RadioButton) findViewById(id)).getText().toString());
        else
            Toast.makeText(this,"Limit of digits reached",Toast.LENGTH_SHORT).show();

        ((RadioButton)findViewById(id)).setChecked(false);
    }

    @Override
    public void Create(User user) {
        if (user != null) {
            this.user = user;
            saveData();
            Toast.makeText(MainActivity.this,"Registration completed!",Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(MainActivity.this,"Registration cancelled",Toast.LENGTH_SHORT).show();
    }

    public boolean authenticate() {
        return user != null
                && user.getEmail().matches(email.getText().toString().trim())
                && user.getPassword().matches(password.getText().toString().trim());
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("MyUser", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("MyUser", null);
        Type type = new TypeToken<User>() {}.getType();
        user = gson.fromJson(json, type);
        if (user == null) {
            user = new User();
        }
    }
}