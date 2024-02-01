package com.acciojob.bookmyshowapplication.Repository;

import com.acciojob.bookmyshowapplication.Entities.User;
import com.acciojob.bookmyshowapplication.Response.UserResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User , Integer> {

    User findByEmailId(String emailId);

    @Query(value = "select * from users where email_id := emailId",nativeQuery = true)
    UserResponseDto customMethodName(String emailId);
}
