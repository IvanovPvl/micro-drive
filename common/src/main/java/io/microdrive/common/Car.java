package io.microdrive.common;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cars")
public class Car {
    @Id private long id;
    @Column private String name;
    @Column private String number;
    @Column(name = "user_id") private String userId;
}
