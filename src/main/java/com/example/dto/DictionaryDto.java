package com.example.dto;

public class DictionaryDto {
    private Integer id;
    private String name;
    private String description;
    private String language;
    
    public DictionaryDto() {}
    
    public DictionaryDto(Integer id, String name, String description, String language) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.language = language;
    }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}