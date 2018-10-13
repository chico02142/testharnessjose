package com.bcbsma.testautomation.backing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import com.bcbsma.testautomation.bo.AutomationProjectBO;
import com.bcbsma.testautomation.model.AutomationProject;

@ManagedBean(name="automationProjectBacking", eager=true)
@ApplicationScoped

public class AutomationProjectBacking {

	private UIComponent mybutton;
	private UIComponent btnsaveproj;
	
	List<String> projectNameOptions;
	List<String> expectedValueTypeOptions;
	List<String> actualValueTypeOptions;
	List<String> expectedValueSubTypeOptions;
	List<String> actualValueSubTypeOptions;
	List<String> expectedLocationOptions;
	List<String> actualLocationOptions;
	List<String> prefResultsLocOptions;
		
	private AutomationProject automationproject = new AutomationProject();
	private AutomationProjectBO automationprojectBO = new AutomationProjectBO();
	
	public AutomationProject getAutomationproject() {
		return automationproject;
	}
	public void setAutomationproject(AutomationProject automationproject) {
		this.automationproject = automationproject;
	}

	public AutomationProjectBacking() {

	}
	
    public void listenerActualSubType() {
		actualLocationOptions = null;
		automationproject.setActualLocation(null);	
		automationproject.setActualValueSubType(null);
		actualValueSubTypeOptions = null;
		
        System.out.println("listenerActualSubType");

        String type_name = automationproject.getActualValueType();
		String sql = "SELECT SUBTYPE_NAME FROM AUTOMATION.TESTTYPE_LIST where TYPE_NAME = '"+type_name+"' "+
        "and ACTIVE = 'Y'";
		actualValueSubTypeOptions = automationprojectBO.returnColumnFromDB(sql);
		automationproject.setActualValueSubType(actualValueSubTypeOptions.get(0));

		listenerActualLocation();				
    }

    public void listenerExpectedSubType() {
		expectedLocationOptions = null;
		automationproject.expectedLocation = null;
		automationproject.expectedValueSubType = null;
		
        System.out.println("listenerExpectedSubType");
        
        String type_name = automationproject.expectedValueType;
		String sql = "SELECT SUBTYPE_NAME FROM AUTOMATION.TESTTYPE_LIST where TYPE_NAME = '"+type_name+"' " +
				"and ACTIVE = 'Y'";
		expectedValueSubTypeOptions = automationprojectBO.returnColumnFromDB(sql); 
		automationproject.setExpectedValueSubType(expectedValueSubTypeOptions.get(0));

		listenerExpectedLocation();
    }

    public void listenerExpectedLocation() {
        System.out.println("listenerExpectedLocation");
        
        String subtype_name = automationproject.getExpectedValueSubType();
        
		expectedLocationOptions = null;
		automationproject.expectedLocation = null;

		String sql = "select distinct tt.STORAGELOCATION "+
		"from AUTOMATION.TESTTYPE tt, AUTOMATION.TESTTYPE_LIST ttl "+
		"where (tt.STORAGELOCATION is not NULL) "+
		"and regexp_substr(tt.testtype, '[^_]+$') = '"+subtype_name+"'";	
		expectedLocationOptions = automationprojectBO.returnColumnFromDB(sql);       
    }

    public void listenerActualLocation() {
		actualLocationOptions = null;
		automationproject.setActualLocation(null);
		
        System.out.println("listenerActualLocation");
        
        String subtype_name = automationproject.getActualValueSubType();
        
		String sql = "select distinct tt.STORAGELOCATION "+
		"from AUTOMATION.TESTTYPE tt, AUTOMATION.TESTTYPE_LIST ttl "+
		"where (tt.STORAGELOCATION is not NULL) "+
		"and regexp_substr(tt.testtype, '[^_]+$') = '"+subtype_name+"'";	
		actualLocationOptions = automationprojectBO.returnColumnFromDB(sql);  	
    }   
    
	public List<String> getProjectNameOptions() {
		return projectNameOptions;
	}
	
	public List<String> getExpectedValueTypeOptions() {
		return expectedValueTypeOptions;
	}
	
	public List<String> getActualValueTypeOptions() {
		return actualValueTypeOptions;
	}
	
	public List<String> getExpectedValueSubTypeOptions() {
		return expectedValueSubTypeOptions;
	}
	
	public List<String> getActualValueSubTypeOptions() {
		return actualValueSubTypeOptions;
	}
	
	public List<String> getExpectedLocationOptions() {
		return expectedLocationOptions;
	}
	
	public List<String> getActualLocationOptions() {
		return actualLocationOptions;
	}
	
	public List<String> getPrefResultsLocOptions() {
		return prefResultsLocOptions;
	}
	
	public String saveProject() {
		
		String project_name = automationproject.getProjectName();
		
		String sql = "INSERT INTO AUTOMATION.TESTSETUP " + "(PROJECTDESC, TIMEDATESTAMP, ACTIVE_YN) "
				+ "VALUES ('"+project_name+"', sysdate, 'Y')";		
		
		int ret = automationprojectBO.insertOneRowIntoDB(sql);
		if (ret>0) {
            FacesMessage message = new FacesMessage("Successfully inserted Project Name, "+project_name);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(btnsaveproj.getClientId(context), message);				
		} else {
            FacesMessage message = new FacesMessage("Unable to insert Project Name, "+project_name);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(btnsaveproj.getClientId(context), message);	
		}
		
		return "project";
	}	

	public String saveTest() {
		String sql1, sql2, sql3, sql4, sql5;		
		String testname = automationproject.getTestName();
		
		String expected_val_type = automationproject.getExpectedValueType();
		String actual_val_type = automationproject.getActualValueType();
		
		if (expected_val_type.equalsIgnoreCase("SQL")) {
			expected_val_type = "DATABASE";
		}

		if (actual_val_type.equalsIgnoreCase("SQL")) {
			actual_val_type = "DATABASE";
		}		
		
		String test_expected_id = expected_val_type +"_"
				+automationproject.getExpectedValueSubType();

		String test_actual_id = actual_val_type +"_"
				+automationproject.getActualValueSubType();
		
		sql1 = "(select project_id from testsetup where projectdesc = '"
		+automationproject.getProjectName()+"'),";
		
		sql2 = "(select reportsys_id from results_location_list where location_name = '"
		+automationproject.getPrefResultsLoc()+"'))";
		
		sql3 = "(select testtype_id from testtype where testtype = '"+test_actual_id+"' "+
				"and storagelocation is not null and rownum = 1)";

		sql4 = "(select testtype_id from testtype where testtype = '"+test_expected_id+"' "+
				"and storagelocation is not null and rownum = 1)";
		
		 sql5 = "INSERT INTO testconfig ("+
		    "testdescrip,"+
		    "test_actual_id,"+
		    "test_expected_id,"+
		    "project_id,"+
		    "reportsys_id"+
		") VALUES ("+
		    "'"+testname+"',"+sql3+","+sql4+","+sql1+sql2;
	
		//int ret = automationprojectBO.insertOneRowIntoDB(sql5);
		int ret = 2;
		if (ret>0) {
            FacesMessage message = new FacesMessage("Successfully saved Test Name, "+testname);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(mybutton.getClientId(context), message);	
            
    		if (automationproject.getActualValueType().equalsIgnoreCase("SQL")) {
    			return "add_new_dbtest_actual";
    		} else {
    			return "add_new_filetest_actual";
    		}
		} else {
            FacesMessage message = new FacesMessage("Unable to save Test Name, "+testname);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(mybutton.getClientId(context), message);
            UIComponent component = context.getViewRoot().findComponent(mybutton.getClientId(context));
            ((UIInput) component).setValid(false);
            return "project_select_upload";
		}	
		
		
	}
	
    public void setMybutton(UIComponent mybutton) {
        this.mybutton = mybutton;
    }

    public UIComponent getMybutton() {
        return mybutton;
    }	

    public UIComponent getBtnsaveproj() {
		return btnsaveproj;
	}
	public void setBtnsaveproj(UIComponent btnsaveproj) {
		this.btnsaveproj = btnsaveproj;
	}
	public void onload() {
    	System.out.println("onload");
		projectNameOptions = new ArrayList<>();
		
		String sql = "SELECT PROJECTDESC, to_date(TIMEDATESTAMP, 'DD-Mon-YY') as fdate "+
				"FROM AUTOMATION.TESTSETUP WHERE ACTIVE_YN = 'Y' "+
				"order by fdate desc";
		projectNameOptions = automationprojectBO.returnColumnFromDB(sql);    	
    }
    
    
	@PostConstruct
	public void init() {
		System.out.println("Init");
		String sql = "";
		projectNameOptions = new ArrayList<>();
		expectedValueTypeOptions = new ArrayList<>();
		actualValueTypeOptions = new ArrayList<>();
		expectedValueSubTypeOptions = new ArrayList<>();
		actualValueSubTypeOptions = new ArrayList<>();
		expectedLocationOptions = new ArrayList<>();
		actualLocationOptions = new ArrayList<>();
		prefResultsLocOptions = new ArrayList<>();
		
		sql = "SELECT PROJECTDESC, to_date(TIMEDATESTAMP, 'DD-Mon-YY') as fdate "+
				"FROM AUTOMATION.TESTSETUP WHERE ACTIVE_YN = 'Y' "+
				"order by fdate desc";
		projectNameOptions = automationprojectBO.returnColumnFromDB(sql);

		sql = "SELECT Distinct TYPE_NAME FROM AUTOMATION.TESTTYPE_LIST where ACTIVE = 'Y'";
		expectedValueTypeOptions = automationprojectBO.returnColumnFromDB(sql);
		
		actualValueTypeOptions = new ArrayList<String>(expectedValueTypeOptions);
	
		String first_type = actualValueTypeOptions.get(0);
		sql = "SELECT SUBTYPE_NAME FROM AUTOMATION.TESTTYPE_LIST where TYPE_NAME = '"+first_type+"'";	
		expectedValueSubTypeOptions = automationprojectBO.returnColumnFromDB(sql);
		
		actualValueSubTypeOptions = new ArrayList<String>(expectedValueSubTypeOptions);
		
		String subtype_name = actualValueSubTypeOptions.get(0);
		sql = "select distinct tt.STORAGELOCATION "+
		"from AUTOMATION.TESTTYPE tt, AUTOMATION.TESTTYPE_LIST ttl "+
		"where (tt.STORAGELOCATION is not NULL) "+
		"and regexp_substr(tt.testtype, '[^_]+$') = '"+subtype_name+"'";
		expectedLocationOptions = automationprojectBO.returnColumnFromDB(sql);
	
		actualLocationOptions = new ArrayList<>(expectedLocationOptions);
		
		prefResultsLocOptions = new ArrayList<>();
		sql = "SELECT LOCATION_NAME FROM AUTOMATION.RESULTS_LOCATION_LIST WHERE ACTIVE_YN = 'Y'";
		prefResultsLocOptions = automationprojectBO.returnColumnFromDB(sql);
	}	
}
