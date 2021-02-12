package domain

import io.ebean.DB
import org.ungkom.domain.Guild
import org.junit.Test

class GuildTest {

    @Test
    fun insert_update_create() {
        val guild = Guild("123")
        DB.save(guild)


        var found = DB.find(Guild::class.java, 1)
        DB.delete(found)
    }

}