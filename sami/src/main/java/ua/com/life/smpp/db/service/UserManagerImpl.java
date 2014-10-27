package ua.com.life.smpp.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.life.smpp.db.dao.UserDao;
import ua.com.life.smpp.db.domain.User;

@Service
public class UserManagerImpl implements UserManager {
	
	@Autowired
	private UserDao userDAO;

	@Transactional
	public void insertUser(User user) {
		userDAO.insertUser(user);
	}

	@Transactional
	public User getUserById(int userId) {
		return null;
	}

	@Transactional
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return userDAO.getUser(username);
	}

	@Transactional
	public List<User> getUsers() {
		return userDAO.getUsers();
	}

	@Transactional
	public void deleteUser(Long id) {
		userDAO.deleteUser(id);
	}
}