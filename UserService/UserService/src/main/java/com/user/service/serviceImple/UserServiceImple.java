package com.user.service.serviceImple;

import com.user.service.entity.Hotel;
import com.user.service.entity.Rating;
import com.user.service.entity.User;
import com.user.service.extrenal.services.HotelServices;
import com.user.service.extrenal.services.RatingServices;
import com.user.service.repository.UserRepository;
import com.user.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImple implements UserService {

    @Autowired
    private UserRepository userRepository;

    //    Adding from Extrenal.Service
    @Autowired
    private HotelServices hotelServices;
    @Autowired
    private RatingServices ratingServices;

    //    adding bean in COnfig Class file
    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImple.class);

    public UserServiceImple(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    @Override
//    public User getUserById(String userId) {
//        // Retrieve user from the database by userId
//        Optional<User> userOptional = userRepository.findById(userId);
//
//        // Check if the user exists in the database
//        if (userOptional.isEmpty()) {
//            throw new RuntimeException("User not found with id: " + userId);
//        }
//
//        // Fetch ratings for the user from an external API
//        ArrayList<Rating> ratingsOfUsers = restTemplate.getForObject("http://localhost:8083/api/ratings/user/9ee88218-e08b-4886-a43b-97a520b90655", ArrayList.class);
//
//        // Log the fetched ratings
//        logger.info(ratingsOfUsers.toString());
//
//        // Set the fetched ratings to the user object
//        User user = userOptional.get();
//        user.setRatings(ratingsOfUsers);
//
//        // Return the retrieved user
//        return user;
//    }

    @Override
    public User getUserById(String userId) {
        // Retrieve user from the database by userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        // Fetch ratings for the user from an external API using RatingServices
        // Uncomment the correct line based on your service configuration
        // Rating[] ratingsOfUsers = restTemplate.getForObject("http://localhost:8083/api/ratings/user/" + user.getUserId(), Rating[].class);
        // Rating[] ratingsOfUsers = restTemplate.getForObject("http://RATING-SERVICE/api/ratings/user/" + user.getUserId(), Rating[].class);

        // Correct call to fetch ratings using Feign client
        Rating[] ratingsOfUsers = ratingServices.getRating(user.getUserId());

        // Handle null response gracefully
        if (ratingsOfUsers == null) {
            ratingsOfUsers = new Rating[0];
        }

        // Convert array to list for easier processing
        List<Rating> ratings = Arrays.asList(ratingsOfUsers);

        // Log the fetched ratings
        logger.info("Fetched ratings: {}", ratings);

        // Fetch hotel data for each rating and map it to the rating object
        List<Rating> ratingList = ratings.stream().map(rating -> {
            // Construct the URL for fetching hotel data using rating's hotelId
            // Uncomment the correct line based on your service configuration
            // String hotelUrl = "http://HOTEL-SERVICE/api/hotels/" + rating.getHotelId();
            // ResponseEntity<Hotel> hotelResponseEntity = restTemplate.getForEntity(hotelUrl, Hotel.class);
            // Hotel hotel = hotelResponseEntity.getBody();

            // Correct call to fetch hotel data using Feign client
            Hotel hotel = hotelServices.getHotel(rating.getHotelId());

            // Log the fetched hotel details
            logger.info("Fetched hotel: {}", hotel);
            // logger.info("Hotel response status: {}", hotelResponseEntity.getStatusCode());

            // Set the fetched hotel data in the rating object
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        // Set the fetched ratings with hotel data to the user object
        user.setRatings(ratingList);

        // Return the retrieved user with populated ratings
        return user;
    }



    @Override
    public User updateUser(String userId, User user) {
        if (userRepository.existsById(userId)) {
            user.setUserId(userId); // Ensure the ID is set correctly
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

}
