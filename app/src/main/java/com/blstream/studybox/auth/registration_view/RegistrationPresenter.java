package com.blstream.studybox.auth.registration_view;

import com.blstream.studybox.api.AuthRequestInterceptor;
import com.blstream.studybox.api.RequestCallback;
import com.blstream.studybox.api.RequestListener;
import com.blstream.studybox.api.RestClientManager;
import com.blstream.studybox.auth.login.CredentialValidator;
import com.blstream.studybox.auth.login.LoginInterface;
import com.blstream.studybox.auth.login.LoginManager;
import com.blstream.studybox.model.AuthCredentials;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit.RetrofitError;

public class RegistrationPresenter extends MvpBasePresenter<RegistrationView> {

    public void validateCredential(AuthCredentials credentials) {

        if (isViewAttached()) {
            //noinspection ConstantConditions
            getView().showLoading();
        }

        CredentialValidator validator = new CredentialValidator(credentials,
                new RegistrationValidatorListener() {
                    @Override
                    public void onSuccess(AuthCredentials credentials) {
                        signUp(credentials);
                    }

                    @Override
                    public void onShowEmptyRepeatPasswordError() {
                        if (isViewAttached()) {
                            getView().showEmptyRepeatPasswordError();
                        }
                    }

                    @Override
                    public void onPasswordsInconsistent() {
                        if (isViewAttached()) {
                            getView().showPasswordInconsistent();
                        }
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
                    public void onPasswordTooShort() {
                        if (isViewAttached()) {
                            getView().showTooShortPasswordError();
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
                }
        );

        validator.validate();
    }

    private void signUp(final AuthCredentials credentials) {
        RestClientManager.signUp(credentials,
                new RequestCallback<>(new RequestListener<AuthCredentials>() {
                    @Override
                    public void onSuccess(AuthCredentials response) {
                        authenticate(credentials);
                    }

                    @Override
                    public void onFailure(RetrofitError error) {
                        showError(error);
                    }
                }));
    }

    private void authenticate(final AuthCredentials credentials) {
        RestClientManager.authenticate(new AuthRequestInterceptor(credentials),
                new RequestCallback<>(new RequestListener<AuthCredentials>() {
                    @Override
                    public void onSuccess(AuthCredentials response) {
                        if (isViewAttached()) {
                            //noinspection ConstantConditions
                            getView().loginSuccessful();

                            response.setPassword(credentials.getPassword());
                            LoginInterface login = new LoginManager();
                            login.saveUser(response);
                        }
                    }

                    @Override
                    public void onFailure(RetrofitError error) {
                        showError(error);
                    }
                })
        );
    }

    private void showError(RetrofitError error) {
        if (error.getKind().equals(RetrofitError.Kind.NETWORK)) {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().showNetworkError();
            }
        } else if (error.getKind().equals(RetrofitError.Kind.HTTP)){
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().showAuthError();
            }
        } else {
            if (isViewAttached()) {
                //noinspection ConstantConditions
                getView().showUnexpectedError();
            }
        }
    }
}
