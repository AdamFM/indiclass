package com.apps.indiclass;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apps.indiclass.api.ApiCall;
import com.apps.indiclass.api.ApiUtils;
import com.apps.indiclass.model.ChangePassRest;
import com.apps.indiclass.util.Constant;
import com.apps.indiclass.util.SessionManager;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangePasswordActivity extends AppCompatActivity {

    private static final String TAG = "ChangePasswordActivity";
    TextInputLayout passwordWrapper, password1Wrapper, password2Wrapper;
    SessionManager session;
    JSONObject doschedule = new JSONObject();
    Button btnChange;
    int id_user;
    boolean status;
    String msg, sToken, rpass1 = "", rpass2 = "";
    AlertDialog b;
    AlertDialog.Builder dialogBuilder;
    ApiCall mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(getString(R.string.change_password));

        session = new SessionManager(this);

        id_user = Integer.parseInt(session.getIds());
        sToken = session.getToken();
        mAPIService = ApiUtils.getRestAPI(Constant.BASE_URL);

        passwordWrapper = findViewById(R.id.passwordr1Wrappero);
        password1Wrapper = findViewById(R.id.passwordr1Wrapper);
        password2Wrapper = findViewById(R.id.passwordr2Wrapper);

        btnChange = findViewById(R.id.buttoncpass);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rpass1 = password1Wrapper.getEditText().getText().toString();
                rpass2 = password2Wrapper.getEditText().getText().toString();

                if (passwordWrapper.getEditText().getText().length() == 0) {
                    passwordWrapper.setError(getString(R.string.cannot_empty));
                    passwordWrapper.requestFocus();

                } else if (rpass1.equals("")) {
                    password1Wrapper.setError(getString(R.string.cannot_empty));
                    password1Wrapper.requestFocus();

                } else if (!rpass1.equals(rpass2)) {
                    password2Wrapper.setError(getString(R.string.password_not_match));
                    password2Wrapper.requestFocus();

                } else {
                    mAPIService.changePassword(String.valueOf(id_user), passwordWrapper.getEditText().getText().toString(), password2Wrapper.getEditText().getText().toString()).enqueue(new Callback<ChangePassRest>() {
                        @Override
                        public void onResponse(Call<ChangePassRest> call, Response<ChangePassRest> response) {

                            if (response.body() != null) {
                                if (response.body().isStatus()) {
                                    Toast.makeText(ChangePasswordActivity.this, getString(R.string.succes_change_password), Toast.LENGTH_SHORT).show();
                                    passwordWrapper.getEditText().setText("");
                                    password1Wrapper.getEditText().setText("");
                                    password2Wrapper.getEditText().setText("");
                                } else {
                                    Toast.makeText(ChangePasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ChangePassRest> call, Throwable t) {

                        }
                    });
                }
            }
        });

        CloseError(passwordWrapper);
        CloseError(password1Wrapper);
        CloseError(password2Wrapper);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void CloseError(final TextInputLayout v) {
        v.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable edt) {
                if (v.getEditText().getText().length() > 0) {
                    v.setError(null);
                    v.setErrorEnabled(false);
                }
            }
        });
    }

    public void ShowProgressDialog() {
        dialogBuilder = new AlertDialog.Builder(ChangePasswordActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.progress_dialog_layout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        b = dialogBuilder.create();
        b.show();
    }

    public void HideProgressDialog() {
        b.dismiss();
    }
}
