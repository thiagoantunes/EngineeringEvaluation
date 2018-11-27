package thiagoantunes.engineeringevaluation.useraddedit;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.Calendar;

import thiagoantunes.engineeringevaluation.R;
import thiagoantunes.engineeringevaluation.databinding.UserAddEditFragmentBinding;

public class UserAddEditFragment extends Fragment {

    public static final String ARGUMENT_EDIT_USER_ID = "EDIT_USER_ID";

    private UserAddEditViewModel mViewModel;

    private UserAddEditFragmentBinding mViewDataBinding;

    public static UserAddEditFragment newInstance() {
        return new UserAddEditFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.user_add_edit_fragment, container, false);
        if (mViewDataBinding == null) {
            mViewDataBinding = UserAddEditFragmentBinding.bind(root);
        }

        mViewModel = ViewModelProviders.of(getActivity()).get(UserAddEditViewModel.class);

        Resources res = getResources();
        mViewModel.SetCities(Arrays.asList(res.getStringArray(R.array.cities_array)));

        mViewDataBinding.setViewmodel(mViewModel);

        return mViewDataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupFab();

        setupDatePicker();

        loadData();
    }

    private void loadData() {
        // Add or edit an existing task?
        if (getArguments() != null) {
            mViewModel.start(getArguments().getString(ARGUMENT_EDIT_USER_ID));
        } else {
            mViewModel.start(null);
        }
    }

    private void setupFab() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab_save_user);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.saveUser();
            }
        });
    }

    private void setupDatePicker(){
        EditText dateOfBirthEditText = getActivity().findViewById(R.id.edit_text_date_of_birth);
        dateOfBirthEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                }
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            EditText dateOfBirthEditText = getActivity().findViewById(R.id.edit_text_date_of_birth);
            dateOfBirthEditText.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

}
