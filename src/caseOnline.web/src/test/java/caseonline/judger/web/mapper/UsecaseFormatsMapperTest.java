package caseonline.judger.web.mapper;
import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import caseonline.judger.web.model.UsecaseFormat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:test-spring-context.xml" })
public class UsecaseFormatsMapperTest {
	
	@Ignore
	@Test
    public void testCreateUsercaseFormat(){
		UsecaseFormat usecaseFormats=new UsecaseFormat("jsonString");
		int result=usecaseFormatMapper.createUsecaseFormat(usecaseFormats);
		assertEquals(1, result);
	}
	@Ignore
	@Test
    public void testUpdateUsercaseFormat(){
		UsecaseFormat usecaseFormats=new UsecaseFormat(1,"mapString");
		int result=usecaseFormatMapper.updateUsecaseFormat(usecaseFormats);
		assertEquals(1, result);
	}
	//@Ignore
	@Test
    public void testDeleteUsercaseFormat(){
		int result=usecaseFormatMapper.deleteUsecaseFormat(1);
		assertEquals(1, result);
	}
	@Autowired
	UsecaseFormatMapper usecaseFormatMapper;
}
