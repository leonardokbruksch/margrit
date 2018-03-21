package com.app.margrit.randomizer;

import com.app.margrit.services.RandomizeParametersService;
import de.svenjacobs.loremipsum.LoremIpsum;
import org.springframework.beans.factory.annotation.Autowired;

public class RandomizeParametersServiceTest {

    @Autowired
    private RandomizeParametersService randomizeParametersService;

    public static void main(String[] args) {

        for (int i = 0; i < 50; i++){
            RandomizeParametersService service = new RandomizeParametersService();

            System.out.print(service.getRandomString() + "\n");
        }
    }
}
