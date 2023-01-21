
package junit;

import org.junit.Before;
import org.junit.Test;


/**
 * Generated Junit Test Case for: JdbcAccountRepository class.
 * 
 */
public class JdbcAccountRepositoryTest {

    private JdbcAccountRepository jdbcAccountRepository;

    @Before
    public void setUp() {
        jdbcAccountRepository = new JdbcAccountRepository();
    }

    @Test
    public void testfindBySignin() {
        Account returnValue = jdbcAccountRepository.findBySignin("qK*");
        Account returnValue = jdbcAccountRepository.findBySignin("qK*E");
        Account returnValue = jdbcAccountRepository.findBySignin("W8v3ek");
    }

    @Test
    public void testchangePassword() {
        jdbcAccountRepository.changePassword(null , "L^p");
        jdbcAccountRepository.changePassword(null , "L^pW");
        jdbcAccountRepository.changePassword(null , "0m5@K2");
    }

}
