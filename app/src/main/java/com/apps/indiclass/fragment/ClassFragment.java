package com.apps.indiclass.fragment;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.indiclass.Classroom;
import com.apps.indiclass.JanusActivity;
import com.apps.indiclass.R;
import com.apps.indiclass.adapter.ClassAdapter;
import com.apps.indiclass.api.ApiCall;
import com.apps.indiclass.api.ApiUtils;
import com.apps.indiclass.model.ClassRest;
import com.apps.indiclass.model.MyClassModel;
import com.apps.indiclass.model.SessionRest;
import com.apps.indiclass.model.StaticDRest;
import com.apps.indiclass.util.Config;
import com.apps.indiclass.util.Constant;
import com.apps.indiclass.util.Method;
import com.apps.indiclass.util.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassFragment extends Fragment {

    private static final String TAG = "ClassFragment";
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat FORMATTERSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String name = "AKU", token = "49e8d8cb80af828f07dd6ef5dc5906691499784b";
    //    String id_user, sToken;
    String sSession;
    ApiCall mAPIService2, mApi2;
    Retrofit retrofit, retrofit2;
    TextView tvNo;
    String id_user, sdatekirim, sToken, sftime;
    ApiCall mAPIService;
    SessionManager session;
    MyClassModel progamModel;
    android.app.AlertDialog b;
    android.app.AlertDialog.Builder dialogBuilder;
    android.support.v7.app.ActionBar ab;
    LinearLayout llLive, llWait, llPass;
    private RecyclerView recyclerView, recyclerViewW, recyclerViewP;
    private List<MyClassModel> myClassModels;
    private List<MyClassModel> myClassModelsW;
    private List<MyClassModel> myClassModelsP;
    private ClassAdapter mAdapter, mAdapterW, mAdapterP;

    //Creating a broadcast receiver for gcm registration
    public BroadcastReceiver mRegistrationBroadcastReceiver;
    public ClassFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class, container, false);

        session = new SessionManager(getContext());
        id_user = session.getIds();
        sToken = session.getToken();

        llLive = view.findViewById(R.id.llLive);
        llWait = view.findViewById(R.id.llWait);
        llPass = view.findViewById(R.id.llPass);

        Calendar calendar = Calendar.getInstance();
        sdatekirim = FORMATTER.format(calendar.getTime());

        mAPIService = ApiUtils.getRestAPI(Constant.BASE_URL);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerViewW = view.findViewById(R.id.recycler_viewwait);
        recyclerViewP = view.findViewById(R.id.recycler_viewpass);
        tvNo = view.findViewById(R.id.tvnoclassdetail);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.LayoutManager mLayoutManagerW = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewW.setLayoutManager(mLayoutManagerW);

        RecyclerView.LayoutManager mLayoutManagerP = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewP.setLayoutManager(mLayoutManagerP);

        // add the decoration to the recyclerView
//        SeparatorDivider decoration = new SeparatorDivider(getActivity(), Color.LTGRAY, 0f);
//        recyclerView.addItemDecoration(null);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerViewW.setItemAnimator(new DefaultItemAnimator());
        recyclerViewW.setNestedScrollingEnabled(false);
        recyclerViewP.setItemAnimator(new DefaultItemAnimator());
        recyclerViewP.setNestedScrollingEnabled(false);
        //Initializing our broadcast receiver
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Config.NEW_CLASS)) {
                    new android.os.Handler().postDelayed(new Runnable() {
                        @SuppressLint("NewApi")
                        @Override
                        public void run() {

                            fetchStoreItems();
                        }
                    }, 50);
                }
            }
        };
        fetchStoreItems();
        return view;
    }

    public void fetchStoreItems() {

        mAPIService.myclass(id_user, sdatekirim, Method.getUTCTime(), sToken).enqueue(new Callback<ClassRest>() {
            @Override
            public void onResponse(Call<ClassRest> call, Response<ClassRest> response) {

                if (response.body() != null) {
                    Log.i(TAG, "post submitted to API." + response.body().toString());

                    if (response.body().getClassData() == null) {
                        tvNo.setVisibility(View.VISIBLE);
                    } else {
                        if (response.body().getClassData().size() > 0) {

                            myClassModels = new ArrayList<>();
                            myClassModelsW = new ArrayList<>();
                            myClassModelsP = new ArrayList<>();
                            Calendar caal = Calendar.getInstance();
                            sftime = FORMATTERSQL.format(caal.getTime());
                            Date datenow = null, datestart = null, dateend = null;
                            for (int i = 0; i < response.body().getClassData().size(); i++) {

                                long sums = 0;
                                try {
                                    datenow = FORMATTERSQL.parse(sftime);
                                    datestart = FORMATTERSQL.parse(response.body().getClassData().get(i).getDate() + " " + response.body().getClassData().get(i).getTime() + ":00");
                                    dateend = FORMATTERSQL.parse(response.body().getClassData().get(i).getEnddate() + " " + response.body().getClassData().get(i).getEndtime() + ":00");
                                    Log.w(TAG, "onResponse: C: "+response.body().getClassData().get(i).getClass_id());
                                    Log.w(TAG, "onResponse:  TIME NOW: "+datenow.getTime()  );
                                    Log.w(TAG, "onResponse:  TIME END: "+dateend.getTime()  );
                                    if (datenow.compareTo(datestart) < 0) {
                                        // WAIT
                                        Date mDate = FORMATTERSQL.parse(response.body().getClassData().get(i).getDate() + " " + response.body().getClassData().get(i).getTime() + ":00");
                                        sums = mDate.getTime();
                                        System.out.println("Date in milli WAIT:: " + sums);

                                        MyClassModel m = new MyClassModel();
                                        m.setsClassID(response.body().getClassData().get(i).getClass_id());
                                        m.setsSubject(response.body().getClassData().get(i).getSubject_name() + ", " + response.body().getClassData().get(i).getDescription());
                                        m.setsNama(response.body().getClassData().get(i).getTutorName());
                                        m.setsTime(response.body().getClassData().get(i).getTime() + " - " + response.body().getClassData().get(i).getEndtime());
                                        m.setsImage(response.body().getClassData().get(i).getUser_image());
                                        m.setsDateStart(response.body().getClassData().get(i).getDate() + " " + response.body().getClassData().get(i).getTime() + ":00");
                                        m.setsDateEnd(response.body().getClassData().get(i).getEnddate() + " " + response.body().getClassData().get(i).getEndtime() + ":00");
                                        m.setExpiredTime(sums);
                                        myClassModelsW.add(m);
                                    } else if (datenow.compareTo(datestart) == 0 || datenow.compareTo(datestart) > 0 && datenow.compareTo(dateend) < 0){
                                        // LIVE
                                        Date mDate = FORMATTERSQL.parse(response.body().getClassData().get(i).getEnddate() + " " + response.body().getClassData().get(i).getEndtime() + ":00");
                                        sums = mDate.getTime();
                                        System.out.println("Date in milli LIVE:: " + sums);

                                        MyClassModel m = new MyClassModel();
                                        m.setsClassID(response.body().getClassData().get(i).getClass_id());
                                        m.setsSubject(response.body().getClassData().get(i).getSubject_name() + ", " + response.body().getClassData().get(i).getDescription());
                                        m.setsNama(response.body().getClassData().get(i).getTutorName());
                                        m.setsTime(response.body().getClassData().get(i).getTime() + " - " + response.body().getClassData().get(i).getEndtime());
                                        m.setsImage(response.body().getClassData().get(i).getUser_image());
                                        m.setsDateStart(response.body().getClassData().get(i).getDate() + " " + response.body().getClassData().get(i).getTime() + ":00");
                                        m.setsDateEnd(response.body().getClassData().get(i).getEnddate() + " " + response.body().getClassData().get(i).getEndtime() + ":00");
                                        m.setExpiredTime(sums);
                                        myClassModels.add(m);
                                    } else if (datenow.compareTo(dateend) > 0){
                                        // PASS
                                        System.out.println("Date in milli PASS:: " + sums +" CLASS "+response.body().getClassData().get(i).getClass_id());

                                        MyClassModel m = new MyClassModel();
                                        m.setsClassID(response.body().getClassData().get(i).getClass_id());
                                        m.setsSubject(response.body().getClassData().get(i).getSubject_name() + ", " + response.body().getClassData().get(i).getDescription());
                                        m.setsNama(response.body().getClassData().get(i).getTutorName());
                                        m.setsTime(response.body().getClassData().get(i).getTime() + " - " + response.body().getClassData().get(i).getEndtime());
                                        m.setsImage(response.body().getClassData().get(i).getUser_image());
                                        m.setsDateStart(response.body().getClassData().get(i).getDate() + " " + response.body().getClassData().get(i).getTime() + ":00");
                                        m.setsDateEnd(response.body().getClassData().get(i).getEnddate() + " " + response.body().getClassData().get(i).getEndtime() + ":00");
                                        m.setExpiredTime(sums);
                                        myClassModelsP.add(m);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            tvNo.setVisibility(View.VISIBLE);
                        }
                    }

                    if (myClassModels.size() > 0) {
                        // refreshing recycler view
                        mAdapter = new ClassAdapter(getActivity(), myClassModels, new ClassAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(MyClassModel item) {

                                ShowProgressDialog();
                                progamModel = item;
                                Intent in = new Intent(getActivity(), Classroom.class);
                                in.putExtra("visible", "");
                                in.putExtra("subject", progamModel.getsSubject());
                                in.putExtra("template", "all_featured");
                                in.putExtra("id_user", id_user);
                                in.putExtra("class_id", progamModel.getsClassID());
                                in.putExtra("token", token);
                                in.putExtra("isTV", false);
                                startActivity(in);
                                HideProgressDialog();
//
//                            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//                            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//                            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//
//                            retrofit = new Retrofit.Builder()
//                                    .baseUrl(Constant.TPS_ME)
//                                    .client(client)
//                                    .addConverterFactory(GsonConverterFactory.create())
//                                    .build();
//                            retrofit2 = new Retrofit.Builder()
//                                    .baseUrl(Constant.STATIC_REST)
//                                    .client(client)
//                                    .addConverterFactory(GsonConverterFactory.create())
//                                    .build();
//
//                            mAPIService2 = retrofit.create(ApiCall.class);
//
//                            mApi2 = retrofit2.create(ApiCall.class);
//
//                                Toast.makeText(getContext(), "Masuk Kelas", Toast.LENGTH_SHORT).show();
//                            mAPIService2.getSession(id_user, progamModel.getsClassID()).enqueue(new Callback<SessionRest>() {
//                                @Override
//                                public void onResponse(Call<SessionRest> call, Response<SessionRest> response) {
//                                    if (response.body() != null) {
//                                        if (response.body().getAccess_session() != null) {
//                                            sSession = response.body().getAccess_session();
//                                            mApi2.getStaticData(response.body().getAccess_session()).enqueue(new Callback<StaticDRest>() {
//                                                @Override
//                                                public void onResponse(Call<StaticDRest> call, Response<StaticDRest> response) {
//                                                    if (response.body() != null) {
//                                                        if (response.body().getStaticDRes() != null) {
//
//                                                            name = response.body().getStaticDRes().getDisplay_name();
////                                        roomid = Integer.valueOf(response.body().getStaticDRes().getClass_id());
//                                                            getToken(response.body().getStaticDRes().getJanus());
//                                                        }
//
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onFailure(Call<StaticDRest> call, Throwable t) {
//
//                                                }
//                                            });
//                                        }
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<SessionRest> call, Throwable t) {
//
//                                }
//                            });
                            }
                        }, ClassFragment.this);
                        recyclerView.setAdapter(mAdapter);
                        recyclerView.invalidate();
                        mAdapter.notifyDataSetChanged();
                        llLive.setVisibility(View.VISIBLE);
                    } else {
                        llLive.setVisibility(View.GONE);
                    }
                    if (myClassModelsW.size() > 0) {
                        // refreshing recycler view
                        mAdapterW = new ClassAdapter(getActivity(), myClassModelsW, new ClassAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(MyClassModel item) {

//
//                            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//                            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//                            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//
//                            retrofit = new Retrofit.Builder()
//                                    .baseUrl(Constant.TPS_ME)
//                                    .client(client)
//                                    .addConverterFactory(GsonConverterFactory.create())
//                                    .build();
//                            retrofit2 = new Retrofit.Builder()
//                                    .baseUrl(Constant.STATIC_REST)
//                                    .client(client)
//                                    .addConverterFactory(GsonConverterFactory.create())
//                                    .build();
//
//                            mAPIService2 = retrofit.create(ApiCall.class);
//
//                            mApi2 = retrofit2.create(ApiCall.class);
//
//                            mAPIService2.getSession(id_user, progamModel.getsClassID()).enqueue(new Callback<SessionRest>() {
//                                @Override
//                                public void onResponse(Call<SessionRest> call, Response<SessionRest> response) {
//                                    if (response.body() != null) {
//                                        if (response.body().getAccess_session() != null) {
//                                            sSession = response.body().getAccess_session();
//                                            mApi2.getStaticData(response.body().getAccess_session()).enqueue(new Callback<StaticDRest>() {
//                                                @Override
//                                                public void onResponse(Call<StaticDRest> call, Response<StaticDRest> response) {
//                                                    if (response.body() != null) {
//                                                        if (response.body().getStaticDRes() != null) {
//
//                                                            name = response.body().getStaticDRes().getDisplay_name();
////                                        roomid = Integer.valueOf(response.body().getStaticDRes().getClass_id());
//                                                            getToken(response.body().getStaticDRes().getJanus());
//                                                        }
//
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onFailure(Call<StaticDRest> call, Throwable t) {
//
//                                                }
//                                            });
//                                        }
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<SessionRest> call, Throwable t) {
//
//                                }
//                            });
                            }
                        },ClassFragment.this);
                        recyclerViewW.setAdapter(mAdapterW);
                        recyclerViewW.invalidate();
                        mAdapterW.notifyDataSetChanged();
                        llWait.setVisibility(View.VISIBLE);
                    } else {
                        llWait.setVisibility(View.GONE);
                    }
                    if (myClassModelsP.size() > 0) {
                        // refreshing recycler view
                        mAdapterP = new ClassAdapter(getActivity(), myClassModelsP, new ClassAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(MyClassModel item) {

//
//                            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//                            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//                            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//
//                            retrofit = new Retrofit.Builder()
//                                    .baseUrl(Constant.TPS_ME)
//                                    .client(client)
//                                    .addConverterFactory(GsonConverterFactory.create())
//                                    .build();
//                            retrofit2 = new Retrofit.Builder()
//                                    .baseUrl(Constant.STATIC_REST)
//                                    .client(client)
//                                    .addConverterFactory(GsonConverterFactory.create())
//                                    .build();
//
//                            mAPIService2 = retrofit.create(ApiCall.class);
//
//                            mApi2 = retrofit2.create(ApiCall.class);
//
//                            mAPIService2.getSession(id_user, progamModel.getsClassID()).enqueue(new Callback<SessionRest>() {
//                                @Override
//                                public void onResponse(Call<SessionRest> call, Response<SessionRest> response) {
//                                    if (response.body() != null) {
//                                        if (response.body().getAccess_session() != null) {
//                                            sSession = response.body().getAccess_session();
//                                            mApi2.getStaticData(response.body().getAccess_session()).enqueue(new Callback<StaticDRest>() {
//                                                @Override
//                                                public void onResponse(Call<StaticDRest> call, Response<StaticDRest> response) {
//                                                    if (response.body() != null) {
//                                                        if (response.body().getStaticDRes() != null) {
//
//                                                            name = response.body().getStaticDRes().getDisplay_name();
////                                        roomid = Integer.valueOf(response.body().getStaticDRes().getClass_id());
//                                                            getToken(response.body().getStaticDRes().getJanus());
//                                                        }
//
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onFailure(Call<StaticDRest> call, Throwable t) {
//
//                                                }
//                                            });
//                                        }
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<SessionRest> call, Throwable t) {
//
//                                }
//                            });
                            }
                        },ClassFragment.this);
                        recyclerViewP.setAdapter(mAdapterP);
                        recyclerViewP.invalidate();
                        mAdapterP.notifyDataSetChanged();
                        llPass.setVisibility(View.VISIBLE);
                    } else {
                        llPass.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ClassRest> call, Throwable t) {

            }
        });

    }

    public void getToken(final String janus) {
        mApi2.getToken(sSession, Method.getUTCTime(), janus, "student", progamModel.getsClassID()).enqueue(new Callback<StaticDRest>() {
            @Override
            public void onResponse(Call<StaticDRest> call, Response<StaticDRest> response) {
                if (response.body() != null) {
                    if (response.body().getStaticDRes() != null) {
                        if (response.body().getStaticDRes().getToen() != null) {
                            token = response.body().getStaticDRes().getToen();
                            Intent in = new Intent(getActivity(), JanusActivity.class);
                            in.putExtra("visible", "");
                            in.putExtra("subject", progamModel.getsSubject());
                            in.putExtra("template", "all_featured");
                            in.putExtra("id_user", id_user);
                            in.putExtra("class_id", progamModel.getsClassID());
                            in.putExtra("token", token);
                            in.putExtra("isTV", false);
                            in.putExtra("name", name);
                            startActivity(in);
                        } else
                            getToken(janus);
                    }
                }
            }

            @Override
            public void onFailure(Call<StaticDRest> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.NEW_CLASS));
    }

    public void ShowProgressDialog() {
        dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
