package com.utnproject.myflight.login.interactor;

import com.utnproject.myflight.login.presenter.LoginPresenter;
import com.utnproject.myflight.login.repository.LoginRepository;
import com.utnproject.myflight.login.repository.LoginRepositoryImpl;

public class LoginInteractorImpl implements LoginInteractor {
    private LoginPresenter loginPresenter;
    private LoginRepository repository;

    public LoginInteractorImpl(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
        repository = new LoginRepositoryImpl(loginPresenter);
    }

    @Override
    public void singIn(String username, String password) {
        repository.signIn(username,password);

    }
}
