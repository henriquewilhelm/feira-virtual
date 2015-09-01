package models;

import java.io.Serializable;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Mail implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -950794566290527195L;

	private Email email;
	
	private String msg;
	private String assunto;
	private String destino;
	private String nomeDestino;
	
    public Mail() {
    	this.email = new SimpleEmail();
	}

    public Mail(String msg, String assunto, String destino, String nomeDestino) throws EmailException {

    	this.email = new SimpleEmail();
    	this.msg = msg;
    	this.assunto = assunto;
    	this.destino = destino;
    	this.nomeDestino = nomeDestino;
	}
    
	public void sendMail(){        
        try {
            String authuser = "sistema.lnb@gmail.com";
            String authpwd = "sis1234!";

            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator(authuser, authpwd));
            email.setDebug(true);
            email.setHostName("smtp.gmail.com");
            email.getMailSession().getProperties().put("mail.smtps.auth", "true");
            email.getMailSession().getProperties().put("mail.debug", "true");
            email.getMailSession().getProperties().put("mail.smtps.port", "587");
            email.getMailSession().getProperties().put("mail.smtps.socketFactory.port", "587");
            email.getMailSession().getProperties().put("mail.smtps.socketFactory.class",   "javax.net.ssl.SSLSocketFactory");
            email.getMailSession().getProperties().put("mail.smtps.socketFactory.fallback", "false");
            email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
           
            
            email.setFrom("sistema.lnb@gmail.com", "LNB Sistema");
            
            email.setSubject(getAssunto());
            email.setMsg(getMsg());
            email.addTo(getDestino(), getNomeDestino());
          //  email.setStartTLSRequired(false);
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getNomeDestino() {
		return nomeDestino;
	}

	public void setNomeDestino(String nomeDestino) {
		this.nomeDestino = nomeDestino;
	}
}