package com.bcbsma.testautomation.model;

public class AutomationProject {

	public String projectName;
	public String expectedValueType;
	public String actualValueType;
	public String expectedValueSubType;
	public String actualValueSubType;
	public String expectedLocation;
	public String actualLocation;
	public String prefResultsLoc = "preferred location";
	public String testName = "test name";
	
	public AutomationProject () {
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getExpectedValueType() {
		return expectedValueType;
	}
	public void setExpectedValueType(String expectedValueType) {
		this.expectedValueType = expectedValueType;
	}
	public String getActualValueType() {
		return actualValueType;
	}
	public void setActualValueType(String actualValueType) {
		this.actualValueType = actualValueType;
	}
	public String getExpectedValueSubType() {
		return expectedValueSubType;
	}
	public void setExpectedValueSubType(String expectedValueSubStype) {
		this.expectedValueSubType = expectedValueSubStype;
	}
	public String getActualValueSubType() {
		return actualValueSubType;
	}
	public void setActualValueSubType(String actualValueSubType) {
		this.actualValueSubType = actualValueSubType;
	}
	public String getExpectedLocation() {
		return expectedLocation;
	}
	public void setExpectedLocation(String valuesLocation1) {
		this.expectedLocation = valuesLocation1;
	}
	public String getActualLocation() {
		return actualLocation;
	}
	public void setActualLocation(String valuesLocation2) {
		this.actualLocation = valuesLocation2;
	}
	public String getPrefResultsLoc() {
		return prefResultsLoc;
	}
	public void setPrefResultsLoc(String pref_results_loc) {
		this.prefResultsLoc = pref_results_loc;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String test_name) {
		this.testName = test_name;
	}
	
}
