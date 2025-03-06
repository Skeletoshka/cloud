package biz.bna.core.task;

import biz.bna.core.dto.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WriterTask {

    AtomicInteger playerIndex = new AtomicInteger(0);

    @Scheduled(fixedDelayString = "PT1S")
    public void process(){
        Player player = new Player();
        player.setName("player_" + playerIndex.getAndIncrement());
        player.setScore(Math.random() * 100);
        try(Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.lpush("player-requests", new ObjectMapper().writeValueAsString(player));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
