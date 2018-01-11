package arkanoidTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import arkanoid.BrickTest;
import arkanoid.BallTest;

@RunWith(Suite.class)
@SuiteClasses({ BrickTest.class, 
        BallTest.class })
public class ArkanoidAllTests {

}