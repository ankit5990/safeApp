package com.mcafee.scor.safety.serverTask;

import org.apache.log4j.Logger;


public abstract class CommonServerTask implements Runnable{

	private static final Logger logger = Logger.getLogger(CommonServerTask.class);
	
	private long delay;
	
	public CommonServerTask(long delay) {
		this.delay = delay;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		while(true){
			try{
				Thread.sleep(delay);
				runTask();
			}catch(Throwable t){
				logger.error("error occured while running server task, ignoring error",t);
			}
		}
	}

	protected abstract void runTask();
}
