package thiagoantunes.engineeringevaluation.data.source;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import thiagoantunes.engineeringevaluation.data.City;
import thiagoantunes.engineeringevaluation.data.User;

public class TestData {

    public static final City CITY_ENTITY = new City(1, "Belo Horizonte");

    public static final City CITY_ENTITY2 = new City(2, "Contagem");

    public static final User USER_ENTITY = new User(1, "João Silva", "3134333333",
            "Centro", 2, new Date());

    public static final User USER_ENTITY2 = new User(2, "Maria Silva", "31945443322",
            "Savassi", 1, new Date() );

    public static final List<User> USERS = Arrays.asList(USER_ENTITY, USER_ENTITY2);

    public static final List<City> CITIES = Arrays.asList(CITY_ENTITY, CITY_ENTITY2);


}