package com.utnproject.myflight.login.repository;

import com.utnproject.myflight.login.presenter.LoginPresenter;

public class LoginRepositoryImpl implements LoginRepository {

    LoginPresenter presenter;

    public LoginRepositoryImpl(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void signIn(String username, String password) {


        boolean success = true;

        if (success){
            presenter.loginSuccess();
        }
        else{
            presenter.loginError("Ocurrió un error");
        }
    }
}
