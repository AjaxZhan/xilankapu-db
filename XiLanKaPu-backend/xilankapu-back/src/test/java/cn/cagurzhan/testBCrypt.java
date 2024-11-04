package cn.cagurzhan;

import cn.dev33.satoken.secure.BCrypt;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Cagur Zhan
 */
@SpringBootTest
public class testBCrypt {

    @Test
    public void testBC(){
        String admin123 = BCrypt.hashpw("admin123");
        System.out.println(admin123);
    }
}
