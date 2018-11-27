package thiagoantunes.engineeringevaluation.useraddedit;

import android.app.Application;
import android.telephony.PhoneNumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import thiagoantunes.engineeringevaluation.EngineeringEvaluationApp;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.data.converter.DateConverter;
import thiagoantunes.engineeringevaluation.data.source.UserRepository;
import thiagoantunes.engineeringevaluation.util.SingleLiveEvent;

public class UserAddEditViewModel extends AndroidViewModel {

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> phone = new ObservableField<>();
    public final ObservableField<String> neighborhood = new ObservableField<>();
    public final ObservableField<String> dateOfBirth = new ObservableField<>();
    public final ObservableInt cityIdx = new ObservableInt();

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);
    private final SingleLiveEvent<Void> mUserUpdated = new SingleLiveEvent<>();
    private final UserRepository mUsersRepository;

    private int mUserId;
    private boolean mIsNewUser;
    private boolean mIsDataLoaded = false;
    private List<String> cities = new ArrayList<>();

    public UserAddEditViewModel(Application context) {
        super(context);
        mUsersRepository = ((EngineeringEvaluationApp) context).getRepository();
    }

    public void SetCities(List<String> cities) {
        this.cities = cities;
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

    // Called when clicking on fab.
    void saveUser() {

        String formatedPhone = PhoneNumberUtils.formatNumberToE164(phone.get(), Locale.getDefault().getCountry());
        String teste = PhoneNumberUtils.formatNumber(formatedPhone, Locale.getDefault().getCountry());

        User user = new User(ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE ),
                name.get(), formatedPhone, neighborhood.get(), cities.get(cityIdx.get()), DateConverter.toDate(dateOfBirth.get()));
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
