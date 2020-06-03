package com.example.ai003_1.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ai003_1.CDictionary;
import com.example.ai003_1.LoginActivity;
import com.example.ai003_1.MainActivity;
import com.example.ai003_1.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rv_account;
    private String[] accounts;
    private List<FAccount> fAccounts;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        InitialComponent();

        accounts = getResources().getStringArray(R.array.accounts);
        fAccounts = new ArrayList<>();
        fAccounts.add(new FAccount(accounts[0],R.drawable.iconfinder_account_user));
        fAccounts.add(new FAccount(accounts[1],R.drawable.iconfinder_help));
        fAccounts.add(new FAccount(accounts[2],R.drawable.iconfinder_login_out));

        //return inflater.inflate(R.layout.fragment_account, container, false);
        return rv_account;
    }

    private void InitialComponent() {
        rv_account = new RecyclerView(getContext());
        //rv_account = getView().findViewById(R.id.rv_account);
        rv_account.setHasFixedSize(true);
        rv_account.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        TextViewAdapter adapter = new TextViewAdapter();
        rv_account.setAdapter(adapter);
    }

    public class TextViewAdapter extends RecyclerView.Adapter<TextViewAdapter.TextViewHolder> {
        @NonNull
        @Override
        public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.recycler_account, parent, false);
            return new TextViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
            final FAccount fAccount = fAccounts.get(position);
            holder.textView.setText(fAccount.getName());
            holder.imageView.setImageResource(fAccount.icon);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (fAccount.getName()) {
                        case "帳號設定":

                        case "幫助中心":
                            break;
                        case "登出":
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("登出")
                                        .setMessage("確定登出嗎?")
                                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                getActivity().getSharedPreferences(CDictionary.LOGIN,MODE_PRIVATE)
                                                            .edit()
                                                            .putBoolean(CDictionary.LOGON,false)
                                                            .commit();
                                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                                            startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("取消",null)
                                        .show();
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return accounts.length;
        }

        public class TextViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            ImageView imageView;

            public TextViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.txt_account_name);
                imageView = itemView.findViewById(R.id.image_account_image);
            }
        }
    }
}