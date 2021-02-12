package org.ungkom.domain

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(
    name = "Messages",
)
class Messages(
    @ManyToOne
    var author: Member,

    var discord_id: String,

    @ManyToOne
    var guild: Guild,

    var body: String,

    @ManyToOne
    var parentMessage: Messages? = null

) : BaseModel() {
}