package io.muic.ooc.fab;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class AnimalFactory {
    private static final Map<AnimalType, Class<? extends Animal>> ANIMAL_MAPPING = new HashMap<>();

        public static Animal createAnimal(AnimalType animalType, boolean randomAge, Field field, Location location)  {
       try {
           return animalType.getAnimalClass().getDeclaredConstructor(boolean.class, Field.class, Location.class).newInstance(randomAge, field,location);
       } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e){
           e.printStackTrace();
       }
        throw new RuntimeException("Unknown animal type");
    }
}
