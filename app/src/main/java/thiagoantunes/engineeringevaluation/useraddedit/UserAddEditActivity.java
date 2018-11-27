package thiagoantunes.engineeringevaluation.useraddedit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import thiagoantunes.engineeringevaluation.R;

import android.os.Bundle;

public class UserAddEditActivity extends AppCompatActivity implements UserAddEditCallback{

    public static final int REQUEST_CODE = 1;

    public static final int ADD_EDIT_RESULT_OK = RESULT_FIRST_USER + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_add_edit_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, UserAddEditFragment.newInstance())
                    .commitNow();
        }

        subscribeToNavigationChanges();
    }

    private void subscribeToNavigationChanges() {
        UserAddEditViewModel viewModel = ViewModelProviders.of(this).get(UserAddEditViewModel.class);

        // The activity observes the navigation events in the ViewModel
        viewModel.getTaskUpdatedEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void _) {
                UserAddEditActivity.this.onUserSaved();
            }
        });
    }

    @Override
    public void onUserSaved() {
        setResult(ADD_EDIT_RESULT_OK);
        finish();
    }

}
