package com.example.gadsprojects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SubmissionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText firstName, lastName, email, githubLink;
    private Button submit;

    private AlertDialog alertDialog;
    private ProgressBar progressBar;

    SubmitService submitService;
    Retrofit retrofit;

    private String Email, FirstName, LastName, Link;


    static final String BASE_FORMS_URL = "https://docs.google.com/forms/d/e/"; //GADS forms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_FORMS_URL)
                .build();

        submitService = retrofit.create(SubmitService.class);


        toolbar = findViewById(R.id.toolbar_submit);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        firstName = findViewById(R.id.edtFirstName);
        lastName = findViewById(R.id.edtLastName);
        email = findViewById(R.id.edtEmail);
        githubLink = findViewById(R.id.edtGithubLink);
        submit = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progressBar2);


    }


    public void Submit(View view) {



        if (firstName.getText().toString().isEmpty()
                || lastName.getText().toString().isEmpty()
                || email.getText().toString().isEmpty()
                || githubLink.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_LONG).show();
        } else {



            progressBar.setVisibility(View.VISIBLE);


            FirstName = firstName.getText().toString();
            LastName = lastName.getText().toString();
            Email = email.getText().toString();
            Link = githubLink.getText().toString();

            firstName.setVisibility(View.INVISIBLE);
            lastName.setVisibility(View.INVISIBLE);
            email.setVisibility(View.INVISIBLE);
            githubLink.setVisibility(View.INVISIBLE);
            submit.setVisibility(View.INVISIBLE);

            AlertDialog.Builder builder = new AlertDialog.Builder(SubmissionActivity.this, R.style.Theme_MaterialComponents_Light_Dialog_Alert);
            View views = LayoutInflater.from(SubmissionActivity.this).inflate(
                    R.layout.decision_dialog,
                    findViewById(R.id.layout_dialog)
            );
            builder.setView(views);
            alertDialog = builder.create();
            views.findViewById(R.id.yes_button).setOnClickListener(view1 -> {

                progressBar.setVisibility(View.INVISIBLE);
                cornfirmDialog();


                alertDialog.dismiss();
            });
            views.findViewById(R.id.cancel_btn).setOnClickListener(view12 -> {
                        alertDialog.dismiss();
                progressBar.setVisibility(View.INVISIBLE);
                firstName.setVisibility(View.VISIBLE);
                lastName.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
                githubLink.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);

                    }

            );

            alertDialog.show();
        }


    }

    @Override
    public void onBackPressed() {

        progressBar.setVisibility(View.INVISIBLE);
        return;

    }

    public void cornfirmDialog() {


        Call<Void> call = submitService.merchartRest(Email, FirstName, LastName, Link);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (response.isSuccessful()) {

                    progressBar.setVisibility(View.INVISIBLE);
                    firstName.setText("");
                    lastName.setText("");
                    email.setText("");
                    githubLink.setText("");

                    AlertDialog.Builder builder = new AlertDialog.Builder(SubmissionActivity.this, R.style.Theme_MaterialComponents_Light_Dialog_Alert);
                    View views = LayoutInflater.from(SubmissionActivity.this).inflate(R.layout.decision_dialog, findViewById(R.id.sub2));
                    views.findViewById(R.id.sub2).setVisibility(View.VISIBLE);
                    views.findViewById(R.id.sub1).setVisibility(View.GONE);
                    builder.setView(views);
                    AlertDialog alertDialog = builder.create();
                    ImageView image = views.findViewById(R.id.sub_image);
                    image.setImageResource(R.drawable.ic_checked);
                    alertDialog.show();

                    firstName.setVisibility(View.INVISIBLE);
                    lastName.setVisibility(View.INVISIBLE);
                    email.setVisibility(View.INVISIBLE);
                    githubLink.setVisibility(View.INVISIBLE);
                    submit.setVisibility(View.INVISIBLE);
                } else

                    {

                    progressBar.setVisibility(View.INVISIBLE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(SubmissionActivity.this, R.style.Theme_MaterialComponents_Light_Dialog_Alert);
                    View views = LayoutInflater.from(SubmissionActivity.this).inflate(
                            R.layout.decision_dialog,
                            findViewById(R.id.sub)
                    );
                    views.findViewById(R.id.sub3).setVisibility(View.VISIBLE);
                    views.findViewById(R.id.sub1).setVisibility(View.GONE);
                    builder.setView(views);
                    AlertDialog alertDialog = builder.create();
                    ImageView image = views.findViewById(R.id.sub);
                    image.setImageResource(R.drawable.ic_warning_24);
                    alertDialog.show();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("failure", Objects.requireNonNull(t.getLocalizedMessage()));

            }


        });


    }


}