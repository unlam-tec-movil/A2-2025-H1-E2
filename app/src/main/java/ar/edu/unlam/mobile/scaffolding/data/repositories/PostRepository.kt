package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post

interface PostRepository {
    fun getPosts(): MutableList<Post>
}