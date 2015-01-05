package com.mrprez.mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

public class DummyFolder extends Folder {
	private static final String SEPARATOR_LINE = "**************************************";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
	
	private File file;
	private BufferedReader reader;
	private int currentMessageIndex = 0;
	

	protected DummyFolder(Store store) throws MalformedURLException, URISyntaxException {
		super(store);
		this.file = new File(store.getURLName().getURL().toURI());
	}

	public DummyFolder(Store store, File file) {
		super(store);
		this.file = file;
	}

	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public String getFullName() {
		// TODO Auto-generated method stub
		return file.getAbsolutePath();
	}

	@Override
	public Folder getParent() throws MessagingException {
		return null;
	}

	@Override
	public boolean exists() throws MessagingException {
		return file.exists();
	}

	@Override
	public Folder[] list(String pattern) throws MessagingException {
		if(file.isDirectory()){
			File subFiles[] = file.listFiles();
			Folder[] result = new Folder[subFiles.length];
			for(int i=0; i<subFiles.length; i++){
				result[i] = new DummyFolder(store, subFiles[i]);
			}
			return result;
		}
		return new Folder[0];
	}

	@Override
	public char getSeparator() throws MessagingException {
		return File.separatorChar;
	}

	@Override
	public int getType() throws MessagingException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean create(int type) throws MessagingException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasNewMessages() throws MessagingException {
		return true;
	}

	@Override
	public Folder getFolder(String name) throws MessagingException {
		return new DummyFolder(store, new File(file, name));
	}

	@Override
	public boolean delete(boolean recurse) throws MessagingException {
		return false;
	}

	@Override
	public boolean renameTo(Folder f) throws MessagingException {
		return false;
	}

	@Override
	public void open(int mode) throws MessagingException {
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		} catch (FileNotFoundException e) {
			throw new MessagingException("Cannot open FileReader", e);
		} catch (UnsupportedEncodingException e) {
			throw new MessagingException("UTF-8 not supported", e);
		}
	}

	@Override
	public void close(boolean expunge) throws MessagingException {
		if(reader!=null){
			try {
				reader.close();
				reader = null;
			} catch (IOException e) {
				throw new MessagingException("Cannot close FileReader", e);
			}
		}
	}

	@Override
	public boolean isOpen() {
		return reader != null;
	}

	@Override
	public Flags getPermanentFlags() {
		return null;
	}

	@Override
	public int getMessageCount() throws MessagingException {
		int count = 0;
		try{
			BufferedReader countReader = new BufferedReader(new FileReader(file));
			try{
				String line;
				while((line = countReader.readLine()) != null){
					if(line.equals(SEPARATOR_LINE)){
						count++;
					}
				}
			}finally{
				countReader.close();
			}
		} catch (IOException ioe) {
			throw new MessagingException(ioe.getMessage(), ioe);
		}
		return count;
	}

	@Override
	public Message getMessage(int msgnum) throws MessagingException {
		try{
			String line;
			while(msgnum > currentMessageIndex && (line = reader.readLine()) != null){
				if(line.equals(SEPARATOR_LINE)){
					currentMessageIndex++;
				}
			}
			if(msgnum == currentMessageIndex){
				return readMessage();
			}
			return null;
		}catch (IOException ioe) {
			throw new MessagingException(ioe.getMessage(), ioe);
		} catch (ParseException pe) {
			throw new MessagingException(pe.getMessage(), pe);
		}
	}
	
	private Message readMessage() throws IOException, ParseException{
		String line = reader.readLine();
		String dateString = line.substring(6);
		Date date = dateFormat.parse(dateString);
		line = reader.readLine();
		String from = line.substring(6);
		line = reader.readLine();
		String to[] = line.substring(3).split(";");
		
		line = reader.readLine();
		String subject = line.substring(8);
		StringBuilder contentBuilder = new StringBuilder();
		while((line = reader.readLine()) != null && !line.equals(SEPARATOR_LINE)){
			contentBuilder.append(line + "\r\n");
		}
		Message message = new DummyMessage(date, to, from, subject, contentBuilder.toString());
		
		currentMessageIndex++;
		
		return message;
	}

	@Override
	public void appendMessages(Message[] msgs) throws MessagingException {
		;
	}

	@Override
	public Message[] expunge() throws MessagingException {
		return new Message[0];
	}

}
