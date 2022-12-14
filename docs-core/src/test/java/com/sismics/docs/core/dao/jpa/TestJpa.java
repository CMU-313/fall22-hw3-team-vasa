package com.sismics.docs.core.dao.jpa;

import com.sismics.docs.BaseTransactionalTest;
import com.sismics.docs.core.dao.UserDao;
import com.sismics.docs.core.model.jpa.User;
import com.sismics.docs.core.dao.DocumentDao;
import com.sismics.docs.core.model.jpa.Document;
import com.sismics.docs.core.util.TransactionUtil;
import com.sismics.docs.core.util.authentication.InternalAuthenticationHandler;
import org.junit.Assert;
import org.junit.Test;
import java.util.Date;

/**
 * Tests the persistance layer.
 * 
 * @author jtremeaux
 */
public class TestJpa extends BaseTransactionalTest {
    @Test
    public void testJpa1() throws Exception {
        // Create a user
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername("username");
        user.setPassword("12345678");
        user.setEmail("toto@docs.com");
        user.setRoleId("admin");
        user.setStorageQuota(10L);
        String id = userDao.create(user, "me");
        
        TransactionUtil.commit();

        // Search a user by his ID
        user = userDao.getById(id);
        Assert.assertNotNull(user);
        Assert.assertEquals("toto@docs.com", user.getEmail());

        // Authenticate using the database
        Assert.assertNotNull(new InternalAuthenticationHandler().authenticate("username", "12345678"));
    }

    @Test
    public void testJpa2() throws Exception {
        // Create a new document with status set to 2
        DocumentDao docDao = new DocumentDao();
        Document doc = new Document();
        Assert.assertEquals(Integer.valueOf(0), doc.getStatus());
        doc.setTitle("My new document");
        doc.setDescription("My description");
        doc.setCreateDate(new Date());
        doc.setLanguage("eng");
        doc.setUserId("admin");
        doc.setStatus(Integer.valueOf(2));
        String docId = docDao.create(doc, "admin");

        TransactionUtil.commit();

        // Search the document by its ID and make sure the status is 2
        doc = docDao.getById(docId);
        Assert.assertNotNull(doc);
        Assert.assertEquals(Integer.valueOf(2), doc.getStatus());
    }

    @Test
    public void testJpa3() throws Exception {
        // Create a new document without setting status
        DocumentDao docDao = new DocumentDao();
        Document doc = new Document();
        Assert.assertEquals(Integer.valueOf(0), doc.getStatus());
        doc.setTitle("My new document");
        doc.setDescription("My description");
        doc.setCreateDate(new Date());
        doc.setLanguage("eng");
        doc.setUserId("admin");
        String docId = docDao.create(doc, "admin");

        TransactionUtil.commit();

        // Search the document by its ID and make sure the status is 0
        doc = docDao.getById(docId);
        Assert.assertNotNull(doc);
        Assert.assertEquals(Integer.valueOf(0), doc.getStatus());
    }
}
