### 一、BeanFactoryPostProcessor
#### 1、定义
```java
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
```

#### 2、执行时机
> 在BeanDefinition已经完成加载、注册到BeanDefinitionRegistry后，但在bean实例化之前。这一时期主要处理bean的元数据

#### 3、示例
```java
@Component
public class TintLabelSetterFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 这个时候beanFactory里只有beanDefinition，
        // 不要在这里调用beanFactory的getBean方法，这会导致bean跳过一些必要的Advice而直接实例化，继而出现各种问题
        // 正确的做法是，访问beanDefinition，并且处理beanDefinition
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition definition = beanFactory.getBeanDefinition(beanName);

            if (!StringUtils.hasLength(definition.getBeanClassName())) {
                continue;
            }

            Class<?> clazz = ClassUtils.resolveClassName(definition.getBeanClassName(), null);
            if (Tint.class.isAssignableFrom(clazz)) {
                definition.getPropertyValues().add("label", "postProcessBeanFactory_" + beanName);
            }
        }
    }
}
```

### 二、BeanPostProcessor
#### 1、定义
```java
public interface BeanPostProcessor {

    /**
     * 在Spring完成Bean的实例化、进行属性注入后，但是在bean的自定义初始化（如通过@PostConstruct注解方法
     * 或init-method方法）之前调用
     *
     */
    @Nullable
	default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
	
	/**
     * 相对于前者，在bean的自定义初始化方法执行后调用
     *
     */
	@Nullable
	default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}
```

#### 2、示例
```java
@Component
public class TintLabelSetterPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // bean 已经被示例化，可以直接操作bean
        if (bean instanceof Tint) {
            Tint tint = (Tint) bean;
            tint.setLabel("postProcessAfterInitialization_" + beanName);
        }
        return bean;
    }
}
```

### 三、BeanDefinitionRegistryPostProcessor
#### 1、定义
```java
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {
    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException;
}
```

#### 2、执行时机
> 在Spring容器完成对bean的定义信息的加载后，但在他们真正实例化之前，进行额外的操作。

#### 3、示例
```java
@Component
public class OrangeRegisterPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("postProcessBeanDefinitionRegistry in OrangeRegisterPostProcessor started.");

        // 在postProcessBeanDefinitionRegistry中动态注册bean
        if (!registry.containsBeanDefinition("orange")) {
            BeanDefinition orangeDefinition = BeanDefinitionBuilder.genericBeanDefinition(Orange.class).getBeanDefinition();
            registry.registerBeanDefinition("orange", orangeDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("postProcessBeanFactory in OrangeRegisterPostProcessor started.");

        // 这里可以取到上面注册的orange，说明是先执行 postProcessBeanDefinitionRegistry，再执行 postProcessBeanFactory
        String[] fruitNames = beanFactory.getBeanNamesForType(Fruit.class);
        for (String name : fruitNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            beanDefinition.getPropertyValues().add("type", name);
        }
    }
}
```

### 四、小结
#### 1、BeanFactoryPostProcessor 与 BeanPostProcessor 的差异
>BeanFactoryPostProcessor处理的是beanDefinition，这个时候bean还没有被实例化，也千万不要手动调用BeanFactory的getBean方法，这会导致bean的提前实例化，出现很多意料之外的问题。

>BeanPostProcessor处理的是bean，此时，bean已经被实例化出来了，而且属性也被注入进去了。

#### 2、BeanFactoryPostProcessor 与 BeanDefinitionRegistryPostProcessor 的关系
>很明显，BeanDefinitionRegistryPostProcessor继承自BeanFactoryPostProcessor，都是在bean被实例化之前执行，处理的都是beanDefinition。

>BeanDefinitionRegistryPostProcessor除了支持BeanFactoryPostProcessor的功能外，还加了一个更加精细话的方法：
```
void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException
```
>通常可以用它来动态地注册、修改、移除bean定义

>他们二者的执行顺序是，先执行postProcessBeanDefinitionRegistry，再执行postProcessBeanFactory


### 五、prototype = scope的bean
>scope = "singleton"，我们很常见，就是单例bean，每次获取bean的时候都返回同一个实例，而"prototype"则是每次从BeanFactory获取bean的时候，都会重新初始化，并创建一个新的。
---
先创建一个测试用的bean
```java
public class CounterBean implements InitializingBean, DisposableBean {

    private static AtomicInteger index = new AtomicInteger(0);

    private int counter;

    public CounterBean() {
        counter = index.incrementAndGet();
        System.out.println("init counter with: " + counter);
    }

    public int getCounter() {
        return counter;
    }

    public void initMethod() {
        System.out.println("-----------------------------------initMethod");
    }

    public void destroyMethod() {
        System.out.println("-----------------------------------destroyMethod");
    }

    @PostConstruct
    public void postConstructMethod() {
        System.out.println("-----------------------------------postConstructMethod");
    }

    @PreDestroy
    public void preDestroyMethod() {
        System.out.println("-----------------------------------preDestroyMethod");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("-----------------------------------destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("-----------------------------------afterPropertiesSet");
    }
}
```
再在configuration里定义它：
```java
// 这里的bean的定义没有指定name(id)，对应测试代码中取bean的时候，也不需要指定beanName参数，其实这里是可以指定beanName的
@Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
@Scope("prototype")
@Description("A bean for person")
public CounterBean counterBean() {
    return new CounterBean();
}
```

最后测试：
```java
@Test
public void test7() {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    Map<String, CounterBean> beans = context.getBeansOfType(CounterBean.class);
    System.out.println("beans = " + beans);
    for (int i = 0; i < 10; i++) {
        // 没有指定beanName，直接使用类型获取bean
        CounterBean counterBean = context.getBean(CounterBean.class);
        System.out.println("[" + counterBean + "]'s counter: " + counterBean.getCounter());
    }

    beans = context.getBeansOfType(CounterBean.class);
    System.out.println("beans = " + beans);

    context.close();
}
```
>可以看到，第一次getBeansOfType时会创建一个实例，counter = 1，后面的for循环，每次都会创建一个新的实例，但是知道程序结束，也没有打印三个destroy方法。

可以指定beanName
```java
@Bean(name = "counterBean01", initMethod = "initMethod", destroyMethod = "destroyMethod")
@Scope("prototype")
@Description("A bean for person")
public CounterBean01 counterBean01() {
    return new CounterBean01();
}
```
相应的取bean的代码调整
```java
@Test
public void test8() {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    Map<String, CounterBean01> beans = context.getBeansOfType(CounterBean01.class);
    System.out.println("beans = " + beans);

    for (int i = 0; i < 10; i++) {
        // 指定了beanName，一定要使用beanName获取bean，否则会报错；获取到的bean也是每次都不同的
        CounterBean01 counterBean01 = context.getBean("counterBean01", CounterBean01.class);
        System.out.println("[" + counterBean01 + "]'s counter: " + counterBean01.getCounter());
    }

    context.close();
}
```