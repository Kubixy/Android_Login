package com.example.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.DialogFragment;

public class NewUser extends DialogFragment {

    private Context context;
    private EditText email, password, name, phone;
    private CheckBox terms, spam;
    MakeNew interf;
    User user;

    public NewUser(Context context) {this.context = context;}

    @Override
    public Dialog onCreateDialog (Bundle savedInstance) {

        AlertDialog.Builder constructor = new AlertDialog.Builder(context);
        LayoutInflater bombin = requireActivity().getLayoutInflater();
        View view = bombin.inflate(R.layout.custom_dialog, null);

        name = view.findViewById(R.id.myname);
        phone = view.findViewById(R.id.myphone);
        terms = view.findViewById(R.id.cb1);
        spam = view.findViewById(R.id.cb2);

        email = view.findViewById(R.id.nombre);
        email.setOnFocusChangeListener((v, hasFocus) -> {

            String input = email.getText().toString().trim();

            if (!input.contains("@") || input.contains(" ") || input.length()<5)
                email.setError("Incorrect format");
        });

        password = view.findViewById(R.id.contra);
        password.setOnFocusChangeListener((v, hasFocus) -> {

            String input = password.getText().toString().trim();

            if (input.length() < 6)
                password.setError("Password must have at least 6 digits");
            else if (input.length() > 12)
                password.setError("Password can't have more than 12 digits");
        });

        constructor.setView(view)
                .setPositiveButton("OK",(dialog, id) -> {})
                .setNegativeButton("CANCEL", (dialog, id) -> {
                    interf.Create(null);
                    dialog.dismiss();
                });

        return constructor.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog dialog = (AlertDialog)getDialog();

        if (dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {

                if(password.getError() == null && email.getError() == null) {

                    user = new User();
                    user.setEmail(email.getText().toString().trim());
                    user.setPassword(password.getText().toString().trim());
                    user.setPhone(phone.getText().toString().trim());
                    user.setUsername(name.getText().toString().trim());
                    user.setTerms(terms.isChecked());
                    user.setSpam(spam.isChecked());
                    interf.Create(user);
                    dismiss();

                } else {
                    Toast.makeText(context, "Please, fix the errors before continuing", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onAttach (Context context){
        super.onAttach(context);
        this.context = context;
        interf = (MakeNew) context;
    }

    public interface MakeNew {
        void Create (User user);
    }
}