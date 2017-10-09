package org.data.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/** 
 *这里需要注意的是：
 * <p>虽然<code>@TransactionConfiguration</code>的rollback会成功，但是数据库总的主键还是会自动往上加的; 
 * <br>如果想在rollback的时候，主键不自增，比较好的方式是用code生成主键
 * 
 * @author gushu
 * @date 2017/09/29
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
        "classpath:datasource.xml",
        "classpath:mybatis-spring.xml"
})
@Transactional
@TransactionConfiguration(defaultRollback=false)
public abstract class BaseDAOTest{

	protected abstract <T> T generateDO();
}
