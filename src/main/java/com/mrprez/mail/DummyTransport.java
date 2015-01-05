package com.mrprez.mail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;

public class DummyTransport extends Transport {
	private File outputFile;
	private FileWriter writer;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
	
	
	public DummyTransport(Session session, URLName urlname) {
		super(session, urlname);
		outputFile = new File(session.getProperty("mail.smtp.host"));
	}

	@Override
	public void sendMessage(Message message, Address[] adressTab) throws MessagingException {
		try {
			writer.write("Date:");
			writer.write(dateFormat.format(new Date()));
			writer.write("\r\n");
			if(message.getFrom().length>0){
				writer.write("From: ");
				writer.write(message.getFrom()[0].toString());
				writer.write("\r\n");
			}
			writer.write("To: ");
			for(int i=0; i<adressTab.length; i++){
				writer.write(adressTab[i].toString());
				writer.write(';');
			}
			writer.write("\r\n");
			writer.write("Subject: ");
			writer.write(message.getSubject());
			writer.write("\r\n");
			if(message.getContent() instanceof Multipart){
				for(int i=0; i<((Multipart)message.getContent()).getCount(); i++){
					writer.write(((Multipart)message.getContent()).getBodyPart(i).getContent().toString());
					writer.write("\r\n");
				}
			}else{
				writer.write(message.getContent().toString());
			}
			writer.write("\r\n**************************************\r\n");
		} catch (IOException e) {
			throw new MessagingException(e.getMessage(), e);
		}
	}

	@Override
	public synchronized void close() throws MessagingException {
		try {
			writer.close();
			writer = null;
			setConnected(false);
		} catch (IOException e) {
			throw new MessagingException(e.getMessage(), e);
		}
	}

	@Override
	public synchronized void connect(String arg0, int arg1, String arg2, String arg3) throws MessagingException {
		try {
			writer = new FileWriter(outputFile, true);
			setConnected(true);
		} catch (IOException e) {
			throw new MessagingException(e.getMessage(), e);
		}
	}
	
	

}
