package lma.mapper;

import lma.dto.UserRegisterDto;
import lma.entity.Role;
import lma.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleMapper roleMapper;

    public User mapFromRegisterDto(UserRegisterDto userRegisterDto) {
        List<Role> userRoles = userRegisterDto.roles()
                .stream()
                .map(roleMapper::mapFromDto)
                .toList();

        return User.builder()
                .username(userRegisterDto.username())
                .password(userRegisterDto.password())
                .roles(userRoles)
                .build();
    }
}
