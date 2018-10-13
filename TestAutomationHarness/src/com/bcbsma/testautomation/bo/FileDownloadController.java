package com.bcbsma.testautomation.bo;

import java.io.InputStream;

import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;  
import org.primefaces.model.StreamedContent;  

public class FileDownloadController {  

    private StreamedContent file;  

    public FileDownloadController() {
     }

    public StreamedContent getFile() {
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/files/Dataset Definition Sheet Template.xlsx");
        file = new 
        		DefaultStreamedContent(stream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Dataset Definition Sheet Template.xlsx"); 
    	return file;
    }
}
