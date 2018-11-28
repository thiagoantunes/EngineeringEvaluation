package thiagoantunes.engineeringevaluation.userlist;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import thiagoantunes.engineeringevaluation.R;
import thiagoantunes.engineeringevaluation.useraddedit.UserAddEditActivity;
import thiagoantunes.engineeringevaluation.userdetails.UserDetailsDialog;


public class UserListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_activity);
        setupToolbar();
        setUpFab();

        // Add user list fragment if this is first creation
        if (savedInstanceState == null) {
            UserListFragment fragment = new UserListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, UserListFragment.TAG).commit();
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void setUpFab(){
        FloatingActionButton fab = findViewById(R.id.fab_add_user);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(UserListActivity.this, UserAddEditActivity.class);
            startActivityForResult(intent, UserAddEditActivity.REQUEST_CODE);
        });
    }

    /** Shows the user detail fragment */
    public void showUserDetails(int userId) {
        Bundle bundle = new Bundle();
        bundle.putInt("USER_ID",userId);
        UserDetailsDialog dialog = new UserDetailsDialog();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager() , "Edit User");
    }
}
