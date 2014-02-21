package test;

import jason.RevisionFailedException;
import jason.asSemantics.Agent;
import jason.asSemantics.Intention;
import jason.asSemantics.Unifier;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.Atom;
import jason.asSyntax.ListTermImpl;
import jason.asSyntax.Literal;
import jason.asSyntax.LiteralImpl;
import jason.asSyntax.LogExpr;
import jason.asSyntax.LogicalFormula;
import jason.asSyntax.Pred;
import jason.asSyntax.PredicateIndicator;
import jason.asSyntax.Structure;
import jason.asSyntax.Term;
import jason.asSyntax.VarTerm;
import jason.asSyntax.parser.ParseException;
import jason.bb.BeliefBase;
import jason.bb.ChainBB;
import jason.bb.DefaultBeliefBase;
import jason.bb.IndexedBB;
import jason.bb.JDBCPersistentBB;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

/** JUnit test case for syntax package */
public class _My_BeliefBaseTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }
  
    public void testIndexed() {
    	BeliefBase bb = new IndexedBB();
    	bb.clear();
    	bb.add(Literal.parseLiteral("book_author(2,book_author)"));
    	bb.add(Literal.parseLiteral("author(2,author)"));
    	bb.add(Literal.parseLiteral("test(2,testtable)"));
    	test(bb);
    }
    
    public void testDefault() {
    	BeliefBase bb = new DefaultBeliefBase();
    	bb.clear();
    	bb.add(Literal.parseLiteral("book_author(2,book_author)"));
    	bb.add(Literal.parseLiteral("author(2,author)"));
    	bb.add(Literal.parseLiteral("test(2,testtable)"));
    	test(bb);
    }
    
    public void testChain() {
    	BeliefBase bb = new ChainBB();
    	bb.clear();
    	//bb.add(Literal.parseLiteral("book(5,book)"));
    	bb.add(Literal.parseLiteral("book_author(2,book_author)"));
    	bb.add(Literal.parseLiteral("author(2,author)"));
    	bb.add(Literal.parseLiteral("test(2,testtable)"));
    	test(bb);
    }
    
    public void testJDBCBB() {
    	BeliefBase bb = new JDBCPersistentBB();
    	bb.init(null, new String[] {
                "org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:bookstore",
                "sa",
                "",
                "[book(5,book),book_author(2,book_author),author(2,author),test(2,testtable)]"
                });
        
    	test(bb);
    }
    
    public void test(BeliefBase bb) {
      
        bb.abolish(new PredicateIndicator("book",5));
        bb.abolish(new PredicateIndicator("author",2));
        bb.abolish(new PredicateIndicator("book_author",2));
        bb.abolish(new PredicateIndicator("test",2));
        assertEquals(bb.size(),0);

        assertTrue(bb.add(Literal.parseLiteral("test(30)")));
        assertEquals(bb.size(),1);
        Literal l;
        
        // add authors
        assertTrue(bb.add(Literal.parseLiteral("author(1,\"Rafael H. Bordini\")")));
        assertFalse(bb.add(Literal.parseLiteral("author(1,\"Rafael H. Bordini\")")));
        assertTrue(bb.add(Literal.parseLiteral("author(2,\"Mehdi Dastani\")")));
        assertTrue(bb.add(Literal.parseLiteral("author(3,\"Jurgen Dix\")")));
        assertTrue(bb.add(Literal.parseLiteral("author(4,\"Amal El Fallah Seghrouchni\")")));
        assertTrue(bb.add(Literal.parseLiteral("author(5,\"Michael Wooldridge\")")));
        assertEquals(bb.size(),6);
        assertEquals(iteratorSize(bb.iterator()),bb.size());
        
        // add books
        l = Literal.parseLiteral("book(1,\"Multi-Agent Programming : Languages, Platforms and Applications\", \"Springer\", 2005, \"0387245685\")");
        assertTrue(bb.add(l));
        assertFalse(bb.add(l));
        // add book authors
        assertTrue(bb.add(Literal.parseLiteral("book_author(1,1)")));
        assertTrue(bb.add(Literal.parseLiteral("book_author(1,2)")));
        assertTrue(bb.add(Literal.parseLiteral("book_author(1,3)")));
        assertTrue(bb.add(Literal.parseLiteral("book_author(1,4)")));
        assertEquals(bb.size(),11);

        // add another book
        l = Literal.parseLiteral("book(2,\"Another Multi-Agent Programming : Languages, Platforms and Applications\", \"Springer\", 2005, \"0387245685\")");
        assertTrue(bb.add(l));

        l = Literal.parseLiteral("book(3,\"An introduction to multiagent systems\", \"John Wiley & Sons\", 2002, \"\")");
        assertTrue(bb.add(l));
        assertTrue(bb.add(Literal.parseLiteral("book_author(3,5)")));
        assertEquals(bb.size(),14);

        // test with legacy table
//        ((JDBCPersistentBB)bb).test();
        assertTrue(bb.add(Literal.parseLiteral("publisher(1,\"Springer\")")));
        assertTrue(bb.add(Literal.parseLiteral("publisher(2,\"MIT Press\")")));

        // test add two records
        assertEquals(bb.size(),16);
        assertTrue(bb.add(Literal.parseLiteral("publisher(10,\"Prentice Hall\")")));
        assertFalse(bb.add(Literal.parseLiteral("publisher(10,\"Prentice Hall\")")));
        assertEquals(bb.size(),17);

        

        // test negated
        int size = bb.size();
        l = Literal.parseLiteral("test(a,b)");
        assertTrue(bb.add(l));
        l = Literal.parseLiteral("~test(a,b)");
        assertTrue(bb.add(l));
        assertEquals(bb.size(),size+2);
        
        // test get all
        assertEquals(iteratorSize(bb.iterator()),size+2);

        //for (Literal l2: bb) {
        //  System.out.println(l2);
        //}
        
        bb.stop();
    }
 
    
    private int iteratorSize(@SuppressWarnings("rawtypes") Iterator i) {
        int c = 0;
        while (i.hasNext()) {
            i.next();
            c++;
        }
        return c;
    }
}
