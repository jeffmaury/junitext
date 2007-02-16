package org.junitext.runners.parameters.factory;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.*;
import org.junitext.runners.Robot;
import org.junitext.runners.parameters.factory.DigesterParameterFactory;

public class DigesterParameterFactoryTest {
	
	@Test
	public void parseRobotWithFriend() throws Exception {
		URL robotWithFriendURL = getClass().getResource("./RobotWithFriend.xml");
		File robotWithFriendFile = new File(new URI(robotWithFriendURL.toString()));
		DigesterParameterFactory testFactory = new DigesterParameterFactory();
		
		//Create the expected parameter set
		Robot expectedRobot = new Robot();
		expectedRobot.setName("Daneel Olivaw");
		expectedRobot.setId(134);
		expectedRobot.setManufacturer("Han Fastolfe");
		expectedRobot.setModel("X24R");
		
		Robot friend = new Robot();
		friend.setName("Johnny 5");
		friend.setId(5);
		friend.setManufacturer("Nova Laboratories");
		friend.setModel("SAINT");
		
		List<Robot> expectedFriends = new ArrayList<Robot>();
		expectedFriends.add(friend);
		expectedRobot.setFriends(expectedFriends);
		
		
		List<Object[]> parmSets = testFactory.createParameters(null, robotWithFriendFile);
		
		//Verify that the correct object structure was created.
		assertEquals("The correct number of paramter sets were not created.", 1, parmSets.size());
		
		Object[] parmSet = parmSets.get(0);
		assertEquals("The correct number of beans were not created.", 2, parmSet.length);
		
		assertTrue("The first bean is not a Robot.", parmSet[0] instanceof Robot);
		assertTrue("The second bean is not a Robot.", parmSet[1] instanceof Robot);
		
		//Verify the first robot
		assertEquals("The first robot does not equal the expected robot.", expectedRobot.toString(), parmSet[0].toString());
		assertEquals("The second robot does not equal the expected robot.", expectedRobot.toString(), parmSet[1].toString());
	}

}
