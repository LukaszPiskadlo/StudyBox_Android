package com.blstream.studybox.login_view;

import com.blstream.studybox.Constants;
import com.blstream.studybox.CredentialValidator;
import com.blstream.studybox.ValidatorListener;
import com.blstream.studybox.api.AuthRequestInterceptor;
import com.blstream.studybox.api.RequestCallback;
import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.api.RestClientManager;
import com.blstream.studybox.model.AuthCredentials;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import org.json.JSONObject;

import retrofit.RetrofitError;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    public void validateCredential(AuthCredentials credentials) {

        if (isViewAttached()) {
            getView().showLoading();
        }

        CredentialValidator validator = new CredentialValidator(credentials, new ValidatorListener() {
            @Override
            public void onSuccess(AuthCredentials credentials) {
                authenticate(credentials);
            }

            @Override
            public void onEmailFieldEmpty() {
                if (isViewAttached()) {
                    getView().showEmptyEmailError();
                }
            }

            @Override
            public void onPasswordFieldEmpty() {
                if (isViewAttached()) {
                    getView().showEmptyPasswordError();
                }
            }

            @Override
            public void onEmailValidationFailure() {
                if (isViewAttached()) {
                    getView().showInvalidEmailError();
                }
            }

            @Override
            public void onPasswordValidationFailure() {
                if (isViewAttached()) {
                    getView().showInvalidPasswordError();
                }
            }
        });

        validator.validate();
    }

    protected void authenticate(AuthCredentials credentials) {
        RestClientManager.authenticate(Constants.AUTH_URL,
                new AuthRequestInterceptor(credentials.getEmail(), credentials.getPassword()),
                new RequestCallback<>(new RequestListener<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        if (isViewAttached()) {
                            getView().loginSuccessful();
                        }
                    }

                    @Override
                    public void onFailure(RetrofitError error) {
                        if (isViewAttached()) {
                            getView().showAuthError();
                        }
                    }
                }));
    }
}
