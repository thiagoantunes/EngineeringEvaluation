package thiagoantunes.engineeringevaluation;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import thiagoantunes.engineeringevaluation.data.User;
import thiagoantunes.engineeringevaluation.useraddedit.UserAddEditActivity;
import thiagoantunes.engineeringevaluation.userlist.UserClickCallback;
import thiagoantunes.engineeringevaluation.userlist.UserListFragment;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements UserClickCallback {

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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == ADD_USER_REQUEST && resultCode == RESULT_OK) {
//            String name = data.getStringExtra(UserAddEditActivity.EXTRA_NAME);
//            String phone = data.getStringExtra(UserAddEditActivity.EXTRA_PHONE);
//            String neighborhood = data.getStringExtra(UserAddEditActivity.EXTRA_NEIGHBORHOOD);
//            int cityId = data.getIntExtra(UserAddEditActivity.EXTRA_CITY_ID, 1);
//            Date dateOfBitrh = (Date)data.getSerializableExtra(UserAddEditActivity.EXTRA_DATE_OF_BIRTH);
//
//            User note = new User(ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE),
//                    name, phone, neighborhood, cityId, dateOfBitrh);
//            //noteViewModel.insert(note);
//
//            Toast.makeText(this, "User saved", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "User not saved", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void setUpFab(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserAddEditActivity.class);
                startActivityForResult(intent, UserAddEditActivity.REQUEST_CODE);
            }
        });
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

    /** Shows the user detail fragment */
    public void showUserDetails(User user) {

//        UserDetailsFragment userDetailsFragment = UserDetailsFragment.forUser(user.getId());
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .addToBackStack("product")
//                .replace(R.id.fragment_container,
//                        userDetailsFragment, null).commit();
    }

    @Override
    public void onClick(User user) {

    }
}
