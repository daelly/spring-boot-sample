## 一、Spring中的几种Bean装配方式
#### 1、 基于Java的配置
> 通过使用@Configuration 和 @Bean 注解在Java代码中定义Bean。

#### 2、基于XML的配置
> Spring也支持通过XML配置文件定义Bean，这种方式在早期的Spring版本中更为常见。

#### 3、基于类注解的组件扫描
> 通过使用@Component、@Service、@Repository、@Controller等注解以及@ComponentScan来自动检测和注册Bean。

#### 4、使用@Import注解
> 可以直接通过它直接注册类到IOC容器中，无需这些类带有@Component类的注解。我们可以使用它来注册普通的类，或者注册实现了 ImportSelector 或 ImportBeanDefinitionRegistrar 接口的类，以提供更加高级的装配能力。

## 二、@Import注解的使用
#### 1、注册一个普通的类
```java
/**
 * 先定义一个简单的类
 *
 **/
public class Book {

    private String name;

    public Book() {
        this.name = "unknown";
    }

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
```
```java
/**
 * 找个类简单Import
 *
 **/
@Import(Book.class)
public class AppConfig {
}
```
```java
@Test
public void test1() {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    Book book = context.getBean(Book.class);
    // bean 已经被调用无参构造函数示例化了，会打印出 "unknown"
    System.out.println(book.getName());
    context.close();
}
```

#### 2、注入一个配置类
```java
/**
 * 定义一个配置类，里面定义一些bean
 *
 **/
public class Library {

    @Bean
    public Book book1() {
        return new Book("The Catcher in the Rye");
    }

    @Bean
    public Book book2() {
        return new Book("To Kill a Mockingbird");
    }

    @Bean
    public BookShelf bookShelf(Book book1, Book book2) {
        return new BookShelf(Arrays.asList(book1, book2));
    }
}
```
```java
/**
 * 找个类简单Import
 *
 **/
@Import(Library.class)
public class AppConfig {
}
```
```java
@Test
public void test2() {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    BookShelf bookShelf = context.getBean(BookShelf.class);
    // 可以打印出Library中定义的两个book bean
    System.out.println(bookShelf.getBooks());
    context.close();
}
```

#### 3、注入一个ImportSelector
实现的功能和1相同
```java
public class BookImportSelector implements ImportSelector {

    /**
     * 此方法返回的是要Import的bean对应的类的全名
     * 和直接Import对应的类名相同，直接调用类的无参构造函数示例话bean
     *
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {Book.class.getName()};
    }
}
```
```java
// 测试方法和1相同，就不写了
```
#### 4、使用ImportSelector和自定义注解实现选择性Import
###### 4.1、定义一些Bean
```java
public interface Color {

    String color();
}

public class Black implements Color {

    @Override
    public String color() {
        return "black";
    }
}

public class Orange implements Color {

    @Override
    public String color() {
        return "orange";
    }
}

public class Purple implements Color {

    @Override
    public String color() {
        return "purple";
    }
}

public class Yellow implements Color {

    @Override
    public String color() {
        return "yellow";
    }
}
```

###### 4.2、定义注解
```java
/**
 * ImportSelector的用法：<br/>
 * 1、自定义的注解，并在其中定义参数<br/>
 * 2、实现一个ImportSelector，使用1步骤的注解参数，生成需要import的类<br/>
 * 3、在1步骤的注解中@Import步骤2的ImportSelector<br/>
 * 4、使用步骤1定义的注解，并使用对应参数注解到Spring的类中，实现选择性Import
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ColorImportSelector.class)
public @interface RegisterColor {
    
    /**
     * 这里简单演示，直接返回需要import的类，应用中可以定义更丰富的参数，实现更丰富的功能
     *
     */
    Class<? extends Color>[] colorUsed() default {};
}
```

###### 4.3、定义4.2中的ImportSelector
```java
public class ColorImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 获取注解的属性
        Map<String, Object> annotationAttrs = importingClassMetadata.getAnnotationAttributes(RegisterColor.class.getName(), true);
        String[] colorUsed = (String[]) annotationAttrs.get("colorUsed");
        System.out.println(Arrays.toString(colorUsed));
        
        // 返回需要import的类名
        return colorUsed;
    }
}
```

###### 4.4、使用
```java
@RegisterColor(colorUsed = {Black.class, Purple.class})
public class AppConfig {
}

@Test
public void test3() {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    Map<String, Color> colors = context.getBeansOfType(Color.class);
    System.out.println(colors);
    context.close();
}
```

###### 4.5、总结
通过这里例子，可以知道ImportSelector可以帮助实现更加丰富的动态Import class的功能。只需要在注解中配置好参数，就可以实现Import不同的class到Spring 容器中。但是我们也可以发现ImportSelector的一个局限，就是它Import进来的bean只能调用无参构造函数。下一小节将介绍功能更强大的ImportBeanDefinitionRegistrar。更多ImportSelector的使用，可以参考：Spring的EnableAsync
```java
public class AsyncConfigurationSelector extends AdviceModeImportSelector<EnableAsync> {
```

#### 5、ImportBeanDefinitionRegistrar的简单使用
实现的功能和1、3相同
```java
public class BookRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinition definition = BeanDefinitionBuilder.genericBeanDefinition(Book.class)
                .setScope(BeanDefinition.SCOPE_PROTOTYPE)
                .addPropertyValue("name", "War and Piece")
                .getBeanDefinition();

        registry.registerBeanDefinition("myBook", definition);
    }
}
```

```java
@Import(BookRegistrar.class)
public class AppConfig {
}
```

```
// 测试方法和1相同
```

#### 6、ImportBeanDefinitionRegistrar配合注解注册bean
###### 6.1、定义一个Bean
```java
@Getter
@Setter
public class Person {

    private String name;

    private int age;

    private boolean gender;
}
```

###### 6.2、定义注解
```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Documented
@Import(PersonRegistrar.class)
public @interface EnablePerson {

    String name();

    int age();

    boolean gender();
}
```

###### 6.3、定义ImportBeanDefinitionRegistrar
```java
public class PersonRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attrs = importingClassMetadata.getAnnotationAttributes(EnablePerson.class.getName());
        String name = (String) attrs.get("name");
        BeanDefinition definition = BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                .addPropertyValue("name", name)
                .addPropertyValue("age", attrs.get("age"))
                .addPropertyValue("gender", attrs.get("gender"))
                .getBeanDefinition();

        registry.registerBeanDefinition("person:" + name, definition);
    }
}
```

###### 6.4、使用
```java
@EnablePerson(name = "Daelly", age = 18, gender = true)
public class AppConfig {
}

@Test
public void test4() {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    Person person = context.getBean(Person.class);
    System.out.println("Person' name = " + person.getName() + ", age = " + person.getAge() + ", gender = " + person.isGender());
    context.close();
}
```

###### 6.5、这是一个结合注解定义bean属性的简单的示例，在实际的应用中，我们可以使用注解定义属性，然后在PersonRegistrar中实现复杂的逻辑，甚至可以在PersonRegistrar中注册一个 ***Configurer类来完成我们的配置工作。可以参考MyBatis、Dubbo关于ImportBeanDefinitionRegistrar的使用。
```java
public class MapperScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {}
```

```java
public class DubboConfigConfigurationRegistrar implements ImportBeanDefinitionRegistrar {}
```