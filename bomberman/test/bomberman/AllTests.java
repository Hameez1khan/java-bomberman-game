package bomberman;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ Junit1.class, Junit2.class, junit3.class, Junit4.class, Junit5.class, Junit6.class ,Junit7.class })
public class AllTests {

}
