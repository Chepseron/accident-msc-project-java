/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BEAN;

import com.amon.db.Actorgroup;
import com.amon.db.Deploymentunit;
import com.amon.db.Status;
import com.amon.db.User;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author amon.sabul
 */
@WebService(serviceName = "accident")

@SessionScoped
public class accident implements Serializable {

    @PersistenceContext(unitName = "accPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    User users = new User();

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "registerActor")
    public String registerActor(@WebParam(name = "orgName") String orgName, @WebParam(name = "actorType") String actorType) {

        Deploymentunit unit = new Deploymentunit();
        users = (User) em.createQuery("select u from User u where u.name = " + actorType + "").getSingleResult();
        unit.setActors(users);
        unit.setCreatedBy(new User(3));
        unit.setDateCreated(new java.util.Date());
        unit.setOrgname(orgName);
        unit.setStatusID(new Status(1));
        try {

            utx.begin();
            em.persist(unit);
            utx.commit();
            return "Registration a success";
        } catch (Exception ex) {
            return "Registration was not a success";
        }
    }

    @WebMethod(operationName = "listActorgroup")
    public List<String> listActorgroup() {
        List<String> actorsList = em.createQuery("select a.names from Actorgroup a").getResultList();
        return actorsList;

    }

    @WebMethod(operationName = "listAccidents")
    public List<String> listAccidents() {
        List<String> accidents = em.createQuery("select a.names from Accident a").getResultList();
        return accidents;

    }

}
