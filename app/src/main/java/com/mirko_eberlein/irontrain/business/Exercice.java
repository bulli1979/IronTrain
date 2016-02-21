package com.mirko_eberlein.irontrain.business;

/**
 * Created by Ebi on 16.02.2016.
 */
public class Exercice {
    private String id;
    private String name;
    private String description;
    private String language;
    private int importnumber;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getImportnumber() {
        return importnumber;
    }

    public void setImportnumber(int importnumber) {
        this.importnumber = importnumber;
    }
}
