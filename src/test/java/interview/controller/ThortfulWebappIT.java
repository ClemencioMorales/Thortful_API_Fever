package interview.controller;

import interview.ThortfulApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for Thortful REST Service
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = {ThortfulApplication.class})
public class ThortfulWebappIT {

	@Mock
	private HttpSession session;
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	private ThortfulController thortfulController;
	
	/**
	 * configure the mock Mvc with our web application context
	 */
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		thortfulController = new ThortfulController();
	}
	
	/**
	 * Test logging in.  We expect an http 200 response
	 * with the value "Welcome <username>!" in the response body
	 */
	@Test
	public void testLoginSuccess() throws Exception {
		MvcResult result = mockMvc.perform(get("/login/User/P4ssw0rd"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		String message = result.getResponse().getContentAsString();
		assertThat(message, containsString("Welcome User!"));
	}

	/**
	 * Test logging out without logging in before.  We expect an http 200 response
	 * with the value "You have to initiate your session for logging out..." in the response body
	 */
	@Test
	public void testLogoutSuccess() throws Exception {
		MvcResult result = mockMvc.perform(get("/logout"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		String message = result.getResponse().getContentAsString();
		assertEquals(message, "You have to initiate your session for logging out...");
	}

	/**
	 * Combined Text.  We expect the user to log in receiving an http 200 response
	 * with the login message in the response body and then the session is closed
	 * receiving the farewell message
	 */
	@Test
	public void testLoginWithLogoutSuccess() throws Exception {
		MvcResult result = mockMvc.perform(get("/login/User/P4ssw0rd"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		String message = result.getResponse().getContentAsString();
		assertThat(message, containsString("Welcome User!"));

		Mockito.doReturn(true).when(session).getAttribute(ThortfulController.IS_USER_LOGGED_IN);
		thortfulController.setHttpSession(session);
		message = thortfulController.logout();

		assertEquals(message, "You have closed your session, bye!");
	}
	
	/**
	 * Test not adding the url path parameters for the input.  We expect
	 * to return an http 404 not found response status in this case
	 */
	@Test
	public void testWrongURL() throws Exception {
		mockMvc.perform(get("/login"))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
}
