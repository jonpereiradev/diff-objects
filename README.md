# Diff Objects

This project helps you to build functionalities that needs to show the differences between two objects.

You can use this project in two ways, using annotations or a Builder to map the properties of the object that will be
checked for difference.

## DiffBuilder

The DiffBuilder provide an API to map an object without the use of annotations:

**Example**

```
public class User {
    
    private final String login;
    private final String password;
    private final LocalDate birthday;
    private final Boolean active;
    private final List<Email> emails;
    
    // getters and constructor ommited ...
}
```

```
public class Email {
    
    private final String description;
    private final Boolean active;
    
    // getters and constructor ommited ...
}
```

```
public class Main {
    
    public static void main(String[] args) {
        User u1 = new User("user1", "123456", LocalDate.now().minusDays(1), true);
        User u2 = new User("user2", "12345678", LocalDate.now(), true);
        
        u1.getEmails().add(new Email("user@gmail.com", true));
        u2.getEmails().add(new Email("user@gmail.com", false));
        
        DiffConfiguration configuration = DiffBuilder.map(User.class)
            .mapper()
            .mappingAll()
            .mapping("emails", "description")
            .instance()
            .configuration();
        
        List<DiffResult<?>> diffs = DiffObjects.diff(u1, u2, configuration);
        
        for (DiffResult<?> diff : diffs) {
            if (!diff.isEquals()) {
                System.out.println("Field: " + diff.getProperties().get("field"));
                System.out.println("Before: " + diff.getBefore());
                System.out.println("After: " + diff.getAfter());
            }
        }
    }
}
```

## Annotations

The annotations provided by the diff objects are:

- DiffMapping - to map a method of the object;
- DiffMappings - to map multiple properties of an object relationship;
- DiffProperty - to add properties that will be on the diff result for access;

**Example**

```
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
    
    @DiffMapping(value = "description")
    public List<Email> getEmails() {
        return emails;
    }
}
```

```
public class Main {
    
    public static void main(String[] args) {
        User u1 = new User("user1", "123456", LocalDate.now().minusDays(1), true);
        User u2 = new User("user2", "12345678", LocalDate.now(), true);
        
        u1.getEmails().add(new Email("user@gmail.com", true));
        u2.getEmails().add(new Email("user@gmail.com", false));
        
        List<DiffResult<?>> diffs = DiffObjects.diff(u1, u2);
        
        for (DiffResult<?> diff : diffs) {
            if (!diff.isEquals()) {
                System.out.println("Field: " + diff.getProperties().get("field"));
                System.out.println("Before: " + diff.getBefore());
                System.out.println("After: " + diff.getAfter());
            }
        }
    }
}
```

### @DiffProperty

This annotations is used to provide information in the DiffResult about the field. All objects already have the 
property "field" with the field name as value.

`@DiffProperty(key = "field", value = "{{fieldName}}")`

**Example**

```
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

```
public class Main {
    
    public static void main(String[] args) {
        // code omitted ...
        
        List<DiffResult<?>> diffs = DiffObjects.diff(u1, u2);
        
        for (DiffResult<?> diff : diffs) {
            if (diff.getProperties().containsKey("id")) {
                String id = diff.getProperties().get("id");
                String maxlength = diff.getProperties().get("maxlength");
            
                System.out.println("Id: " + id);
                System.out.println("Maxlength: " + maxlength);
                System.out.println("Field: " + diff.getProperties().get("field"));
                System.out.println("Before: " + diff.getBefore());
                System.out.println("After: " + diff.getAfter());
            }
        }
    }
}
```

## Future implementations

- Order of the result using @DiffOrder not implemented yet;
- Disable mapped fields after calling DiffBuilder.map(User.class).mapper().mappingAll();
- Enable only different object for diff result;
- Collection with value working with navigate properties like "emails.principal.description";
- Remove wildcard from DiffResult class;
