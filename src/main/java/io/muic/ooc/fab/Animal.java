package io.muic.ooc.fab;

import java.util.List;
import java.util.Random;

public abstract class Animal implements Actor {
    // A shared random number generator to control breeding.
    protected static final Random RANDOM = new Random();
    // The rabbit's age
    private int age=0;
    // Whether the fox is alive or not.
    private boolean alive = true;
    // The fox's position.
    private Location location;
    // The field occupied.
    private Field field;

    public Animal(boolean randomAge, Field field, Location location) {
        this.field = field;
        setLocation(location);
        if (randomAge) {
            setAge(RANDOM.nextInt(getMaxAge()));
        }
    }

    protected abstract int getMaxAge();

    protected abstract int getBreedingAge();

    protected abstract double getBreedingProbability();

    protected abstract int getMaxLitterSize();

    public abstract void act(List<Animal> newAnimals);

    public Location getLocation() {
        return location;
    }

    public Field getField() {
        return field;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Check whether the fox is alive or not.
     *
     * @return True if the fox is still alive.
     */
    public boolean isAlive() {
        return alive;
    }
    /**
     * Increase the age. This could result in the rabbit's death.
     */
    protected void incrementAge() {
        age++;
        if (age > getMaxAge()) {
            setDead();
        }
    }

    protected boolean canBreed() {
        return getAge() >= getBreedingAge();
    }

    /**
     * Place the rabbit at the new location in the given field.
     *
     * @param newLocation The rabbit's new location.
     */
    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    protected void setDead(){
        setAlive(false);
        if (getLocation() != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    /**
     * Generate a number representing the number of births, if it can breed.
     *
     * @return The number of births (may be zero).
     */
    protected int breed() {
        int births = 0;
        if (canBreed() && RANDOM.nextDouble() <= getBreedingProbability()) {
            births = RANDOM.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
}
