package br.com.trixti.itm.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AbstractController<T> {

	
	public HttpServletRequest getRequest() { 
		FacesContext ctx = getFacesContext();
		ExternalContext exc = ctx.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) exc.getRequest();
		return request;
	}
	
	public HttpServletResponse getResponse() { 
		FacesContext ctx = getFacesContext();
		ExternalContext exc = ctx.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) exc.getResponse();
		return response;
	}
	
	public ServletContext getServletContext() { 
		FacesContext ctx = getFacesContext();
		ExternalContext exc = ctx.getExternalContext();
		return (ServletContext)exc.getContext();
	}
	
	
	protected FacesContext getFacesContext() {
		return FacesContext
				.getCurrentInstance();
	}
	
	
	protected void download(ByteArrayOutputStream outputStream, String fileName) throws IOException {
        try{
        	HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
	    	response.reset();
	        response.setContentLength(outputStream.size());
	        response.setContentType("application/".concat(fileName.substring(fileName.length()-3, fileName.length())));
	        response.setHeader("Content-Disposition", "attachment; filename=".concat(fileName).concat(";"));
	        ServletOutputStream outputStreamServlet = response.getOutputStream();
	        outputStream.toByteArray();
	        outputStream.writeTo(outputStreamServlet);
	        outputStreamServlet.flush();
	        outputStreamServlet.close();
	        outputStream.flush();
	        outputStream.close();
	        getFacesContext().responseComplete();
        }catch (Exception e) {
        	e.printStackTrace();
        }    
    }
	
	 public String getStackTrace() {
	        Throwable throwable = (Throwable)  FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.exception");
	        StringBuilder builder = new StringBuilder();
	        if(throwable!= null && throwable.getMessage() != null){
		        builder.append(throwable.getMessage()).append("\n");
		        for (StackTraceElement element : throwable.getStackTrace()) {
		            builder.append(element).append("\n");
		        }
	        }   
	        return builder.toString();
	    }
}
