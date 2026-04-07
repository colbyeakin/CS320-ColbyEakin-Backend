package ExampleEndpoints;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "people")
public class Person extends PanacheEntity {
    public String name;
    public int age;
    public String favoriteThing;
}
