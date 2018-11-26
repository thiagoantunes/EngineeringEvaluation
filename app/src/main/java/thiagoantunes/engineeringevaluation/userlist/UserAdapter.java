package thiagoantunes.engineeringevaluation.userlist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import thiagoantunes.engineeringevaluation.R;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.databinding.UserItemBinding;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    List<? extends User> mUserList;

    @Nullable
    private final UserClickCallback mUserClickCallback;

    public UserAdapter(@Nullable UserClickCallback useritemNavigator) {
        mUserClickCallback = useritemNavigator;
    }

    public void setUserList(final List<? extends User> userList) {
        if (mUserList == null) {
            mUserList = userList;
            notifyItemRangeInserted(0, userList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {

                    return mUserList.size();
                }

                @Override
                public int getNewListSize() {

                    return userList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mUserList.get(oldItemPosition).getId() ==
                            userList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    User newUser = userList.get(newItemPosition);
                    User oldUser = mUserList.get(oldItemPosition);
                    return newUser.getId() == oldUser.getId()
                            && Objects.equals(newUser.getName(), oldUser.getName())
                            && Objects.equals(newUser.getPhone(), oldUser.getPhone())
                            && Objects.equals(newUser.getNeighborhood(), oldUser.getNeighborhood())
                            && newUser.getCityId() == oldUser.getCityId()
                            && newUser.getDateOfBirth() == oldUser.getDateOfBirth();
                }
            });
            mUserList = userList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.user_item,
                        parent, false);
        binding.setCallback(mUserClickCallback);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.binding.setUser(mUserList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mUserList == null ? 0 : mUserList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        final UserItemBinding binding;

        public UserViewHolder(UserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

