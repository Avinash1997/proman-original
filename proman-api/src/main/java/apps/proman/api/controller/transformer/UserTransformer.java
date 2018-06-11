package apps.proman.api.controller.transformer;

import java.util.List;
import java.util.stream.Collectors;

import apps.proman.api.model.CreateUserRequest;
import apps.proman.api.model.CreateUserResponse;
import apps.proman.api.model.PermissionsType;
import apps.proman.api.model.RoleDetailsType;
import apps.proman.api.model.RoleSummaryType;
import apps.proman.api.model.SignupUserRequest;
import apps.proman.api.model.SignupUserResponse;
import apps.proman.api.model.UserDetailsResponse;
import apps.proman.api.model.UserStatusType;
import apps.proman.api.model.UsersSummaryResponse;
import apps.proman.api.model.UsersSummaryType;
import apps.proman.service.user.entity.RoleEntity;
import apps.proman.service.user.entity.RolePermissionEntity;
import apps.proman.service.user.entity.UserEntity;
import apps.proman.service.common.model.SearchResult;
import apps.proman.service.user.model.UserStatus;

public final class UserTransformer {

    public static UserEntity toEntity(SignupUserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setLastName(userRequest.getLastName());
        userEntity.setEmail(userRequest.getEmailAddress());
        userEntity.setMobilePhone(userRequest.getMobileNumber());
        return userEntity;
    }

    public static UserEntity toEntity(CreateUserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setLastName(userRequest.getLastName());
        userEntity.setEmail(userRequest.getEmailAddress());
        userEntity.setMobilePhone(userRequest.getMobileNumber());
        return userEntity;
    }

    public static CreateUserResponse toCreateUserResponse(UserEntity userEntity) {
        return new CreateUserResponse().id(userEntity.getUuid()).status(toStatus(userEntity.getStatus()));
    }

    public static SignupUserResponse toSignupResponse(UserEntity userEntity) {
        return new SignupUserResponse().id(userEntity.getUuid()).status(toStatus(userEntity.getStatus()));
    }

    public static UserDetailsResponse toUserDetailsResponse(UserEntity userEntity) {
        return new UserDetailsResponse().id(userEntity.getUuid())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .emailAddress(userEntity.getEmail())
                .mobileNumber(userEntity.getMobilePhone())
                .status(toStatus(userEntity.getStatus()))
                .role(toResponse(userEntity.getRole()).permissions(toResponse(userEntity.getRole().getPermissions())));
    }

    public static UsersSummaryResponse toUsersSummaryResponse(final int page, final int limit, final SearchResult<UserEntity> searchResult) {

        UsersSummaryResponse usersSummaryResponse = new UsersSummaryResponse().totalCount(searchResult.getTotalCount()).page(page).limit(limit);

        for(UserEntity userEntity : searchResult.getPayload()) {
            UsersSummaryType summaryType = new UsersSummaryType().id(userEntity.getUuid()).firstName(userEntity.getFirstName())
                    .lastName(userEntity.getLastName()).emailAddress(userEntity.getEmail())
                    .status(toStatus(userEntity.getStatus()))
                    .role(toRoleSummaryResponse(userEntity.getRole()));
            usersSummaryResponse.addUsersItem(summaryType);
        }

        return usersSummaryResponse;
    }

    private static RoleDetailsType toResponse(RoleEntity roleEntity) {
        return new RoleDetailsType().id(roleEntity.getUuid()).name(roleEntity.getName());
    }

    private static RoleSummaryType toRoleSummaryResponse(RoleEntity roleEntity) {
        if(roleEntity == null) {
            return null;
        }
        return new RoleSummaryType().id(roleEntity.getUuid()).name(roleEntity.getName());
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
