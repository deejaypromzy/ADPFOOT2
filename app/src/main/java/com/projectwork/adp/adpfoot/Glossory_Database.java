package com.projectwork.adp.adpfoot;

class Glossory_Database {
    String name;
    String definition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
    public Glossory_Database() {
    }

    public Glossory_Database(String name, String definition) {
        this.name = name;
        this.definition = definition;
    }
}
