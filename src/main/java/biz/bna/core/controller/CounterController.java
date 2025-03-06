package biz.bna.core.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import biz.bna.core.dto.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;

@RestController
public class CounterController {

    @Value("${spring.redis.host:localhost}")
    String host;

    @Value("${spring.redis.port:6379}")
    Integer port;

    private final String keySetPlayers = "players";

    @RequestMapping(value = "/counter", method = RequestMethod.GET)
    public String counter() {
        try(Jedis jedis = new Jedis("localhost", 6379)) {
            String counterValue = jedis.get("counter");
            if (counterValue == null) {
                counterValue = "0";
            } else {
                counterValue = String.valueOf(Integer.parseInt(counterValue) + 1);
            }
            jedis.set("counter", counterValue);
            return String.format("{\"success\":%s}", counterValue);
        }
    }

    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public String players() {
        try(Jedis jedis = new Jedis("localhost", 6379)) {
            List<Tuple> players = jedis.zrandmemberWithScores(keySetPlayers, 10);
            return String.format(new ObjectMapper().writeValueAsString(players));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/players/score/set", method = RequestMethod.POST)
    public String playersSetScore(@RequestBody Player player) {
        try(Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.zadd(keySetPlayers, player.getScore(), player.getName());
            return "{\"status\":\"success\"}";
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
