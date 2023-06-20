package com.example.redditapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;


import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter@Setter
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long communityId;

    @Column(name = "community_name")
    private String communityName;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name="members")
    private Integer members;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User ownerId;

    @Column(name = "about")
    private String about;

    @Column(name = "wiki")
    private String wiki;

    @Column(name = "is_private")
    private Boolean isPrivate;

    @Column(name = "is_restrict")
    private Boolean isRestrict;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "community_members",
            joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<User> communityMembers;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "community_moderators",
            joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "moderator_id")
    )
    private Set<User> communityModerators;

    @ManyToMany(mappedBy = "communities")
    private List<Categories> categories;

    @OneToMany(mappedBy = "community")
    private List<Post> posts;

}
