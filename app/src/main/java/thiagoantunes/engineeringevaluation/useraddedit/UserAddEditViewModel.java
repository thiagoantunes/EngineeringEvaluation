package thiagoantunes.engineeringevaluation.useraddedit;

import android.app.Application;
import android.telephony.PhoneNumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import thiagoantunes.engineeringevaluation.EngineeringEvaluationApp;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.data.converter.DateConverter;
import thiagoantunes.engineeringevaluation.data.source.UserRepository;
import thiagoantunes.engineeringevaluation.util.SingleLiveEvent;

public class UserAddEditViewModel extends AndroidViewModel {

    private LiveData<User> mObservableUser;

    private UserRepository mUserRepository;

    public ObservableField<User> user = new ObservableField<>();

    private List<String> cities = new ArrayList<>();

    private final SingleLiveEvent<Void> mUserUpdated = new SingleLiveEvent<>();

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> phone = new ObservableField<>();
    public final ObservableField<String> neighborhood = new ObservableField<>();
    public final ObservableField<String> dateOfBirth = new ObservableField<>();
    public final ObservableInt cityIdx = new ObservableInt();

    public UserAddEditViewModel(Application context) {
        super(context);
        mUserRepository = ((EngineeringEvaluationApp) context).getRepository();
    }

    public void start(int userId) {
        if (userId != 0) {
            mObservableUser = mUserRepository.getUser(userId);
        }
    }

    LiveData<User> getObservableUser() {
        return mObservableUser;
    }

    SingleLiveEvent<Void> getUserUpdatedEvent() { return mUserUpdated; }

    void SetCities(List<String> cities) {
        this.cities = cities;
    }

    public void setUser(User user) {
        this.user.set(user);
        name.set(user.getName());
        phone.set(user.getPhone());
        neighborhood.set(user.getNeighborhood());
        cityIdx.set(cities.indexOf(user.getCity()));
        dateOfBirth.set(user.getDateOfBirth().toString());
    }

    // Called when clicking on fab.
    void saveUser() {

        String formatedPhone = PhoneNumberUtils.formatNumberToE164(phone.get(), Locale.getDefault().getCountry());
        //String teste = PhoneNumberUtils.formatNumber(formatedPhone, Locale.getDefault().getCountry());

        User user = new User(
                0,
                Objects.requireNonNull(name.get()), formatedPhone,
                Objects.requireNonNull(neighborhood.get()),
                cities.get(cityIdx.get()),
                Objects.requireNonNull(DateConverter.toDate(dateOfBirth.get())));

        if (user.isEmpty()) {
            //mSnackbarText.setValue(R.string.empty_user_message);
            return;
        }

        mUserRepository.saveUser(user);
        mUserUpdated.call();
    }
}
