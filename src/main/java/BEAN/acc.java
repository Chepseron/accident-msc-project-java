/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BEAN;

import com.amon.db.*;
import com.google.maps.GeoApiContext;

import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author amon.sabul
 */
@SessionScoped
@ManagedBean(name = "acc")

public class acc implements Serializable {

    @PersistenceContext(unitName = "accPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private String latitude;
    private String longitude;
    private Usergroup group1 = new Usergroup();
    private List<Usergroup> group1List = new ArrayList<Usergroup>();
    private User user = new User();
    private List<User> userList = new ArrayList<User>();
    private Status status = new Status();
    private boolean remember;
    private boolean reportAccident = false;
    private boolean deployHelpers = false;
    private boolean auditTrails = false;
    private boolean criteriaReports = false;
    private boolean registerUsers = false;
    private boolean registerHelpers = false;
    private boolean recordStatus = false;
    private boolean registerGroup = false;
    private List<Status> statusList = new ArrayList<Status>();
    private Audit audit = new Audit();
    private List<Audit> auditList = new ArrayList<Audit>();
    private Accident accident = new Accident();
    private List<Accident> accidentList = new ArrayList<Accident>();
    private Actorgroup actors = new Actorgroup();
    private List<Actorgroup> actorsList = new ArrayList<Actorgroup>();
    private Deployment deployment = new Deployment();
    private List<Deployment> deploymentList = new ArrayList<Deployment>();
    private List<Deployment> personalDeploymentList = new ArrayList<Deployment>();
    private Deploymentunit deploymentunit = new Deploymentunit();
    private List<Deploymentunit> deploymentunitList = new ArrayList<Deploymentunit>();
    private Apikey apiKey = new Apikey();
    private List<Apikey> apiKeyList = new ArrayList<Apikey>();
    private List<String> responsibilities;
    private List<String> deploymentUnitID;
    private String username = new String();
    private String password = new String();
    private Long accidentListCount;
    private Long deployedUnitsCount;
    private Long systemUsersCount;
    private BigDecimal victimsCount;

    /**
     * Creates a new instance of acc
     */
    public acc() {
    }

    @PostConstruct
    public void init() {
        try {
            createAccidentModel();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private MapModel advancedModel;
    private MapModel actorModel;
    private Marker marker;

    private LineChartModel accidentModel;

    private void createAccidentModel() {
        setAccidentList((List<Accident>) getEm().createQuery("select a from Accident a").getResultList());
        setAccidentModel(new LineChartModel());
        LineChartSeries Cohort = new LineChartSeries();
        Cohort.setFill(true);
        Cohort.setLabel("Accident/places occured");

        for (Accident med : getAccidentList()) {
            Cohort.set(med.getRoad(), med.getDeadVictims());
        }

        getAccidentModel().addSeries(Cohort);
        getAccidentModel().setTitle("Accident");
        getAccidentModel().setLegendPosition("ne");
        getAccidentModel().setStacked(true);
        getAccidentModel().setShowPointLabels(true);

        Axis xAxis = new CategoryAxis("Places/Road occured");
        getAccidentModel().getAxes().put(AxisType.X, xAxis);
        Axis yAxis = getAccidentModel().getAxis(AxisType.Y);
        yAxis.setLabel("Deceased victims");
        yAxis.setMin(0);

        advancedModel = new DefaultMapModel();
        accidentList = (List<Accident>) getEm().createQuery("select a from Accident a").getResultList();
        for (Accident c : accidentList) {

            System.out.println("Latitude and location " + c.getLatitude() + " Road " + c.getRoad());
            getAdvancedModel().addOverlay(new Marker(new LatLng(Double.parseDouble(c.getLatitude()), Double.parseDouble(c.getLongitude())), "Road Name:" + c.getRoad() + " Location " + c.getPlaceOccured() + " Deceased Victims " + c.getDeadVictims(), "/images/xml.png", "http://maps.google.com/mapfiles/ms/micons/red-dot.png"));
        }

        actorModel = new DefaultMapModel();
        deploymentunitList = (List<Deploymentunit>) getEm().createQuery("select d FROM Deploymentunit d").getResultList();
        for (Deploymentunit c : deploymentunitList) {
            getAdvancedModel().addOverlay(new Marker(new LatLng(Double.parseDouble(c.getLatitude()), Double.parseDouble(c.getLongitude())), "Organisation Name:" + c.getOrgname() + " Phone Numbers " + c.getPhoneNumber(), "/images/xml.png", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
            getActorModel().addOverlay(new Marker(new LatLng(Double.parseDouble(c.getLatitude()), Double.parseDouble(c.getLongitude())), "Organisation Name:" + c.getOrgname() + " Phone Numbers " + c.getPhoneNumber(), "/images/xml.png", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
        }

    }

    public String login() {
        try {

            setUser((User) getEm().createQuery("select u from User u where u.username = '" + getUsername() + "' and u.pword = '" + getPassword() + "'").getSingleResult());
            setGroup1((Usergroup) getEm().createQuery("select u from Usergroup u where u.idgroups = " + getUser().getGroupID() + "").getSingleResult());
            if (getGroup1().getName().equalsIgnoreCase("ADMIN")) {
                setReportAccident(true);
                setDeployHelpers(true);
                setAuditTrails(true);
                setCriteriaReports(true);
                setRegisterUsers(true);
                setRegisterHelpers(true);
                setRecordStatus(true);
                setRegisterGroup(true);
            } else if (getGroup1().getName().equalsIgnoreCase("PARAMEDICS")) {
                setReportAccident(true);
                setDeployHelpers(false);
                setAuditTrails(false);
                setCriteriaReports(true);
                setRegisterUsers(false);
                setRegisterHelpers(false);
                setRecordStatus(false);
                setRegisterGroup(false);
            } else if (getGroup1().getName().equalsIgnoreCase("FIRSTAIDER")) {
                setReportAccident(true);
                setDeployHelpers(false);
                setAuditTrails(false);
                setCriteriaReports(true);
                setRegisterUsers(false);
                setRegisterHelpers(false);
                setRecordStatus(false);
                setRegisterGroup(false);
            } else if (getGroup1().getName().equalsIgnoreCase("POLICE")) {
                setReportAccident(true);
                setDeployHelpers(false);
                setAuditTrails(false);
                setCriteriaReports(true);
                setRegisterUsers(false);
                setRegisterHelpers(false);
                setRecordStatus(false);
                setRegisterGroup(false);
            } else {
                setReportAccident(true);
                setDeployHelpers(false);
                setAuditTrails(false);
                setCriteriaReports(false);
                setRegisterUsers(false);
                setRegisterHelpers(false);
                setRecordStatus(false);
                setRegisterGroup(false);
            }
            getUtx().begin();
            getAudit().setAction("logged into the system at  " + new Date());
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getUtx().commit();

            return "index2.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "!ERROR!", "Please provide correct credentials");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginInfoMessages", message);
            e.printStackTrace();
            return null;
        }

    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getAttributes().clear();
        return "/index.xhtml?faces-redirect=true";
    }

    public MapModel getAdvancedModel() {
        return advancedModel;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        setMarker((Marker) event.getOverlay());
    }

    public Marker getMarker() {
        return marker;
    }

    public String createDeployment() {
        try {

            StringBuilder units = new StringBuilder();
            for (String str : getDeploymentUnitID()) {
                units.append(str);

            }
            getDeployment().setDeploymentUnitID(units.toString());
            getDeployment().setCreatedOn(new java.util.Date());
            getDeployment().setCreatedBy(getUser());
            System.out.println(getDeployment().getAccident());
            System.out.println(getDeployment().getCreatedBy());
            System.out.println(getDeployment().getDeploymentUnitID());
            System.out.println(getDeployment().getCreatedOn());
            System.out.println(getDeployment().getSeverity());
            System.out.println(getDeployment().getStatusID());
            getUtx().begin();
            getAudit().setAction("created deployment");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getDeployment());
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getDeployment().getDeploymentUnitID() + " saved successfully."));
            setDeployment(new Deployment());
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setDeployment(new Deployment());
        return null;
    }

    public String updateDeployment() {
        try {

            Deployment deployment = getEm().find(Deployment.class,
                    getDeployment().getIddeployment());

            getUtx().begin();
            getAudit().setAction("updated deployment");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(deployment);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", deployment.getDeploymentUnitID() + " Updated successfully."));
            deployment = new Deployment();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a deployment unit."));
        }
        setDeployment(new Deployment());
        return null;
    }

    public String acknowledgeDeployment() {
        try {

            Deployment dep = getEm().find(Deployment.class,
                    deployment.getIddeployment());

            getUtx().begin();
            getAudit().setAction("updated deployment");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(dep);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", deployment.getDeploymentUnitID() + " Updated successfully."));
            deployment = new Deployment();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a deployment unit."));
        }
        setDeployment(new Deployment());
        return null;
    }

    public String deleteDeployment(Deployment deployment) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted deployment");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Deployment toBeRemoved = (Deployment) getEm().merge(deployment);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            deployment = new Deployment();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", deployment.getDeploymentUnitID() + " Deleted successfully."));

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", deployment.getDeploymentUnitID() + " failed to delete successfully."));
        }
        deployment = new Deployment();
        return null;
    }

    public boolean isRemember() {
        return this.remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public String createDeploymentunit() {
        try {

            Status stat = new Status(1);
            deploymentunit.setStatusID(stat);
            getUtx().begin();
            getAudit().setAction("created deployment unit");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getDeploymentunit().setCreatedBy(new User(1));
            getDeploymentunit().setDateCreated(new java.util.Date());
            getEm().persist(getAudit());
            getEm().persist(getDeploymentunit());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getDeploymentunit().getOrgname() + " saved successfully."));
            deploymentunit.setActors(null);
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        return null;
    }

    public String updateDeploymentunit() {
        try {

            Deploymentunit deployment = getEm().find(Deploymentunit.class,
                    getDeploymentunit().getIddeploymentUnit());
            getUtx().begin();
            getAudit().setAction("updated deployment unit");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(deployment);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", deployment.getOrgname() + " Updated successfully."));
            deployment = new Deploymentunit();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a deployment."));
        }
        setDeploymentunit(new Deploymentunit());
        return null;
    }

    public String deleteDeploymentunit(Deploymentunit deployment) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted deployment unit");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Deploymentunit toBeRemoved = (Deploymentunit) getEm().merge(deployment);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            deployment = new Deploymentunit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", deployment.getOrgname() + " Deleted successfully."));

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", deployment.getOrgname() + " failed to delete successfully."));
        }
        deployment = new Deploymentunit();
        return null;
    }

    //group
    public String createActorgroup() {
        try {

            getUtx().begin();
            getAudit().setAction("created actors");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getActorgroup().setCreatedBy(1);
            getActorgroup().setDateCreated(new java.util.Date());
            getEm().persist(getAudit());
            getEm().persist(getActorgroup());
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getActorgroup().getNames() + " saved successfully."));
            setActorgroup(new Actorgroup());
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setActorgroup(new Actorgroup());
        return null;
    }

    public String updateActorgroup() {
        try {

            Actorgroup actors = getEm().find(Actorgroup.class,
                    getActorgroup().getIdactors());
            getUtx().begin();
            getAudit().setAction("updated actors");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            actors.setCreatedBy(1);
            getEm().persist(getAudit());
            getEm().merge(actors);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", actors.getNames() + " Updated successfully."));
            actors = new Actorgroup();
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update an actor."));
        }
        setActorgroup(new Actorgroup());
        return null;
    }

    public String deleteActorgroup(Actorgroup actors) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted actors");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Actorgroup toBeRemoved = (Actorgroup) getEm().merge(actors);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            actors = new Actorgroup();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", actors.getNames() + " Deleted successfully."));

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", actors.getNames() + " failed to delete successfully."));
        }
        actors = new Actorgroup();
        return null;
    }

    public String createUsergroup() {

        try {
            getUtx().begin();
            getAudit().setAction("created group");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getGroup1().setCreatedBy(new User(1));
            getGroup1().setCreatedAt(new java.util.Date());
            getEm().persist(getAudit());
            getEm().persist(getGroup1());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getUsergroup().getName() + " saved successfully."));
            setUsergroup(new Usergroup());
        } catch (Exception ex) {
            Logger.getLogger(acc.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not create a group."));
        }
        return null;
    }

    public String updateUsergroup() {
        try {

            Usergroup group2 = getEm().find(Usergroup.class,
                    getUsergroup().getIdgroups());
            getUtx().begin();
            getAudit().setAction("updated group");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(group2);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", group2.getName() + " Updated successfully."));
            group2 = new Usergroup();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a group."));
        }
        setUsergroup(new Usergroup());
        return null;
    }

    public String deleteUsergroup(Usergroup group) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted group");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Usergroup toBeRemoved = (Usergroup) getEm().merge(group);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            group = new Usergroup();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", group.getName() + " Deleted successfully."));

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", group.getName() + " failed to delete successfully."));
        }
        group = new Usergroup();
        return null;
    }

    public String createUser() {
        try {

            getUser().setCreatedAt(new java.util.Date());
            getUser().setCreatedBy(1);
            getUser().setLastLogin(new java.util.Date());
            getUser().setStatusID(1);
            getUser().setPword("1234");
            getUser().setDepartment(1);
            getUtx().begin();
            getAudit().setAction("saved user " + getUser().getUsername());
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getUser());
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getUser().getName() + " saved successfully."));
            setUser(new User());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setUser(new User());
        return null;
    }

    public String updateUser() {
        try {

            User user2 = getEm().find(User.class,
                    user.getIdusers());
            user2.setCreatedAt(new java.util.Date());
            user2.setCreatedBy(1);
            user2.setDepartment(1);
            user2.setEmail(user.getEmail());
            user2.setGroupID(user.getGroupID());
            user2.setLastLogin(new java.util.Date());
            user2.setName(user.getName());
            user2.setPhone(user.getPhone());
            user2.setPword("1234");
            user2.setStaffID(user.getStaffID());
            user2.setStatusID(1);
            user2.setUsername(user.getUsername());

            getUtx().begin();
            getAudit().setAction("updated user " + user.getIdusers());
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(user2);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", user.getName() + " Updated successfully."));
            user = new User();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a user."));
        }
        setUser(new User());
        return null;
    }

    public String deleteUser(User user) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted user");
            getAudit().setCreatedby(user.getIdusers());
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            User toBeRemoved = (User) getEm().merge(user);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            user = new User();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User deleted", "User deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("User", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("User", success);
        }
        user = new User();
        return null;
    }

    public String createAccident() {
        try {

            Apikey apiKey = (Apikey) em.createQuery("select a from Apikey a").getSingleResult();

            System.out.println(latitude);
            System.out.println(longitude);
            System.out.println("the key" + apiKey.getApikey());

            GeoApiContext context = new GeoApiContext().setApiKey(apiKey.getApikey());
            com.google.maps.model.LatLng latlng = new com.google.maps.model.LatLng(Float.parseFloat(latitude), Float.parseFloat(longitude));
            GeocodingResult[] results;
            results = GeocodingApi.reverseGeocode(context, latlng).await();
            accident.setLatitude(latitude);
            accident.setLongitude(longitude);
            accident.setStatusID(new Status(1));
            accident.setMsisdn("0739895326");
            accident.setRoad(results[0].formattedAddress);
            accident.setPlaceOccured(results[0].formattedAddress);
            accident.setTimeOccured(new java.util.Date());
            accident.setDeadVictims(accident.getDeadVictims());
            accident.setInjuredVictims(accident.getInjuredVictims());

            getUtx().begin();
            getAudit().setAction("reported accident " + getAccident().getPlaceOccured() + getAccident().getRoad());
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(accident);
            em.flush();
            deploy(accident, accident.getStatusID(), accident.getStatusID().getName(), accident.getLatitude(), accident.getLongitude());
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Accident along " + getAccident().getRoad() + " has been reported."));
            setAccident(new Accident());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
            ex.printStackTrace();
        }
        setAccident(new Accident());
        return null;

    }

    public String deploy(Accident accident, Status status, String severity, String latitude, String longitude) {
        try {

            Float latitude2 = Float.parseFloat(latitude) + Float.parseFloat("-0.2");
            Float longitude2 = Float.parseFloat(longitude) + Float.parseFloat("0.2");
            System.out.println("passed longitude " + longitude2);
            System.out.println("passed latitude " + latitude2);
            System.out.println("passed accident " + accident);
            System.out.println("passed status " + status);
            deploymentunitList = em.createQuery("select d from Deploymentunit d where d.latitude <= '" + latitude2 + "' and d.longitude <= '" + longitude2 + "'").getResultList();
            for (Deploymentunit d : deploymentunitList) {

                System.out.println(d.getOrgname());
                System.out.println(d.getActors());
                deployment.setDeploymentUnitID(d.getOrgname());
                deployment.setCreatedOn(new java.util.Date());
                deployment.setCreatedBy(new User(1));
                deployment.setActor(d.getActors());
                deployment.setAccident(accident);
                deployment.setSeverity("HIGH");
                deployment.setStatusID(new Status(1));
                deployment.setAcknowledgement(0);
                getEm().persist(deployment);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", deployment.getDeploymentUnitID() + " deployed successfully."));
            }

            setDeployment(new Deployment());
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setDeployment(new Deployment());
        return null;
    }

    public void setlatLong() {
        try {
            String latitude = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("latitude");
            String longitude = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("longitude");
            setLatitude(latitude);
            setLongitude(longitude);

            System.out.println("the set latitude " + latitude);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String updateAccident() {
        try {

            System.out.println("Updated " + accident.getRoad());
            Accident accid = getEm().find(Accident.class,
                    accident.getIdaccident());

            accid.setDeadVictims(accident.getDeadVictims());
            accid.setInjuredVictims(accident.getInjuredVictims());
            accid.setMsisdn(accident.getMsisdn());
            accid.setPlaceOccured(accident.getPlaceOccured());
            accid.setRoad(accident.getRoad());
            accid.setStatusID(accident.getStatusID());
            accid.setTimeOccured(new java.util.Date());

            getUtx().begin();
            getAudit().setAction("updated accident " + accid.getPlaceOccured() + " " + accid.getRoad());
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().merge(accid);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", accid.getPlaceOccured() + " " + accid.getRoad() + " Updated successfully."));
            accid = new Accident();
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update an accident."));
        }
        setAccident(new Accident());
        return null;
    }

    public String deleteAccident(Accident accid) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted accident");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Accident toBeRemoved = (Accident) getEm().merge(accid);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            accid = new Accident();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Accident deleted", "Accident deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Accident", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Accident", success);
        }
        accid = new Accident();
        return null;
    }

    public String createStatus() {
        try {

            getStatus().setCreatedBy(getUser());
            getUtx().begin();
            getAudit().setAction("created status");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            getEm().persist(getStatus());
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getStatus().getName() + " saved successfully."));
            setStatus(new Status());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setStatus(new Status());
        return null;
    }

    public String updateStatus() {
        try {

            Status statu = getEm().find(Status.class,
                    this.status.getIdstatus());

            statu.setCreatedBy(user);
            statu.setDescription(status.getDescription());
            statu.setName(status.getName());

            getUtx().begin();
            getAudit().setAction("updated status");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());

            getEm().persist(getAudit());
            getEm().merge(statu);
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", status.getName() + " Updated successfully."));
            status = new Status();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a status."));
        }
        setStatus(new Status());
        return null;
    }

    public String deleteStatus(Status status) {
        try {

            getUtx().begin();
            getAudit().setAction("Deleted status");
            getAudit().setCreatedby(1);
            getAudit().setTimer(new Date());
            getEm().persist(getAudit());
            Status toBeRemoved = (Status) getEm().merge(status);
            getEm().remove(toBeRemoved);
            getUtx().commit();
            status = new Status();
            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Status deleted", "Status deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Status", success);

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("Status", success);
        }
        status = new Status();
        return null;
    }

    public String createApiKey() {
        try {

            getUtx().begin();
            getEm().persist(getApiKey());
            getUtx().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", getApiKey().getApikey() + " saved successfully."));
            setApiKey(new Apikey());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
        }
        setApiKey(new Apikey());
        return null;
    }

    public String updateApiKey() {
        try {

            Apikey statu = getEm().find(Apikey.class,
                    this.apiKey.getIdapiKey());
            statu.setApikey(apiKey.getApikey());
            getUtx().begin();
            getEm().merge(statu);
            getUtx().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", apiKey.getApikey() + " Updated successfully."));
            apiKey = new Apikey();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Could not update a status."));
        }
        setApiKey(new Apikey());
        return null;
    }

    public String deleteApiKey() {
        try {

            getUtx().begin();
            Apikey toBeRemoved = (Apikey) getEm().merge(apiKey);
            getEm().remove(toBeRemoved);
            getUtx().commit();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ApiKey deleted", "ApiKey deleted");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("ApiKey", success);
            apiKey = new Apikey();
            return "mapKeys.xhtml";
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("ApiKey", success);
        }
        apiKey = new Apikey();
        return null;
    }

    /**
     * @return the em
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * @param em the em to set
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * @return the utx
     */
    public javax.transaction.UserTransaction getUtx() {
        return utx;
    }

    /**
     * @param utx the utx to set
     */
    public void setUtx(javax.transaction.UserTransaction utx) {
        this.utx = utx;
    }

    /**
     * @return the group1
     */
    public Usergroup getUsergroup() {
        return getGroup1();
    }

    /**
     * @param group1 the group1 to set
     */
    public void setUsergroup(Usergroup group1) {
        this.setGroup1(group1);
    }

    /**
     * @return the group1List
     */
    public List<Usergroup> getUsergroupList() {
        setGroup1List((List<Usergroup>) getEm().createQuery("select g from Usergroup g").getResultList());
        return getGroup1List();
    }

    /**
     * @param group1List the group1List to set
     */
    public void setUsergroupList(List<Usergroup> group1List) {
        this.setGroup1List(group1List);
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the userList
     */
    public List<User> getUserList() {
        userList = getEm().createQuery("select u from User u").getResultList();
        return userList;
    }

    /**
     * @param userList the userList to set
     */
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the statusList
     */
    public List<Status> getStatusList() {
        statusList = getEm().createQuery("select s from Status s").getResultList();
        return statusList;
    }

    /**
     * @param statusList the statusList to set
     */
    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    /**
     * @return the audit
     */
    public Audit getAudit() {
        return audit;
    }

    /**
     * @param audit the audit to set
     */
    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    /**
     * @return the auditList
     */
    public List<Audit> getAuditList() {
        auditList = getEm().createQuery("select a from Audit a").getResultList();
        return auditList;
    }

    /**
     * @param auditList the auditList to set
     */
    public void setAuditList(List<Audit> auditList) {
        this.auditList = auditList;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the accident
     */
    public Accident getAccident() {
        return accident;
    }

    /**
     * @param accident the accident to set
     */
    public void setAccident(Accident accident) {
        this.accident = accident;
    }

    public Long getAccidentListCount() {
        this.accidentListCount = ((Long) this.getEm().createNativeQuery("select count(*) from accident").getSingleResult());
        return this.accidentListCount;
    }

    /**
     * @return the accidentList
     */
    public List<Accident> getAccidentList() {
        accidentList = getEm().createQuery("select a from Accident a").getResultList();
        return accidentList;
    }

    /**
     * @param accidentList the accidentList to set
     */
    public void setAccidentList(List<Accident> accidentList) {
        this.accidentList = accidentList;
    }

    /**
     * @return the actors
     */
    public Actorgroup getActorgroup() {
        return getActors();
    }

    /**
     * @param actors the actors to set
     */
    public void setActorgroup(Actorgroup actors) {
        this.setActors(actors);
    }

    /**
     * @return the actorsList
     */
    public List<Actorgroup> getActorgroupList() {
        setActorsList((List<Actorgroup>) getEm().createQuery("select a from Actorgroup a").getResultList());
        return getActorsList();
    }

    /**
     * @param actorsList the actorsList to set
     */
    public void setActorgroupList(List<Actorgroup> actorsList) {
        this.setActorsList(actorsList);
    }

    /**
     * @return the deployment
     */
    public Deployment getDeployment() {
        return deployment;
    }

    /**
     * @param deployment the deployment to set
     */
    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }

    /**
     * @return the deploymentList
     */
    public List<Deployment> getDeploymentList() {
        deploymentList = getEm().createQuery("select d from Deployment d").getResultList();
        return deploymentList;
    }

    /**
     * @param deploymentList the deploymentList to set
     */
    public void setDeploymentList(List<Deployment> deploymentList) {
        this.deploymentList = deploymentList;
    }

    /**
     * @return the deploymentunit
     */
    public Deploymentunit getDeploymentunit() {
        return deploymentunit;
    }

    /**
     * @param deploymentunit the deploymentunit to set
     */
    public void setDeploymentunit(Deploymentunit deploymentunit) {
        this.deploymentunit = deploymentunit;
    }

    /**
     * @return the deploymentunitList
     */
    public List<Deploymentunit> getDeploymentunitList() {
        deploymentunitList = getEm().createQuery("select d from Deploymentunit d").getResultList();
        return deploymentunitList;
    }

    public List<Deploymentunit> selectIndividuals() {
        deploymentunitList = getEm().createQuery("select d from Deployment d where d.deploymentUnitID = " + deployment.getDeploymentUnitID() + "").getResultList();
        return deploymentunitList;
    }

    /**
     * @param deploymentunitList the deploymentunitList to set
     */
    public void setDeploymentunitList(List<Deploymentunit> deploymentunitList) {
        this.deploymentunitList = deploymentunitList;
    }

    /**
     * @return the accidentModel
     */
    public LineChartModel getAccidentModel() {
        return accidentModel;
    }

    /**
     * @param accidentModel the accidentModel to set
     */
    public void setAccidentModel(LineChartModel accidentModel) {
        this.accidentModel = accidentModel;
    }

    /**
     * @param accidentListCount the accidentListCount to set
     */
    public void setAccidentListCount(Long accidentListCount) {
        this.accidentListCount = accidentListCount;
    }

    /**
     * @return the deploymentUnitID
     */
    public List<String> getDeploymentUnitID() {
        return deploymentUnitID;
    }

    /**
     * @param deploymentUnitID the deploymentUnitID to set
     */
    public void setDeploymentUnitID(List<String> deploymentUnitID) {
        this.deploymentUnitID = deploymentUnitID;
    }

    /**
     * @return the responsibilities
     */
    public List<String> getResponsibilities() {
        return responsibilities;
    }

    /**
     * @param responsibilities the responsibilities to set
     */
    public void setResponsibilities(List<String> responsibilities) {
        this.responsibilities = responsibilities;
    }

    /**
     * @return the group1
     */
    public Usergroup getGroup1() {
        return group1;
    }

    /**
     * @param group1 the group1 to set
     */
    public void setGroup1(Usergroup group1) {
        this.group1 = group1;
    }

    /**
     * @return the group1List
     */
    public List<Usergroup> getGroup1List() {
        return group1List;
    }

    /**
     * @param group1List the group1List to set
     */
    public void setGroup1List(List<Usergroup> group1List) {
        this.group1List = group1List;
    }

    /**
     * @return the reportAccident
     */
    public boolean isReportAccident() {
        return reportAccident;
    }

    /**
     * @param reportAccident the reportAccident to set
     */
    public void setReportAccident(boolean reportAccident) {
        this.reportAccident = reportAccident;
    }

    /**
     * @return the deployHelpers
     */
    public boolean isDeployHelpers() {
        return deployHelpers;
    }

    /**
     * @param deployHelpers the deployHelpers to set
     */
    public void setDeployHelpers(boolean deployHelpers) {
        this.deployHelpers = deployHelpers;
    }

    /**
     * @return the auditTrails
     */
    public boolean isAuditTrails() {
        return auditTrails;
    }

    /**
     * @param auditTrails the auditTrails to set
     */
    public void setAuditTrails(boolean auditTrails) {
        this.auditTrails = auditTrails;
    }

    /**
     * @return the criteriaReports
     */
    public boolean isCriteriaReports() {
        return criteriaReports;
    }

    /**
     * @param criteriaReports the criteriaReports to set
     */
    public void setCriteriaReports(boolean criteriaReports) {
        this.criteriaReports = criteriaReports;
    }

    /**
     * @return the registerUsers
     */
    public boolean isRegisterUsers() {
        return registerUsers;
    }

    /**
     * @param registerUsers the registerUsers to set
     */
    public void setRegisterUsers(boolean registerUsers) {
        this.registerUsers = registerUsers;
    }

    /**
     * @return the registerHelpers
     */
    public boolean isRegisterHelpers() {
        return registerHelpers;
    }

    /**
     * @param registerHelpers the registerHelpers to set
     */
    public void setRegisterHelpers(boolean registerHelpers) {
        this.registerHelpers = registerHelpers;
    }

    /**
     * @return the recordStatus
     */
    public boolean isRecordStatus() {
        return recordStatus;
    }

    /**
     * @param recordStatus the recordStatus to set
     */
    public void setRecordStatus(boolean recordStatus) {
        this.recordStatus = recordStatus;
    }

    /**
     * @return the registerGroup
     */
    public boolean isRegisterGroup() {
        return registerGroup;
    }

    /**
     * @param registerGroup the registerGroup to set
     */
    public void setRegisterGroup(boolean registerGroup) {
        this.registerGroup = registerGroup;
    }

    /**
     * @return the latitude
     */
    /**
     * @return the actors
     */
    public Actorgroup getActors() {
        return actors;
    }

    /**
     * @param actors the actors to set
     */
    public void setActors(Actorgroup actors) {
        this.actors = actors;
    }

    /**
     * @return the actorsList
     */
    public List<Actorgroup> getActorsList() {
        actorsList = em.createQuery("select a from Actorgroup a").getResultList();
        return actorsList;
    }

    /**
     * @param actorsList the actorsList to set
     */
    public void setActorsList(List<Actorgroup> actorsList) {
        this.actorsList = actorsList;
    }

    /**
     * @param advancedModel the advancedModel to set
     */
    public void setAdvancedModel(MapModel advancedModel) {
        this.advancedModel = advancedModel;
    }

    /**
     * @param marker the marker to set
     */
    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the actorModel
     */
    public MapModel getActorModel() {
        return actorModel;
    }

    /**
     * @param actorModel the actorModel to set
     */
    public void setActorModel(MapModel actorModel) {
        this.actorModel = actorModel;
    }

    /**
     * @return the deployedUnitsCount
     */
    public Long getDeployedUnitsCount() {
        this.deployedUnitsCount = ((Long) this.getEm().createNativeQuery("select count(*) from deployment").getSingleResult());
        return deployedUnitsCount;
    }

    /**
     * @param deployedUnitsCount the deployedUnitsCount to set
     */
    public void setDeployedUnitsCount(Long deployedUnitsCount) {
        this.deployedUnitsCount = deployedUnitsCount;
    }

    /**
     * @return the systemUsersCount
     */
    public Long getSystemUsersCount() {
        this.systemUsersCount = ((Long) this.getEm().createNativeQuery("select count(*) from user").getSingleResult());
        return systemUsersCount;
    }

    /**
     * @param systemUsersCount the systemUsersCount to set
     */
    public void setSystemUsersCount(Long systemUsersCount) {
        this.systemUsersCount = systemUsersCount;
    }

    /**
     * @return the victimsCount
     */
    public BigDecimal getVictimsCount() {
        this.victimsCount = ((BigDecimal) this.getEm().createNativeQuery("select sum(deadVictims) from accident").getSingleResult());
        return victimsCount;
    }

    /**
     * @param victimsCount the victimsCount to set
     */
    public void setVictimsCount(BigDecimal victimsCount) {
        this.victimsCount = victimsCount;
    }

    /**
     * @return the personalDeploymentList
     */
    public List<Deployment> getPersonalDeploymentList() {
        personalDeploymentList = em.createQuery("select d from Deployment d where d.actor.username = '" + user.getUsername() + "'").getResultList();
        return personalDeploymentList;
    }

    /**
     * @param personalDeploymentList the personalDeploymentList to set
     */
    public void setPersonalDeploymentList(List<Deployment> personalDeploymentList) {
        this.personalDeploymentList = personalDeploymentList;
    }

    /**
     * @return the apiKey
     */
    public Apikey getApiKey() {
        return apiKey;
    }

    /**
     * @param apiKey the apiKey to set
     */
    public void setApiKey(Apikey apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @return the apiKeyList
     */
    public List<Apikey> getApiKeyList() {
        apiKeyList = em.createQuery("select a from Apikey a").getResultList();

        return apiKeyList;
    }

    /**
     * @param apiKeyList the apiKeyList to set
     */
    public void setApiKeyList(List<Apikey> apiKeyList) {
        this.apiKeyList = apiKeyList;
    }

}
