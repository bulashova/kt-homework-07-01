package ru.netology

class PostNotFoundException(message: String) : RuntimeException(message)

class CommentNotFoundException(message: String) : RuntimeException(message)

class ReasonNotFoundException(message: String) : RuntimeException(message)

data class Comment(
    val id: Int = 0,
    val fromId: Int = 0,
    val date: Int = 101023,
    val text: String = "text",
)

data class ReportComment(
    val ownerId: Int = 0,
    val commentId: Int = 0,
    val reason: Int = -1,
)

object WallService {
    private var posts = emptyArray<Post>()
    private var lastId = 0
    private var comments = emptyArray<Comment>()
    private var commentId = 0
    private var reportComments: Array<ReportComment> = emptyArray<ReportComment>()

    fun add(post: Post): Post {
        posts += post.copy(
            id = ++lastId, comments = post.comments.copy(), likes = post.likes.copy(),
            copyright = post.copyright.copy(), reposts = post.reposts.copy()
        )
        return posts.last()
    }

    fun update(postUpdate: Post): Boolean {
        for ((index, post) in posts.withIndex()) {
            if (postUpdate.id == post.id) {
                posts[index] = postUpdate.copy(
                    comments = postUpdate.comments.copy(), likes = postUpdate.likes.copy(),
                    copyright = postUpdate.copyright.copy(), reposts = postUpdate.reposts.copy()
                )
                return true
            }
        }
        return false
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        for (post in posts) {
            if (post.id == postId) {
                comments += comment.copy(id = ++commentId)
                post.comments.count++
                return comments.last()
            }
        }
        throw PostNotFoundException("No post with id $postId")
    }

    fun createReportComment(reportComment: ReportComment): Boolean {
        for (comment in comments) {
            if (comment.id == reportComment.commentId) {
                if (reportComment.reason in 0..8 && reportComment.reason != 7) {
                    reportComments += reportComment.copy()
                    return true
                }
                throw ReasonNotFoundException("No reason ${reportComment.reason}")
            }
        }
        throw CommentNotFoundException("No comment with id ${reportComment.commentId}")
    }

    fun printAll() {
        for (post in posts) {
            println(post)
        }
    }

    fun clear() {
        posts = emptyArray()
        lastId = 0
    }
}