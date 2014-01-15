jade-fff
========

Java Spring project with MyBatis ORM.

Using technologies:

    Backend:    Java
                Spring-mvc
                Spring security
                Spring config
                myBatis
    Frontend:   Jade Templates
                CoffeeScript
                SCSS
                Bootstrap
    Collector:  Maven
    DB:         MySQL
    VCS:        Git
    Ideology:   DDD (domain driven design)

About
-----
This project was started as an attempt of using DDD (Domain Driven Design) ideology with Java Web technologies. DDD was initially more RoR (Ruby on Rails ) ideology but our team decided to take a try of it with Java. DDD offers more business oriented application building approach than traditional methods.

Also we have used MyBatis ORM instead of more usual for Java Spring web applications Hibernate, because we've already created a lot of Hibernate projects, and we are tired of its behavior. Nowadays Java specialists prefer to use Hibernate entities configured with Java annotations but as a result you have heavy-weight entities in every layer of your application. Moreover Hibernate used in lazy mode often contains unnecessary data increasing entity weight too and sometimes disregarding security requirements.

On the other side, MyBatis provides you a way of obtaining only information you need for certain request. So MyBatis will be more appropriate when using with DDD.

Architecture
------------
Blahblah.

