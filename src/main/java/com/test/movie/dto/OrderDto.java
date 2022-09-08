package com.test.movie.dto;

public class OrderDto {
    private Integer id;
    private Integer movieId;
    private String time;

    public OrderDto(Integer movieId, String time) {
        this.movieId = movieId;
        this.time = time;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderDto() {
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
