package com.mrprez.mail;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;

public class DummyMessage extends Message {
	private Date date;
	private String to[];
	private String from;
	private String subject;
	private String content;
	
	
	public DummyMessage(Date date, String to[], String from, String subject, String content) {
		super();
		this.date = date;
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.content = content;
	}

	public int getSize() throws MessagingException {
		return -1;
	}

	public int getLineCount() throws MessagingException {
		return -1;
	}

	public String getContentType() throws MessagingException {
		return "text/plain";
	}

	public boolean isMimeType(String mimeType) throws MessagingException {
		return mimeType.contains("text/plain");
	}

	public String getDisposition() throws MessagingException {
		return null;
	}

	public void setDisposition(String disposition) throws MessagingException {
		;
	}

	public String getDescription() throws MessagingException {
		return null;
	}

	public void setDescription(String description) throws MessagingException {
		;
	}

	public String getFileName() throws MessagingException {
		return null;
	}

	public void setFileName(String filename) throws MessagingException {
		;
	}

	public InputStream getInputStream() throws IOException, MessagingException {
		// TODO Auto-generated method stub
		return null;
	}

	public DataHandler getDataHandler() throws MessagingException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getContent() throws IOException, MessagingException {
		return content;
	}

	public void setDataHandler(DataHandler dh) throws MessagingException {
		// TODO Auto-generated method stub

	}

	public void setContent(Object obj, String type) throws MessagingException {
		this.content = (String) obj;
	}

	public void setText(String text) throws MessagingException {
		this.content = text;
	}

	public void setContent(Multipart mp) throws MessagingException {
		throw new RuntimeException("This method should not be used");
	}

	public void writeTo(OutputStream os) throws IOException, MessagingException {
		os.write(content.getBytes("UTF-8"));
	}

	public String[] getHeader(String header_name) throws MessagingException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setHeader(String header_name, String header_value)
			throws MessagingException {
		// TODO Auto-generated method stub

	}

	public void addHeader(String header_name, String header_value)
			throws MessagingException {
		// TODO Auto-generated method stub

	}

	public void removeHeader(String header_name) throws MessagingException {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("rawtypes")
	public Enumeration getAllHeaders() throws MessagingException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getMatchingHeaders(String[] header_names)
			throws MessagingException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getNonMatchingHeaders(String[] header_names)
			throws MessagingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address[] getFrom() throws MessagingException {
		return new Address[]{new InternetAddress(from)};
	}

	@Override
	public void setFrom() throws MessagingException {
		throw new RuntimeException("This method should not be used");
	}

	@Override
	public void setFrom(Address address) throws MessagingException {
		from = ((InternetAddress) address).getAddress();

	}

	@Override
	public void addFrom(Address[] addresses) throws MessagingException {
		// TODO Auto-generated method stub

	}

	@Override
	public Address[] getRecipients(RecipientType type) throws MessagingException {
		Address[] result = new Address[to.length];
		for(int i=0; i<to.length; i++){
			result[i] = new InternetAddress(to[i]);
		}
		return result;
	}

	@Override
	public void setRecipients(RecipientType type, Address[] addresses) throws MessagingException {
		to = new String[addresses.length];
		for(int i=0; i<addresses.length; i++){
			to[i] = ((InternetAddress)addresses[i]).getAddress().trim();
		}
	}

	@Override
	public void addRecipients(RecipientType type, Address[] addresses)
			throws MessagingException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSubject() throws MessagingException {
		return subject;
	}

	@Override
	public void setSubject(String subject) throws MessagingException {
		this.subject = subject;
	}

	@Override
	public Date getSentDate() throws MessagingException {
		return date;
	}

	@Override
	public void setSentDate(Date date) throws MessagingException {
		this.date = date;
	}

	@Override
	public Date getReceivedDate() throws MessagingException {
		return date;
	}

	@Override
	public Flags getFlags() throws MessagingException {
		return new Flags();
	}

	@Override
	public void setFlags(Flags flag, boolean set) throws MessagingException {
		// TODO Auto-generated method stub

	}

	@Override
	public Message reply(boolean replyToAll) throws MessagingException {
		return new DummyMessage(new Date(), new String[]{from}, to[0], "Re:"+subject, content);
	}

	@Override
	public void saveChanges() throws MessagingException {
		// TODO Auto-generated method stub

	}

}
