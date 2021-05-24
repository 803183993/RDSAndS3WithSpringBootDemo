 import org.jmock.Expectations;
 import org.jmock.integration.junit4.JUnitRuleMockery;
 import org.jmock.lib.concurrent.Synchroniser;
 import org.jmock.lib.legacy.ClassImposteriser;
 import org.junit.Before;
 import org.junit.Rule;
 import org.junit.Test;

 import static org.hamcrest.MatcherAssert.assertThat;
 import static org.hamcrest.Matchers.*;
 import static org.junit.Assert.assertFalse;

   @Rule
    public JUnitRuleMockery mockery = new JUnitRuleMockery()
    {{
        setImposteriser(ClassImposteriser.INSTANCE);
        setThreadingPolicy(new Synchroniser());
    }};

            mockery.checking(new Expectations()
            {
                {

                }
            });