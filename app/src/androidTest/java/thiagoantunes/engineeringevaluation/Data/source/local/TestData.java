package thiagoantunes.engineeringevaluation.Data.source.local;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import thiagoantunes.engineeringevaluation.Data.City;
import thiagoantunes.engineeringevaluation.Data.User;

public class TestData {

    static final City CITY_ENTITY = new City(1, "Belo Horizonte");

    static final City CITY_ENTITY2 = new City(2, "Contagem");

    static final User USER_ENTITY = new User(1, "João Silva", "3134333333",
            "Centro", 2, new Date());

    static final User USER_ENTITY2 = new User(2, "Maria Silva", "31945443322",
            "Savassi", 1, new Date() );

    static final List<User> USERS = Arrays.asList(USER_ENTITY, USER_ENTITY2);

    static final List<City> CITIES = Arrays.asList(CITY_ENTITY, CITY_ENTITY2);


}