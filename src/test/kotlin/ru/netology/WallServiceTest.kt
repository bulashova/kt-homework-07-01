package ru.netology

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.netology.Post
import ru.netology.WallService

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addExisting() {
        val result = WallService.add(Post(replyOwnerId = null, replyPostId = null)).id
        assertEquals(1, result)
    }

    @Test
    fun updateExisting() {

        WallService.add(Post(replyOwnerId = null, replyPostId = null))
        WallService.add(Post(replyOwnerId = 1, replyPostId = 11))
        WallService.add(Post(replyOwnerId = null, replyPostId = null))

        val result = WallService.update(
            Post(
                id = 2, text = "content", friendsOnly = true,
                replyOwnerId = null, replyPostId = null
            )
        )
        assertTrue(result)
    }

    @Test
    fun updateNoExisting() {

        WallService.add(Post(replyOwnerId = null, replyPostId = null))
        WallService.add(Post(replyOwnerId = 1, replyPostId = 11))
        WallService.add(Post(replyOwnerId = null, replyPostId = null))

        val result = WallService.update(
            Post(
                id = 8, text = "content", friendsOnly = true,
                replyOwnerId = null, replyPostId = null
            )
        )
        assertFalse(result)
    }

    @Test
    fun addCommentExisting() {
        WallService.add(Post(replyOwnerId = null, replyPostId = null))
        WallService.add(Post(replyOwnerId = 1, replyPostId = 11))
        WallService.add(Post(replyOwnerId = null, replyPostId = null))

        val result = (WallService.createComment(2, Comment(text = "First comment"))).id
        assertEquals(1, result)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        WallService.add(Post(replyOwnerId = null, replyPostId = null))
        WallService.add(Post(replyOwnerId = 1, replyPostId = 11))
        WallService.add(Post(replyOwnerId = null, replyPostId = null))

        WallService.createComment(5, Comment(text = "First comment"))
    }

    @Test
    fun addReportCommentExisting() {
        WallService.add(Post(replyOwnerId = null, replyPostId = null))
        WallService.add(Post(replyOwnerId = 1, replyPostId = 11))
        WallService.add(Post(replyOwnerId = null, replyPostId = null))

        WallService.createComment(2, Comment(text = "First comment for post 2"))
        WallService.createComment(2, Comment(text = "Second comment for post 2"))
        WallService.createComment(1, Comment(text = "First comment for post 1"))

        val result = WallService.createReportComment(ReportComment(commentId = 2, reason = 0))
        assertTrue(result)
    }

    @Test(expected = CommentNotFoundException::class)
    fun shouldThrowCommentNotFound() {
        WallService.add(Post(replyOwnerId = null, replyPostId = null))
        WallService.add(Post(replyOwnerId = 1, replyPostId = 11))
        WallService.add(Post(replyOwnerId = null, replyPostId = null))

        WallService.createComment(2, Comment(text = "First comment for post 2"))
        WallService.createComment(2, Comment(text = "Second comment for post 2"))
        WallService.createComment(1, Comment(text = "First comment for post 1"))

        WallService.createReportComment(ReportComment(commentId = 6, reason = 0))
    }

    @Test(expected = ReasonNotFoundException::class)
    fun shouldThrowReasonNotFound() {
        WallService.add(Post(replyOwnerId = null, replyPostId = null))
        WallService.add(Post(replyOwnerId = 1, replyPostId = 11))
        WallService.add(Post(replyOwnerId = null, replyPostId = null))

        WallService.createComment(2, Comment(text = "First comment for post 2"))
        WallService.createComment(2, Comment(text = "Second comment for post 2"))
        WallService.createComment(1, Comment(text = "First comment for post 1"))

        WallService.createReportComment(ReportComment(commentId = 2, reason = 7))
    }
}