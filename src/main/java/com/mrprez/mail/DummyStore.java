package com.mrprez.mail;

import java.io.File;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

public class DummyStore extends Store {
	private Folder defaultFolder;
	
	
	public DummyStore(Session session, URLName urlname) {
		super(session, urlname);
	}

	@Override
	public Folder getDefaultFolder() throws MessagingException {
		return defaultFolder;
	}

	@Override
	public Folder getFolder(String name) throws MessagingException {
		return defaultFolder.getFolder(name);
	}

	@Override
	public Folder getFolder(URLName url) throws MessagingException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected boolean protocolConnect(String host, int port, String user, String password) throws MessagingException {
		defaultFolder = new DummyFolder(this, new File(session.getProperty("mail.imap.host")));
		return true;
	}

}
