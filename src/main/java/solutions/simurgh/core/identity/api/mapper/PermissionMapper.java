package solutions.simurgh.core.identity.api.mapper;

import org.mapstruct.Mapper;
import solutions.simurgh.core.identity.api.dto.response.PermissionResponse;
import solutions.simurgh.core.identity.domain.model.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
  PermissionResponse toResponse(Permission permission);
}