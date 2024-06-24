package com.rating.service.service;

import com.rating.service.entity.Rating;

import java.util.List;

public interface RatingService {
    Rating createRating(Rating rating);

    List<Rating> getRatingByUserId(String userId);

    List<Rating> getAllRatings();

    List<Rating> getRatingsByHotelId(String hotelId);
}
