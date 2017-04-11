package com.amazonaws.demos.polly;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

public class Execution {

	public static void main(String[] args) throws Exception{
		
		PollyDemo pollyDemo = new PollyDemo(Region.getRegion(Regions.US_EAST_1));
		pollyDemo.initialize("Hi Navdeep ! How are you doing today?");

	}

}
