package com.lzy.studysource.dialogfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.lzy.studysource.R;

/**
 * @author: cyli8
 * @date: 2018/3/7 14:56
 */

public class LoginDialogFragment extends DialogFragment {

    private EditText mNameEt;
    private EditText mPwdEt;

    //1、第一种方法
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(getContext(), R.layout.dialog_login, null);
//        mNameEt = ((EditText) view.findViewById(R.id.et_name));
//        mPwdEt = ((EditText) view.findViewById(R.id.et_pwd));
        view.findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "点击", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    //第二种方法
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        View view = View.inflate(getContext(), R.layout.dialog_login, null);
//        mNameEt = view.findViewById(R.id.et_name);
//        mNameEt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "点击", Toast.LENGTH_SHORT).show();
//            }
//        });
//        builder.setView(view);
//        return builder.create();
//    }
}
