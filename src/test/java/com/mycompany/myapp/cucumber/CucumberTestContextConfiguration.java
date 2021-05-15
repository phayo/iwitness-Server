package com.mycompany.myapp.cucumber;

import com.mycompany.myapp.IwitnessApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = IwitnessApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
