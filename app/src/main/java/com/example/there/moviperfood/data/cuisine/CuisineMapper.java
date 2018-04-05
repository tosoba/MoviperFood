package com.example.there.moviperfood.data.cuisine;

public class CuisineMapper {
    public Cuisine toCuisine(CuisineResponse response) {
        return response.getCuisine();
    }
}
