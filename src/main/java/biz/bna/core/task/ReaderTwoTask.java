package biz.bna.core.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class ReaderTwoTask {

    @Scheduled(fixedDelayString = "PT5S")
    public void process(){
        try(Jedis jedis = new Jedis("localhost", 6379)) {
            List<String> playerQueue = jedis.brpop(10, "player-requests");
            System.out.println("Reader 2: " + new ObjectMapper().writeValueAsString(playerQueue));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
