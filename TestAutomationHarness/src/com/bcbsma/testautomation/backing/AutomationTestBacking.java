package com.bcbsma.testautomation.backing;


import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import com.bcbsma.testautomation.bo.FileDownloadController;
import com.bcbsma.testautomation.bo.FileUploadController;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import com.bcbsma.testautomation.model.AutomationTest;

@ManagedBean(name = "automationTestBacking")
@ViewScoped

public class AutomationTestBacking {
	
	private String act_testname;
	private String act_testtyppe;
	private String act_testsubtype;
	private String exp_testname;
	private String exp_testtyppe;
	private String exp_testsubtype;
	private AutomationTest automationtest = new AutomationTest();
	
	public AutomationTest getAutomationtest() {
		return automationtest;
	}

	private FileDownloadController file_download = new FileDownloadController();
	private FileUploadController file_upload = new FileUploadController();
	
	public AutomationTestBacking() {
	}
	
    @ManagedProperty("#{automationProjectBacking}")
    private AutomationProjectBacking automationProjectBacking;
	
	public void setAutomationProjectBacking(AutomationProjectBacking automationProjectBacking) {
		this.automationProjectBacking = automationProjectBacking;
	}
	
	@PostConstruct
	public void init() {
		System.out.println("Init automationtestbacking");
		automationtest.setRadioVal("true");
		act_testname = automationProjectBacking.getAutomationproject().testName;
		act_testtyppe = automationProjectBacking.getAutomationproject().getActualValueType();
		act_testsubtype = automationProjectBacking.getAutomationproject().getActualValueSubType();
		exp_testname = automationProjectBacking.getAutomationproject().testName;
		exp_testtyppe = automationProjectBacking.getAutomationproject().getExpectedValueType();
		exp_testsubtype = automationProjectBacking.getAutomationproject().getExpectedValueSubType();
	}
	
	public String getAct_testname() {
		return act_testname;
	}

	public void setAct_testname(String act_testname) {
		this.act_testname = act_testname;
	}

	public String getAct_testtyppe() {
		return act_testtyppe;
	}

	public void setAct_testtyppe(String act_testtyppe) {
		this.act_testtyppe = act_testtyppe;
	}

	public String getAct_testsubtype() {
		return act_testsubtype;
	}

	public void setAct_testsubtype(String act_testsubtype) {
		this.act_testsubtype = act_testsubtype;
	}

	public String getExp_testname() {
		return exp_testname;
	}

	public void setExp_testname(String exp_testname) {
		this.exp_testname = exp_testname;
	}

	public String getExp_testtyppe() {
		return exp_testtyppe;
	}

	public void setExp_testtyppe(String exp_testtyppe) {
		this.exp_testtyppe = exp_testtyppe;
	}

	public String getExp_testsubtype() {
		return exp_testsubtype;
	}

	public void setExp_testsubtype(String exp_testsubtype) {
		this.exp_testsubtype = exp_testsubtype;
	}

	public StreamedContent downloadfile () {
		return file_download.getFile();
	}
	
    public void uploadFile(FileUploadEvent event) {
    	file_upload.handleFileUpload(event);
        try {
        	
    		if (exp_testtyppe.equalsIgnoreCase("SQL")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("add_new_dbtest_expected.xhtml"); 
                FacesContext.getCurrentInstance().responseComplete();
    		} else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("add_new_filetest_expected.xhtml"); 
                FacesContext.getCurrentInstance().responseComplete();
    		}

        } catch (IOException ex) {}   	
    }	
    
    public void downloadscreen() {
    	if (automationtest.radioVal.equalsIgnoreCase("no")) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("add_new_filetest_download.xhtml");   
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {} }          
    }

    public void uploadscreen() {
    	if (automationtest.radioVal.equalsIgnoreCase("yes")) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("add_new_filetest_upload.xhtml"); 
                FacesContext.getCurrentInstance().responseComplete();
            } catch (IOException ex) {} }       
    }
}
