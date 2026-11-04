package solutions.simurgh.core.identity.api.mapper;

import org.mapstruct.Mapper;
import solutions.simurgh.core.identity.api.dto.response.UserResponse;
import solutions.simurgh.core.identity.domain.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserResponse toResponse(User user);
}
