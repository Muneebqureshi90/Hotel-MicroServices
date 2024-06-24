package com.rating.service.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("user_rating")
public class Rating {
    @Id
    private String ratingId;
    private String hotelId;
    private String userId;
    private String feedback;
    private int rating;

}
