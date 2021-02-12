package org.ungkom.domain

import javax.persistence.*

@Entity
@Table(
    name = "Guilds",
    uniqueConstraints = [UniqueConstraint(columnNames = ["discord_id"])]
)
class Guild(
    var discord_id: String
) : BaseModel() {
    @OneToMany
    lateinit var members: List<RelGuildMember>
}