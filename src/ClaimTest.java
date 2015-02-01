import java.util.Date;

import junit.framework.TestCase;

import com.school.cmput301.Models.Claim;
import com.school.cmput301.Models.Expense;


public class ClaimTest extends TestCase {
	Claim claim = null;
	
	
	public void testClaim(){
		assertTrue("Claim is indeed empty" ,claim == null);
		claim = new Claim("newClaim", "category", "desc", "test date","test date 2", new Date());
		Expense exp = new Expense(new Date(), "string", "aa", "cc",(float) 23.93, "CAD");
		claim.addExpense(exp);
		assertTrue("Claim is added", claim.getExpenseList().contains(exp));
		claim.removeExpense(exp);
		assertFalse("Claim is removed", claim.getExpenseList().contains(exp));
		
	}
	
	
	
}
