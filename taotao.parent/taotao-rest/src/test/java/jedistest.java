import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class jedistest {


    @Test
    public void jedissingle(){
        //创建Jedis对象
        Jedis jedis = new Jedis("localhost",6379);
        //调用对象的方法
        jedis.set("key1","jedis test");
        String s = jedis.get("key1");
        System.out.println(s);
        //关闭jedis
        jedis.close();
    }

    /**
     * 使用连接池
     */
    @Test
    public void jedispool(){
        //创建连接池
        JedisPool jedisPool = new JedisPool("localhost",6379);
        //从连接池中获得Jedis对象
        Jedis jedis = jedisPool.getResource();
        String s = jedis.get("key1");
        System.out.println(s);
        //关闭jedis对象
        jedis.close();
        jedisPool.close();
    }

    @Test
    public void testSpringJedis(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisPool pool = (JedisPool)applicationContext.getBean("redisClient");
        Jedis jedis = pool.getResource();
        String s = jedis.get("key1");
        System.out.println(s);
        jedis.close();
        pool.close();
    }
}
