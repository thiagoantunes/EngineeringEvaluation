package thiagoantunes.engineeringevaluation.userlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import thiagoantunes.engineeringevaluation.R;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.databinding.UserListFragmentBinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import java.util.List;
import java.util.Objects;

public class UserListFragment extends Fragment {

    static final String TAG = "UserListViewModel";

    private UserAdapter mUserAdapter;

    private UserListFragmentBinding mBinding;

    private UserListViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.user_list_fragment, container, false);

        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(UserListViewModel.class);

        mUserAdapter = new UserAdapter(mUserClickCallback);

        mBinding.userList.setAdapter(mUserAdapter);

        subscribeToModel(mViewModel.getUsers());

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void subscribeToModel(LiveData<List<User>> liveData) {
        // Update the list when the data changes
        liveData.observe(this, users -> {
            if (users != null) {
                mUserAdapter.setUserList(users);
            }
            mBinding.executePendingBindings();
        });
    }

    private final UserClickCallback mUserClickCallback = user -> {

        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((UserListActivity) Objects.requireNonNull(getActivity())).showUserDetails(user);
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query == null || query.isEmpty()) {
                    subscribeToModel(mViewModel.getUsers());
                } else {
                    subscribeToModel(mViewModel.searchUsers("*" + query + "*"));
                }
                return false;
            }
        });
    }
}
