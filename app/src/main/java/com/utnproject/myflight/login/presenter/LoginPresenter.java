package com.utnproject.myflight.login.presenter;

public interface LoginPresenter {

    void singIn(String username, String password); // interactor
    void loginSuccess();
    void loginError();


}
