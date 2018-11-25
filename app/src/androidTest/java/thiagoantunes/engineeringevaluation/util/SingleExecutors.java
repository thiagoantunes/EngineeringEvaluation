package thiagoantunes.engineeringevaluation.util;

import java.util.concurrent.Executor;

import androidx.annotation.NonNull;

public class SingleExecutors extends AppExecutors {
    private static Executor instant = new Executor() {
        @Override
        public void execute(@NonNull Runnable command) {
            command.run();
        }
    };

    public SingleExecutors() {
        super(instant, instant, instant);
    }
}
