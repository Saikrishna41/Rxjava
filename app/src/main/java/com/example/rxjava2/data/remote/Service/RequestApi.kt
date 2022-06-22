package com.example.rxjava2.data.remote.Service

import com.example.rxjava2.data.entities.Comment
import com.example.rxjava2.data.entities.Post
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestApi {

    @GET("posts")
    fun getPosts(): Observable<List<Post>>

    @GET("posts/{id}/comments")
    fun getcomments(@Path("id") id: Int): Observable<List<Comment>>
}