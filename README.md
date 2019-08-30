## Mola

  Mola (翻车鱼) :  该项目目的在于为基于 Spring 企业应用项目实现 DDD 的构造块(Repository, Factory)提供快捷支持. 简化项目的开发工作, 避免理论的生搬硬套. 

### Example 示例说明

  为了实现 DDD(这里强调充血模型下的领域对象)  按照理论最少需要实现三个基本构造块:  Model  Repository  Factory.  在这三个构造块各自职责:
  
  - Model :  承载领域逻辑 
  - Factory : 负责构建新的领域对象
  - Repository : 负责载入持久化之后的数据
  
  在常规项目中其实现大致如下,
  

```java

  /** 
   * 在 MDD 场景下有个明显的问题就是 Model 需要依赖 Repository 为了实现这个场景
   * 结合Spring框架的限制 我们大概需要如下实现.
   */
  @Component 
  public class XyzFactory {
      private XyzRepository  xyzRepository;
      public Xyz create (){
          return new Xyz(xyzRepository);
      }
  }
  @org.springframework.stereotype.Repository
  public class XyzRepository{ /*...*/ }
  
  public class Xyz {/*...*/}
  
  /* 在 Controller 中大概是这样使用 : 请注意这里没有考虑事务问题.
     如果需要 提供一层外观式的 Service 层用于控制事务范围是值得的 : 这方面的套路你可以看 Martin Fowler 的 EA 这本书
   */
  
  @org.springframework.stereotype.Controller
  public class XyzController {
      private XyzFactory xyzFactory;
      public Object doSomething(){
          Xyz xyz =  xyzFactory.create();
          return xyz.doSomething();
      }
  }

```

上述操作的难点在于: 一旦 Xyz 依赖成分复杂, Factory/Repository 的实现就会变得麻烦, 如 `级联创建对象的场景`.

#### 新的开发模式

该项目的功能在于提供一种更简单的方式: 实现相同的功能.  在该方案下将 Factory 由显式调整为隐式 : 即将其作为一个函数接口使用, 将其传递给 Repository.

> 关于在创建对象时特殊路径  create -> Repository -> Factory 的方案 在 Eric Evans 的领域驱动一书中有详细模式


 
```java
 
   public class XyzController2 {
        private XyzRepository xyzRepository;
       
        public Object doSomething(){
              Xyz xyz = xyzRepository.create( () -> new Xyz());
              return xyz.doSomething();
        }
       
   }
   /* 作为限制 你需要做如下的操作 
      - 声明 Xyz 是一个领域对象  
    */
   
   @org.cokebook.mola.Domain
   public class Xyz{ /*...*/}
   
   /* 在SpringBOOT 启动类中允许DDD */
   @org.cokebook.mola.boot.EnableDDD
   @SpringBootApplication
   public class Bootstrap { /* ... */}
   
```


### 关于样例的更多测试 见 测试代码中的 org.cokebook.mola.example 目录
