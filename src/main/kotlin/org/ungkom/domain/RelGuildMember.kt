package org.ungkom.domain

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(
    name = "GuildMemberRelation",
    uniqueConstraints = [UniqueConstraint(columnNames = ["guild_id", "member_id"])]
)
class RelGuildMember(
    @ManyToOne
    val guild: Guild,
    @ManyToOne
    val member: Member
) : BaseModel() {

}