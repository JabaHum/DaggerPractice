package com.example.daggerpractice.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.daggerpractice.models.User;
import com.example.daggerpractice.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";

    @Inject
    AuthApi authApi;

    private MediatorLiveData<Resource<User>> authUser = new MediatorLiveData<>();

    @Inject
    AuthViewModel(AuthApi authApi) {
        this.authApi = authApi;
    }

    void authenticateWithId(int userId) {

        authUser.setValue(Resource.loading((User) null));

        final LiveData<Resource<User>> source = LiveDataReactiveStreams.fromPublisher(
                authApi.getUsers(userId)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {

                                User errorUser = new User();
                                errorUser.setId(-1);

                                return errorUser;
                            }
                        })
                        .map(new Function<User, Resource<User>>() {
                            @Override
                            public Resource<User> apply(User user) throws Exception {
                                if (user.getId() == -1) {
                                    return Resource.error("Could Not Authenticate", (User) null);
                                }
                                return Resource.success(user);
                            }
                        })
                        .subscribeOn(Schedulers.io())
        );

        authUser.addSource(source, new Observer<Resource<User>>() {
            @Override
            public void onChanged(Resource<User> user) {
                authUser.setValue(user);
                authUser.removeSource(source);
            }
        });

    }

    public LiveData<Resource<User>> observeUser() {
        return authUser;
    }
}
