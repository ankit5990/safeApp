package com.mcafee.scor.safety.common.db;

import java.io.File;

import junit.framework.TestCase;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class CommonDbTest extends TestCase{
	private static final String TEST_HIBERNATE_CFG_XML = "src\\test\\java\\testHibernate.cfg.xml";
	
	protected SessionFactory sessionFactory;
	
	@Override
	protected final void setUp() throws Exception {
		File testConfigFile = new File(TEST_HIBERNATE_CFG_XML);
		sessionFactory = new AnnotationConfiguration()
				.configure(testConfigFile)
				.buildSessionFactory();
		caseSetup();
	}
	
	
	/**
	 * override this to perform setup
	 */
	protected void caseSetup() {
		
	}

	@Override
	protected void tearDown() throws Exception {
		caseTearDown();
		if(sessionFactory!=null){
			sessionFactory.close();
		}
	}


	/**
	 * override this to perform teardown
	 */
	protected void caseTearDown() {
		// TODO Auto-generated method stub
		
	}
}
