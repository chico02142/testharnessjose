package com.bcbsma.testautomation.model;

public class AutomationTest {
	public String testName;
	public String fileNameOne;
	public String fileNameTwo;
	public String radioVal;

	public AutomationTest() {
		
	}

	public String getRadioVal() {
		return radioVal;
	}
	
	public void init() {
		radioVal="yes";
	}
	
	public void setRadioVal(String defTempl) {		
		this.radioVal = defTempl;		
	}
	
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getFileNameOne() {
		return fileNameOne;
	}
	public void setFileNameOne(String fileNameOne) {
		this.fileNameOne = fileNameOne;
	}
	public String getFileNameTwo() {
		return fileNameTwo;
	}
	public void setFileNameTwo(String fileNameTwo) {
		this.fileNameTwo = fileNameTwo;
	}

}
