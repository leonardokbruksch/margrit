
package junit;

import com.test.leo.ResetPasswordController;
import org.junit.Before;
import org.junit.Test;


/**
 * Generated Junit Test Case for: ResetPasswordController class.
 * 
 */
public class ResetPasswordControllerTest {

    private ResetPasswordController resetPasswordController;

    @Before
    public void setUp() {
        resetPasswordController = new ResetPasswordController();
    }

    @Test
    public void testsendResetMail() {
        String returnValue = resetPasswordController.sendResetMail("[uf");
        String returnValue = resetPasswordController.sendResetMail("[ufa");
        String returnValue = resetPasswordController.sendResetMail("@_Ly*,");
    }

    @Test
    public void testvalidateResetToken() {
        String returnValue = resetPasswordController.validateResetToken(":N'");
        String returnValue = resetPasswordController.validateResetToken(":N'Z");
        String returnValue = resetPasswordController.validateResetToken("L9{)%u");
    }

    @Test
    public void testchangePassword() {
        String returnValue = resetPasswordController.changePassword("yT:" , "P"L");
        String returnValue = resetPasswordController.changePassword("yT:6" , "P"L%");
        String returnValue = resetPasswordController.changePassword("NXmW|[" , "BjQDNC");
    }

}
