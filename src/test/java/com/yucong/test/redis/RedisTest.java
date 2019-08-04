package com.yucong.test.redis;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yucong.pojo.Users;

/**
 * Redis测试
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:redis/applicationContext.xml")
public class RedisTest {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 添加键值对 - 字符串类型
	 * redisTemplate中，提供了若干的Operations，每一个Operations对应一个Redis中的数据类型。
	 * 如：ValueOperations - 字符串类型。  HashOperations - hash类型。
	 */
	@Test
	public void test1(){
		ValueOperations<String, Object> ops = this.redisTemplate.opsForValue();
		ops.set("key", "test");
	}
	
	/**
	 * 获取redis中的数据
	 */
	@Test
	public void test2(){
		String str = (String)this.redisTemplate.opsForValue().get("key");
		System.out.println(str);
		
		// 新增/更新数据，并设置有效期。
		// this.redisTemplate.opsForValue().set("key", "test", 10L, TimeUnit.SECONDS);
		
		// 设置数据有效期。
		this.redisTemplate.expire("key", 10, TimeUnit.SECONDS);
	}
	
	/**
	 * 添加Users
	 * 将java对象，存储到redis中，可以使用两种存储方式。
	 * 1 - 使用JDK提供的Serializable，实现对象序列化。
	 *  改变valueSerilizer的序列化器。JdkSerializationRedisSerializer
	 *  用JDKSerializationRedisSerializer做序列化，有不好的地方。
	 *  JDKSerialization序列化结果太长。占用更多的字节空间。进行序列化和反序列化的效率较低。
	 */
	@Test
	public void test3(){
		Users users = new Users();
		users.setUserage(30);
		users.setUserid(1);
		users.setUsername("张三");
		//更换序列化器，通过API来更换序列化器，好处是有针对性。需要什么序列化器就提供什么序列化器。默认都使用StringRedisSerializer
		this.redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		this.redisTemplate.opsForValue().set("users", users);
	}
	
	/**
	 * 获取Users
	 * 
	 */
	@Test
	public void test4(){
		//更换序列化器
		this.redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		Users users = (Users)this.redisTemplate.opsForValue().get("users");
		System.out.println(users);
	}

	/**
	 * 添加Users JSON格式
	 * JSON数据，人机皆可使用。
	 * JSON数据占用的存储空间小，且和java对象的转换效率高。
	 * 缺陷 - 不能描述双向关系。如果使用json来描述双向关联关系，则会出现无限嵌套，是一个死循环。会有内存溢出错误, OutOfMemeryError
	 * class A{ B b; }
	 * class B{ A a; }
	 * A a = new A();
	 * json - { .... , b : { ... , a : { ...., b : { ...., a : { }}}}}
	 */
	@Test
	public void test5(){
		Users users = new Users();
		users.setUserage(30);
		users.setUserid(2);
		users.setUsername("李四");
		
		this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Users.class));
		this.redisTemplate.opsForValue().set("usersjson", users);
	}
	
	/**
	 * 获取Uesrs JSON格式
	 */
	@Test
	public void test6(){
		this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Users.class));
		Users users = (Users)this.redisTemplate.opsForValue().get("usersjson");
		System.out.println(users);
	}
	
	/**
	 * 常用API
	 */
	@Test
	public void test7(){
		// 设置有效期
		this.redisTemplate.expire("usersjson", 300, TimeUnit.SECONDS);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// 查询有效期
		long expire = this.redisTemplate.getExpire("usersjson");
		System.out.println("expire = " + expire);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.redisTemplate.persist("usersjson");
	}
}





















