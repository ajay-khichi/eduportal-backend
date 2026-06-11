package com.ignishers.backend.model.user;

import com.ignishers.backend.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    @NotBlank(message = "Role name is required")
    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

}