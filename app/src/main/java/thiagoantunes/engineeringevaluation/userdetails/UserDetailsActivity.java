package thiagoantunes.engineeringevaluation.userdetails;
import androidx.appcompat.app.AppCompatActivity;
import thiagoantunes.engineeringevaluation.R;
import android.os.Bundle;

public class UserDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_USER_ID = "USER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int userId = getIntent().getIntExtra(EXTRA_USER_ID, 0);

        setContentView(R.layout.user_details_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, UserDetailsFragment.newInstance(userId))
                    .commitNow();
        }
    }
}
