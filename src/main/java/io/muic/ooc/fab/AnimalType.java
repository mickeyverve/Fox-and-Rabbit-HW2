package io.muic.ooc.fab;

import java.awt.*;

public enum AnimalType {

    RABBIT(Rabbit.class,Color.ORANGE,0.08),
    FOX(Fox.class,Color.BLUE,0.02);

    private Class<? extends Animal> animalClass;

    private Color color;

    private double probability;

    AnimalType(Class<? extends Animal> animalClass, Color color, double probability) {
        this.animalClass = animalClass;
        this.color=color;
        this.probability = probability;
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



}
