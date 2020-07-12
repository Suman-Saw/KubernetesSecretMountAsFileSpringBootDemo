package com.napster.secretdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@RestController
@SpringBootApplication
public class SecretDemoApplication {

	@Value("${spring.username}")
	private String username;

	@Value("${spring.password}")
	private String password;

	public static void main(String[] args) {
		SpringApplication.run(SecretDemoApplication.class, args);
	}

	@GetMapping(path = "/get")
	public String getDetails() throws IOException, URISyntaxException {
		final URI githubUserSecretsURI = ResourceUtils.getURL("/etc/foo/username").toURI();
		final URI githubUserTokenSecretsURI = ResourceUtils.getURL("/etc/foo/password").toURI();

		final byte[] encodedGithubUser = Files.readAllBytes(Paths.get(githubUserSecretsURI));
		final byte[] encodedGithubToken = Files.readAllBytes(Paths.get(githubUserTokenSecretsURI));
		String githubUser = sanitize(encodedGithubUser);
		String githubUserToken = sanitize(encodedGithubToken);
		return "Username : " + githubUser + " Password : "+ githubUserToken;
	}

	@GetMapping(path = "/test")
	public String test(){
		return "Username : " + username + " Password : "+ password;
	}

	private String sanitize(byte[] strBytes) {
		return new String(strBytes)
				.replace("\r", "")
				.replace("\n", "");
	}

	//@PostConstruct
	public void saveParamChanges() {
		try {
			System.out.println("Hello");
			// create and set properties into properties object
			Properties props = new Properties();
			props.setProperty("spring.username", "test123");
			props.setProperty("spring.password", "hellopassword1234");
			// get or create the file
			File f = new File("application.properties");
			OutputStream out = new FileOutputStream( f );
			// write into it
			DefaultPropertiesPersister p = new DefaultPropertiesPersister();
			p.store(props, out, "Header COmment");
		} catch (Exception e ) {
			e.printStackTrace();
		}
	}

}

