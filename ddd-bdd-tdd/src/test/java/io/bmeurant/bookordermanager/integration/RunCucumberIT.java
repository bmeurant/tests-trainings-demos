package io.bmeurant.bookordermanager.integration;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class RunCucumberIT {
    // This class is used by JUnit to find and run Cucumber feature files.
}
