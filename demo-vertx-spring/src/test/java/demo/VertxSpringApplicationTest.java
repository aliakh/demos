package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class VertxSpringApplicationTest {

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void test() throws InterruptedException {
        ResponseEntity<Article[]> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/articles", Article[].class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}


