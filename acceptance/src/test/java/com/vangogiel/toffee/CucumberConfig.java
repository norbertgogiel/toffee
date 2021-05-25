package com.vangogiel.toffee;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features= "src/test/resources",
        plugin = "pretty",
        glue = "com/vangogiel/toffee"
)
public class CucumberConfig {
}
