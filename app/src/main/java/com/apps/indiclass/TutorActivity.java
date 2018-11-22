package com.apps.indiclass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.indiclass.model.TutorModel;
import com.apps.indiclass.util.Constant;
import com.bumptech.glide.Glide;

public class TutorActivity extends AppCompatActivity {

    private static final String TAG = "TutorActivity";

    TextView tvNama, tvExp, tvCountry, tvDesc, tvSiswa, tvKelas, tvUlasan;
    ImageView imgT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Detail Tutor");

        TutorModel tm = getIntent().getParcelableExtra("data");
        imgT = findViewById(R.id.icon_play);
        tvNama = findViewById(R.id.tvNameT);
        tvExp = findViewById(R.id.tvExpT);
        tvCountry = findViewById(R.id.tvSubjectT);
        tvDesc = findViewById(R.id.tvDescT);
        tvSiswa = findViewById(R.id.tvfollower);
        tvKelas = findViewById(R.id.tvclassall);
        tvUlasan = findViewById(R.id.tvreview);


        tvNama.setText(tm.getsNameTutor());
        tvExp.setText(tm.getsExpTutor());
        tvCountry.setText(tm.getsSubjectTutor());

        Glide.with(this)
                .load(Constant.ImageTutor+tm.getsImageTutor())
                .into(imgT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
