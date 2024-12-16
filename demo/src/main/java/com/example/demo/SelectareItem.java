package com.example.demo;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.List;
import java.util.Map;

public class SelectareItem implements ChangeListener<String> {
    private final Map<String, List<Student>> map;

    public SelectareItem(Map<String, List<Student>> map) {
        this.map = map;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (newValue != null && map.containsKey(newValue)) {
            OBradFrumos.afisareRezultat(map.get(newValue));
        }
    }
}
