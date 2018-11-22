package com.apps.indiclass;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.indiclass.adapter.DemandAdapter;
import com.apps.indiclass.api.ApiCall;
import com.apps.indiclass.api.ApiUtils;
import com.apps.indiclass.model.DemandData;
import com.apps.indiclass.model.DemandRest;
import com.apps.indiclass.model.SendReqRest;
import com.apps.indiclass.model.SubjectData;
import com.apps.indiclass.model.SubjectRest;
import com.apps.indiclass.util.Constant;
import com.apps.indiclass.util.Method;
import com.apps.indiclass.util.SessionManager;
import com.shawnlin.numberpicker.NumberPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReqClassActivity extends AppCompatActivity {

    private static final String TAG = "ReqClassActivity";
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    Calendar now;
    String a;
    TextView tvTime, tvDate, tvDur, tvSub, tvno;
    SimpleDateFormat sdfdate = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat sdftimehour = new SimpleDateFormat("HH");
    SimpleDateFormat sdftimeminute = new SimpleDateFormat("mm");
    SimpleDateFormat df = new SimpleDateFormat("HH:mm");

    String id_user, sToken, subsel, sdate, stime, sdursel = "1", ECSub, ECTgl = "0", ECTime = "0",
            ECDuration = "0", ECTopic, sftimehours;
    SessionManager session;

    int back = 0, iJamPlus, iMenit = 1;
    ApiCall mAPIService;

    String[] subid, subname, datadur, sDurationValue;
    View llAwal, llSearch;
    DemandAdapter demandAdapter;
    List<DemandData> demandData;
    ArrayList<String> alTime = new ArrayList<>();
    ArrayList<String> alDate = new ArrayList<>();
    ArrayList<String> alDateSet = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Buat Kelas");

        session = new SessionManager(this);
        id_user = session.getIds();
        sToken = session.getToken();

        datadur = getResources().getStringArray(R.array.duration_name);
        sDurationValue = getResources().getStringArray(R.array.duration_value);

        now = Calendar.getInstance();

        tvDate = findViewById(R.id.tvTgl);
        tvTime = findViewById(R.id.tvTime);
        tvDur = findViewById(R.id.tvDurasi);
        tvSub = findViewById(R.id.tvSub);
        tvno = findViewById(R.id.tvecnot);

        sdate = FORMATTER.format(now.getTime());
        ECTgl = FORMATTER.format(now.getTime());
        ECTime = sdftimehour.format(now.getTime());
        stime = sdftimehour.format(now.getTime());

        sftimehours = sdftimehour.format(now.getTime());
        iJamPlus = Integer.parseInt(sftimehours) + 2;

        sftimehours = sdftimeminute.format(now.getTime());
        iMenit = Integer.parseInt(sftimehours);
        if (iMenit >= 0 && iMenit < 15)
            iMenit = 15;
        else if (iMenit > 15 && iMenit < 30)
            iMenit = 30;
        else if (iMenit > 30 && iMenit < 45)
            iMenit = 45;

        if (iMenit > 45) {
            tvTime.setText(String.valueOf(iJamPlus + 1) + ":00");
            ECTime = String.valueOf(iJamPlus + 1) + ":00";
        } else {
            tvTime.setText(String.valueOf(iJamPlus) + ":" + iMenit);
            ECTime = String.valueOf(iJamPlus) + ":" + iMenit;
        }

        tvDate.setText(sdfdate.format(now.getTime()));

        // Time Set
        String fromTime = ECTime, toTime = "23:59";
        Date dStart = null, dEnd = null;
        try {
            dStart = df.parse(fromTime);
            dEnd = df.parse(toTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(dStart);
        while (calendarStart.getTime().before(dEnd)) {
            alTime.add(df.format(calendarStart.getTime()));
            calendarStart.add(Calendar.MINUTE, 15);
            Log.i(TAG, "onCreate: " + df.format(calendarStart.getTime()));
        }
        //

        // Date Set
        for (int i = 0; i < 30; i++) {
            Calendar calendars = Calendar.getInstance();
            calendars.add(Calendar.DATE, i);
            String df = sdfdate.format(calendars.getTime());
            alDate.add(df);
            alDateSet.add(FORMATTER.format(calendars.getTime()));
            Log.i(TAG, df);
        }
        //

        mAPIService = ApiUtils.getRestAPI(Constant.BASE_URL);

        mAPIService.getsubject(id_user, sToken).enqueue(new Callback<SubjectRest>() {
            @Override
            public void onResponse(Call<SubjectRest> call, Response<SubjectRest> response) {
                if (response.body().getSubjectData() != null) {
                    if (response.body().getSubjectData().size() > 0) {
                        subid = new String[response.body().getSubjectData().size()];
                        subname = new String[response.body().getSubjectData().size()];
                        for (int i = 0; i < response.body().getSubjectData().size(); i++) {
                            SubjectData sd = response.body().getSubjectData().get(i);
                            subid[i] = sd.getSubject_id();
                            subname[i] = sd.getSubject_name();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SubjectRest> call, Throwable t) {

            }
        });

        llAwal = findViewById(R.id.rlAwals);
        llSearch = findViewById(R.id.layout_ondemanddalem);

        recyclerView = findViewById(R.id.recyclerView);
        /**
         Simple GridLayoutManager that spans two columns
         **/
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    private void display(String toDisplay) {
        Toast.makeText(this, toDisplay, Toast.LENGTH_SHORT).show();
    }

    public void Dates(View v) {

//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ReqClassActivity.this);
//        // Body
//        LayoutInflater inflaters = getLayoutInflater();
//        View viewas = inflaters.inflate(R.layout.duration_layout, null);
//        dialogBuilder.setView(viewas);
//        SingleDateAndTimePicker sp = viewas.findViewById(R.id.datepickers);
//        final NumberPicker numberPicker = viewas.findViewById(R.id.number_picker);
//        numberPicker.setMinValue(1);
//        numberPicker.setMaxValue(datadur.length);
//        numberPicker.setDisplayedValues(datadur);
//        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                Log.d(TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
//            }
//        });
//        numberPicker.setVisibility(View.GONE);
//        sp.setVisibility(View.VISIBLE);
//        dialogBuilder.setNegativeButton(R.string.mdtp_ok, new
//                DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int arg1) {
//                        // TODO Auto-generated method stub
//                        tvDur.setText(datadur[numberPicker.getValue()-1]);
//                        sdursel = sDurationValue[numberPicker.getValue()-1];
//                        ECDuration = sDurationValue[numberPicker.getValue()-1];
//                        dialog.cancel();
//                    }
//                });
//        //Create alert dialog object via builder
//        AlertDialog alertDialogObject = dialogBuilder.create();
//        //Show the dialog
//        alertDialogObject.show();
//        new SingleDateAndTimePickerDialog.Builder(this)
//                //.bottomSheet()
//                //.curved()
//                .minutesStep(15)
//                .minDateRange(now.getTime())
//                .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
//                    @Override
//                    public void onDisplayed(SingleDateAndTimePicker picker) {
//                        //retrieve the SingleDateAndTimePicker
//                    }
//                })
//
//                .title("Tanggal dan Waktu")
//                .listener(new SingleDateAndTimePickerDialog.Listener() {
//                    @Override
//                    public void onDateSelected(Date date) {
//                        Log.i(TAG, "onDateSelected: " + date.toString());
//                        tvDate.setText(sdfdate.format(date));
//
//                        ECTgl = FORMATTER.format(date);
//                        ECTime = sdftimehour.format(date);
//                    }
//                }).display();
        //Create sequence of items
        final CharSequence[] Animals = alDate.toArray(new String[alDate.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // Title
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.headerlisttutor, null);
        TextView tvh = view.findViewById(R.id.tvsubsh);
        tvh.setText("Tanggal");
        dialogBuilder.setCustomTitle(view);

        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = Animals[item].toString();  //Selected item in listview
                ECTgl = alDateSet.get(item);
                tvDate.setText(selectedText);
                String date = FORMATTER.format(now.getTime());
                if (ECTgl.equalsIgnoreCase(date)) {


                    sftimehours = sdftimehour.format(now.getTime());
                    iJamPlus = Integer.parseInt(sftimehours) + 2;

                    sftimehours = sdftimeminute.format(now.getTime());
                    iMenit = Integer.parseInt(sftimehours);
                    if (iMenit >= 0 && iMenit < 15)
                        iMenit = 15;
                    else if (iMenit > 15 && iMenit < 30)
                        iMenit = 30;
                    else if (iMenit > 30 && iMenit < 45)
                        iMenit = 45;

                    if (iMenit > 45) {
                        tvTime.setText(String.valueOf(iJamPlus + 1) + ":00");
                        ECTime = String.valueOf(iJamPlus + 1) + ":00";
                    } else {
                        tvTime.setText(String.valueOf(iJamPlus) + ":" + iMenit);
                        ECTime = String.valueOf(iJamPlus) + ":" + iMenit;
                    }

                    // Time Set
                    alTime = new ArrayList<>();
                    String fromTime = ECTime, toTime = "23:59";
                    Date dStart = null, dEnd = null;
                    try {
                        dStart = df.parse(fromTime);
                        dEnd = df.parse(toTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTime(dStart);
                    while (calendarStart.getTime().before(dEnd)) {
                        alTime.add(df.format(calendarStart.getTime()));
                        calendarStart.add(Calendar.MINUTE, 15);
                        Log.i(TAG, "Date Now: " + df.format(calendarStart.getTime()));
                    }
                    //
                } else {
                    // Time Set
                    alTime = new ArrayList<>();
                    String fromTime = "00:00", toTime = "23:59";
                    Date dStart = null, dEnd = null;
                    try {
                        dStart = df.parse(fromTime);
                        dEnd = df.parse(toTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTime(dStart);
                    while (calendarStart.getTime().before(dEnd)) {
                        alTime.add(df.format(calendarStart.getTime()));
                        calendarStart.add(Calendar.MINUTE, 15);
                        Log.i(TAG, "Date Tom: " + df.format(calendarStart.getTime()));
                    }
                    //
                }
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }

    public void Times(View v){
        //Create sequence of items
        final CharSequence[] Animals = alTime.toArray(new String[alTime.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // Title
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.headerlisttutor, null);
        TextView tvh = view.findViewById(R.id.tvsubsh);
        tvh.setText("Waktu");
        dialogBuilder.setCustomTitle(view);

        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = Animals[item].toString();  //Selected item in listview
//                tvondsubject.setText(selectedText);
                ECTime = selectedText;
                if (back == 0) {
                    tvTime.setText(selectedText);
                }
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }

    public void onDurasi(View v) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ReqClassActivity.this);
        // Body
        LayoutInflater inflaters = getLayoutInflater();
        View viewas = inflaters.inflate(R.layout.duration_layout, null);
        dialogBuilder.setView(viewas);
        final NumberPicker numberPicker = viewas.findViewById(R.id.number_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(datadur.length);
        numberPicker.setDisplayedValues(datadur);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d(TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
            }
        });
        dialogBuilder.setNegativeButton(R.string.mdtp_ok, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        // TODO Auto-generated method stub
                        tvDur.setText(datadur[numberPicker.getValue() - 1]);
                        sdursel = sDurationValue[numberPicker.getValue() - 1];
                        ECDuration = sDurationValue[numberPicker.getValue() - 1];
                        dialog.cancel();
                    }
                });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }

    public void onSubject(View v) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ReqClassActivity.this);
        // Title

        // Body
        LayoutInflater inflaters = getLayoutInflater();
        View viewas = inflaters.inflate(R.layout.duration_layout, null);
        dialogBuilder.setView(viewas);
        final NumberPicker numberPicker = viewas.findViewById(R.id.number_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(subname.length);
        numberPicker.setDisplayedValues(subname);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d(TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
            }
        });
        dialogBuilder.setNegativeButton(R.string.mdtp_ok, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        // TODO Auto-generated method stub
                        tvSub.setText(subname[numberPicker.getValue() - 1]);
                        subsel = subid[numberPicker.getValue() - 1];
                        ECSub = subname[numberPicker.getValue() - 1];
                        dialog.cancel();
                    }
                });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }

    public void onSearch(View v) {
        mAPIService.requestsearch(subsel, ECTgl, ECTime, sdursel, Method.getUTCTime()).enqueue(new Callback<DemandRest>() {
            @Override
            public void onResponse(Call<DemandRest> call, Response<DemandRest> response) {
                Log.w(TAG, "onResponse: " + response.body().toString());
                llAwal.setVisibility(View.VISIBLE);
                llSearch.setVisibility(View.GONE);
                back = 1;
                if (response.body().getDemandData() != null) {
                    if (response.body().getDemandData().size() > 0) {
                        demandData = response.body().getDemandData();
                        demandAdapter = new DemandAdapter(ReqClassActivity.this, demandData, new DemandAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(final DemandData item) {
                                Log.i(TAG, "onItemClick: " + item.toString());
                                final android.app.AlertDialog mAlertDialog;
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ReqClassActivity.this);
                                builder.setTitle(getString(R.string.confirmation));
                                builder.setMessage(getString(R.string.do_you) + item.getUser_name()
                                        + getString(R.string.tobe_your) + ECSub + getString(R.string.tutor_for) + tvDate.getText().toString() + " "
                                        + ECTime + "-" + Method.getEndTime(ECTime, Long.valueOf(ECDuration))
                                        + " ?" + getString(R.string.if_message_approval));

                                LayoutInflater layoutInflater = LayoutInflater.from(ReqClassActivity.this);
                                View mainView = layoutInflater.inflate(R.layout.view_edittext, null);
                                final EditText input = mainView.findViewById(R.id.etTopic);

                                builder.setView(mainView);
                                builder.setCancelable(false);
                                builder.setPositiveButton("IYA", null);

                                builder.setNegativeButton("TIDAK", new
                                        DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int arg1) {
                                                // TODO Auto-generated method stub
                                                dialog.cancel();
                                            }
                                        });
                                mAlertDialog = builder.create();
                                mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

                                    @Override
                                    public void onShow(DialogInterface dialog) {

                                        Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                                        b.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View view) {
                                                // TODO Do something
                                                if (input.getText().length() == 0) {
                                                    input.setError(getString(R.string.cannot_empty));
                                                    input.requestFocus();
                                                } else {
                                                    ECTopic = input.getText().toString();
                                                    doreq(item.getTutor_id(), item.getHarga());
                                                }
                                            }
                                        });
                                    }
                                });
                                mAlertDialog.show();
                            }
                        });

                        recyclerView.setAdapter(demandAdapter);
                        tvno.setVisibility(View.GONE);
                    } else {

                        tvno.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<DemandRest> call, Throwable t) {

            }
        });
    }

    public void doreq(String tutor, String hrt) {
        mAPIService.sendreqprivate(ECTime, tutor, subsel, ECDuration, ECTgl, id_user, Method.getUTCTime(), sToken, "0", hrt, ECTopic).enqueue(new Callback<SendReqRest>() {
            @Override
            public void onResponse(Call<SendReqRest> call, Response<SendReqRest> response) {
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        Toast.makeText(ReqClassActivity.this, "Berhasil meminta kelas", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ReqClassActivity.this, "Maaf waktu Anda, melewati batas. Silahkan atur ulang waktu kelas", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SendReqRest> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (back == 1) {
            back = 0;

            llAwal.setVisibility(View.GONE);
            llSearch.setVisibility(View.VISIBLE);
        } else
            super.onBackPressed();
    }
}
