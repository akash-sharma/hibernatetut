Links : 
http://stackoverflow.com/questions/3472438/why-cant-entity-class-in-jpa-be-final

----------------

=>An entity class must follow these requirements.

(1)The class must be annotated with the javax.persistence.Entity annotation.

(2)The class must have a public or protected, no-argument constructor. 
   The class may have other constructors.

(3)The class must not be declared final. 
   No methods or persistent instance variables must be declared final.

(why) : -> having a final field would make no sense in the context of something that
           will end up being a record in your database.
        -> (proxy classes) It is common for implementations to subclass your class at runtime 
           and/or add byte code dynamically to your classes, in order to detect
          when a change to an entity object has occurred. 
          If your class is declared as final, then that would not be possible.


(4)If an entity instance is passed by value as a detached object, such as through a session bean’s
   remote business interface, the class must implement the Serializable interface.

(5)Entities may extend both entity and non-entity classes, and non-entity classes may extend entity classes.

(6)Persistent instance variables must be declared private, protected, or package-private and can be
   accessed directly only by the entity class’s methods. 
   Clients must access the entity’s state through accessor or business methods.


------------------

=>The following collection interfaces may be used:

->java.util.Collection
->java.util.Set
->java.util.List
->java.util.Map


-------------------




If a field or property of an entity consists of a collection of basic types or embeddable classes, 
use the javax.persistence.ElementCollection annotation on the field or property.
The two attributes of @ElementCollection are targetClass and fetch. 
 By default, the collection will be fetched lazily.



By default, the Persistence provider will automatically perform validation on entities with persistent fields
 or properties annotated with Bean Validation constraints immediately after the PrePersist, PreUpdate, and PreRemove lifecycle events.

-> PrePersist,
-> PreUpdate,
-> PreRemove


--------------------

Each entity has a unique object identifier.
Every entity must have a primary key. 
An entity may have either a simple or a composite primary key.
Simple primary keys use the javax.persistence.
Id annotation to denote the primary key property or field.


