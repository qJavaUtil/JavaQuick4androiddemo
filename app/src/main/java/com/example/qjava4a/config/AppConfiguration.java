package com.example.qjava4a.config;

import blxt.qjava.autovalue.inter.*;

/***
 * 插件通用配置
 * @author 张家磊
 */
//@Configuration("../src/test.test/resources/application.properties")
@Component()
@Configuration()
public class AppConfiguration {

    @Value("test.string_t")
    public String string_t = "Default";

    @Value("test.int_t" )
    public int int_t;

    @Value("test.float_t" )
    public float float_t ;
    @Value("test.double_t" )
    public double double_t ;
    @Value("test.boolean_t" )
    public boolean boolean_t ;
    @Value("test.long_t" )
    public long long_t ;

    @Autowired
    public Bean1 bean1;

    @Run("value1str=hellow, value2=123")
    public void init(@AliasFor(value="value1str")String value1, @AliasFor(value="value2") int value2, Bean1 bean1){
        System.out.println("@Run 自动运行1" + value1 + "--" +  bean1);
    }

    @Run()
    public void init(){
        System.out.println("@Run 自动运行2");
    }


    @Override
    public String toString() {
        return "test.test.util.AppConfiguration{" +
                "string_t='" + string_t + '\'' +
                ", int_t=" + int_t +
                ", float_t=" + float_t +
                ", double_t=" + double_t +
                ", boolean_t=" + boolean_t +
                ", long_t=" + long_t +
                ", bean1=" + (bean1 == null ? "" : bean1.toString()) +
                '}';
    }
}
