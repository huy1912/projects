@SpringBootConfiguration
public class ExampleMain {
    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ExampleMain.class, args);
        MyBean myBean = context.getBean(MyBean.class);
        myBean.doSomething();

        //no need to call context.registerShutdownHook();
    }

    private static class MyBean {

        @PostConstruct
        public void init() {
            System.out.println("init");
        }

        public void doSomething() {
            System.out.println("in doSomething()");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("destroy");
        }
    }
}