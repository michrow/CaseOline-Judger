package caseonline.judger.web.mapper;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import caseonline.judger.web.model.ExcellenceUsecase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:test-spring-context.xml" })
public class ExcellenceUsecaseMapperTest {
	@Ignore
	@Test
	public void testInsert(){
		ExcellenceUsecase excellenceUsecase=new ExcellenceUsecase();
		int result=excellenceUsecaseMapper.insertUsecase(excellenceUsecase);
		assertEquals(1, result);
	}
	@Ignore
	@Test
	public void select(){
		List<ExcellenceUsecase> excellenceUsecases=excellenceUsecaseMapper.selectUsecaseByConditon(null,0.2f);
		int result=excellenceUsecases.size();
		assertEquals(3, result);
	}
	//@Ignore
	@Test
	public void delete(){
		int result=excellenceUsecaseMapper.deleteUsecase(3l);
		assertEquals(1, result);
	}
	
	@Autowired
	
	ExcellenceUsecaseMapper excellenceUsecaseMapper;
}
