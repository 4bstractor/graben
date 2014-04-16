package com.bryobone.graben.db;

import com.bryobone.graben.core.User;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

/**
 * Created by jarvis on 4/8/14.
 */
public class UserDAO extends AbstractDAO<User> {
  public UserDAO(SessionFactory factory) { super(factory); }

  public Optional<User> findById(Long id) {
    return Optional.fromNullable(get(id));
  }

  public User create(User user) {
    // Check user deets
    user.setTimestamps();
    return persist(user);
  }

  public User findByUsername(String username) {
    Query query = namedQuery("com.bryobone.graben.core.User.findByUsername");
    return uniqueResult(query.setString("username", username));
  }
}
