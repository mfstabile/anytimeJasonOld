package jason.tests;

import jason.asunit.TestAgent;

import org.junit.Before;
import org.junit.Test;

public class TestVarInContext {

    TestAgent ag; 

    // initialisation of the agent test
    @Before
    public void setupAg() {
        ag = new TestAgent();
        ag.setDebugMode(true);
        
        // defines the agent's AgentSpeak code
        ag.parseAScode(
                "b1[b]. b2[c]. b3[d]. b4[a,d]. step(5). b(8)[step(3)]. b(9)[step(0)]."+
                "+!test1 : P[e] | P[c] <- jason.asunit.print(P). " + 
                
                "+!test2 : P[e] & P[c] <- jason.asunit.print(P). " + 
                "-!test2               <- jason.asunit.print(\"error\"). " + 
                
                "+!test3 : P[a] & P[d] <- jason.asunit.print(P). " +
                
                "is_old(P) :- (not P | (step(Current) & P[step(N)] & Current - N > 3)). " +
                "+!test4(X) : is_old(X) <- jason.asunit.print(1). "+
                "+!test4(_)             <- jason.asunit.print(2). " 
        );
    }
    
    @Test(timeout=2000)
    public void testContext() {
        ag.addGoal("test1");
        ag.assertPrint("b2", 5);
        ag.addGoal("test1"); // test twice to see if nothing was changed
        ag.assertPrint("b2", 5);
        
        ag.addGoal("test2");
        ag.assertPrint("error", 5);

        ag.addGoal("test3");
        ag.assertPrint("b4", 5);
        
        ag.addGoal("test4(b(8))");
        ag.assertPrint("2", 5);

        ag.addGoal("test4(b(9))");
        ag.assertPrint("1", 5);
    }

}
