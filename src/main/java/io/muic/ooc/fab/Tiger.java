package io.muic.ooc.fab;

import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class Tiger extends Animal {

    // The tiger's food level, which is increased by eating rabbits and foxes.
    private int foodLevel;

    /**
     * Create a tiger. A tiger can be created as a new born (age zero and not
     * hungry) or with a random age and food level.
     *
     * @param randomAge If true, the tiger will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Tiger(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
        foodLevel = RANDOM.nextInt(AnimalType.RABBIT.getFoodValue()+AnimalType.FOX.getFoodValue());
    }

    @Override
    protected double getBreedingProbability() {
        return AnimalType.TIGER.getProbability();
    }

    @Override
    protected int getMaxLitterSize() {
        return 2;
    }

    @Override
    protected int getMaxAge() {
        return 200;
    }

    @Override
    protected int getBreedingAge() {
        return 20;
    }

    /**
     * This is what the tiger does most of the time: it hunts for rabbits. In the
     * process, it might breed, die of hunger, or die of old age.
     *
     * @param newAnimals A list to return newly born tigers.
     */
    public void act(List<Animal> newAnimals) {
        incrementAge();
        incrementHunger();
        if (isAlive()) {
            giveBirth(newAnimals);
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
     * Make this tiger more hungry. This could result in the tiger's death.
     */
    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for rabbits adjacent to the current location. Only the first live
     * rabbit is eaten.
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
                    foodLevel = AnimalType.RABBIT.getFoodValue();
                    return where;
                }
            }
            if (animal instanceof Fox){
                Fox fox= (Fox) animal;
                if (fox.isAlive()) {
                    fox.setDead();
                    foodLevel = AnimalType.FOX.getFoodValue();
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Check whether or not this tiger is to give birth at this step. New births
     * will be made into free adjacent locations.
     *
     * @param newTigers A list to return newly born tigers.
     */
    private void giveBirth(List<Animal> newTigers) {
        // New tigers are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = getField().getFreeAdjacentLocations(getLocation());
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Tiger young = new Tiger(false, getField(), loc);
            newTigers.add(young);
        }
    }
}

// Characteristics shared by all tigers (class variables).

//    // The age at which a tiger can start to breed.
//    private static final int BREEDING_AGE = 20;
//    // The age to which a tiger can live.
//    private static final int MAX_AGE = 200;
//    // The likelihood of a tiger breeding.
//    private static final double BREEDING_PROBABILITY = 0.01;
//    // The maximum number of births.
//    private static final int MAX_LITTER_SIZE = 2;
