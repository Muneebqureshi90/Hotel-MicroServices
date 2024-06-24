package com.user.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    private String userId;

    private String name;
    private String email;
    private String about;

    //    No need to store this in database
    @Transient
    private List<Rating> ratings=new ArrayList<>();

}
