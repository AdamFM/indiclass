package com.apps.indiclass.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apps.indiclass.ChangePasswordActivity;
import com.apps.indiclass.R;
import com.apps.indiclass.model.Configuration;
import com.apps.indiclass.util.SessionManager;

import java.util.ArrayList;
import java.util.List;

import static com.apps.indiclass.util.SessionManager.KEY_EMAI;
import static com.apps.indiclass.util.SessionManager.KEY_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = "NotificationFragment";

    private List<Configuration> listConfig = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserInfoAdapter infoAdapter;

    private static final String USERNAME_LABEL = "Nama";
    private static final String EMAIL_LABEL = "Email";
    private static final String SIGNOUT_LABEL = "Keluar";
    private static final String RESETPASS_LABEL = "Ganti Kata Sandi";

    public ProfileFragment() {
        // Required empty public constructor
    }

    SessionManager sessionManager;

    TextView tvName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        sessionManager = new SessionManager(getContext());

        tvName= view.findViewById(R.id.tv_username);
        tvName.setText(sessionManager.getUserDetails().get(KEY_NAME));

        recyclerView = view.findViewById(R.id.info_recycler_view);
        infoAdapter = new UserInfoAdapter(listConfig);
        setupArrayListInfo();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(infoAdapter);

        return view;
    }

    public void setupArrayListInfo(){
        listConfig.clear();
        Configuration userNameConfig = new Configuration(USERNAME_LABEL, sessionManager.getUserDetails().get(KEY_NAME), R.drawable.ic_account_box_black_24dp);
        listConfig.add(userNameConfig);

        Configuration emailConfig = new Configuration(EMAIL_LABEL, sessionManager.getUserDetails().get(KEY_EMAI), R.drawable.ic_email_blacks_24dp);
        listConfig.add(emailConfig);

        Configuration resetPass = new Configuration(RESETPASS_LABEL, "", R.drawable.ic_settings_backup_restore_black_24dp);
        listConfig.add(resetPass);

        Configuration signout = new Configuration(SIGNOUT_LABEL, "", R.drawable.ic_power_settings_new_black_24dp);
        listConfig.add(signout);
    }

    public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.ViewHolder>{
        private List<Configuration> profileConfig;

        public UserInfoAdapter(List<Configuration> profileConfig){
            this.profileConfig = profileConfig;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_info_item_layout, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Configuration config = profileConfig.get(position);
            holder.label.setText(config.getLabel());
            holder.value.setText(config.getValue());
            holder.icon.setImageResource(config.getIcon());
            ((RelativeLayout)holder.label.getParent()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(config.getLabel().equals(SIGNOUT_LABEL)){android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                        builder.setMessage("Apakah Anda yakin ingin keluar dari akun Anda?");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Iya", new
                                DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        sessionManager.logoutUser();
                                        getActivity().finish();

                                    }
                                });
                        builder.setNegativeButton("Tidak", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int arg1) {
                                        // TODO Auto-generated method stub
                                        dialog.cancel();
                                    }
                                }).show();
                        Log.i(TAG, "onClick: " + SIGNOUT_LABEL);
                    }

                    if(config.getLabel().equals(USERNAME_LABEL)){
//                        View vewInflater = LayoutInflater.from(context)
//                                .inflate(R.layout.dialog_edit_username,  (ViewGroup) getView(), false);
//                        final EditText input = (EditText)vewInflater.findViewById(R.id.edit_username);
//                        input.setText(myAccount.name);
//                        /*Hiển thị dialog với dEitText cho phép người dùng nhập username mới*/
//                        new AlertDialog.Builder(context)
//                                .setTitle("Edit username")
//                                .setView(vewInflater)
//                                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        String newName = input.getText().toString();
//                                        if(!myAccount.name.equals(newName)){
//                                            changeUserName(newName);
//                                        }
//                                        dialogInterface.dismiss();
//                                    }
//                                })
//                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.dismiss();
//                                    }
//                                }).show();
                        Log.i(TAG, "onClick: " + USERNAME_LABEL);
                    }

                    if(config.getLabel().equals(RESETPASS_LABEL)){
//                        new AlertDialog.Builder(context)
//                                .setTitle("Password")
//                                .setMessage("Are you sure want to reset password?")
//                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        resetPassword(myAccount.email);
//                                        dialogInterface.dismiss();
//                                    }
//                                })
//                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.dismiss();
//                                    }
//                                }).show();

                        startActivity(new Intent(getContext(), ChangePasswordActivity.class));
                        Log.i(TAG, "onClick: " + RESETPASS_LABEL);
                    }
                }
            });
        }
//        private void changeUserName(String newName){
//            userDB.child("name").setValue(newName);
//
//
//            myAccount.name = newName;
//            SharedPreferenceHelper prefHelper = SharedPreferenceHelper.getInstance(context);
//            prefHelper.saveUserInfo(myAccount);
//
//            tvUserName.setText(newName);
//            setupArrayListInfo(myAccount);
//        }
//
//        void resetPassword(final String email) {
//            mAuth.sendPasswordResetEmail(email)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            new LovelyInfoDialog(context) {
//                                @Override
//                                public LovelyInfoDialog setConfirmButtonText(String text) {
//                                    findView(com.yarolegovich.lovelydialog.R.id.ld_btn_confirm).setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            dismiss();
//                                        }
//                                    });
//                                    return super.setConfirmButtonText(text);
//                                }
//                            }
//                                    .setTopColorRes(R.color.colorPrimary)
//                                    .setIcon(R.drawable.ic_pass_reset)
//                                    .setTitle("Password Recovery")
//                                    .setMessage("Sent email to " + email)
//                                    .setConfirmButtonText("Ok")
//                                    .show();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            new LovelyInfoDialog(context) {
//                                @Override
//                                public LovelyInfoDialog setConfirmButtonText(String text) {
//                                    findView(com.yarolegovich.lovelydialog.R.id.ld_btn_confirm).setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            dismiss();
//                                        }
//                                    });
//                                    return super.setConfirmButtonText(text);
//                                }
//                            }
//                                    .setTopColorRes(R.color.colorAccent)
//                                    .setIcon(R.drawable.ic_pass_reset)
//                                    .setTitle("False")
//                                    .setMessage("False to sent email to " + email)
//                                    .setConfirmButtonText("Ok")
//                                    .show();
//                        }
//                    });
//        }

        @Override
        public int getItemCount() {
            return profileConfig.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView label, value;
            public ImageView icon;
            public ViewHolder(View view) {
                super(view);
                label = (TextView)view.findViewById(R.id.tv_title);
                value = (TextView)view.findViewById(R.id.tv_detail);
                icon = (ImageView)view.findViewById(R.id.img_icon);
            }
        }

    }
}
