package com.example.demo;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ListaMosului {
    private Map<String, List<Student>> mapNume = new TreeMap<>();
    private Map<Double, List<Student>> mapMedie = new TreeMap<>();
    private Map<String, List<Student>> mapLocalitate = new TreeMap<>();

    public ListaMosului(File file) throws IOException {
        mapNume.clear();
        mapMedie.clear();
        mapLocalitate.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Student s = new Student(parts[0].trim(), Double.parseDouble(parts[1].trim()), parts[2].trim());
                    inscrieInListaMosului(s);
                }
            }
        }
    }

    public ListaMosului() {
        for (Student s : exempluDeTest()) {
            inscrieInListaMosului(s);
        }
    }

    private void inscrieInListaMosului(Student s) {
        adaugaInMap(mapNume, s.getNume(), s);
        adaugaInMap(mapMedie, s.getMedie(), s);
        adaugaInMap(mapLocalitate, s.getLocalitate(), s);
    }

    private <K, E> void adaugaInMap(Map<K, List<E>> map, K cheie, E element) {
        map.computeIfAbsent(cheie, k -> new ArrayList<>()).add(element);
    }

    private List<Student> exempluDeTest() {
        Student[] lstStud = new Student[]{
                new Student("Ionel", 6.55, "Suceava"),
                new Student("Viorel", 7.45, "Suceava"),
                new Student("Violeta", 6.55, "Radauti"),
                new Student("Elena", 8.25, "Cajvana"),
                new Student("Mariana", 9.75, "Suceava"),
                new Student("Costel", 6.55, "Radauti"),
                new Student("Ionel", 8.25, "Cajvana"),
                new Student("Violeta", 8.55, "Campulung Moldovenesc"),
                new Student("Ioana", 8.35, "Vatra-Dornei")
        };
        return List.of(lstStud);
    }

    public Map<String, List<Student>> getMapNume() {
        return mapNume;
    }

    public Map<Double, List<Student>> getMapMedie() {
        return mapMedie;
    }

    public Map<String, List<Student>> getMapLocalitate() {
        return mapLocalitate;
    }
}
