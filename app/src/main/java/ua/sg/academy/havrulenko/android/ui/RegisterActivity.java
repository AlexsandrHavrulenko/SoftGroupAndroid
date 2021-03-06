package ua.sg.academy.havrulenko.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ua.sg.academy.havrulenko.android.R;
import ua.sg.academy.havrulenko.android.CurrentStorage;
import ua.sg.academy.havrulenko.android.dao.UsersDaoInterface;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPass;
    private EditText editTextConfirmPass;
    private AppCompatButton buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRegister();
            }
        });
    }

    private void findViews() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPass = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirmPass = (EditText) findViewById(R.id.editTextConfirmPassword);
        buttonRegister = (AppCompatButton) findViewById(R.id.buttonRegister);
    }

    private void onClickRegister() {
        String email = editTextEmail.getText().toString();
        String pass1 = editTextPass.getText().toString();
        String pass2 = editTextConfirmPass.getText().toString();

        UsersDaoInterface dao = CurrentStorage.getCurrent();

        if (email.length() < 6) {
            String msg = getResources().getString(R.string.err_short_email);
            DialogFragment.newInstance(msg).show(getSupportFragmentManager(), msg);
            return;
        }
        if (dao.contains(email)) {
            String msg = getResources().getString(R.string.err_user_exists);
            DialogFragment.newInstance(msg).show(getSupportFragmentManager(), msg);
            return;
        }
        if (pass1.length() < 4) {
            String msg = getResources().getString(R.string.err_short_password);
            DialogFragment.newInstance(msg).show(getSupportFragmentManager(), msg);
            return;
        }
        if (!pass1.equals(pass2)) {
            String msg = getResources().getString(R.string.err_pass_not_equals);
            DialogFragment.newInstance(msg).show(getSupportFragmentManager(), msg);
            return;
        }

        dao.addUser(email, pass1);

        Toast.makeText(this, R.string.successful_registration, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}


