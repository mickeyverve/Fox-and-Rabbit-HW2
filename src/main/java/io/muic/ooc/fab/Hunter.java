package io.muic.ooc.fab;

import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class Hunter implements Actor {

    // The hunter's field.
    private Field field;
    // The hunter's position in the field.
    private Location location;

    /**
     * Create a hunter. A hunter can be created as a new born (age zero and not
     * hungry) or with a random age and food level.
     *
     * @param randomAge If true, the hunter will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Hunter(boolean randomAge, Field field, Location location) {
        this.field = field;
        setLocation(location);;
    }

    public int getMaxAge() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    /**
     * This is what the hunter does most of the time: it hunts for food. In the
     * process, it might breed, die of hunger, or die of old age.
     *
     * @param newAnimals A list to return newly born hunters.
     */
    public void act(List<Animal> newAnimals) {
        // Move towards a source of food if found.
        Location newLocation = findFood();
        if(newLocation == null) {
            // No food found - try to move to a free location.
            newLocation = getField().freeAdjacentLocation(getLocation());
        }
        // See if it was possible to move.
        if(newLocation != null) {
            setLocation(newLocation);
        }

    }

    /**
     * Return the hunter's location.
     * @return The hunter's location.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Place the hunter at the new location in the given field.
     * @param newLocation The hunter's new location.
     */
    public void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Return the hunter's field.
     * @return Field the hunter's field.
     */
    public Field getField()
    {
        return field;
    }

    /**
     * Look for food adjacent to the current location. Only the first live
     * animal is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood() {
        List<Location> adjacent = field.adjacentLocations(getLocation());
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


//    // Characteristics shared by all hunters (class variables).
//
//    // The age at which a hunter can start to breed.
//    private static final int BREEDING_AGE = 80;
//    // The age to which a hunter can live.
//    private static final int MAX_AGE = Integer.MAX_VALUE;
//    // The likelihood of a hunter breeding.
//    private static final double BREEDING_PROBABILITY = 0.005;
//    // The maximum number of births.
//    private static final int MAX_LITTER_SIZE = 2;

