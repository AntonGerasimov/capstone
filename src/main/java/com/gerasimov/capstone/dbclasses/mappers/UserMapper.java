package com.gerasimov.capstone.dbclasses.mappers;

import com.gerasimov.capstone.dbclasses.domain.FullName;
import com.gerasimov.capstone.dbclasses.domain.User;
import com.gerasimov.capstone.dbclasses.entity.RoleEntity;
import com.gerasimov.capstone.dbclasses.entity.UserEntity;
import com.gerasimov.capstone.dbclasses.repositories.RoleRepository;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserMapper(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User mapUserEntityToDomain(UserEntity userEntity) {
        Long id = userEntity.getId();
        FullName fullName = new FullName(userEntity.getFirstName(), userEntity.getLastName());
        String email = userEntity.getEmail();
        String userName = userEntity.getUsername();
        String password = userEntity.getPassword();
        RoleEntity roleEntity = roleRepository.findById(userEntity.getRoleId()).orElse(null);
        String role = roleEntity.getRoleName();
        boolean isActive = userEntity.isActive();
        return new User(id, fullName, email, userName, password, role, isActive);
    }

    public UserEntity mapUserDomainToEntity(User user){
        String firstName = user.getFullName().getFirstName();
        String lastName = user.getFullName().getLastName();
        String email = user.getEmail();
        String userName = user.getUsername();
        String password = user.getPassword();
        String roleName = user.getRole();
        RoleEntity roleEntity = roleRepository.findByRoleName(roleName);
        Long roleId = roleEntity.getId();
        boolean isActive = user.isActive();
        return new UserEntity(firstName, lastName, email, userName, password, roleId, isActive);
    }
}
