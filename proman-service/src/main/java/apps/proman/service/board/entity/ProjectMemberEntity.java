package apps.proman.service.board.entity;

import static apps.proman.service.common.entity.Entity.SCHEMA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import apps.proman.service.common.entity.Identifier;
import apps.proman.service.common.entity.ImmutableEntity;
import apps.proman.service.user.entity.PermissionEntity;
import apps.proman.service.user.entity.RoleEntity;
import apps.proman.service.user.entity.UserEntity;

@Entity
@Table(name = "PROJECT_MEMBERS", schema = SCHEMA)
public class ProjectMemberEntity extends ImmutableEntity implements Identifier<Integer>, Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "PERMISSION_ID")
    private UserEntity member;

    @Override
    public Integer getId() {
        return id;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public UserEntity getMember() {
        return member;
    }

    public void setMember(UserEntity member) {
        this.member = member;
    }

}