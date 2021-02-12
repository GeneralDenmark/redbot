package org.ungkom.domain

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint
@Entity
@Table(name="member",
       uniqueConstraints = [UniqueConstraint(columnNames =  ["discord_id"])]
)
class Member(
    var discord_id: String,
) : BaseModel() {
}