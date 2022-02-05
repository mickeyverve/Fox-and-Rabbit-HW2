package io.muic.ooc.fab;

import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class Hunter extends Animal {
    // Characteristics shared by all hunters (class variables).

    // The age at which a hunter can start to breed.
    private static final int BREEDING_AGE = 80;
    // The age to which a hunter can live.
    private static final int MAX_AGE = Integer.MAX_VALUE;
    // The likelihood of a hunter breeding.
    private static final double BREEDING_PROBABILITY = 0.005;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a hunter can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 9;
    // The food value of a single fox. In effect, this is the
    // number of steps a tiger can go before it has to eat again.
    private static final int FOX_FOOD_VALUE = 20;
    // The food value of a single tiger. In effect, this is the
    // number of steps a hunter can go before it has to eat again.
    private static final int TIGER_FOOD_VALUE = 40;
    // Random generator
    private static final Random RANDOM = new Random();

    /**
     * Create a hunter. A hunter can be created as a new born (age zero and not
     * hungry) or with a random age and food level.
     *
     * @param randomAge If true, the hunter will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
//    public Fox(boolean randomAge, Field field, Location location) {
//        super(randomAge, field, location);
//        foodLevel = RANDOM.nextInt(RABBIT_FOOD_VALUE);
//    }

    public Hunter(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
    }

    @Override
    protected double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    @Override
    protected int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    /**
     * This is what the hunter does most of the time: it hunts for food. In the
     * process, it might breed, die of hunger, or die of old age.
     *
     * @param newHunters A list to return newly born hunters.
     */
    public void act(List<Animal> newHunters) {
        incrementAge();
        if (isAlive()) {
            giveBirth(newHunters);
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
    private Location findFood() {
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

    /**
     * Check whether or not this hunter is to give birth at this step. New births
     * will be made into free adjacent locations.
     *
     * @param newHunters A list to return newly born hunters.
     */
    private void giveBirth(List<Animal> newHunters) {
        // New hunters are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = getField().getFreeAdjacentLocations(getLocation());
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Hunter young = new Hunter(false, getField(), loc);
            newHunters.add(young);
        }
    }

    @Override
    protected int getMaxAge() {
        return MAX_AGE;
    }
    @Override
    protected int getBreedingAge() {
        return BREEDING_AGE;
    }
}
