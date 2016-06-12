package com.irontrain.business;

/**
 * Created by Mirko Eberlein on 16.02.2016.
 * Pojo Exercice
 * Verantwortlich: Mirko Eberlein
 */
public class Exercice {
    private String id;
    private String name;
    private String description;
    private String language;
    private int importnumber;

    private Exercice(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.language = builder.language;
        this.importnumber = builder.importnumber;
    }
    public static class Builder{
        private String id,name,description,language;
        private int importnumber;
        public Builder(){}

        public Builder id(String id){
            this.id = id;
            return this;
        }
        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder language(String language){
            this.language = language;
            return this;
        }

        public Builder importNumber(int importnumber){
            this.importnumber = importnumber;
            return this;
        }

        public Exercice build(){
            return new Exercice(this);
        }

    }

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
}
