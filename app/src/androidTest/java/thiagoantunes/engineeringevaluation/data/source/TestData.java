package thiagoantunes.engineeringevaluation.data.source;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import thiagoantunes.engineeringevaluation.data.User;

public class TestData {

    public static final User USER_ENTITY = new User(0, "João Silva", "3134333333",
            "Centro", "Belo Horizonte", new Date());

    public static final User USER_ENTITY2 = new User(0, "Maria Silva", "31945443322",
            "Savassi", "Contagem", new Date() );

    public static final List<User> USERS = Arrays.asList(USER_ENTITY, USER_ENTITY2);


}