package thiagoantunes.engineeringevaluation.useraddedit;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        mViewDataBinding.setViewmodel(mViewModel);

        return mViewDataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupFab();

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

}
