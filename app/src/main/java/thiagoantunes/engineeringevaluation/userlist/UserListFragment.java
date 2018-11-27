package thiagoantunes.engineeringevaluation.userlist;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import thiagoantunes.engineeringevaluation.MainActivity;
import thiagoantunes.engineeringevaluation.R;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.databinding.UserlistFragmentBinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserListFragment extends Fragment {

    public static final String TAG = "UserListViewModel";

    private UserAdapter mUserAdapter;

    private UserlistFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.userlist_fragment, container, false);

        mUserAdapter = new UserAdapter(mUserClickCallback);
        mBinding.userList.setAdapter(mUserAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final UserListViewModel viewModel =
                ViewModelProviders.of(this).get(UserListViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(UserListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> myUsers) {
                if (myUsers != null) {
                    mBinding.setIsLoading(false);
                    mUserAdapter.setUserList(myUsers);
                } else {
                    mBinding.setIsLoading(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
                mBinding.executePendingBindings();
            }
        });
    }

    private final UserClickCallback mUserClickCallback = new UserClickCallback() {
        @Override
        public void onClick(User user) {

            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).showUserDetails(user);
            }
        }
    };
}
