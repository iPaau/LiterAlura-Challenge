package com.literalura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    private int id;
    @JsonAlias({"title"})
    private String title;
    @JsonProperty("authors")
    private List<Author> authors;
    @JsonProperty("languages")
    private List<String> languages;
    @JsonProperty("download_count")
    private int downloadCount;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<Author> getAuthors() { return authors; }
    public void setAuthors(List<Author> authors) { this.authors = authors; }
    public List<String> getLanguages() { return languages; }
    public void setLanguages(List<String> languages) { this.languages = languages; }
    public int getDownloadCount() { return downloadCount; }
    public void setDownloadCount(int downloadCount) { this.downloadCount = downloadCount; }

    public String getFirstLanguage() {
        return (languages != null && !languages.isEmpty()) ? languages.get(0) : "";
    }

    @Override
    public String toString() {
        return "Título: '" + title + "', Autor: " + (authors != null && !authors.isEmpty() ? authors.get(0) : "Desconocido") +
                ", Idioma: " + getFirstLanguage() + ", Descargas: " + downloadCount;
    }
}
