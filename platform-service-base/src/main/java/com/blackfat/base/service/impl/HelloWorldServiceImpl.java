package com.blackfat.base.service.impl;

import org.springframework.stereotype.Service;

import com.blacktfat.base.service.IHelloWorldService;

@Service("helloWorldServiceImpl")
public class HelloWorldServiceImpl implements IHelloWorldService {

	@Override
	public void helloWorld() {
		System.out.println("helloWorld");
		
	}

}
