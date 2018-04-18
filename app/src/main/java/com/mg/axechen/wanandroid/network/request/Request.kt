package network.request

import com.mg.axechen.wanandroid.javabean.*
import io.reactivex.Observable
import network.response.Response
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by AxeChen on 2018/3/19.
 *
 * 请求接口封装
 */
interface Request {
    companion object {
        var HOST: String = "http://www.wanandroid.com/"
    }

    /**
     * 获取主页文章
     */
    @GET("/article/list/{page}/json")
    fun getHomeList(
            @Path("page") page: Int
    ): Observable<Response<HomeListBean>>

    /**
     * 获取首页banner数据
     */
    @GET("banner/json")
    fun getBanner(): Observable<Response<List<BannerBean>>>


    /**
     * 用户登陆
     */
    @POST("user/login")
    fun userLogin(
            @Query("username") userName: String,
            @Query("password") password: String
    ): Observable<Response<JSONObject>>

    /**
     * 用户注册
     */
    fun userRegister(
            @Query("username") userName: String,
            @Query("password") password: String,
            @Query("repassword") rePassword: String
    ): Observable<Response<JSONObject>>

    /**
     * 获取知识树
     */
    @GET("tree/json")
    fun getKnowledgeTreeList(): Observable<Response<List<TreeBean>>>


    /**
     * 获取项目树
     */
    @GET("project/tree/json")
    fun getProjectTree(): Observable<Response<List<TreeBean>>>


    /**
     * 根据项目分类id获取项目列表
     */
    @GET("project/list/{page}/json")
    fun getProjectListByCid(@Path("page") page: Int,
                            @Query("cid") cid: Int): Observable<Response<ProjectListBean>>

    /**
     * 获取知识体系的文章
     */
    @GET("article/list/{page}/json")
    fun getKnowledgeList(@Path("page") page: Int,
                         @Query("cid") cid: Int): Observable<Response<ProjectListBean>>

    /**
     * 获取热词
     */
    @GET("hotkey/json")
    fun getRecommendSearchTag(): Observable<Response<MutableList<SearchTag>>>

    /**
     * 搜索
     */
    @POST("article/query/{page}/json")
    fun search(@Path("page") page: Int,
               @Query("k") text: String): Observable<Response<ProjectListBean>>

    /**
     * 网址导航
     */
    @GET("navi/json")
    fun getNaviJson(): Observable<Response<MutableList<NaviBean>>>

}