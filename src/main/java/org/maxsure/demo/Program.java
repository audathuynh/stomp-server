package org.maxsure.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class provides an entry point to execute when running the application.
 * 
 * @author Dat Huynh
 * @since 1.0
 */
@SpringBootApplication
public class Program {

    /**
     * The main method to execute.
     *
     * @param args the arguments of the application
     */
    public static void main(String[] args) {
        SpringApplication.run(Program.class, args);
    }

}
