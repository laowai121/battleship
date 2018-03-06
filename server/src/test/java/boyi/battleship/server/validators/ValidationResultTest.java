package boyi.battleship.server.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ValidationResultTest {
    private static ValidationResult test1 = new ValidationResult(true, "asd");
    private static ValidationResult test2 = new ValidationResult(false, "asd");
    private static ValidationResult test3 = new ValidationResult(true);
    private static ValidationResult test4 = new ValidationResult(false);

    @Test
    public void test1() {
        assertThat(test1.isValid()).isEqualTo(true);
        assertThat(test1.getMessage()).isEqualTo("asd");
    }

    @Test
    public void test2() {
        assertThat(test2.isValid()).isEqualTo(false);
        assertThat(test2.getMessage()).isEqualTo("asd");
    }

    @Test
    public void test3() {
        assertThat(test3.isValid()).isEqualTo(true);
        assertThat(test3.getMessage()).isEqualTo(ValidationResult.VALID);
    }

    @Test
    public void test4() {
        assertThat(test4.isValid()).isEqualTo(false);
        assertThat(test4.getMessage()).isEqualTo(ValidationResult.VALID);
    }
}
