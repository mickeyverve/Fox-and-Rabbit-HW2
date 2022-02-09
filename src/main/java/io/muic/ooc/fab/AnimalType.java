package io.muic.ooc.fab;

import java.awt.*;
import java.util.Random;

public enum AnimalType {

    RABBIT(Rabbit.class,Color.ORANGE,0.08,9),
    FOX(Fox.class,Color.GREEN,0.02,20),
    TIGER(Tiger.class,Color.BLACK,0.01,20),
    HUNTER(Hunter.class,Color.RED,0.005,0);

    private Class<? extends Animal> animalClass;

    private Color color;

    private double probability;

    private int foodValue;

    AnimalType(Class<? extends Animal> animalClass, Color color, double probability, int foodValue) {
        this.animalClass = animalClass;
        this.color=color;
        this.probability = probability;
        this.foodValue=foodValue;
    }

    public double getProbability() {
        return probability;
    }

    public Color getColor() {
        return color;
    }

    public Class<? extends Animal> getAnimalClass(){
        return animalClass;
    }

    public int getFoodValue() {
        return foodValue;
    }


}
