
package junit;

import org.junit.Before;
import org.junit.Test;


/**
 * Generated Junit Test Case for: JdbcRestPasswordService class.
 * 
 */
public class JdbcRestPasswordServiceTest {

    private JdbcRestPasswordService jdbcRestPasswordService;

    @Before
    public void setUp() {
        jdbcRestPasswordService = new JdbcRestPasswordService();
    }

    @Test
    public void testsendResetMail() {
        jdbcRestPasswordService.sendResetMail(">S!");
        jdbcRestPasswordService.sendResetMail(">S!?");
        jdbcRestPasswordService.sendResetMail("9~E6&>");
    }

    @Test
    public void testisValidResetToken() {
        boolean returnValue = jdbcRestPasswordService.isValidResetToken("2U0");
        boolean returnValue = jdbcRestPasswordService.isValidResetToken("2U0v");
        boolean returnValue = jdbcRestPasswordService.isValidResetToken("dgR@^g");
    }

    @Test
    public void testchangePassword() {
        jdbcRestPasswordService.changePassword("cl6" , "wB{");
        jdbcRestPasswordService.changePassword("cl6#" , "wB{T");
        jdbcRestPasswordService.changePassword("Jk{XGm" , "B_Sv<t");
    }

}
