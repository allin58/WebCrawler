package com.web.crawler;

import com.web.crawler.util.WebParser;
import com.web.crawler.util.WordCounter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WebParserTest {

    @Test
    void parse() {

        String text = "The black currawong (Strepera fuliginosa), also known as the black jay, is a large passerine" +
                " bird endemic to Tasmania and nearby islands in the Bass Strait. One of three currawong species, it" +
                " is closely related to the butcherbirds and Australian magpie in the family Artamidae. It is a large" +
                " crow-like bird, around 50 cm (20 in) long, with yellow irises, a heavy bill, and black plumage with" +
                " white wing patches. The sexes are similar in appearance. Three subspecies are recognised, one of" +
                " which, S. f. colei of King Island, is vulnerable to extinction. The black currawong is generally" +
                " sedentary, although populations at higher altitudes relocate to lower areas during the cooler" +
                " months. The habitat includes densely forested areas as well as alpine heathland. It is rare below " +
                "altitudes of 200 m (660 ft). Its omnivore diet includes a variety of berries, invertebrates, and" +
                " small vertebrates. Less arboreal than the pied currawong, the black currawong spends more time " +
                "foraging on the ground. It roosts and breeds in trees.";


        assertEquals(9, WordCounter.count(text,"is"));

    }



}
