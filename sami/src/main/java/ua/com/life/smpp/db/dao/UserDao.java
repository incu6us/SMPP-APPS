package ua.com.life.smpp.db.dao;

import java.util.List;

import ua.com.life.smpp.db.domain.User;

public interface UserDao {
	public void insertUser(User user);
	public User getUser(String username);
	public void deleteUser(Long id);
	public List<User> getUsers();
}