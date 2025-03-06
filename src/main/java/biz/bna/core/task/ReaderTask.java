package biz.bna.core.task;

import biz.bna.core.dto.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ReaderTask {

    @Scheduled(fixedDelayString = "PT5S")
    public void process(){
        try(Jedis jedis = new Jedis("localhost", 6379)) {
            List<String> playerQueue = jedis.brpop(1, "player-requests");
            System.out.println("Reader 1: " + new ObjectMapper().writeValueAsString(playerQueue));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
