package com.ben.notes.model;

import java.util.ArrayList;
import java.util.List;

public class Notes {
    List<String> notes = new ArrayList<>();

    public List<String> getNotes() {
        return notes;
    }

    public Notes setNotes(List<String> notes) {
        this.notes = notes;
        return this;
    }
}
