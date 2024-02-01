package com.acciojob.bookmyshowapplication.Services;

import com.acciojob.bookmyshowapplication.Entities.User;
import com.acciojob.bookmyshowapplication.Repository.UserRepository;
import com.acciojob.bookmyshowapplication.Request.AddUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public String adduser(AddUserRequest addUserRequest){

        User user  = User.builder()
                .name(addUserRequest.getName())
                .emailId(addUserRequest.getEmailId())
                .build();

        userRepository.save(user);

        return "User has been saved into the Db.";

    }
}
