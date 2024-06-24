package com.user.service.extrenal.services;

import com.user.service.entity.Hotel;
import com.user.service.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RATING-SERVICE")
public interface RatingServices {
    @GetMapping("/api/ratings/user/{userId}")
    Rating[] getRating(@PathVariable("userId") String userId);
}

