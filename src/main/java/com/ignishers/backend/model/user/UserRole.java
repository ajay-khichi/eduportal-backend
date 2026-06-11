package com.ignishers.backend.model.user;

import com.ignishers.backend.model.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
        name = "user_role",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_user_role",
                columnNames = {"users_id", "role_id"}
        )
)
public class UserRole extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}