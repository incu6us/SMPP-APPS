package ua.com.life.smpp.db.service;

import java.util.List;

import ua.com.life.smpp.db.domain.User;

public interface UserManager {
	public void insertUser(User user);
	public User getUserById(int userId);
	public User getUser(String username);
	public void deleteUser(Long id);
	public List<User> getUsers();
}