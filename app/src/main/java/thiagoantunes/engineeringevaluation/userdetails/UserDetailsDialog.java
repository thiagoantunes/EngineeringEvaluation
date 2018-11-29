package thiagoantunes.engineeringevaluation.userdetails;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import java.util.Objects;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import thiagoantunes.engineeringevaluation.R;
import thiagoantunes.engineeringevaluation.databinding.UserDetailsFragmentBinding;
import thiagoantunes.engineeringevaluation.useraddedit.UserAddEditActivity;

public class UserDetailsDialog extends AppCompatDialogFragment {

    private static final String KEY_USER_ID = "USER_ID";

    private static final int REQUEST_EDIT_USER = 1;

    private UserDetailsFragmentBinding mBinding;

    private UserDetailsViewModel mViewModel;

    private String mUserId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mUserId = bundle.getString(KEY_USER_ID);

        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(UserDetailsViewModel.class);
        assert getArguments() != null;
        mViewModel.start(mUserId);

        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout. user_details_fragment, null, false);
        mBinding.setViewmodel(mViewModel);

        subscribeToModel();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(mBinding.getRoot())
        .setNegativeButton(getString(R.string.cancel_dialog), (dialog, which) -> {

        }).setPositiveButton("Edit User", (dialog, which) -> {

            Intent intent = new Intent(getActivity(), UserAddEditActivity.class);
            assert getArguments() != null;
            intent.putExtra(UserAddEditActivity.EXTRA_EDIT_USER_ID, getArguments().getString(KEY_USER_ID));
            startActivityForResult(intent, REQUEST_EDIT_USER);
        });

        return builder.create();
    }

    private void subscribeToModel() {
        // Observe user data
        mViewModel.getObservableUser().observe(this, user -> mViewModel.setUser(user));
    }
}
