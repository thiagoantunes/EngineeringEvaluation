package thiagoantunes.engineeringevaluation.userdetails;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import thiagoantunes.engineeringevaluation.R;
import thiagoantunes.engineeringevaluation.databinding.UserDetailsFragmentBinding;
import thiagoantunes.engineeringevaluation.useraddedit.UserAddEditActivity;
import thiagoantunes.engineeringevaluation.useraddedit.UserAddEditFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserDetailsFragment extends Fragment {

    private static final String KEY_USER_ID = "user_id";

    public static final int REQUEST_EDIT_USER = 1;

    private UserDetailsFragmentBinding mBinding;

    private UserDetailsViewModel mViewModel;

    public static UserDetailsFragment newInstance(int userId) {
        return forUser(userId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.user_details_fragment, container, false);
        if (mBinding == null) {
            mBinding = UserDetailsFragmentBinding.bind(root);
        }

        mViewModel = ViewModelProviders.of(getActivity()).get(UserDetailsViewModel.class);
        mViewModel.start(getArguments().getInt(KEY_USER_ID));

        mBinding.setViewmodel(mViewModel);

        subscribeToModel();

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpFab();
    }

    public void setUpFab(){
        FloatingActionButton fab = getActivity().findViewById(R.id.fab_edit);
        fab.setImageResource(R.drawable.ic_edit);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), UserAddEditActivity.class);
            intent.putExtra(UserAddEditActivity.EXTRA_EDIT_USER_ID, getArguments().getInt(KEY_USER_ID));
            startActivityForResult(intent, REQUEST_EDIT_USER);
        });
    }

    private void subscribeToModel() {
        // Observe user data
        mViewModel.getObservableUser().observe(this, user -> mViewModel.setUser(user));
    }

    /** Creates product fragment for specific product ID */
    public static UserDetailsFragment forUser(int userId) {
        UserDetailsFragment fragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }
}
