package com.ikhsan.storyapp.core.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ikhsan.storyapp.core.data.response.ListStory

@Dao
interface StoryDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertQuote(quote: List<ListStory>)

   @Query("SELECT * FROM stories")
   fun getAllStory(): PagingSource<Int, ListStory>
 
   @Query("DELETE FROM stories")
   suspend fun deleteAll()
}