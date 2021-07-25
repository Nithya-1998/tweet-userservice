package com.tweetapp.userservice.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tweetapp.userservice.bean.User;
import com.tweetapp.userservice.dto.UserDto;
import com.tweetapp.userservice.exception.Message;
import com.tweetapp.userservice.exception.UserExistException;
import com.tweetapp.userservice.exception.UserNotFoundException;
import com.tweetapp.userservice.repository.UserRepository;

/**
 * @author Nithya T
 *
 */
@Service
public class UserService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepository userRepository;
	
	@Transactional
	public List<User> getAllUsers(){
		Iterable<User> users = new ArrayList<User>();
		try {
			users = userRepository.findAll();
		} catch (Exception e) {
			LOGGER.info("Internal service error");
		}
		return (List<User>) users;
	}
	
	@Transactional
	public User getUser(UserDto userDto) throws UserNotFoundException {
		User user = userRepository.findByLoginIdAndPassword(userDto.getLoginId(),userDto.getPassword());
		if(user != null) {
			user.setLogIn(true);
			userRepository.save(user);
		}else {
			throw new UserNotFoundException("User Unauthorized");
		}
		return user;
	}
	
	@Transactional
	public User getUserById(String loginId) throws UserNotFoundException {
		User user = userRepository.findByLoginId(loginId);
		if(user != null) {
			return user;
		}else {
			throw new UserNotFoundException("User not found");
		}
	}
	@Transactional
	public Message logOutUser(String loginId)  throws UserNotFoundException {
		User user = userRepository.findByLoginId(loginId);
		if(user != null) {
			user.setLogIn(false);
			userRepository.save(user);
			return new Message("Logged out Successfully");
		}else {
			return new Message("User not found");
		}
		
	}
	
	
	@Transactional
	public Message updateUser(UserDto userDto) throws UserNotFoundException {
		User user = userRepository.findByLoginId(userDto.getLoginId());
		if(user != null) {
			boolean isPwdMatches = userDto.getPassword().equals(user.getPassword()) ? true : false;
			if(isPwdMatches) {
				return new Message("New Password should not be same as old password");
			}else {
				user.setLogIn(userDto.isLogIn());
				user.setPassword(userDto.getPassword());
				userRepository.save(user);	
				return new Message("New Password updated");
			}
		}else {
			throw new UserNotFoundException("User not found");
		}
		
	}
	
	@Transactional
	public Message insertUser(User user1) throws UserExistException {
		User user = userRepository.findByLoginId(user1.getLoginId());
		if(user == null) {
			userRepository.save(user1);	
			return new Message("User details inserted successfully");
		}else {
			throw new UserExistException("User Alreday Exist");
		}
	}
	
	
}
