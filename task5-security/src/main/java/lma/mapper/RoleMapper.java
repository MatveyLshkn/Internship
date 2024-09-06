package lma.mapper;

import lma.dto.RoleDto;
import lma.entity.Role;
import lma.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final RoleRepository roleRepository;

    public Role mapFromDto(RoleDto roleDto) {
        return roleRepository
                .findByAuthority(roleDto.role())
                .orElseGet(() -> roleRepository.save(Role.builder()
                        .authority(roleDto.role())
                        .build()));
    }
}
