package com.bryobone.graben.db;

import com.bryobone.graben.core.User;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by jarvis on 4/8/14.
 */
public class UserDAO extends AbstractDAO<User> {
  public UserDAO(SessionFactory factory) { super(factory); }

  public Optional<User> findById(Long id) {
    return Optional.fromNullable(get(id));
  }

  public User create(User user) {
    return persist(user);
  }

  public List<User> findAll() {
    return list(namedQuery("com.bryobone.graben.core.User.findAll"));
  }

  public int checkUser(String username, String password) { return list(namedQuery("com.bryobone.graben.core.User.checkUser")).size(); }
}
