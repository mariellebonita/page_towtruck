package com.bonitasoft.custompage.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.bonitasoft.engine.api.ApiAccessType;
import org.bonitasoft.engine.api.CommandAPI;
import org.bonitasoft.engine.api.IdentityAPI;
import org.bonitasoft.engine.api.LoginAPI;
import org.bonitasoft.engine.api.PlatformAPI;
import org.bonitasoft.engine.api.PlatformAPIAccessor;
import org.bonitasoft.engine.api.PlatformLoginAPI;
import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.api.TenantAPIAccessor;
import org.bonitasoft.engine.session.APISession;
import org.bonitasoft.engine.session.PlatformSession;
import org.bonitasoft.engine.util.APITypeManager;
import org.junit.Test;

import com.bonitasoft.custompage.towtruck.Timer;
import com.bonitasoft.custompage.towtruck.Timer.MethodResetTimer;

public class JunitTimerTest {

		@Test
		public void test() {
				Map<String, String> map = new HashMap<String, String>();
				map.put("server.url", "http://localhost:8080");
				map.put("application.name", "bonita");
				APITypeManager.setAPITypeAndParams(ApiAccessType.HTTP, map);

				// Set the username and password
				// final String username = "helen.kelly";
				final String username = "walter.bates";
				final String password = "bpm";

				// get the LoginAPI using the TenantAPIAccessor
				LoginAPI loginAPI;
				try {
						loginAPI = TenantAPIAccessor.getLoginAPI();
						// log in to the tenant to create a session
						APISession session = loginAPI.login(username, password);
						ProcessAPI processAPI = TenantAPIAccessor.getProcessAPI(session);
						IdentityAPI identityAPI = TenantAPIAccessor.getIdentityAPI(session);
						CommandAPI commandAPI = TenantAPIAccessor.getCommandAPI(session);
						
						PlatformLoginAPI platformLoginAPI = PlatformAPIAccessor.getPlatformLoginAPI();

						// log in to the platform to create a section
						PlatformSession platformSession = platformLoginAPI.login("install", "install");

						// get the PlatformAPI bound to the session created previously.
						 PlatformAPI platformAPI = PlatformAPIAccessor.getPlatformAPI(platformSession);
						 
						
						File commandFile = new File("target/CustomPageTowTruck-1.0.1.jar");
						FileInputStream fis;

						fis = new FileInputStream(commandFile);
						HashMap<String, Object> timers = Timer.getMissingTimers(true, processAPI);
						System.out.println("GetTimer "+timers);
						
						HashMap<String, Object> createTimers= Timer.createMissingTimers(MethodResetTimer.Handle, fis, processAPI, commandAPI, platformAPI);
						System.out.println("GetTimer "+createTimers);
				}
				catch(Exception e)
				{
						System.out.println("Error during test missing timer: "+e.toString());
				}
		}

}
