package com.example.queryform;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.intellij.lang.annotations.Pattern;

import java.util.regex.PatternSyntaxException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the button in the main activity layout
        Button buttonOpenForm = findViewById(R.id.qrybtn);

        // Set a click listener for the button
        buttonOpenForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the query form dialog when the button is clicked
                showQueryFormDialog();
            }
        });
    }

    private void showQueryFormDialog() {
        // Inflate the custom layout/view
        LayoutInflater inflater = LayoutInflater.from(this);
        View queryFormView = inflater.inflate(R.layout.query_form, null);

        // Initialize the form fields
        final EditText editTextName = queryFormView.findViewById(R.id.editName);
        final EditText editTextEmail = queryFormView.findViewById(R.id.editEmail);
        final EditText editTextQuery = queryFormView.findViewById(R.id.editQry);
        Button buttonSubmit = queryFormView.findViewById(R.id.buttonSubmit);
        Button buttonCancel = queryFormView.findViewById(R.id.buttonCancel);

        // Build the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(queryFormView)
                .setTitle("Submit Query");

        final AlertDialog dialog = builder.create();

        // Handle the Submit button click
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String query = editTextQuery.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    editTextName.setError("Name is required");
                } else if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail.setError("Valid email is required");
                } else if (TextUtils.isEmpty(query)) {
                    editTextQuery.setError("Query is required");
                } else {
                    View queryToastView = inflater.inflate(R.layout.custom_toast_layout, (ViewGroup) findViewById(R.id.customToast));

                    // Set the toast messages
                    TextView toastm1 = queryToastView.findViewById(R.id.toastm1);
                    TextView toastm2 = queryToastView.findViewById(R.id.toastm2);
                    TextView toastm3 = queryToastView.findViewById(R.id.toastm3);
                    toastm1.setText("Name : " + name);
                    toastm2.setText("Email : " + email);
                    toastm3.setText("Query : " + query);

                    // Show the custom toast
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(queryToastView);
                    toast.show();

                    dialog.dismiss();
                }
            }
        });

        // Not cancel alert-box outside click on alert-box
        dialog.setCancelable(false);

        // Handle the Cancel button click
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        // Show the AlertDialog
        dialog.show();
    }
}
