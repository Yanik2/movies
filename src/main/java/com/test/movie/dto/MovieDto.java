package com.test.movie.dto;

public class MovieDto {
    private Integer id;
    private String name;
    private String director;

    public MovieDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public MovieDto() {
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MovieDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
