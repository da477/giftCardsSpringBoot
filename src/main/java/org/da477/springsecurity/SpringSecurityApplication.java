package org.da477.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);

//        long min = System.currentTimeMillis();
//        long max = (long) Integer.MAX_VALUE * (long) 10;
//        String result = "";
//        ThreadLocalRandom random = ThreadLocalRandom.current();
//        for (int i = 0; i < 10; i++) {
//            result += String.valueOf(random.nextLong(start + 1, max)) + "\n";
//        }
//        System.out.println(result);
    }

}
