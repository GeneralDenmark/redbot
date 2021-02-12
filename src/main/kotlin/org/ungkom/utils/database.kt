package org.ungkom.utils

import io.ebean.DB
import io.ebean.Database

class Database {
    public var database: Database = DB.getDefault()
}