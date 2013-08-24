package com.broadsoft.xmdownload.rsservice;

import java.util.concurrent.atomic.AtomicInteger;

public class RsServiceOnMeetingInfoCounter {

	private RsServiceOnMeetingInfoCounter() {
		counter.set(0);
	}

	private static RsServiceOnMeetingInfoCounter instance = new RsServiceOnMeetingInfoCounter();

	public static RsServiceOnMeetingInfoCounter getInstance() {
		return instance;
	}

	private AtomicInteger counter = new AtomicInteger();

	public AtomicInteger getCounter() {
		return counter;
	}

}
