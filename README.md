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
This project was started as an attempt of using DDD (Domain Driven Design) ideology with Java Web technologies.
 DDD offers more business oriented application building approach than traditional methods.

Also we have used MyBatis ORM instead of Hibernate, which is more usual for Java Spring web applications, because we've
 already created a lot of Hibernate projects, and we are tired of its behavior. Nowadays Java specialists prefer
 to use Hibernate entities configured with Java annotations, but as a result you have heavy-weight entities in
 every layer of your application. Moreover Hibernate used in lazy mode often contains unnecessary data
 increasing entity weight and sometimes disregarding security requirements.

On the other side, MyBatis provides you a way of obtaining only information you need for certain request. So
 MyBatis will be more appropriate ORM when using with DDD.

Architecture
------------
As this application were build with DDD approach, we will explain its architecture from frontend layer to backend
 up to repository backend layer: the same way it was building.

### Frontend

#### Jade Templates
We've used Jade Templates instead of JSP because Jade is more modern technology saving your time not duplicating
 XML-like dialect, which is too excessive for human use. As disadvantage you loose all JSTL tags and should
 implement them, if you need any, in your application and provide a way you could interact with them
 from templates.

#### CSS and CoffeeScript
It's a simple and fast alternative to CSS and Javascript, having no drawbacks except need of compiling on every
 application rebuild.

#### Bootstrap
Bootstrap is nice for building demonstration applications until design is not ready for production. It
 looks fine, and usually there are no issues with moving from it to production-ready design.

### Backend

#### Java Spring
Spring framework is standard for Java Web Application and there are no comparable alternative. Our application
 is built with Spring configured with Java config and annotations. No XML-configs for Spring as it's most trendy
 viewpoint to get rid of them.

##### Layer 1: Web Layer
On this layer we have Controllers and all corresponding stuff: Validators and Forms, no instance of them get out
 of this layer. Also because we've used Jade instead of JSP for our templates, we were forced to implement some
 things, like localisation message getter method, on this layer too. This methods are in Helper part of it.
 The only logic we left on controller layer is sending request to Service layer, and translating exceptions
 to certain error web pages if any is thrown. Controller layer should not contain any business logic, it should
 interact with templates only.
##### Layer 2: Service Layer
This layer were divided by user roles in two parts: Employer and Recruiter services. In Service layer we check
 all security concerns: whether user is performing request he have permission to do, if not, we throw Security
 Exception. Also here we transmit any technical exceptions we get from repository. Service layer should be independent
 from the source we obtain data from, so we have no idea we got it from database on this layer.
##### Model
All models we use on business layers of our application are defined here. They are very lightweight - POJO
(Pretty Old Java Objects).
##### Config
Spring is configured with java config. Most of things like filters usually configured in web.xml are configured
there too. This layer do not interacts with any other part of application directly.

### MyBatis
MyBatis is JDBC-like ORM which offers a way of interacting with database in your Java application not in such
 handy manner like Hibernate, but you have a benefit from using it: lightweight Model objects in any part of your
  project. And you can modify any SQL request to database if you want to increase speed of slow interactions.

##### Layer 3: Repository Layer
Repository Layer is divided on classes by Model. As all security measures were implemented on Service Layers, we
 do not check them here. The only thing we check is whether parameters are correct or not. Also application could
 throw Repository Exception from this layer, if there are any difficulties with obtaining information from
 database or performing request. It's DAO layer if you use more common definition.

DDD
---
This project was built using Domain Driven Design. So we used some unusual techniques in the process of
 developing it. And all of them are not visible when application is complete. From developing process you got an
 answer why these techniques are better for business. First of all our mission was to implement number of use
 cases provided by customer:

1. At first templates were made with hardcoded use case examples in it.
2. Than Controllers were made with hardcoded model data providing data for all templates.
3. Templates were modified to get data from controllers. Templates are ready now.
4. Service methods were build with separate methods for each use case
 moving all data from controllers. Controllers are ready now.
5. All hardcoded data from service methods were moved to repository, creating separate Repository for each Model.
Service Layer is ready now.
6. After that we modify Repository: adding ORM and implementing all interactions with it.

Concluding it: we have separate methods for each of our use cases in any layer. We can easily implement any new
 use case by adding methods to all 3 business layers. Or we can easily remove one of them if our customer do not want it more.
 With such flexibility application have pretty code and can be easily maintained. Moreover you can show working
 application to customer from the second day since job started!