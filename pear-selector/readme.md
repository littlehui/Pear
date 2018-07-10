# pear-selector包说明
## 功能：
    1：作为mybatis的增强版而存在，目的是消除sql语句。提供统一的orm层编写规范。
    2：目前支持mysql数据库，Oracle通过继承不同的DAO实现来支持。
## 一些约定：
    1：DAO层为借口层，默认名称为  XxxDAO其中Xxx代表的是数据对象在java层面的映射。
    我们称为PO.
    2:PO里如果有updateTime, createTime等如果操作类继承的是repository类的话，会在添加或者修改的时候自动填充updateTime值和createTime值。
    PS他们都是Long型。
    3:如果操作集成的是repository。并且PO里有autoCode字段，则，在插入的时候如果此字段没值，会进行唯一编码生成，并进行赋值。
    3：数据默认存储都是用UTF-8
    
## 实例
    数据库表    ：t_user;
    PO         : User;
    Repository : UserRepository extend DefaultSqlRepository<String, User>
    
    Manager层  : UserManager
    Service层  : UserService
    
 