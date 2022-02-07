package io.muic.ooc.fab;

import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class Fox extends Animal {
   private int foodLevel;

    /**
     * Create a fox. A fox can be created as a new born (age zero and not
     * hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Fox(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
        foodLevel = RANDOM.nextInt(AnimalType.RABBIT.getFoodValue());
    }

    @Override
    protected double getBreedingProbability() {
        return AnimalType.FOX.getProbability();
    }

    @Override
    protected int getMaxLitterSize() { return 2; }

    @Override
    protected int getMaxAge() {
        return 150;
    }

    @Override
    protected int getBreedingAge() {
        return 15;
    }

    /**
     * This is what the fox does most of the time: it hunts for rabbits. In the
     * process, it might breed, die of hunger, or die of old age.
     *
     * @param newAnimals A list to return newly born foxes.
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
     * Make this fox more hungry. This could result in the fox's death.
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
                    foodLevel = AnimalType.RABBIT.getFoodValue();
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Check whether or not this fox is to give birth at this step. New births
     * will be made into free adjacent locations.
     *
     * @param newFoxes A list to return newly born foxes.
     */
    private void giveBirth(List<Animal> newFoxes) {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = getField().getFreeAdjacentLocations(getLocation());
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Fox young = new Fox(false, getField(), loc);
            newFoxes.add(young);
        }
    }
}
