package edu.uw.data.lecture2;


import edu.uw.data.lecture2.dao.*;
import edu.uw.data.lecture2.model.*;
import org.junit.*;
import org.junit.runner.*;
import org.slf4j.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.transaction.*;

import javax.annotation.*;
import javax.sql.*;
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * using an embedded database that
 * self initializes into a known state allows consistently reproducible tests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/userapp-spring.xml",
        // "classpath:/datasource-embedded-init.xml"
        "classpath:/datasource-standalone-test.xml"
})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)

public class UserDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    static final Logger log = LoggerFactory.getLogger(UserDaoTest.class);

    @Resource
    private UserDao userDao;

    @Override
    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }


    @Test
    public void createUser() {
        User newUser = new User.Builder()
                .userName("new")
                .firstName("first")
                .lastName("last")
                .activeSince(new Date())
                .phone("HOME", "123-555-1212")
                .phone("CELL", "425-555-9999")
                .build();

        User save = userDao.save(newUser);
        log.info("saved " + save);

        User found = userDao.findByUsername("new");

        log.info("found " + found);
        assertNotNull(found);
        assertThat(found.getUserName(),is("new"));
        assertThat(found.getFirstName(),is("first"));
        assertThat(found.getLastName(),is("last"));
    }

    @Test
    public void readUser() {
        User user = userDao.findById(1);
        log.info("foundUser " + user);
        assertNotNull(user);
        assertNotNull(user.getId());
    }


    @Test
    public void updateUser() {
        // load user and check initial state
        User jsmith = userDao.findByUsername("jsmith");
        assertThat(jsmith.getLastName(), is("Smith"));

        // update  user!

        jsmith.setLastName("Smith2");
        userDao.save(jsmith);

        // verify user was updated
        String updatedUsername = jdbcTemplate.queryForObject(
                "select LASTNAME  from USERS  where USERNAME = ?", String.class, "jsmith");

        assertThat(updatedUsername, is("Smith2"));
    }

    @Test
    public void deleteUser() {

        String username = "jsmith";
        User user = new User.Builder().userName(username).build();


        // verify book is already in the database
        String foundUsername = jdbcTemplate.queryForObject(
                "select USERNAME  from USERS  where USERNAME = ?", String.class, username);

        assertThat("can't delete user if it don't exist", foundUsername, notNullValue());

        //  delete   user
        userDao.delete(user);


        //  lookup the user im the database
        List<String> matchingUsers = jdbcTemplate.queryForList(
                "select USERNAME  from USERS  where USERNAME = ?", String.class, username);
        //  and verify it's gone
        assertThat(matchingUsers.size(), is(0));

    }


    @Test
    public void findUserByUsername() {

        User credmond = userDao.findByUsername("credmond");
        log.info("foundUser " + credmond);
        assertNotNull(credmond);
        assertEquals(1L, credmond.getId().longValue());
    }


    @Test
    public void findAll() {
        log.info("userDao " + userDao);
        List<User> users = userDao.findAll();
        assertNotNull(users);
        assertTrue(users.size() > 0);
        users.forEach(System.out::println);
    }


}
