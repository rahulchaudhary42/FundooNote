package com.bridgelabz.fundoo.util;

import org.springframework.stereotype.Component;

@Component
public class MessageListnerImpl implements MessageListener {

	@Override
	public void onMessage(byte[] note) {
	System.out.println(new String(note));
		
	}

}
