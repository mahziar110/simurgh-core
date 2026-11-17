package solutions.simurgh.core.identity.api.mapper;

import org.mapstruct.Mapper;
import solutions.simurgh.core.identity.api.dto.response.RoleResponse;
import solutions.simurgh.core.identity.domain.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
  RoleResponse toResponse(Role role);
}
