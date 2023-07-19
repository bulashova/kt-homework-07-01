package ru.netology

data class Post(
    val id: Int = 0,
    val ownerId: Int = 0,
    val fromId: Int = 0,
    val createdBy: Int = 0,
    val date: Int = 101023,
    val text: String = "text",
    val replyOwnerId: Int?,
    val replyPostId: Int?,
    val friendsOnly: Boolean = false,
    val isFavorite: Boolean = false,
    val markedAsAds: Boolean = false,
    val canEdit: Boolean = false,
    val canDelete: Boolean = false,

    val comments: Comments = Comments(),
    val likes: Likes = Likes(),
    val copyright: Copyright = Copyright(),
    val reposts: Reposts = Reposts(),

    val attachment: Array<Attachment> = emptyArray<Attachment>(),
)

data class Comments(
    var count: Int = 0,
    val canPost: Boolean = false,
    val groupsCanPost: Boolean = false,
    val canClose: Boolean = false,
    val canOpen: Boolean = false,
)

data class Likes(
    val count: Int = 0,
    val userLikes: Boolean = false,
    val canLike: Boolean = false,
    val canPublish: Boolean = false,
)

data class Copyright(
    val id: Int = 0,
    val link: String = "link",
    val name: String = "name",
    val type: String = "type",
)

data class Reposts(
    val count: Int = 0,
    val userReposted: Boolean = false,
)

fun main() {
    WallService.add(Post(replyOwnerId = null, replyPostId = null))
    WallService.add(Post(replyOwnerId = 1, replyPostId = 11))
    WallService.add(Post(replyOwnerId = null, replyPostId = null))
    WallService.printAll()
    println()
    if (WallService.update(
            Post(
                id = 2, text = "content", friendsOnly = true, replyOwnerId = null,
                replyPostId = null, likes = Likes(count = 10)
            )
        )
    ) {
        println("Пост обновлен")
        WallService.printAll()
    } else println("Пост не найден")

    println("")
    val photo = Attachment.PhotoAttachment(photo = Photo(id = 333))
    val audio = Attachment.AudioAttachment(audio = Audio(id = 555))
    println(WallService.add(Post(replyOwnerId = null, replyPostId = null, attachment = arrayOf(photo, audio))))

    val attachment: Attachment = Attachment.VideoAttachment(Video())
    println(attachment.type)

    println(WallService.createComment(2, Comment(text = "First comment for post 2")))
    println(WallService.createComment(2, Comment(text = "Second comment for post 2")))
    println(WallService.createComment(4, Comment(text = "First comment for post 4")))
    WallService.printAll()

    val report = WallService.createReportComment(ReportComment(commentId = 2, reason = 0))
    println(report)
}