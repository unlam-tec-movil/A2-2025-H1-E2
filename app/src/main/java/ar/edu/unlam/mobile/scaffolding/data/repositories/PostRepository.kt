package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.model.Post

interface PostRepository {
    fun getPosts(): MutableList<Post>
}