package lma.dto;

import lma.entity.Role;

import java.util.List;

public record UserRegisterDto(String username, String password, List<RoleDto> roles) {
}
