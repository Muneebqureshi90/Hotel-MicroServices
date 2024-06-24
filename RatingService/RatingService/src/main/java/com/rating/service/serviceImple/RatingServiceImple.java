package com.rating.service.serviceImple;

import com.rating.service.entity.Rating;
import com.rating.service.repository.RatingRepository;
import com.rating.service.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImple implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public RatingServiceImple(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }


    @Override
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getRatingByUserId(String userId) {
        return ratingRepository.findByUserId(userId);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> getRatingsByHotelId(String hotelId) {
        return ratingRepository.findByHotelId(hotelId);
    }
}
