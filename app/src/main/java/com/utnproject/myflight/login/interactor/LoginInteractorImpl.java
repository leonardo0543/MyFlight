package com.utnproject.myflight.login.interactor;

import com.utnproject.myflight.login.presenter.LoginPresenter;

public class LoginInteractorImpl implements LoginInteractor {
    private LoginPresenter loginPresenter;

    public LoginInteractorImpl(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void singIn(String username, String password) {

        
    }
}
