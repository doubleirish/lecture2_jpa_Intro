package edu.uw.data.lecture2.dao;

import edu.uw.data.lecture2.model.User;

import java.util.List;

/**
 * Created by credmond on 03/03/15.
 */
public interface UserDao {

  // CRUD :  Insert  and update
  User save(User user) ;
  void delete(User user) ;


  // Queries
  User findById(Integer id) ;
  User findByUsername(String username);
  List<User> findAll();

  }
