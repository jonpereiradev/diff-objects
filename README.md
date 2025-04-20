# Diff Objects

This project helps you to build features to show the differences between two objects. You can use it in two ways:

- using the annotations to map the class fields;
- using the builder to manually map the class fields;

## Installation

```xml
<dependency>
    <groupId>com.github.jonpereiradev</groupId>
    <artifactId>diff-objects</artifactId>
    <version>1.3.0</version>
</dependency>
```

## Builder

The Builder is called by DiffBuilder. It provides an API to map an object without the use of annotations:

**Usage example**

```java
public class User {

    private final String login;
    private final String password;
    private final LocalDate birthday;
    private final Boolean active;
    private final List<Email> emails;

    // getters and constructor omitted ...
}
```

```java
public class Email {
    
    private final String description;
    private final Boolean active;
    
    // getters and constructor omitted ...
}
```

```java
import com.github.jonpereiradev.diffobjects.DiffConfig;
import com.github.jonpereiradev.diffobjects.DiffObjects;
import com.github.jonpereiradev.diffobjects.DiffResults;
import com.github.jonpereiradev.diffobjects.DiffResult;
import com.github.jonpereiradev.diffobjects.builder.DiffConfigBuilder;

public class Main {

    public static void main(String[] args) {
        // creates 2 users for this example
        User u1 = new User("user1", "123456", LocalDate.now().minusDays(1), true);
        User u2 = new User("user2", "654321", LocalDate.now(), false);

        // creates the configuration for the diff
        DiffConfig diffConfig = DiffConfigBuilder
            .forClass(User.class)
            .mapping()
            .fields()
            .map("login")
            .map("password")
            .build();

        // executes the diff
        DiffResults diffResults = DiffObjects.forClass(User.class).diff(u1, u2, diffConfig);

        // iterates over the results to show the differences
        for (DiffResult diffResult : diffResults) {
            if (!diffResult.isEquals()) {
                System.out.println("Field: " + diffResult.getField());
                System.out.println("Current: " + diffResult.getCurrent());
                System.out.println("Expected: " + diffResult.getExpected());
            }
        }
    }
}
```

## Annotations

The annotations provided by the diff objects are:

- __DiffMapping:__ maps an object, a field or a method for comparison;
- __DiffProperty:__ add properties available on the diff result object;
- __DiffIgnore:__ ignores a property from the auto scanning when using DiffMapping on a class;
- __DiffOrder:__ defines an order of evaluation for the object properties;

**Usage example**

```java
public class User {
    
    // fields and constructor ommited ...
    
    @DiffMapping
    public String getLogin() {
        return login;
    }
    
    @DiffMapping
    public String getPassword() {
        return password;
    }
    
    @DiffMapping
    public LocalDate getBirthday() {
        return birthday;
    }
    
    @DiffMapping
    public Boolean getActive() {
        return active;
    }
    
    @DiffMapping("description")
    public List<Email> getEmails() {
        return emails;
    }
}
```

```java
import com.github.jonpereiradev.diffobjects.DiffObjects;
import com.github.jonpereiradev.diffobjects.DiffResults;
import com.github.jonpereiradev.diffobjects.DiffResult;

public class Main {
    
    public static void main(String[] args) {
        // creates 2 users for this example
        User u1 = new User("user1", "123456", LocalDate.now().minusDays(1), true);
        User u2 = new User("user2", "654321", LocalDate.now(), true);

        // creates 2 emails for this example
        u1.getEmails().add(new Email("user@gmail.com", true));
        u2.getEmails().add(new Email("user@gmail.com", false));
        
        // executes the diff
        DiffResults diffResults = DiffObjects.forClass(User.class).diff(u1, u2);

        // iterates over the results to show the differences
        for (DiffResult diffResult : diffResults) {
            if (!diffResult.isEquals()) {
                System.out.println("Field: " + diffResult.getField());
                System.out.println("Before: " + diffResult.getBefore());
                System.out.println("After: " + diffResult.getAfter());
            }
        }
    }
}
```

### @DiffProperty

This annotation is used to provide more information in the DiffResult about the field.

All objects already have the property "field" with the field name as value.
E.g. `@DiffProperty(key = "field", value = "{{fieldName}}")`

**Usage example**

```java
import com.github.jonpereiradev.diffobjects.annotation.DiffMapping;
import com.github.jonpereiradev.diffobjects.annotation.DiffProperty;

public class User {
    
    // code ommited ...
    
    @DiffMapping(value = "description", properties = {
        @DiffProperty(key = "id", value = "E-mail:"),
        @DiffProperty(key = "maxlength", value = "50")
    })
    public List<Email> getEmails() {
        return emails;
    }
}
```

```java
import com.github.jonpereiradev.diffobjects.DiffObjects;
import com.github.jonpereiradev.diffobjects.DiffResults;
import com.github.jonpereiradev.diffobjects.DiffResult;

public class Main {
    
    public static void main(String[] args) {
        // code omitted ...
        
        // executes the diff
        DiffResults diffResults = DiffObjects.forClass(User.class).diff(u1, u2);
        
        // iterates over the results to show the differences
        for (DiffResult diffResult : diffResults) {
            if (diffResult.containsProperty("id")) {
                String id = diffResult.getProperty("id");
                String maxlength = diffResult.getProperty("maxlength");

                System.out.println("Id: " + id);
                System.out.println("Maxlength: " + maxlength);
                System.out.println("Field: " + diffResult.getField());
                System.out.println("Current: " + diffResult.getCurrent());
                System.out.println("Expected: " + diffResult.getExpected());
            }
        }
    }
}
```

# License

Diff-Objects is available under the [MIT license](https://tldrlegal.com/license/mit-license).
