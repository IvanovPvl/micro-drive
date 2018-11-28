package io.microdrive.auth.domain;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id private long id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
