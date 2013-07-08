package net.woeye.verbatim.model;

import java.util.ArrayList;

public class Collection {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return (int)this.id;
    }

    @Override
    public String toString() {
        return "[Collection] id:" + this.id;
    }
}
