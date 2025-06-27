package io.bmeurant.bookordermanager;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
public class RunCucumberTests {
    // This class is used by JUnit to find and run Cucumber feature files.
}
