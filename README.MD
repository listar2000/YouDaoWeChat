# 微信小程序DEMO-有道API查词小程序

---

**简介:** 这是一个适合初学者入门的，实现简单而规范的微信小程序项目。其大致分为两个部分组成，第一个部分实现前端 `WXML+WXSS+JS` ,第二部分则是一个简单的后台，由 `Java` 先调用有道API的接口然后再返回 `Json` 数据给微信小程序。如果对于 `Java` 不熟悉可以放弃第二部分。

##项目组成
`app.js/app.json/app.wxss/project.config.json` 为重要的全局文件，请不要更改。
请将上面的文件同 `pages/utils` 文件夹一并放入新建的微信小程序项目中。编译运行即可!
<font color="red">**这一步效果如下：**</font>

![此处输入图片的描述][1]

##调用Java Servlet的API
**原理：**项目中 `Java_resource` 文件夹中的 `YouDaoAPIGetter.java` 类封装了从有道词典返回Json数据的方法。而这一方法在微信小程序的 `app.js` 中被调用（使用了微信提供的 `wx.request` 方法）。
```javascript
App({
  getExpressInfo: function (nu, cb) {
    wx.request({
      url: 'http://localhost:8080/YD/youDaoServlet?word=' + nu,
      data: {
        x: '',
        y: ''
      },
      success: function(res) {
        console.log(res.data);
        cb(res.data);
      }
    })
  },
  globalData: {
    userInfo: null
  }
})
```
其中回调函数（callback）cb 在 `index.js` 中被运用于按钮的点击事件：
```javascript
btnClick: function(e) {
    var thisExpress = this;
    app.getExpressInfo(this.data.inputInfo, function(data) {
      console.log(data);
      thisExpress.setData({expressInfo: data});
    }) 
}
```
##Java部分设置
Java部分设置较为繁琐，需要分为几部完成：
1. 注册网易[有道智云][2]的账号（免费），注册自然语言翻译应用获得APPKEY和APPSECRET。
2. 在Java文件中进行设置：
```java
static String sign;
static String url = "https://openapi.youdao.com/api";
static String appKey = "YOUR APPKEY"; //此处填写APPKEY
static String from = "auto";
static String to = "EN";
static String salt = String.valueOf(System.currentTimeMillis());
static String appSecret = "YOUR APPSECRET"; //此处填写APPSECRET
```
3.需要启动TOMCAT，并且Deploy你的Java Web项目到Web服务器中。端口号需要为8080，应用Application Context设置为YD，此外需要额外编写一个Servlet进行操作。

  [1]: https://github.com/listar2000/java_Projects/blob/master/blogPic/miniprog2.png
  [2]: http://ai.youdao.com/docs/doc-trans-api.s#p02
