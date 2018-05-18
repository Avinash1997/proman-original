package apps.proman.api.controller.transformer;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import apps.proman.api.model.CreateUserRequest;
import apps.proman.api.model.CreateUserResponse;
import apps.proman.api.model.PermissionsType;
import apps.proman.api.model.RoleDetailsType;
import apps.proman.api.model.UserDetailsResponse;
import apps.proman.api.model.UserStatusType;
import apps.proman.service.user.entity.RoleEntity;
import apps.proman.service.user.entity.RolePermissionEntity;
import apps.proman.service.user.entity.UserEntity;
import apps.proman.service.user.model.UserStatus;

public final class UserTransformerFunctions {

    public static Function<CreateUserRequest, UserEntity> toEntity() {
        return userRequest -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setFirstName(userRequest.getFirstName());
            userEntity.setLastName(userRequest.getLastName());
            userEntity.setEmail(userRequest.getEmailAddress());
            userEntity.setMobilePhone(userRequest.getMobileNumber());
            return userEntity;
        };
    }

    public static Function<UserEntity, CreateUserResponse> toCreateUserResponse() {
        return userEntity ->
                new CreateUserResponse().id(userEntity.getUuid()).status(toStatus(userEntity.getStatus()));
    }

    public static Function<UserEntity, UserDetailsResponse> toUserDetailsResponse() {
        return userEntity ->
                new UserDetailsResponse().id(userEntity.getUuid())
                        .firstName(userEntity.getFirstName())
                        .lastName(userEntity.getLastName())
                        .emailAddress(userEntity.getEmail())
                        .mobileNumber(userEntity.getMobilePhone())
                        .status(toStatus(userEntity.getStatus()))
                        .role(toResponse(userEntity.getRole()).permissions(toResponse(userEntity.getRole().getPermissions())));
    }

    private static RoleDetailsType toResponse(RoleEntity roleEntity) {
        return new RoleDetailsType().id(roleEntity.getUuid()).name(roleEntity.getName());
    }

    private static List<PermissionsType> toResponse(List<RolePermissionEntity> permissions) {
        return permissions.stream().map(rolePermissionEntity -> {
            return new PermissionsType().id(rolePermissionEntity.getPermissionEntity().getUuid()).name(rolePermissionEntity.getPermissionEntity().getName());
        }).collect(Collectors.toList());
    }

    private static UserStatusType toStatus(final int statusCode) {
        return UserStatusType.valueOf(UserStatus.get(statusCode).name());
    }

}
