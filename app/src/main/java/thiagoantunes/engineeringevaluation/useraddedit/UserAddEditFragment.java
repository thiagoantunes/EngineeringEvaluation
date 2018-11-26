package thiagoantunes.engineeringevaluation.useraddedit.ui.useraddedit;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thiagoantunes.engineeringevaluation.useraddedit.R;

public class UserAddEditFragment extends Fragment {

    private UserAddEditViewModel mViewModel;

    public static UserAddEditFragment newInstance() {
        return new UserAddEditFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_add_edit_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserAddEditViewModel.class);
        // TODO: Use the ViewModel
    }

}
