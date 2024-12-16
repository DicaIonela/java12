package com.example.demo;

public class Student {
    private String nume;
    private double medie;
    private String localitate;
    public Student(String nume, double varsta, String localitate) {
        this.nume = nume;
        this.medie = varsta;
        this.localitate = localitate;
    }
    public String getNume() {
        return nume;
    }
    public double getMedie() {
        return medie;
    }
    public String getLocalitate() {
        return localitate;
    }
    @Override
    public String toString() {
        return nume + ", " + medie + ", " + localitate;
    }
}
