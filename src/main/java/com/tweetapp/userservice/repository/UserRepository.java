package com.tweetapp.userservice.repository;

import java.util.List;


import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.tweetapp.userservice.bean.User;

/**
 * @author Nithya T
 *
 */
@EnableScan
public interface UserRepository extends CrudRepository<User, String>{
	
	User findByLoginIdAndPassword(String loginId,String password);
	
	User findByLoginId(String loginId);

}
