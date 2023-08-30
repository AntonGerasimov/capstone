package com.gerasimov.capstone.dbclasses.mappers;

import com.gerasimov.capstone.dbclasses.domain.FullName;
import com.gerasimov.capstone.dbclasses.domain.UserDto;
import com.gerasimov.capstone.dbclasses.entity.Role;
import com.gerasimov.capstone.dbclasses.entity.User;
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

    public UserDto mapUserEntityToDomain(User userEntity) {
        Long id = userEntity.getId();
        FullName fullName = new FullName(userEntity.getFirstName(), userEntity.getLastName());
        String email = userEntity.getEmail();
        String userName = userEntity.getUsername();
        String password = userEntity.getPassword();
        Role roleEntity = roleRepository.findById(userEntity.getRoleId()).orElse(null);
        String role = roleEntity.getRoleName();
        boolean isActive = userEntity.isActive();
        return new UserDto(id, fullName, email, userName, password, role, isActive);
    }

    public User mapUserDomainToEntity(UserDto user){
        String firstName = user.getFullName().getFirstName();
        String lastName = user.getFullName().getLastName();
        String email = user.getEmail();
        String userName = user.getUsername();
        String password = user.getPassword();
        String roleName = user.getRole();
        Role roleEntity = roleRepository.findByRoleName(roleName);
        Long roleId = roleEntity.getId();
        boolean isActive = user.isActive();
        return new User(firstName, lastName, email, userName, password, roleId, isActive);
    }
}
