package edu.uw.data.lecture2.dao;

import edu.uw.data.lecture2.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * simple single-table Jdbc example with try-resources and datasource
 */
@Repository("userDao")
@Transactional 
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

  static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

  @Autowired
  SessionFactory sessionFactory;

  public User findById(Integer id) {
    return (User) sessionFactory.getCurrentSession().get(User.class, id);
  }


  public List<User> findAll() {
    return (List<User>) getHibernateTemplate().find("from User");

    //  return sessionFactory.getCurrentSession().createQuery("from User").list();

  }


  @Override
  public User findByUsername(String uname) {
    Query query = sessionFactory.getCurrentSession().createQuery(
        "SELECT u FROM User u WHERE u.userName LIKE :uname");
    query.setParameter("uname", uname);
    User  user =(User) query.uniqueResult();
    return user;

  }

  @Override

  public User save(User user) {
    Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(user);
    session.flush();


    log.info("saved " + user.getId());
    return user;
  }


  public void delete (User user){
    if (user.getId()!=null) {
      sessionFactory.getCurrentSession().delete(user);
    }else if (user.getUserName()!=null) {
      sessionFactory.getCurrentSession()
          .createQuery("DELETE FROM User u WHERE u.userName = :username")
          .setString("username", user.getUserName())
          .executeUpdate();
    } else {
      throw new IllegalArgumentException("User does not contain identifying information "+user);
    }
  }





}
