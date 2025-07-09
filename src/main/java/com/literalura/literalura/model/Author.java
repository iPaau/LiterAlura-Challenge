package com.literalura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
    @JsonAlias({"name"})
    private String name;
    @JsonAlias({"birth_year"})
    private Integer birthYear;
    @JsonAlias({"death_year"})
    private Integer deathYear;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getBirthYear() { return birthYear; }
    public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }
    public Integer getDeathYear() { return deathYear; }
    public void setDeathYear(Integer deathYear) { this.deathYear = deathYear; }

    public boolean isAliveInYear(int year) {
        return (birthYear != null && birthYear <= year) && (deathYear == null || deathYear > year);
    }

    @Override
    public String toString() {
        return name + (birthYear != null ? " (" + birthYear + (deathYear != null ? " - " + deathYear : "") + ")" : "");
    }
}
