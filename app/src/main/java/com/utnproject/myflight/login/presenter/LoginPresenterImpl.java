package com.utnproject.myflight.login.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.utnproject.myflight.login.interactor.LoginInteractor;
import com.utnproject.myflight.login.interactor.LoginInteractorImpl;
import com.utnproject.myflight.login.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter{

    private LoginView loginView;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        interactor = new LoginInteractorImpl(this);
    }

    @Override

    public void singIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {
        loginView.disableInputs();
        loginView.showProgressBar();
        interactor.singIn(username,password, activity, firebaseAuth);

    }


    @Override
    public void loginSuccess() {
        loginView.goHome();
        loginView.hideProgressBar();
    }

    @Override
    public void loginError(String error) {
        loginView.enableInputs();
        loginView.hideProgressBar();
        loginView.loginError(error);
    }



}
