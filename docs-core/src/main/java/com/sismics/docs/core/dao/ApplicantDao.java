package com.sismics.docs.core.dao;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sismics.docs.core.constant.AuditLogType;
import com.sismics.docs.core.constant.Constants;
import com.sismics.docs.core.model.jpa.AppInfo;
import com.sismics.docs.core.util.AuditLogUtil;
import com.sismics.docs.core.util.EncryptionUtil;
import com.sismics.docs.core.util.jpa.QueryParam;
import com.sismics.docs.core.util.jpa.QueryUtil;
import com.sismics.docs.core.util.jpa.SortCriteria;
import com.sismics.util.context.ThreadLocalContext;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.*;

/**
 * User DAO.
 * 
 * @author jtremeaux
 */
public class ApplicantDao {    
    public String create(AppInfo appInf) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.persist(appInf);
        
        return appInf.getId();
    }

    public AppInfo updateAccept(AppInfo appInf, String n) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        
        Query q = em.createQuery("select a from ApplicantInfo where t.name = :n and t.status = 'INPROGRESS'");
        q.setParameter("n", n);
        AppInfo appDB = (AppInfo) q.getSingleResult();
        
        appDB.setName(appInf.getName());
        appDB.setAccept();
        
        return appDB;
    }

    public AppInfo updateReject(AppInfo appInf, String n) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();

        Query q = em.createQuery("select a from ApplicantInfo where t.name = :n and t.status = 'INPROGRESS'");
        q.setParameter("n", n);
        AppInfo appDB = (AppInfo) q.getSingleResult();

        appDB.setName(appInf.getName());
        appDB.setReject();
        
        return appDB;
    }
}
