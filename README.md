## Mola

  Mola (翻车鱼) :  该项目目的在于为基于 Spring 企业应用项目实现 MDD 的构造块(Repository, Factory)提供快捷支持,简化项目的开发工作. 
  
  在常规的实现方案中对某一单一的Model来说, 你大体需要定义 Xyz (Model 类), XyzRepository , XyzFactory, XyzService(可选) 四个类.
  这个四个类中: XyzRepository , XyzFactory, XyzService 这三个类通常会被作为 Spring  Bean 使用. 其作用分别如下
  - XyzRepository  : 负责从持久化存储器中构建 Xyz 对象
  - XyzFactory     : 负责创建新的为持久化的 Xyz 对象
  - XyzService     :  可选仅在某些业务逻辑场景下使用.

  

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


- [mole-example](./mola-example)