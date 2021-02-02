import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.vijayian.Application;
import org.vijayian.service.HelloService;

/**
 * test
 *
 * @author ViJay
 * @date 2021-01-30
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class TestSpringBoot {

    @Autowired
    HelloService helloService;

    @Test
    public void contextLoads() {
        String hello = helloService.hello();
        Assert.assertThat(hello, Matchers.is("hello"));
    }

    //>> TODO 注入WebApplicationContext来模拟ServletContext环境.
    @Autowired
    WebApplicationContext context;

    //声明一个mockmvc对象，在每个测试方法前进行mockmvc的初始化操作
    MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test01() throws Exception {
        //>> TODO perform方法开启一个RequestBuilder请求.
        MvcResult mvcResult = mockMvc.perform(
                //>> TODO 请求通过MockMvcRequestBuilders进行构建.
                MockMvcRequestBuilders.get("/")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                //>> TODO 验证响应码是否为200.
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
