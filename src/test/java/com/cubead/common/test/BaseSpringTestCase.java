package com.cubead.common.test;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext-base.xml"})
public class BaseSpringTestCase {
	protected Logger log = Logger.getLogger(this.getClass());
}
