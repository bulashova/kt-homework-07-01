package ru.netology

sealed class Attachment(val type: String) {

    data class PhotoAttachment(val photo: Photo) : Attachment("photo")
    data class VideoAttachment(val video: Video) : Attachment("video")
    data class AudioAttachment(val audio: Audio) : Attachment("audio")
    data class LinkAttachment(val link: Link) : Attachment("link")
    data class DocAttachment(val doc: Doc) : Attachment("doc")
}

data class Photo(
    val id: Int = 0,
    val ownerId: Int = 0,
    val text: String = "text",
    val width: Int = 0,
    val height: Int = 0,
)

data class Video(
    val id: Int = 0,
    val ownerId: Int = 0,
    val title: String = "title",
    val description: String = "description",
    val duration: Int = 0,
)

data class Audio(
    val id: Int = 0,
    val ownerId: Int = 0,
    val artist: String = "artist",
    val title: String = "title",
    val duration: Int = 0,
    val url: String = "url",
)

data class Link(
    val url: String = "url",
    val title: String = "title",
    val description: String = "description",
)

data class Doc(
    val id: Int = 0,
    val ownerId: Int = 0,
    val title: String = "title",
    val size: Int = 0,
    val ext: String = "ext",
    val url: String = "url",
)