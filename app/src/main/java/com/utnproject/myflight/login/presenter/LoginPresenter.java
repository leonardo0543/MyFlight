package com.utnproject.myflight.login.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

public interface LoginPresenter {

    void singIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth); // interactor
    void loginSuccess();
    void loginError(String error);


}
