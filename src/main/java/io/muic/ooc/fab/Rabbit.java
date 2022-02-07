package io.muic.ooc.fab;

import java.util.List;
import java.util.Random;

public class Rabbit extends Animal {
    /**
     * Create a new rabbit. A rabbit may be created with age zero (a new born)
     * or with a random age.
     *
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
    }

    @Override
    protected double getBreedingProbability() {
        return AnimalType.RABBIT.getProbability();
    }

    @Override
    protected int getMaxLitterSize() {
        return 4;
    }

    @Override
    protected int getMaxAge() {
        return 40;
    }

    @Override
    protected int getBreedingAge() {
        return 5;
    }

    /**
     * This is what the rabbit does most of the time - it runs around. Sometimes
     * it will breed or die of old age.
     *
     * @param newAnimals A list to return newly born rabbits.
     */
    public void act(List<Animal> newAnimals) {
        incrementAge();
        if (isAlive()) {
            giveBirth(newAnimals);
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Check whether or not this rabbit is to give birth at this step. New
     * births will be made into free adjacent locations.
     *
     * @param animals A list to return newly born rabbits.
     */
    protected void giveBirth(List<Animal> animals) {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = getField().getFreeAdjacentLocations(getLocation());
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Rabbit young = new Rabbit(false, getField(), loc);
            animals.add(young);
        }
    }
}
