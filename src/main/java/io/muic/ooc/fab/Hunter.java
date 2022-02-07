package io.muic.ooc.fab;

import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class Hunter extends Animal {
    /**
     * Create a hunter. A hunter can be created as a new born (age zero and not
     * hungry) or with a random age and food level.
     *
     * @param randomAge If true, the hunter will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Hunter(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
    }

    @Override
    protected double getBreedingProbability() {
        return AnimalType.HUNTER.getProbability();
    }

    @Override
    protected int getMaxLitterSize() {
        return 2;
    }

    @Override
    protected int getMaxAge() {
        return Integer.MAX_VALUE;
    }
    @Override
    protected int getBreedingAge() {
        return 80;
    }

    /**
     * This is what the hunter does most of the time: it hunts for food. In the
     * process, it might breed, die of hunger, or die of old age.
     *
     * @param newAnimals A list to return newly born hunters.
     */
    public void act(List<Animal> newAnimals) {
        incrementAge();
        if (isAlive()) {
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if (newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Look for food adjacent to the current location. Only the first live
     * animal is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood() {
        List<Location> adjacent = getField().adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object animal = getField().getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    return where;
                }
            }
            if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setDead();
                    return where;
                }
            }
            if (animal instanceof Tiger) {
                Tiger tiger = (Tiger) animal;
                if (tiger.isAlive()) {
                    tiger.setDead();
                    return where;
                }
            }
        }
        return null;
    }
}
