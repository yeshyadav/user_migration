package com.digilytics.user_migration;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.digilytics.user_migration.controller.UserController;
import com.digilytics.user_migration.service.UserService;

import junit.framework.Assert;

//@RunWith(SpringRunner.class)
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserMigrationApplicationTests {

	@Value("${server.port}")
    int serverPort;

	private InputStream is;
    private MockMvc mockMvc;
	
	@Mock
	UserService userService;
    @Spy
    @InjectMocks
    private UserController controller = new UserController();
    
    @Before
    public void init() {
    	mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        is = controller.getClass().getClassLoader().getResourceAsStream("error.csv");
    }
    //Download api test case 
	 @Test
	    public void testDownloadFile() throws Exception {
	       // Mockito.when(userService.downloadCsvFile()).thenReturn(new ArrayList()<>());
	        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/User/download/error.csv").contentType(MediaType.APPLICATION_OCTET_STREAM)).andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
	        Assert.assertEquals(200, result.getResponse().getStatus());
	    }
	//
	 @Test
	    public void testUploadFile() throws Exception {
	        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "error.csv", "multipart/form-data", is);
	        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/User/register").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
	                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
	        Assert.assertEquals(200, result.getResponse().getStatus());
	        Assert.assertNotNull(result.getResponse().getContentAsString());
	    }
	
}
