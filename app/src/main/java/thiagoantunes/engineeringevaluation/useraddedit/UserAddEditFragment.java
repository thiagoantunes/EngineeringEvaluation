package thiagoantunes.engineeringevaluation.useraddedit;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

import thiagoantunes.engineeringevaluation.R;
import thiagoantunes.engineeringevaluation.databinding.UserAddEditFragmentBinding;
import thiagoantunes.engineeringevaluation.userdetails.UserDetailsFragment;
import thiagoantunes.engineeringevaluation.util.DateInputMask;
import thiagoantunes.engineeringevaluation.util.SnackbarMessage;
import thiagoantunes.engineeringevaluation.util.SnackbarUtils;

public class UserAddEditFragment extends Fragment {

    private static final String KEY_EDIT_USER_ID = "EDIT_USER_ID";

    private UserAddEditViewModel mViewModel;

    private UserAddEditFragmentBinding mViewDataBinding;

    static UserAddEditFragment newInstance(int userId) { return forUser(userId); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.user_add_edit_fragment, container, false);
        if (mViewDataBinding == null) {
            mViewDataBinding = UserAddEditFragmentBinding.bind(root);
        }

        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(UserAddEditViewModel.class);
        assert getArguments() != null;
        mViewModel.start(getArguments().getInt(KEY_EDIT_USER_ID));

        Resources res = getResources();
        mViewModel.SetCities(Arrays.asList(res.getStringArray(R.array.cities_array)));

        mViewDataBinding.setViewmodel(mViewModel);

        subscribeToModel();

        return mViewDataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupFab();

        setupDatePicker();

        setupPhoneEditText();

        setupSnackBar();
    }

    private void subscribeToModel() {
        // Observe user data
        assert getArguments() != null;
        if(getArguments().getInt(KEY_EDIT_USER_ID) > 0){
            mViewModel.getObservableUser().observe(this, user -> mViewModel.setUser(user));
        }
    }

    private void setupFab() {
        FloatingActionButton fab = Objects.requireNonNull(getActivity()).findViewById(R.id.fab_save_user);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(v -> mViewModel.saveUser());
    }

    private void setupPhoneEditText(){
        EditText phoneInput = Objects.requireNonNull(getActivity()).findViewById(R.id.edit_text_phone);
        phoneInput.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    private void setupDatePicker(){
        EditText dateOfBirthEditText = Objects.requireNonNull(getActivity()).findViewById(R.id.edit_text_date_of_birth);
        dateOfBirthEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        //DateInputMask teste = new DateInputMask(dateOfBirthEditText);
    }

    private void setupSnackBar() {
        mViewModel.getSnackbarMessage().observe(this, (SnackbarMessage.SnackbarObserver) snackbarMessageResourceId -> SnackbarUtils.showSnackbar(getView(), getString(snackbarMessageResourceId)));
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, day);
        }

        @SuppressLint("SetTextI18n")
        public void onDateSet(DatePicker view, int year, int month, int day) {
            EditText dateOfBirthEditText = getActivity().findViewById(R.id.edit_text_date_of_birth);
            dateOfBirthEditText.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

    /** Creates product fragment for specific product ID */
    private static UserAddEditFragment forUser(int userId) {
        UserAddEditFragment fragment = new UserAddEditFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_EDIT_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

}
