
WanAndroid主页：[http://www.wanandroid.com/index](http://www.wanandroid.com/index)   


非常感谢张鸿洋老师提供的开放api！向开源者致敬！

#### 1、WanAndroid的整体模块和部分效果图

##### 1.1、项目架构
Kotlin语言开发，采用MVP架构。

**刚刚学完Kotlin基础，但是并不是非常好的应用在项目中，所以有些Kotlin的特性使用起来不是非常顺畅。本软件的源码还需要不断改进！**

##### 1.2、整体模块：  
* 首页：推荐最新的博客。
* 知识体系：对安卓知识体系做整理。
* 项目：在WanAndroid上发布的项目。
* 用户界面：用户的信息和其他一些辅助功能。
* 收藏体系：依靠Cookie持久化，实现对文章的收藏和展示。
* 用户体系：登录、注册、Cookie持久化。
* 文章详情：展示文章详情。
* 搜索：输入搜索、搜索推荐、历史搜索等等。
* 网址导航：展示常用的开发网站。

##### 1.3、开放API地址
[http://www.wanandroid.com/blog/show/2](http://www.wanandroid.com/blog/show/2)


##### 1.4、部分效果图
整体采用MD设计体验。 
![部分效果图](https://upload-images.jianshu.io/upload_images/1930161-6e5e5164c7128f8b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 2、用到的优秀开源框架
[Retrofit2+RxJava2：搭建的网络请求框架](https://www.jianshu.com/p/2e8b400909b7)   
[glide：图片请求框架](https://github.com/bumptech/glide)  
[ImmersionBar：沉浸式状态栏](https://github.com/gyf-dev/ImmersionBar)
[MagicaSakura：BIlibili开源的换肤框架](https://github.com/Bilibili/MagicaSakura)  
[AgentWeb：webView](https://github.com/Justson/AgentWeb)  
[BaseRecyclerViewAdapter：非常便捷的RecyclerView适配器](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)

#### 3、开发中遇到的问题
开发中总会遇到不少难题，分享一些自己遇到的问题吧。
##### 3.1、换肤框架
[MagicaSakura：BIlibili开源的换肤框架](https://github.com/Bilibili/MagicaSakura)  总体来说对代码入侵性是非常大的，因为需要做到换肤效果，就需要使用到MagicaSakura封装的控件。如果项目已经非常庞大了才考虑换肤，用这个框架是比较耗时的。
![image.png](https://upload-images.jianshu.io/upload_images/1930161-cb12049cdb379eda.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)




##### 3.2、Cookies持久化
在项目中，需要用到Cookies持久化。Retrofit中可以使用OkHttp的Interceptor来做。

##### 3.3、Response的data为null的情况
当接口返回errorCode为0但是data为null的时，Json解析时会直接抛出NullPointException。最后通过自定义GsonConverterFactory解决，但是并不确定这是否为最优解。


#### 4、图标的来源
不是设计师，但是需要图标怎么办？   
[http://www.iconfont.cn/](http://www.iconfont.cn/)

#### 5、源码地址
[https://github.com/AxeChen/WanAndroid](https://github.com/AxeChen/WanAndroid) (老铁，留下你的star再走吧)

#### 6、APK下载
酷安下载地址：   
[https://www.coolapk.com/apk/com.mg.axechen.wanandroid](https://www.coolapk.com/apk/com.mg.axechen.wanandroid)   


扫码下载：
![酷安下载](https://upload-images.jianshu.io/upload_images/1930161-90c155764bcbd48f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)   



蒲公英地址：[https://www.pgyer.com/pxix](https://www.pgyer.com/pxix)   
扫码下载：
![蒲公英下载](https://upload-images.jianshu.io/upload_images/1930161-6ca04c7fe51958e6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


如果发现bug，希望提出issue。
