package thiagoantunes.engineeringevaluation;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.useraddedit.UserAddEditActivity;
import thiagoantunes.engineeringevaluation.userdetails.UserDetailsActivity;
import thiagoantunes.engineeringevaluation.userdetails.UserDetailsFragment;
import thiagoantunes.engineeringevaluation.userlist.UserClickCallback;
import thiagoantunes.engineeringevaluation.userlist.UserListFragment;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setUpFab();

        // Add user list fragment if this is first creation
        if (savedInstanceState == null) {
            UserListFragment fragment = new UserListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, UserListFragment.TAG).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void setUpFab(){
        FloatingActionButton fab = findViewById(R.id.fab_add_user);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UserAddEditActivity.class);
            startActivityForResult(intent, UserAddEditActivity.REQUEST_CODE);
        });
    }

    /** Shows the user detail fragment */
    public void showUserDetails(User user) {
        Intent intent = new Intent(MainActivity.this, UserDetailsActivity.class);
        intent.putExtra(UserDetailsActivity.EXTRA_USER_ID, user.getId());
        startActivityForResult(intent, UserAddEditActivity.REQUEST_CODE);

//        UserDetailsFragment userDetailsFragment = UserDetailsFragment.forUser(user.getId());
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .addToBackStack("user")
//                .replace(R.id.fragment_container,
//                        userDetailsFragment, null).commit();
    }
}
