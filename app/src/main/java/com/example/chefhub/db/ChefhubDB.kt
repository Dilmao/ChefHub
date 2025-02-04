package com.example.chefhub.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chefhub.db.dao.*
import com.example.chefhub.db.data.*

@Database(
    entities = [
        Users::class,
        Recipes::class,
        Categories::class,
        Comments::class,
        Ratings::class,
        Favorites::class,
        Follows::class
    ],
    version = 6,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class ChefhubDB: RoomDatabase() {
    abstract val usersDao: UsersDao
    abstract val recipesDao: RecipesDao
    abstract val categoriesDao: CategoriesDao
    abstract val commentsDao: CommentsDao
    abstract val ratingsDao: RatingsDao
    abstract val favoritesDao: FavoritesDao
    abstract val followsDao: FollowsDao

    companion object {
        @Volatile
        private var INSTANCE: ChefhubDB? = null

        fun getDatabase(context: Context): ChefhubDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChefhubDB::class.java,
                    "chefhub_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}