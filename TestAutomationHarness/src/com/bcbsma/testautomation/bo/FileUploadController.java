package com.bcbsma.testautomation.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
//import com.gorski.apiproof.Collections.*;

public class FileUploadController {
	private UploadedFile file;

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	private void writeBytesToFile(byte[] bFile, String fileDest) {

		try (FileOutputStream fileOuputStream = new FileOutputStream(fileDest)) {
			fileOuputStream.write(bFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		String fileName = uploadedFile.getFileName();
		String sheetname = "Metadata";
		String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF");
		String path2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\WEB-INF");
		String tmploc = "C:\\Users\\jayala01\\eclipse-workspace\\TestAutomationHarness\\tmp\\";

		 //String contentType = uploadedFile.getContentType();
		fileName = tmploc+fileName;
		 byte[] contents = uploadedFile.getContents(); // Or getInputStream()
		 writeBytesToFile(contents, fileName);

		//PositionalFileLayout layout = new PositionalFileLayout(fileName + "@" + sheetname, "layout");
		//layout.readLayoutFile(sheetname);

		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
