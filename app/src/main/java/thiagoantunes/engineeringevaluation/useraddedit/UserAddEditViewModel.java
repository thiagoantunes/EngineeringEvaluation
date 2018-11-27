package thiagoantunes.engineeringevaluation.useraddedit;

import android.app.Application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import thiagoantunes.engineeringevaluation.EngineeringEvaluationApp;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.data.converter.DateConverter;
import thiagoantunes.engineeringevaluation.data.source.UserRepository;
import thiagoantunes.engineeringevaluation.util.SingleLiveEvent;

import static java.text.DateFormat.getDateInstance;

public class UserAddEditViewModel extends AndroidViewModel {

    public final ObservableField<String> name = new ObservableField<>();

    public final ObservableField<String> phone = new ObservableField<>();

    public final ObservableField<String> neighborhood = new ObservableField<>();

    public final ObservableField<String> dateOfBirth = new ObservableField<>();

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    private final SingleLiveEvent<Void> mUserUpdated = new SingleLiveEvent<>();

    private final UserRepository mUsersRepository;

    private int mUserId;

    private boolean mIsNewUser;

    private boolean mIsDataLoaded = false;

    public UserAddEditViewModel(Application context) {
        super(context);
        mUsersRepository = ((EngineeringEvaluationApp) context).getRepository();
    }

    public void start(String temp) {
        int userId = 0;
        if (dataLoading.get()) {
            // Already loading, ignore.
            return;
        }
        mUserId = userId;
        if (userId == 0) {
            // No need to populate, it's a new User
            mIsNewUser = true;
            return;
        }
        if (mIsDataLoaded) {
            // No need to populate, already have data.
            return;
        }
        mIsNewUser = false;
        dataLoading.set(true);

        mUsersRepository.getUser(userId);
    }

    /*@Override
    public void onUserLoaded(User user) {
        name.set(user.getName());
        phone.set(user.getPhone());
        dateOfBirth.set(user.getDateOfBirth());
        neighborhood.set(user.getNeighborhood());
        dataLoading.set(false);
        mIsDataLoaded = true;

        // Note that there's no need to notify that the values changed because we're using
        // ObservableFields.
    }

    @Override
    public void onDataNotAvailable() {
        dataLoading.set(false);
    }*/

    // Called when clicking on fab.
    void saveUser() {

        User user = new User(ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE ),
                name.get(), phone.get(), neighborhood.get(), 1, DateConverter.toDate(dateOfBirth.get()));
        if (user.isEmpty()) {
            //mSnackbarText.setValue(R.string.empty_user_message);
            return;
        }
        if (isNewUser() || mUserId == 0) {
            createUser(user);
        } else {
            updateUser(user);
        }
    }

    SingleLiveEvent<Void> getTaskUpdatedEvent() {
        return mUserUpdated;
    }

    private boolean isNewUser() {
        return mIsNewUser;
    }

    private void createUser(User newUser) {
        mUsersRepository.saveUser(newUser);
        mUserUpdated.call();
    }

    private void updateUser(User User) {
        if (isNewUser()) {
            throw new RuntimeException("updateUser() was called but User is new.");
        }
        mUsersRepository.saveUser(User);
        mUserUpdated.call();
    }
}
