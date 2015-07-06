package io.github.scarletsky.bangumi.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.rengwuxian.materialedittext.MaterialEditText;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.api.ApiManager;
import io.github.scarletsky.bangumi.api.models.User;
import io.github.scarletsky.bangumi.utils.ToastManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by scarlex on 15-7-6.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private MaterialDialog mProgressDialog;
    private MaterialEditText mUsernameField;
    private MaterialEditText mPasswordField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initProgressDialog();

        mUsernameField = (MaterialEditText) getView().findViewById(R.id.username_field);
        mPasswordField = (MaterialEditText) getView().findViewById(R.id.password_field);

        Button loginBtn = (Button) getView().findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsernameField.getText().toString();
                String password = mPasswordField.getText().toString();

                if (isInputsValid(username, password)) {

                    showProgressDialog();
                    login(username, password);

                }

            }
        });
    }

    private void login(String username, String password) {
        ApiManager.getBangumiApi().auth(username, password, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                hideProgressDialog();

                if (user.getAuth() == null) {
                    ToastManager.show(getActivity(), getString(R.string.toast_username_or_password_wrong));
                } else {
                    ToastManager.show(getActivity(), "登录成功");
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void initProgressDialog() {
        mProgressDialog = new MaterialDialog
                .Builder(getActivity())
                .content(getString(R.string.dialog_logining))
                .progress(true, 0)
                .theme(Theme.LIGHT)
                .build();
    }

    private void showProgressDialog() {
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        mProgressDialog.hide();
    }

    private boolean isInputsValid(String username, String password) {
        boolean flag = true;

        if (username.trim().length() == 0) {
            flag = false;
            mUsernameField.setError(getString(R.string.error_username_must_not_be_empty));
        }

        if (password.trim().length() == 0) {
            flag = false;
            mPasswordField.setError(getString(R.string.error_password_must_not_be_empty));
        }

        return flag;
    }
}

