package thiagoantunes.engineeringevaluation.useraddedit;

import android.app.Application;
import android.telephony.PhoneNumberUtils;

import com.google.common.base.Strings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import thiagoantunes.engineeringevaluation.EngineeringEvaluationApp;
import thiagoantunes.engineeringevaluation.R;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.data.converter.DateConverter;
import thiagoantunes.engineeringevaluation.data.source.UserRepository;
import thiagoantunes.engineeringevaluation.util.SingleLiveEvent;
import thiagoantunes.engineeringevaluation.util.SnackbarMessage;

public class UserAddEditViewModel extends AndroidViewModel {

    private LiveData<User> mObservableUser;

    private UserRepository mUserRepository;

    public ObservableField<User> user = new ObservableField<>();

    private List<String> cities = new ArrayList<>();

    private final SingleLiveEvent<Void> mUserUpdated = new SingleLiveEvent<>();

    private final SnackbarMessage mSnackBarText = new SnackbarMessage();

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

    SnackbarMessage getSnackbarMessage() {
        return mSnackBarText;
    }

    void SetCities(List<String> cities) {
        this.cities = cities;
    }

    public void setUser(User user) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy",
                java.util.Locale.getDefault());
        this.user.set(user);
        name.set(user.getName());
        phone.set(user.getPhone());
        neighborhood.set(user.getNeighborhood());
        cityIdx.set(cities.indexOf(user.getCity()));
        dateOfBirth.set(dateFormat.format(user.getDateOfBirth()));
    }

    // Called when clicking on fab.
    void saveUser() {
        if(validUser()){
            mUserRepository.saveUser(mapUserFields());
            mUserUpdated.call();
        }
    }

    private boolean validUser() {
        if(Strings.isNullOrEmpty(name.get())){
            mSnackBarText.setValue(R.string.required_name);
            return false;
        }
        if(Strings.isNullOrEmpty(phone.get())){
            mSnackBarText.setValue(R.string.required_phone);
            return false;
        } else if( PhoneNumberUtils
                .formatNumberToE164(phone.get(), Locale.getDefault()
                        .getCountry()) == null){
            mSnackBarText.setValue(R.string.invalid_phone);
            return false;
        }
        if(Strings.isNullOrEmpty(neighborhood.get())){
            mSnackBarText.setValue(R.string.required_neighborhood);
            return false;
        }
        if(Strings.isNullOrEmpty(dateOfBirth.get())){
            mSnackBarText.setValue(R.string.required_date);
            return false;
        } else if(DateConverter.convertAndValidateDateOfbirth(dateOfBirth.get()) == null){
            mSnackBarText.setValue(R.string.invalid_date);
            return false;
        }

        return true;
    }

    private User mapUserFields(){
        String formattedPhone = PhoneNumberUtils.formatNumberToE164(phone.get(), Locale.getDefault().getCountry());
        User user = new User(
                this.user.get() != null ? this.user.get().getId() : 0,
                Objects.requireNonNull(name.get()), formattedPhone,
                Objects.requireNonNull(neighborhood.get()),
                cities.get(cityIdx.get()),
                Objects.requireNonNull(DateConverter.convertAndValidateDateOfbirth(dateOfBirth.get())));
        return user;
    }
}
