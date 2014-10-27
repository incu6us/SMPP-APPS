package ua.com.life.smpp.db.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.com.life.smpp.db.domain.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public void insertUser(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	public User getUser(String username) {
		Query query = sessionFactory.getCurrentSession().createQuery("from User where username = :username");
		query.setParameter("username", username);
		return (User) query.list().get(0);
	}

	public void deleteUser(Long id) {
		User user = (User) sessionFactory.getCurrentSession().load(User.class, id);
		if (null != user) {
			sessionFactory.getCurrentSession().delete(user);
		}
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		return sessionFactory.getCurrentSession().createQuery("from User").list();
	}
}