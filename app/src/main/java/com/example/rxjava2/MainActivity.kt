package com.example.rxjava2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjava2.data.entities.Comment
import com.example.rxjava2.data.entities.Post
import com.example.rxjava2.data.remote.ServiceGenerator
import com.example.rxjava2.ui.PostsAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.functions.Predicate
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var mAdapter: PostsAdapter
    private lateinit var recyclerView: RecyclerView
    private val disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById<RecyclerView>(R.id.rv)
        initiRecyclerView()
        getPostsObservable().subscribeOn(Schedulers.io())
            .flatMap(object : Function<Post, ObservableSource<Post>> {
                override fun apply(t: Post): ObservableSource<Post> {
                    return getCommentsObservable(t)
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Post> {
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onNext(t: Post) {
                    updatePost(t)
                }

                override fun onError(e: Throwable) {
                    Log.e("TAGS", "onError 1");

                }

                override fun onComplete() {
                    Log.e("TAGS", "onComplete 1");
                }

            })
    }


//        val taskObservable =
//            Observable.fromIterable(TasksList.createLists()).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//
//        taskObservable.subscribe(object : Observer<Tasks>{
//            override fun onSubscribe(d: Disposable) {
//               Log.i("TAGS", "onSubscribe called")
//            }
//
//            override fun onNext(t: Tasks) {
//                Log.i("TAGS", "onNext called")
//            }
//
//            override fun onError(e: Throwable) {
//                Log.i("TAGS", "onError called")
//            }
//
//            override fun onComplete() {
//                Log.i("TAGS", "onComplete called")
//            }
//        })

    fun getPostsObservable(): Observable<Post> {
        val sg = ServiceGenerator.getRequestApi()?.getPosts()?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.flatMap(object : Function<List<Post>, ObservableSource<Post>> {
                override fun apply(t: List<Post>): ObservableSource<Post> {
                    mAdapter.setPosts(t as ArrayList<Post> /* = java.util.ArrayList<com.example.rxjava2.data.entities.Post> */)
                    return Observable.fromIterable(t).subscribeOn(Schedulers.io())
                }
            })
        return sg!!
    }

    fun getCommentsObservable(post: Post): Observable<Post> {

        val sg =
            ServiceGenerator.getRequestApi()?.getcomments(post.id)
                ?.map(object : Function<List<Comment>, Post> {
                    override fun apply(t: List<Comment>): Post {
                        try {
                            val delay: Long = (Random.nextLong(5) + 1) * 1000
                            Thread.sleep(delay)
                            post?.comment = t

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        return post
                    }
                })?.subscribeOn(Schedulers.io())
        return sg!!
    }

    fun updatePost(p: Post) {
        Observable.fromIterable(mAdapter.getPosts()).filter(object : Predicate<Post> {
            override fun test(t: Post): Boolean {
                return p.id == t.id

            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(object  : Observer<Post> {
            override fun onSubscribe(d: Disposable) {
                disposable.add(d)
            }

            override fun onNext(t: Post) {
                mAdapter.update(t)

            }

            override fun onError(e: Throwable) {
                Log.e("TAGS", "onError");
            }

            override fun onComplete() {
                Log.e("TAGS", "onComplete: ");
            }


        })
    }

    fun initiRecyclerView() {
        mAdapter = PostsAdapter()
        recyclerView.adapter = mAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}