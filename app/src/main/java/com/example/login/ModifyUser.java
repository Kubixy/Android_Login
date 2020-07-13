package com.example.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.fragment.app.DialogFragment;

public class ModifyUser extends DialogFragment {

    private Context context;
    private EditText email, password, name, phone;
    private CheckBox terms, spam;
    User user;

    public ModifyUser (Context context, User user) {
        this.context = context;
        this.user = user;
    }

    @Override
    public Dialog onCreateDialog (Bundle savedInstance) {

        AlertDialog.Builder constructor = new AlertDialog.Builder(context);
        LayoutInflater bombin = requireActivity().getLayoutInflater();
        View view = bombin.inflate(R.layout.custom_preferences, null);

        name = view.findViewById(R.id.myname);
        phone = view.findViewById(R.id.myphone);
        terms = view.findViewById(R.id.cb1);
        spam = view.findViewById(R.id.cb2);
        email = view.findViewById(R.id.nombre);
        password = view.findViewById(R.id.contra);

        name.setText(user.getUsername());
        phone.setText(user.getPhone());
        email.setText(user.getEmail());
        password.setText(user.getPassword());
        terms.setChecked(user.getTerms());
        spam.setChecked(user.getSpam());

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                user.setUsername(editable.toString());
            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                user.setPhone(editable.toString());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {;}
            @Override
            public void afterTextChanged(Editable editable) {
                user.setEmail(editable.toString());
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                user.setPassword(editable.toString());
            }
        });

        constructor.setView(view).setPositiveButton("SAVE",(dialog, id) -> {
            user.setSpam(spam.isChecked());
            user.setTerms(terms.isChecked());
            dismiss();
        });
        return constructor.create();
    }

    @Override
    public void onAttach (Context context){
        super.onAttach(context);
        this.context = context;
    }
} 