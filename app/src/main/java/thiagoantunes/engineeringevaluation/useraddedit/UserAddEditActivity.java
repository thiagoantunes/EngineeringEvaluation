package thiagoantunes.engineeringevaluation.useraddedit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import thiagoantunes.engineeringevaluation.useraddedit.ui.useraddedit.UserAddEditFragment;

public class UserAddEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_add_edit_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, UserAddEditFragment.newInstance())
                .commitNow();
        }
    }
}
