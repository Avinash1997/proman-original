package apps.proman.service.board.entity;

import static apps.proman.service.common.entity.Entity.SCHEMA;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import apps.proman.service.common.entity.Identifier;
import apps.proman.service.common.entity.MutableEntity;
import apps.proman.service.common.entity.UniversalUniqueIdentifier;
import apps.proman.service.common.entity.ext.EntityEqualsBuilder;
import apps.proman.service.common.entity.ext.EntityHashCodeBuilder;
import apps.proman.service.user.entity.UserEntity;

@Entity
@Table(name = "BOARDS", schema = SCHEMA)
@NamedQueries({
        @NamedQuery(name = BoardEntity.BY_NAME, query = "select b from BoardEntity b where b.name = :name")
})
public class BoardEntity extends MutableEntity implements Identifier<Integer>, UniversalUniqueIdentifier<String>, Serializable {

    public static final String BY_NAME = "BoardEntity.byName";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "UUID")
    @NotNull
    private String uuid;

    @Column(name = "NAME")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "DESCRIPTION")
    @Size(max = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private UserEntity owner;

    @Column(name = "STATUS")
    @NotNull
    private Integer status;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        return new EntityEqualsBuilder<Integer>().equalsById(this, obj);
    }

    @Override
    public int hashCode() {
        return new EntityHashCodeBuilder<Integer>().hashCodeById(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @PrePersist
    public void prePersist() {
        this.uuid = UUID.randomUUID().toString();
        super.prePersist();
    }

}
