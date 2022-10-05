package com.sismics.docs.rest;

import com.sismics.util.filter.TokenBasedSecurityFilter;
import org.junit.Assert;
import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.sismics.util.context.ThreadLocalContext;
import com.sismics.docs.core.model.jpa.AppInfo;
import com.sismics.docs.core.dao.ApplicantDao; 
import com.sismics.docs.core.constant.StatusType;

/**
 * Test the AppInfo resource.
 *
 * @author bgamard
 */
public class TestApplicantInfo extends BaseJerseyTest {
    /**
     * Test the ACL resource.
     */
    @Test
    public void testAppInfoDB() {
        AppInfo a = new AppInfo();
        a.setName("Test");
        a.setId("123");
        a.setIP();

        Assert.assertEquals(a.getName(), "Test");
        Assert.assertEquals(a.getId(), "123");
        Assert.assertEquals(a.getStatus(), StatusType.INPROGRESS);

        ApplicantDao aDao = new ApplicantDao();
        aDao.create(a);
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select * from ApplicantInfo");
        Assert.assertFalse(q.getResultList().size() == 0);

        aDao.updateAccept(a);
        Assert.assertEquals(a.getStatus(), StatusType.ACCEPTED);

        a.setIP();
        aDao.updateReject(a);
        Assert.assertEquals(a.getStatus(), StatusType.REJECTED);

    }
}